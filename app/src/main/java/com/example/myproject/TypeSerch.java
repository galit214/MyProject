package com.example.myproject;

public class TypeSerch {
    String type;
    String email;

    public TypeSerch(){
    }

    public TypeSerch(String type, String email) {
        this.type = type;
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
