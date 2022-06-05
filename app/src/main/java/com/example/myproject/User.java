package com.example.myproject;

public class User {
    private String Name;
    private String Email;
    private String Password;
    private String Type;
    private String uid;
    private String imageUrl;

    public User(){}

    public User(String name, String email, String password,String Type , String uid, String imageUrl) {
        this.Type=Type;
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.uid=uid;
        this.imageUrl=imageUrl;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

