package com.example.submission5.notification;

public class NotifItem {
    private int id;
    private String title;
    private String messsage;

    public NotifItem(int id, String title, String messsage) {
        this.id = id;
        this.title = title;
        this.messsage = messsage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
