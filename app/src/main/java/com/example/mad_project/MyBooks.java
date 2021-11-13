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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.mad_project.R.layout.bookview;

public class MyBooks extends AppCompatActivity {
    String username;
    ActionBar actionBar;
    TextView txtUsername;
    ArrayList<Book> bookArrayList;
    ListView lvBook;
    SQLiteDatabase db;
    Button back;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffffff"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>My Books </font>"));

        setContentView(R.layout.activity_my_books);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        back = (Button) findViewById(R.id.btnDashboard);
        lvBook = (ListView) findViewById(R.id.bookList);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtUsername.setText(username);

        sp = getSharedPreferences("book",MODE_PRIVATE);
        edit = sp.edit();

        db = openOrCreateDatabase("bookDB",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS books(id int PRIMARY KEY,bTitle varchar(30) NOT NULL,bAuthor varchar(30) NOT NULL,bDept varchar(30) NOT NULL,bOwner varchar(20) NOT NULL,bContact varchar(30) NOT NULL,bPic INTEGER)");

        bookArrayList = new ArrayList<>();

        Cursor getBooks = db.rawQuery("SELECT * FROM books where bOwner='"+username+"'", null);

        bookArrayList.clear();
        while (getBooks.moveToNext()){
                Book b = new Book();
                b.setTitle(getBooks.getString(1));
                b.setAuthor(getBooks.getString(2));
                b.setDept(getBooks.getString(3));
                b.setOwner(getBooks.getString(4));
                b.setPhone(getBooks.getString(5));
                b.setPicture(getBooks.getInt(6));
                bookArrayList.add(b);
            }



        final BookAdapter myAdapter = new BookAdapter(this, bookview,bookArrayList);
        lvBook.setAdapter(myAdapter);


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
