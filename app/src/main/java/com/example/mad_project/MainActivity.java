package com.example.mad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    Button reg;
    Button login;
    EditText un;
    EditText pw;
    SQLiteDatabase db;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        un = (EditText) findViewById(R.id.txtLoginUN);
        pw = (EditText) findViewById(R.id.txtLoginPW);
        db = openOrCreateDatabase("bookDB",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(id int PRIMARY KEY,fname varchar(30) NOT NULL,lname varchar(30) NOT NULL,uname varchar(30) NOT NULL,contact varchar(20) NOT NULL,password varchar(30) NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS books(id int PRIMARY KEY,bTitle varchar(30) NOT NULL,bAuthor varchar(30) NOT NULL,bDept varchar(30) NOT NULL,bOwner varchar(20) NOT NULL,bContact varchar(30) NOT NULL,bPic INTEGER)");



        reg = (Button) findViewById(R.id.btnReg);
        login = (Button) findViewById(R.id.btnLogin);


        sp = getSharedPreferences("book",MODE_PRIVATE);
        edit = sp.edit();

        String check = sp.getString("check","def");
        if(check.equals("def")){
            edit.putString("check","added");
            db.execSQL("INSERT INTO books(bTitle,bAuthor,bDept,bOwner,bContact,bPic) values('" + "Book 1" + "','" + "Author 1" + "','" + "BBA" + "','"+"User 1"+"','"+ "03001234567"+"','"+R.drawable.applogo+ "')");
            db.execSQL("INSERT INTO books(bTitle,bAuthor,bDept,bOwner,bContact,bPic) values('" + "Book 2" + "','" + "Author 2" + "','" + "BBA" + "','"+"User 2"+"','"+ "03331234567"+"','"+R.drawable.applogo+ "')");
            db.execSQL("INSERT INTO books(bTitle,bAuthor,bDept,bOwner,bContact,bPic) values('" + "Book 3" + "','" + "Author 3" + "','" + "CS" + "','"+"User 3"+"','"+ "03331234567"+"','"+R.drawable.applogo+ "')");
            db.execSQL("INSERT INTO books(bTitle,bAuthor,bDept,bOwner,bContact,bPic) values('" + "Book 4" + "','" + "Author 4" + "','" + "CS" + "','"+"User 4"+"','"+ "03001234567"+"','"+R.drawable.applogo+ "')");
            db.execSQL("INSERT INTO books(bTitle,bAuthor,bDept,bOwner,bContact,bPic) values('" + "Book 5" + "','" + "Author 1" + "','" + "BED" + "','"+"User 5"+"','"+ "03311234567"+"','"+R.drawable.applogo+ "')");
            db.execSQL("INSERT INTO books(bTitle,bAuthor,bDept,bOwner,bContact,bPic) values('" + "Book 6" + "','" + "Author 2" + "','" + "BED" + "','"+"User 6"+"','"+ "03121234567"+"','"+R.drawable.applogo+ "')");
            db.execSQL("INSERT INTO books(bTitle,bAuthor,bDept,bOwner,bContact,bPic) values('" + "Book 7" + "','" + "Author 3" + "','" + "BEE" + "','"+"User 7"+"','"+ "03151234567"+"','"+R.drawable.applogo+ "')");
            db.execSQL("INSERT INTO books(bTitle,bAuthor,bDept,bOwner,bContact,bPic) values('" + "Book 8" + "','" + "Author 4" + "','" + "BBA" + "','"+"User 8"+"','"+ "03001234567"+"','"+R.drawable.applogo+ "')");
        }


        String temp=sp.getString("username","default");
        if(!temp.equals("default")){
            Intent intent = new Intent(MainActivity.this,Dashboard.class);
            intent.putExtra("username",temp);
            startActivity(intent);
            finish();
        }

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Registration.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = un.getText().toString();
                String pass = pw.getText().toString();
                pass = md5(pass);
                Cursor c = db.rawQuery("SELECT * FROM users where uname= '" + username + "'AND password='"+pass+"'", null);
                if (c.getCount() > 0) {
                    Intent intent = new Intent(MainActivity.this,Dashboard.class);
                    intent.putExtra("username",username);
                    edit.putString("username",username);
                    edit.apply();
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please Enter Correct Username and Password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
