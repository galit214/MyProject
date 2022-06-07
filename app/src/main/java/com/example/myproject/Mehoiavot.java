package com.example.myproject;

public class Mehoiavot {
    private double hours;
    private Boolean approved;
    private String date;
    private String description;
    private String pid;
    private String uid;


    public Mehoiavot(){
        //4 fire base
    }

    public Mehoiavot(double hours, String description, String pid,String date,boolean approved,String uid) {
        this.hours = hours;
        this.description = description;
        this.pid = pid;
        this.date=date;
        this.approved=approved;
        this.uid=uid;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getHours() {
        return hours;
    }

    public String getDescription() {
        return description;
    }

    public String getPid() {
        return pid;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
