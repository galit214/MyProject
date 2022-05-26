package com.example.myproject;

import android.media.Image;

public class Place {
    public String name;
    public String description;
    public Image image;
    public String address;
    public String pid;


    public Place(){}

    public Place(String name, String description, Image image, String addres, String pid) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.address = addres;
        this.pid = pid;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

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
}
