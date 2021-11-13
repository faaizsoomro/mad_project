package com.example.mad_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {
    String username;
    CardView vb;
    CardView ab;
    CardView mb;
    TextView txtUsername;
    ActionBar actionBar;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffffff"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Dashboard </font>"));
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        sp = getSharedPreferences("book",MODE_PRIVATE);
        edit = sp.edit();

        vb = (CardView) findViewById(R.id.cardVB);
        ab = (CardView) findViewById(R.id.cardAB);
        mb = (CardView) findViewById(R.id.cardMB);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtUsername.setText(username);

        vb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BookList.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddBooks.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MyBooks.class);
                intent.putExtra("username",username);
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
