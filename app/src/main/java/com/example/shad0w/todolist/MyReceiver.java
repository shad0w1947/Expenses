package com.example.shad0w.todolist;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Log.i("alarm", "called" + " " + intent.getAction());
        Bundle bundle = intent.getExtras();
        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            todohelper helpper = todohelper.getInstance(context);
            SQLiteDatabase database = helpper.getReadableDatabase();
            String[] col = {contract.ALARM, contract.ID};
            Cursor cursor = database.query(contract.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Long time = System.currentTimeMillis();
                long id = cursor.getLong(cursor.getColumnIndex(contract.ID));
                long alarm = cursor.getLong(cursor.getColumnIndex(contract.ALARM));
                String title = cursor.getString(cursor.getColumnIndex(contract.COLUMN_TITLE));
                String des = cursor.getString(cursor.getColumnIndex(contract.COLUMN_DETAIL));
                if (time < alarm) {
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                    Log.i("alarm", alarm + " function " + id + " " + System.currentTimeMillis());
                    Intent intent1 = new Intent(context.getApplicationContext(), MyReceiver.class);
                    intent1.putExtra("title", title);
                    intent1.putExtra("des", des);
                    int alarmid = (int) id;
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), alarmid, intent1, 0);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarm, pendingIntent);
                }
            }
        } else if (intent.getAction() != null && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            try {
                if (bundle != null) {
                    final Object[] pdusObj = (Object[]) bundle.get("pdus");
                    String format = bundle.getString("format");
                    String senderNum = "";
                    String message = "";
                    for (int i = 0; i < pdusObj.length; i++) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                        senderNum = currentMessage.getDisplayOriginatingAddress();
                        message = currentMessage.getDisplayMessageBody();
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context,
                                "senderNum: " + senderNum + ", message: " + message, duration);
                        toast.show();

                    }
                    NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("mychannel", "Channel", NotificationManager.IMPORTANCE_HIGH);
                        manager.createNotificationChannel(channel);
                    }
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel");
                    builder.setContentTitle(senderNum);
                    builder.setContentText(message);
                    builder.setSmallIcon(R.drawable.ic_launcher_background);
                    Intent open = new Intent(context, Add_Expenses.class);
                    open.putExtra("title", senderNum);
                    open.putExtra("detail", message);
                    Log.i("hello", senderNum + " " + message);
                    long request_code = System.currentTimeMillis() / 1000;
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) request_code, open, 0);
                    builder.setContentIntent(pendingIntent);
                    Notification notification = builder.build();
                    manager.notify(1, notification);
                }

            } catch (Exception e) {
                Log.e("SmsReceiver", "Exception smsReceiver" + e);

            }
        } else {
            Log.i("alarm", "called1");
            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("mychannelid", "Expenses Channel", NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);

            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "mychannelid");
            builder.setContentTitle(intent.getStringExtra("title"));
            builder.setContentText(intent.getStringExtra("des"));
            builder.setSmallIcon(R.drawable.ic_launcher_background);
            Intent intent1 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, intent1, 0);
            builder.setContentIntent(pendingIntent);
            Notification notification = builder.build();
            manager.notify(1, notification);
        }


    }

}
