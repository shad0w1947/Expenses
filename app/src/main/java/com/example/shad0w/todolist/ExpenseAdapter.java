package com.example.shad0w.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpenseAdapter extends ArrayAdapter {

    ArrayList<Expenses> items;
    LayoutInflater inflater;

    public ExpenseAdapter(@NonNull Context context, ArrayList<Expenses> item) {
        super(context, 0, item);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = item;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View output = inflater.inflate(R.layout.row_layout, parent, false);
        Expenses expenses = items.get(position);
        TextView name = output.findViewById(R.id.name);
        TextView price = output.findViewById(R.id.price);
        TextView check = output.findViewById(R.id.check);
        name.setText(expenses.getName());
        price.setText(expenses.getPrice() + "");
        check.setText(expenses.isCheck() + "");
        return output;
    }
}
