package com.example.shad0w.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

public class Expenses  {
    private String name="";
    private int price;
    private boolean check;
    Expenses(String name,int Price,boolean check)
    {
        this.name=name;
        this.price=Price;
        this.check=check;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public void setCheck(boolean check){
        this.check=check;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isCheck() {
        return check;
    }
}
