package com.example.francoisdebellescize.a01_contact_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.francoisdebellescize.a01_contact_manager.model.Contact;


public class EditContact extends AppCompatActivity {
    private DBOpenHelper dbOpenHelper;
    private Contact contact;
    private Button saveButton;
    private EditText ed_firstname;
    private EditText ed_lastname;
    private EditText ed_email;
    private EditText ed_phone;
    private boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        dbOpenHelper = new DBOpenHelper(this);

        ed_firstname = (EditText) findViewById(R.id.firstName);
        ed_lastname = (EditText) findViewById(R.id.lastName);
        ed_email = (EditText) findViewById(R.id.email);
        ed_phone = (EditText) findViewById(R.id.phone);
        saveButton = (Button) findViewById(R.id.saveButton);

        setContact();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_firstname.getText().toString().matches("") ||
                        ed_lastname.getText().toString().matches("") ||
                        ed_email.getText().toString().matches("") ||
                        ed_phone.getText().toString().matches("")){
                    Toast.makeText(EditContact.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                contact.setFirstName(ed_firstname.getText().toString());
                contact.setLastName(ed_lastname.getText().toString());
                contact.setEmail(ed_email.getText().toString());
                contact.setPhone(ed_phone.getText().toString());

                Contact tmp = dbOpenHelper.getContact(contact.getFirstName(), contact.getLastName());
                if (tmp != null && tmp.getId() != contact.getId()){
                    Toast.makeText(EditContact.this, "This contact already exist", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edit){
                    dbOpenHelper.updateContact(contact);
                    Toast.makeText(EditContact.this, "Contact updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbOpenHelper.addContact(contact);
                    Toast.makeText(EditContact.this, "New contact added", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(EditContact.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    void setContact(){
        Bundle b = getIntent().getExtras();
        if(b != null){
            int contact_id = b.getInt("contact_id");
            contact = dbOpenHelper.getContact(contact_id);
            if (contact != null){
                edit = true;
                ed_firstname.setText(contact.getFirstName());
                ed_lastname.setText(contact.getLastName());
                ed_email.setText(contact.getEmail());
                ed_phone.setText(contact.getPhone());
                setTitle("Edit Contact");
            }
        }

        if (!edit){
            setTitle("New Contact");
            contact = new Contact();
        }
    }
}
