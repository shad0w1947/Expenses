package com.example.shad0w.todolist;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ArrayList<Expenses> expenses=new ArrayList<>();
    ExpenseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView=(ListView)findViewById(R.id.listview);
        for(int i=0;i<50;i++)
        {
            String a="List "+i;
            Expenses temp=new Expenses(a,i,true);
            expenses.add(temp);
        }
        //ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,expenses);
      adapter =new ExpenseAdapter(this,expenses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Expenses temp=expenses.get(i);
        //Toast.makeText(this,temp.getName()+" "+temp.getPrice()+" "+temp.isCheck(), Toast.LENGTH_SHORT).show();
       
        expenses.remove(i);
        adapter.notifyDataSetChanged();
        //TextView t=(TextView)view;
        //t.setText("clicked");
       // expenses.set(i,"clicked");
    }
}
