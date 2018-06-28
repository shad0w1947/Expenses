package com.example.shad0w.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ArrayList<Expenses> expenses = new ArrayList<>();
    ExpenseAdapter adapter;
    SharedPreferences sharedPreferences;
    int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listview);
        sharedPreferences = getSharedPreferences("mydata", MODE_PRIVATE);
        int size = sharedPreferences.getInt("size", 0);
        number = size;
        for (int i = 0; i < size; i++) {
            String a = "t" + i;
            String b = "d" + i;
            String title = sharedPreferences.getString(a, null);
            String des = sharedPreferences.getString(b, null);
            if (title == null || des == null)
                continue;
            Expenses temp = new Expenses(title, des);
            expenses.add(temp);

        }
        //ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,expenses);
        adapter = new ExpenseAdapter(this, expenses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
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

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (resultCode == 3) {
            Bundle b = data.getExtras();
            Expenses temp = expenses.get(b.getInt("index"));
            String name = b.getString("name");
            temp.setDescription(name);
            String bs = "d" + b.getInt("index");
            //TextView name = findViewById(R.id.descripton);
            //name.setText(b.getString("name") + " you");
            editor.putString(bs, name);
            adapter.notifyDataSetChanged();
        }
        if (resultCode == 2) {

            String a = "t" + number;
            String bs = "d" + number;

            String name = data.getStringExtra("name");
            String title = data.getStringExtra("title");

            Expenses temp = new Expenses(title, name);
            editor.putString(a, title);
            editor.putString(bs, name);
            expenses.add(temp);
            adapter.notifyDataSetChanged();
            number++;
            editor.putInt("size", number);

        }
        editor.commit();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Expenses temp = expenses.get(i);
        //Toast.makeText(this,temp.getName()+" "+temp.getPrice()+" "+temp.isCheck(), Toast.LENGTH_SHORT).show();
        //expenses.remove(i);
        //adapter.notifyDataSetChanged();
        //TextView t=(TextView)view;
        //t.setText("clicked");
        // expenses.set(i,"clicked");
        Intent intent = new Intent(this, detailShow.class);
        Bundle bundle = new Bundle();
        bundle.putInt("index", i);
        bundle.putString("name", temp.getDescription());
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final int positon = i;
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("CONFIRM DELETE");
        builder.setMessage("Do you really want to delete?");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("size",expenses.size()+"");
                expenses.remove(positon);
                adapter.notifyDataSetChanged();
                updateshare();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();

        return true;
    }

    private void updateshare() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < expenses.size(); i++) {
            Expenses temp = expenses.get(i);
            editor.putString("d" + i, temp.getDescription());
            editor.putString("t" + i, temp.getTitle());

        }
        editor.putInt("size", expenses.size());
        editor.commit();
    }

}
