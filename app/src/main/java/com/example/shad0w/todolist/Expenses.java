package com.example.shad0w.todolist;

public class Expenses {
    private String description;
    private String title;
    private long id;
    private String time;
    private String date;

    Expenses(String title, String description, long id, String date, String time) {
        this.title = title;
        this.id = id;
        this.date = date;
        this.time = time;
        this.description = description;

    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDescription(String name) {
        this.description = name;
    }

    public void setTitle(String price) {
        this.title = price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }
}
