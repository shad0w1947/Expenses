package com.example.shad0w.todolist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class viewholder extends RecyclerView.ViewHolder {
    TextView description;
    TextView title;
    TextView date;
    TextView time;
    CheckBox checkBox;
    View view;
    public viewholder(@NonNull View itemView) {
        super(itemView);
        view=itemView.getRootView();
        //description = itemView.findViewById(R.id.descripton);
         title = itemView.findViewById(R.id.title);
         date=itemView.findViewById(R.id.Tdate);
         time=itemView.findViewById(R.id.Ttime);
         checkBox=itemView.findViewById(R.id.checkbox);
    }
}
