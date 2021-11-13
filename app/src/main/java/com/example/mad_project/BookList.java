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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.mad_project.R.layout.bookview;

public class BookList extends AppCompatActivity {
    String username;
    String phone;
    Spinner subjects;
    ListView lvBook;
    SQLiteDatabase db;
    Button btnVB ;
    Button back;
    ArrayList<Book> bookArrayList;
    ActionBar actionBar;
    TextView txtUsername;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffffff"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>View Books </font>"));
        setContentView(R.layout.activity_book_list);



        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtUsername.setText(username);

        sp = getSharedPreferences("book",MODE_PRIVATE);
        edit = sp.edit();

        db = openOrCreateDatabase("bookDB",MODE_PRIVATE,null);
        final Cursor c = db.rawQuery("SELECT contact FROM users where uname= '" + username+"'", null);
        c.moveToFirst();
        if(c != null){
            phone = c.getString(0);
        }
        db.execSQL("CREATE TABLE IF NOT EXISTS books(id int PRIMARY KEY,bTitle varchar(30) NOT NULL,bAuthor varchar(30) NOT NULL,bDept varchar(30) NOT NULL,bOwner varchar(20) NOT NULL,bContact varchar(30) NOT NULL,bPic INTEGER)");

        lvBook = (ListView) findViewById(R.id.bookList);
        subjects = (Spinner) findViewById(R.id.subjects);
        btnVB = (Button) findViewById(R.id.btnVB);
        back = (Button) findViewById(R.id.btnDashboard);
        bookArrayList = new ArrayList<>();


        Cursor getBooks = db.rawQuery("SELECT * FROM books", null);
        getBooks.moveToFirst();
        bookArrayList.clear();
        do{
            Book b = new Book();
            b.setTitle(getBooks.getString(1));
            b.setAuthor(getBooks.getString(2));
            b.setDept(getBooks.getString(3));
            b.setOwner(getBooks.getString(4));
            b.setPhone(getBooks.getString(5));
            b.setPicture(getBooks.getInt(6));
            bookArrayList.add(b);
        }
        while (getBooks.moveToNext());


        final BookAdapter myAdapter = new BookAdapter(this, bookview,bookArrayList);
        lvBook.setAdapter(myAdapter);


        btnVB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookArrayList.clear();
                myAdapter.notifyDataSetChanged();
                String department = subjects.getSelectedItem().toString();
                if(department.equals("All")){
                    Cursor getBooks = db.rawQuery("SELECT * FROM books", null);
                    getBooks.moveToFirst();
                    do{
                        Book b = new Book();
                        b.setTitle(getBooks.getString(1));
                        b.setAuthor(getBooks.getString(2));
                        b.setDept(getBooks.getString(3));
                        b.setOwner(getBooks.getString(4));
                        b.setPhone(getBooks.getString(5));
                        b.setPicture(getBooks.getInt(6));
                        bookArrayList.add(b);
                    }
                    while (getBooks.moveToNext());
                }else{

                    Cursor getBooks = db.rawQuery("SELECT * FROM books where bDept='"+department+"'", null);
                    getBooks.moveToFirst();
                    do{
                        Book b = new Book();
                        b.setTitle(getBooks.getString(1));
                        b.setAuthor(getBooks.getString(2));
                        b.setDept(getBooks.getString(3));
                        b.setOwner(getBooks.getString(4));
                        b.setPhone(getBooks.getString(5));
                        b.setPicture(getBooks.getInt(6));
                        bookArrayList.add(b);
                    }
                    while (getBooks.moveToNext());
                    myAdapter.notifyDataSetChanged();

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

        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book temp = bookArrayList.get(position);

                Intent intent = new Intent(BookList.this,BookDetail.class);
                intent.putExtra("username",username);
                intent.putExtra("title",temp.getTitle());
                intent.putExtra("author",temp.getAuthor());
                intent.putExtra("dept",temp.getDept());
                intent.putExtra("owner",temp.getOwner());
                intent.putExtra("phone",temp.getPhone());
                intent.putExtra("pic",temp.getPicture());
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
