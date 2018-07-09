package com.example.shad0w.todolist;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class Add_Expenses extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    EditText name;
    Intent intent;
    EditText amount;
    TextView time;
    Calendar alarm;
    TextView date;
    boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__expenses);
        alarm = Calendar.getInstance();
        intent = getIntent();
        String s = intent.getAction();
        time = findViewById(R.id.showtime);
        date = findViewById(R.id.showdate);
        amount = findViewById(R.id.titleEditText);
        name = findViewById(R.id.descriptonEditText);
        Calendar c = Calendar.getInstance();
        String del = intent.getStringExtra("detail");
        String title = intent.getStringExtra("title");
        Log.i("hello", "here we go " + del + " " + title);
        if (del != null && title != null) {
            name.setText(del);
            amount.setText(title);
            Log.i("hello", "here we go" + del + " " + title);
            check = true;
        }
        if (s == Intent.ACTION_SEND) {
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            name.setText(text);
        }
        date.setText(c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR));
        time.setText(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE));
    }

    public void alarmset(long alarm, long id, String title, String des) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Log.i("alarm", alarm + " function " + id + " " + System.currentTimeMillis());
        Intent intent = new Intent(getApplicationContext(), MyReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("des", des);
        int alarmid = (int) id;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), alarmid, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm, pendingIntent);

    }

    public void addexpenses(View view) {
        String Sname = name.getText().toString();
        String Samount = amount.getText().toString();
        //Intent intent=getIntent();
        //intent.putExtra("name",Sname);
        //intent.putExtra("title",Samount);
        todohelper helper = todohelper.getInstance(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(contract.COLUMN_DETAIL, Sname);
        contentValues.put(contract.COLUMN_TITLE, Samount);
        contentValues.put(contract.DATE, date.getText().toString());
        contentValues.put(contract.TIME, time.getText().toString());
        long al = alarm.getTimeInMillis();
        contentValues.put(contract.ALARM, al);
        Long id = database.insert(contract.TABLE_NAME, null, contentValues);
        intent.putExtra("id", id);
        alarmset(al, id, Samount, Sname);
        Log.i("alarm", al + "in add");
        if (intent.getAction() == Intent.ACTION_SEND || check) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            setResult(2, intent);
        }
        finish();
    }

    public void time(View view) {
        DialogFragment time = new Timeclass();
        time.show(getSupportFragmentManager(), "time");
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        //String a=t.getText().toString();
        time.setText(+i + ":" + i1 + " ");
        alarm.set(Calendar.HOUR, i);
        alarm.set(Calendar.MINUTE, i1);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        alarm.set(Calendar.YEAR, i);
        alarm.set(Calendar.MONTH, i1);
        alarm.set(Calendar.DAY_OF_MONTH, i2);
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_MONTH, i2);
        String a = DateFormat.getDateInstance().format(c.getTime());
        date.setText(a);
    }

    public void date(View view) {
        DialogFragment date = new Dateclass();
        date.show(getSupportFragmentManager(), "date");
    }
}
