package com.example.shad0w.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<viewholder> {

    ArrayList<Expenses> items;
    LayoutInflater inflater;
    itemclicklistner itemclicklistner;
    LongItemclick longItemclick;
    Checkbutton checkbutton;

    public ExpenseAdapter(@NonNull Context context, ArrayList<Expenses> item,itemclicklistner itemclicklistner,LongItemclick longItemclick,Checkbutton checkbutton) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemclicklistner=itemclicklistner;
        this.longItemclick=longItemclick;
        this.items = item;
        this.checkbutton=checkbutton;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View output=inflater.inflate(R.layout.row_layout,viewGroup,false);
        return new viewholder(output);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder v,final int i) {

        final Expenses expenses = items.get(i);
        v.title.setText(expenses.getTitle());
       // v.description.setText(expenses.getDescription());
        v.time.setText(expenses.getTime());
        v.date.setText(expenses.getDate());
        v.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemclicklistner.itemclick(view,i);
            }
        });
        v.checkBox.setChecked(false);
        v.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longItemclick.longclick(view,i);
                return true;
            }
        });
        v.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkbutton.buttonclick(view,i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

}