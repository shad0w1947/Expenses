package com.example.shad0w.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class todohelper extends SQLiteOpenHelper {
    public static final String database = "todoList_db";
    public static final int VERSION = 1;

    public static todohelper instance;
    public static todohelper getInstance(Context context){
        if(instance==null)
            instance=new todohelper(context.getApplicationContext());
        return instance;
    }

    public todohelper(Context context) {
        super(context, database, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String todosql = "CREATE TABLE " + contract.TABLE_NAME + " ( " +
                contract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                contract.COLUMN_TITLE + " TEXT , " +
                contract.DATE+" TEXT , "+
                contract.TIME+" TEXT , "+
                contract.ALARM+" TEXT , "+
                contract.COLUMN_DETAIL + " TEXT  )";
        sqLiteDatabase.execSQL(todosql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
