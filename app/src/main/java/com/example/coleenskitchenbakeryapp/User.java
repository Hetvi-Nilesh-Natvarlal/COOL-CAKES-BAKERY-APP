package com.example.coleenskitchenbakeryapp;

public class User
{
    String Name,Id,EmailId;

    public User() {
    }

    public User(String name, String id,  String emailId) {
        Name = name;
        Id = id;
        EmailId = emailId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }
}
