# Contact Manager

## MainActivity

### Template

For the MainActivity Template, i'm using the default `RelativeLayout`.

![MainActivity](https://s21.postimg.org/7bdqqpjrb/Main_Activity.png)

A `FloatingActionButton` to add a Contact. This kind of button is used in a lot of apps and in general, it's used to create or add something like emails for example.

A `ListView` to display all the contacts. This widget is a good option to display a lot of informations/objects and we can handle a click on each lines.

![ContextMenu]()


### Variables

I'm using four class variables in the main Activity :

> DBOpenHelper tdb;

The `DBOpenHelper` is the class to get the data from the SqlLite.

> List<Contact> contacts;

The contacts list is all the contacts get from the `DBOpenHelper`.

> ListView listView;

The `ListView` is the design Widget to display the contacts list.

> FloatingActionButton fab;

The `FloatingActionButton` is the button to add a new contact.

### Functions

> listView.setOnItemClickListener

Is the function to handle a simple click on a contact in the list.
It will start the `ViewActivity` and send the as parameter the contact id to display it.

> listView.setOnCreateContextMenuListener

Is the function to handle a long click on a contact in the list. Before that i had to use `registerForContextMenu(listView)` to be able to set the setOnCreateContextMenuListener on the ListView.

It will open a `contextMenu` with the following actions:

      menu.add(0, 0, 0, "Edit");
      menu.add(0, 1, 1, "Delete");

Edit menu with start the activity `EditActivity` and send as parameter the contact_id.

The delete menu will delete the contact from the database and refresh the `ListView`.

> fab.setOnClickListener(new View.OnClickListener()

This is used to handle the click on the `FloatingActionButton`. It will start the `EditActivity` without sending a parameter.

## ViewActivity

### Template

For the `ViewActivity`, i'm using only four `TextView` to display informations.

![ViewActivity](https://s21.postimg.org/quic02iiv/View_Activity.png)

### Variables

The `DBOpenHelper` to request the contact in the database :

> DBOpenHelper dbOpenHelper

The contact to display :

> Contact contact

The `TextViews` to display contact's informations :

> TextView tv_firstname

> TextView tv_lastname

> TextView tv_email

> TextView tv_phone

### Functions

I get the contact id send as parameter to this activity :

        Bundle b = getIntent().getExtras();
        int contact_id = b.getInt("contact_id");

I get the contact from the database using it id :

        contact = dbOpenHelper.getContact(contact_id);

And set the `TextViews` :

        tv_firstname.setText("First Name : " + contact.getFirstName());
        tv_lastname.setText("Last Name : " + contact.getLastName());
        tv_email.setText("Email : " + contact.getEmail());
        tv_phone.setText("Phone : " + contact.getPhone());

## EditActivity

### Template

I'm using four `EditTexts` in the `EditActivity` to be able to modify the informations of the contact. A simply button Save to save theses informations.

![EditActivity](https://s21.postimg.org/n8cinfc5j/Edit_Activity.png)

### Variables

The `DBOpenHelper` to request the contact in the database :

> DBOpenHelper dbOpenHelper

The contact to edit :

> Contact contact

The button to save the current informations and the EditTexts :
> Button saveButton

> EditText ed_firstname

> EditText ed_lastname

> EditText ed_email

> EditText ed_phone

A boolean to know if we're editing a contact or creating one

> boolean edit = false;

### Functions

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

The `setContact` function will try to get the contact if if there is one passed as parameter to this activity. If yes, it will set the values of the contact to the EditTexts and set the boolean edit at true.

> saveButton.setOnClickListener

It will handle a click on the saveButton.

First, i check if all `EditText` are not empty :

      if (ed_firstname.getText().toString().matches("") ||
              ed_lastname.getText().toString().matches("") ||
              ed_email.getText().toString().matches("") ||
              ed_phone.getText().toString().matches("")){
          Toast.makeText(EditContact.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
          return;
      }

Then i check if a contact already exist with this FirstName and LastName even if we're not actually editing it :

      Contact tmp = dbOpenHelper.getContact(contact.getFirstName(), contact.getLastName());
      if (tmp != null && tmp.getId() != contact.getId()){
          Toast.makeText(EditContact.this, "This contact already exist", Toast.LENGTH_SHORT).show();
          return;
      }

After that i return to the MainActivity.

## DBOpenHelper

### Functions

> void onCreate(SQLiteDatabase db)

It will create the database when we create an Object `DBOpenHelper`.

> void addContact(Contact contact)

It will create a `User` using a `Contact` object.

> Contact getContact(int id)

It will request a contact from the database using an id and return it.

> Contact getContact(String first_name, String last_name)

It will request a contact from the database using the FirstName and the LastName.

> List<Contact> getAllContacts()

It will return a List of `Contact` with all contacts in the database.

> int updateContact(Contact contact)

It will update a contact using an object `Contact`.

> void deleteContact(Contact contact)

It will delete a contact using an object `Contact`.
