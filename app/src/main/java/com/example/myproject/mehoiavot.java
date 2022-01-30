package com.example.myproject;

public class mehoiavot {
    public String date;
    public double hours;
    public String studentId;
    public String mid;

    public mehoiavot() {
    }

    public mehoiavot( String date, double hours, String studentId, String mid) {
        this.date = date;
        this.hours = hours;
        this.studentId = studentId;
        this.mid = mid;
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

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}