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
        View output=convertView;
        if(output==null)
        {
            output=inflater.inflate(R.layout.row_layout,parent,false);
            TextView name = output.findViewById(R.id.descripton);
            TextView price = output.findViewById(R.id.title);
            TextView date=output.findViewById(R.id.Tdate);
            TextView time=output.findViewById(R.id.Ttime);
            viewholder v=new viewholder();
            v.amount=price;
            v.name=name;
            v.date=date;
            v.time=time;
            output.setTag(v);
        }
        Expenses expenses = items.get(position);
        viewholder v=(viewholder)output.getTag();
       // TextView check = output.findViewById(R.id.check);
        v.amount.setText(expenses.getTitle());
        v.name.setText(expenses.getDescription());
        v.time.setText(expenses.getTime());
        v.date.setText(expenses.getDate());
        //check.setText(expenses.isCheck() + "");
        return output;
    }
}