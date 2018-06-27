package com.example.shad0w.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Add_Expenses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__expenses);
    }

    public void addexpenses(View view) {
        EditText name=findViewById(R.id.nameEditText);
        EditText amount=findViewById(R.id.amountEditText);
        String Sname=name.getText().toString();
        String Samount=amount.getText().toString();
        Intent intent=getIntent();
        int price=Integer.parseInt(Samount);
        intent.putExtra("name",Sname);
        intent.putExtra("amount",price);
        setResult(2,intent);
        finish();
    }
}
