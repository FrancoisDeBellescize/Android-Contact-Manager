package com.example.francoisdebellescize.a01_contact_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.francoisdebellescize.a01_contact_manager.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton fab;
    private DBOpenHelper tdb;
    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tdb = new DBOpenHelper(this);
        listView = (ListView) findViewById(R.id.listview);
        registerForContextMenu(listView);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        loadContact();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterview, View view, int pos, long id) {
                Contact tmp = (Contact) listView.getAdapter().getItem(pos);

                Intent intent = new Intent(MainActivity.this, ViewContact.class);
                intent.putExtra("contact_id", tmp.getId());
                startActivity(intent);
            }
        });

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Actions");
                menu.add(0, 0, 0, "Edit");
                menu.add(0, 1, 1, "Delete");
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditContact.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Contact tmp = (Contact) listView.getAdapter().getItem(info.position);
        if (item.getItemId() == 0) {
            Intent intent = new Intent(MainActivity.this, EditContact.class);
            intent.putExtra("contact_id", tmp.getId());
            startActivity(intent);
            return true;
        } else if (item.getItemId() == 1) {
            tdb.deleteContact(tmp);
            loadContact();
            Toast.makeText(this, "Contact deleted", Toast.LENGTH_SHORT).show();
        }
        else
            return false;
        return true;
    }

    private void loadContact() {
        contacts = tdb.getAllContacts();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts);
        listView.setAdapter(adapter);
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
