package com.example.shad0w.todolist;

public class Expenses {
    private String description;
    private String title;

    Expenses(String title, String description) {
        this.title = title;
        this.description = description;

    }

    public void setDescription(String name) {
        this.description = name;
    }

    public void setTitle(String price) {
        this.title = price;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
