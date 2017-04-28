package com.example.francoisdebellescize.a01_contact_manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.francoisdebellescize.a01_contact_manager.model.Contact;

public class ViewContact extends AppCompatActivity {
    private DBOpenHelper dbOpenHelper;
    private Contact contact;

    private TextView tv_firstname;
    private TextView tv_lastname;
    private TextView tv_email;
    private TextView tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        dbOpenHelper = new DBOpenHelper(this);

        tv_firstname = (TextView) findViewById(R.id.tv_firstname);
        tv_lastname = (TextView) findViewById(R.id.tv_lastname);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_phone = (TextView) findViewById(R.id.tv_phone);

        Bundle b = getIntent().getExtras();
        int contact_id = b.getInt("contact_id");

        contact = dbOpenHelper.getContact(contact_id);

        tv_firstname.setText("First Name : " + contact.getFirstName());
        tv_lastname.setText("Last Name : " + contact.getLastName());
        tv_email.setText("Email : " + contact.getEmail());
        tv_phone.setText("Phone : " + contact.getPhone());
    }
}
