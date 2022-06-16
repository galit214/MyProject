package com.example.myproject;

public class Place {
    public String name;
    public String description;
    public String imageUrl;
    public String address;
    public String pid;
    public String phone;
    public String token;

    //todo add three attributes( phone,token)


    public Place(){}

    public Place(String name, String description, String placeImage, String addres, String pid, String phone,String token) {
        this.name = name;
        this.description = description;
        this.imageUrl =placeImage;
        this.address = addres;
        this.pid = pid;
        this.phone=phone;
        this.token=token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {return imageUrl;}

    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
