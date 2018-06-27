package com.example.shad0w.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Expenses> expenses = new ArrayList<>();
    ExpenseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listview);
       /* for(int i=0;i<500000;i++)
        {
            String a="List "+i;
            Expenses temp=new Expenses(a,i,true);
            expenses.add(temp);
        }*/
        //ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,expenses);
        adapter = new ExpenseAdapter(this, expenses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, Add_Expenses.class);
        startActivityForResult(intent, 1);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != 2)
            return;
        String name = data.getStringExtra("name");
        int amount = data.getIntExtra("amount", 0);
        Expenses temp = new Expenses(name, amount, true);
        expenses.add(temp);
        adapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Expenses temp = expenses.get(i);
        //Toast.makeText(this,temp.getName()+" "+temp.getPrice()+" "+temp.isCheck(), Toast.LENGTH_SHORT).show();

        expenses.remove(i);
        adapter.notifyDataSetChanged();
        //TextView t=(TextView)view;
        //t.setText("clicked");
        // expenses.set(i,"clicked");
    }
}
