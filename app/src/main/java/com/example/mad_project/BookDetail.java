package com.example.mad_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetail extends AppCompatActivity {
    ImageView pic;
    TextView title;
    TextView author;
    TextView dept;
    TextView owner;
    TextView contact;
    TextView username;
    Button back;
    ActionBar actionBar;
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffffff"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Book Details </font>"));
        setContentView(R.layout.activity_book_detail);

        Intent intent = getIntent();

        sp = getSharedPreferences("book",MODE_PRIVATE);
        edit = sp.edit();

        String u = intent.getStringExtra("username");
        username = (TextView) findViewById(R.id.txtUsername);
        username.setText(u);
        pic = (ImageView) findViewById(R.id.pic);
        title = (TextView) findViewById(R.id.txtTitle);
        author = (TextView) findViewById(R.id.txtAuthor);
        dept = (TextView) findViewById(R.id.txtDept);
        owner = (TextView) findViewById(R.id.txtOwner);
        contact = (TextView) findViewById(R.id.txtContact);
        back = (Button) findViewById(R.id.btnDashboard);
        int p = intent.getIntExtra("pic",0);
        if(p != 0){
            pic.setImageResource(p);
        }
        title.setText(intent.getStringExtra("title"));
        author.setText(intent.getStringExtra("author"));
        dept.setText(intent.getStringExtra("dept"));
        owner.setText(intent.getStringExtra("owner"));
        contact.setText(intent.getStringExtra("phone"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btnLogout){
            edit.putString("username","default");
            edit.apply();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
