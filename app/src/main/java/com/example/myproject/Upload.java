package com.example.myproject;

public class Upload {
    private String imageUrl;
    private String pid;

        public Upload(){
        }

    public Upload(String imageUrl, String pid) {
        this.imageUrl = imageUrl;
        this.pid = pid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
