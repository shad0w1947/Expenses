package com.example.shad0w.todolist;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class detailShow extends AppCompatActivity {
   Intent intent;
   Bundle bundle;
   String show;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_show);
        intent=getIntent();
        bundle=intent.getExtras();
        show=bundle.getString("name");
        t=findViewById(R.id.showtext);
        t.setText(show);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmenu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(resultCode!=5)
           return;
        Bundle b=data.getExtras();
        bundle.putString("name",b.getString("name"));
        intent.putExtras(bundle);
        t.setText(bundle.getString("name"));
        setResult(3,intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i=new Intent(this,editior.class);
        i.putExtras(bundle);
        startActivityForResult(i,3);
        return true;
    }
}
