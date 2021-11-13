package com.example.mad_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddBooks extends AppCompatActivity {
    ActionBar actionBar;
    String username;
    TextView txtUsername;
    EditText bt;
    EditText ba;
    Spinner bd;
    Button add;
    Button back;
    SQLiteDatabase db;
    String phone;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffffff"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Add Books </font>"));
        setContentView(R.layout.activity_add_books);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        db = openOrCreateDatabase("bookDB",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS books(id int PRIMARY KEY,bTitle varchar(30) NOT NULL,bAuthor varchar(30) NOT NULL,bDept varchar(30) NOT NULL,bOwner varchar(20) NOT NULL,bContact varchar(30) NOT NULL,bPic INTEGER)");
        Cursor c = db.rawQuery("SELECT contact FROM users where uname= '" + username+"'", null);
        c.moveToFirst();
        phone = c.getString(0);


        sp = getSharedPreferences("book",MODE_PRIVATE);
        edit = sp.edit();

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtUsername.setText(username);
        bt = (EditText) findViewById(R.id.bookTitle);
        ba = (EditText) findViewById(R.id.bookAuthor);
        bd = (Spinner) findViewById(R.id.bookDept);
        add = (Button) findViewById(R.id.btnAdd);
        back = (Button) findViewById(R.id.btnDashboard);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = bt.getText().toString();
                String author = ba.getText().toString();
                String department = bd.getSelectedItem().toString();

                if(department.equals("Select Department")){
                    Toast.makeText(getApplicationContext(),"Please select department",Toast.LENGTH_LONG).show();
                }
                else{
                    if(title.equals("") && author.equals("")){
                        Toast.makeText(getApplicationContext(),"Please Fill form completely",Toast.LENGTH_LONG).show();
                    }
                    else{
                        db.execSQL("INSERT INTO books(bTitle,bAuthor,bDept,bOwner,bContact,bPic) values('" + title + "','" + author + "','" + department + "','"+username.toLowerCase()+"','"+phone +"','"+R.drawable.applogo+ "')");
                        Toast.makeText(getApplicationContext(),"Book added",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

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
