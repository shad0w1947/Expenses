package com.example.shad0w.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class editior extends AppCompatActivity {
   EditText e;
   Intent intent;
   Bundle bundle;
   String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editior);
        intent =getIntent();
        bundle=intent.getExtras();
        e=findViewById(R.id.editior);
        s=bundle.getString("name");
        e.setText(s);
    }

    public void edit(View view) {
        s=e.getText().toString();
        bundle.putString("name",s);
        intent.putExtras(bundle);
        setResult(5,intent);
        finish();

    }
}
