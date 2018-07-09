package com.example.shad0w.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    //SharedPreferences sharedPreferences;
    int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("life", "onCreate");
        ListView listView = (ListView) findViewById(R.id.listview);
        todohelper helper = todohelper.getInstance(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(contract.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(contract.COLUMN_TITLE));
            String detail = cursor.getString(cursor.getColumnIndex(contract.COLUMN_DETAIL));
            long id = cursor.getLong(cursor.getColumnIndex(contract.ID));
            String date = cursor.getString(cursor.getColumnIndex(contract.DATE));
            String time = cursor.getString(cursor.getColumnIndex(contract.TIME));
            Expenses temp = new Expenses(title, detail, id, date, time);
            expenses.add(temp);

        }
        /*sharedPreferences = getSharedPreferences("mydata", MODE_PRIVATE);
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

        }*/
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

        //SharedPreferences.Editor editor = sharedPreferences.edit();

        if (resultCode == 3) {
            Bundle b = data.getExtras();
            Expenses temp = expenses.get(b.getInt("index"));
            String name = b.getString("name");
            String title = temp.getTitle();
            temp.setDescription(name);
            todohelper todo = todohelper.getInstance(this);
            SQLiteDatabase database = todo.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(contract.COLUMN_TITLE, title);
            contentValues.put(contract.COLUMN_DETAIL, name);

            //String bs = "d" + b.getInt("index");
            long id = temp.getId();
            String[] arg = {id + ""};
            database.update(contract.TABLE_NAME, contentValues, contract.ID + "=?", arg);
            //TextView name = findViewById(R.id.descripton);
            //name.setText(b.getString("name") + " you");
            //editor.putString(bs, name);
            adapter.notifyDataSetChanged();
        }
        if (resultCode == 2) {

            // String a = "t" + number;
            //String bs = "d" + number;
            long id = data.getLongExtra("id", 0);
            long alarm = data.getLongExtra("alarm", 0);

            String name = "";// = data.getStringExtra("name");
            String title = "";// = data.getStringExtra("title");
            todohelper todo = todohelper.getInstance(this);
            SQLiteDatabase database = todo.getReadableDatabase();
            String date = "";
            String time = "";
            String arg[] = {id + ""};
            Cursor cursor = database.query(contract.TABLE_NAME, null, contract.ID + "=?", arg, null, null, null);
            while (cursor.moveToNext()) {
                title = cursor.getString(cursor.getColumnIndex(contract.COLUMN_TITLE));
                name = cursor.getString(cursor.getColumnIndex(contract.COLUMN_DETAIL));
                date = cursor.getString(cursor.getColumnIndex(contract.DATE));
                time = cursor.getString(cursor.getColumnIndex(contract.TIME));
            }

            Expenses temp = new Expenses(title, name, id, date, time);
            //editor.putString(a, title);
            //editor.putString(bs, name);
            expenses.add(temp);
            adapter.notifyDataSetChanged();
            number++;
            //editor.putInt("size", number);

        }
        //editor.commit();
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
                Log.i("size", expenses.size() + "");
                long id = expenses.get(positon).getId();
                alarmcanel(id,"dklf","dkdl");
                String[] arg = {id + ""};
                expenses.remove(positon);
                adapter.notifyDataSetChanged();
                todohelper helper = todohelper.getInstance(MainActivity.this);
                SQLiteDatabase database = helper.getWritableDatabase();
                database.delete(contract.TABLE_NAME, contract.ID + "=?", arg);
                //updateshare();
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
    private void alarmcanel(long id,String title,String des) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //Log.i("alarm",alarm+" function "+id+" "+System.currentTimeMillis());
        Intent intent = new Intent(getApplicationContext(), MyReceiver.class);
        intent.putExtra("title",title);
        intent.putExtra("des",des);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), (int) id, intent, 0);
        alarmManager.cancel(pendingIntent);

    }

    /*private void updateshare() {
       // SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < expenses.size(); i++) {
            Expenses temp = expenses.get(i);
            editor.putString("d" + i, temp.getDescription());
            editor.putString("t" + i, temp.getTitle());

        }
        editor.putInt("size", expenses.size());
        editor.commit();
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("life", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("life", "onRestart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("life", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("life", "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("life", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("life", "onDEstroy");
    }
}
