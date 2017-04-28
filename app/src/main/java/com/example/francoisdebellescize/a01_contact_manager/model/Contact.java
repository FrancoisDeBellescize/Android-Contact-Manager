package com.example.francoisdebellescize.a01_contact_manager.model;

public class Contact {

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;

    public Contact(){}

    @Override
    public String toString() {
        return first_name + " " + last_name;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getFirstName(){
        return this.first_name;
    }

    public void setFirstName(String first_name){
        this.first_name = first_name;
    }

    public String getLastName(){
        return this.last_name;
    }

    public void setLastName(String last_name){
        this.last_name = last_name;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

}
