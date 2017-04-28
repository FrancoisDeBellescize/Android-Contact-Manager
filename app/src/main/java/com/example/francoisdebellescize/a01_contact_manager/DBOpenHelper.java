package com.example.francoisdebellescize.a01_contact_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.francoisdebellescize.a01_contact_manager.model.Contact;

import java.util.LinkedList;
import java.util.List;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    private static final String table_name = "contact";

    private static final String[][] table_fields = {
            {"id", "integer primary key autoincrement"},
            {"first_name", "string"},
            {"last_name", "string"},
            {"email", "string"},
            {"phone", "string"}
    };

    private static final String[] COLUMNS = {"id", "first_name", "last_name", "email", "phone"};

    public DBOpenHelper(Context context) {
        super(context, table_name, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    public void onUpgrade(SQLiteDatabase db, int version_old, int version_new) {
        db.execSQL("drop table " + table_name);
        createTable(db);
    }

    private void createTable(SQLiteDatabase db) {
        String sql = "create table " + table_name + " (";

        for (int i = 0; i < table_fields.length; ++i) {
            if (i > 0)
                sql += ", ";
            sql += table_fields[i][0] + " " + table_fields[i][1];
        }
        sql += ")";

        Log.d("SQL", sql);
        db.execSQL(sql);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("first_name", contact.getFirstName());
        values.put("last_name", contact.getLastName());
        values.put("email", contact.getEmail());
        values.put("phone", contact.getPhone());
        db.insert(table_name, null, values);
        db.close();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(table_name, COLUMNS, " id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        Contact contact = new Contact();
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setFirstName(cursor.getString(1));
        contact.setLastName(cursor.getString(2));
        contact.setEmail(cursor.getString(3));
        contact.setPhone(cursor.getString(4));

        return contact;
    }

    public Contact getContact(String first_name, String last_name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(table_name, COLUMNS, " first_name = ? AND last_name = ?", new String[]{first_name, last_name}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        try {
            if (cursor.moveToFirst()) {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contact.setPhone(cursor.getString(4));

                return contact;
            } else {

            }
        } catch (Exception exception) {
        }
        return null;
    }


    public List<Contact> getAllContacts() {
        List<Contact> contacts = new LinkedList<Contact>();

        String query = "SELECT  * FROM " + table_name;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Contact contact = null;
        if (cursor.moveToFirst()) {
            do {
                contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contact.setEmail(cursor.getString(4));

                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        return contacts;
    }

    public int updateContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("first_name", contact.getFirstName());
        values.put("last_name", contact.getLastName());
        values.put("email", contact.getEmail());
        values.put("phone", contact.getPhone());

        int i = db.update(table_name, values, "ID = ?", new String[]{String.valueOf(contact.getId())});

        db.close();

        return i;
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name, "ID = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }
}