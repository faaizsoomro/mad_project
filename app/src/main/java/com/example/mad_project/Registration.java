package com.example.mad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Registration extends AppCompatActivity {
    TextView jmplogin;
    Button signup;
    EditText fn;
    EditText ln;
    EditText un;
    EditText pw;
    EditText cpw;
    EditText ph;
    String firstname;
    String lastname;
    String username;
    String password;
    String confirmPassword;
    String phone;
    SQLiteDatabase db;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registration);

        db = openOrCreateDatabase("bookDB",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(id int PRIMARY KEY,fname varchar(30) NOT NULL,lname varchar(30) NOT NULL,uname varchar(30) NOT NULL,contact varchar(20) NOT NULL,password varchar(30) NOT NULL)");

        jmplogin = (TextView) findViewById(R.id.txtLoginLink);
        signup = (Button) findViewById(R.id.btnSignUp);
        fn = (EditText) findViewById(R.id.txtRegFN);
        ln = (EditText) findViewById(R.id.txtRegLN);
        un = (EditText) findViewById(R.id.txtRegUN);
        pw = (EditText) findViewById(R.id.txtRegPW);
        cpw = (EditText) findViewById(R.id.txtRegCPW);
        ph = (EditText) findViewById(R.id.txtRegContact);
        firstname="";
        lastname="";
        username="";
        phone="";
        password="";
        confirmPassword="";


        jmplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this,MainActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname = fn.getText().toString();
                lastname = ln.getText().toString();
                username = un.getText().toString();
                phone = ph.getText().toString();
                password = pw.getText().toString();
                confirmPassword = cpw.getText().toString();

                try {
                    if (firstname.equals("") || lastname.equals("") || username.equals("") || phone.equals("") || password.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please fill Form completely", Toast.LENGTH_LONG).show();
                    } else {

                        Cursor c = db.rawQuery("SELECT * FROM users where uname= '" + username + "'", null);
                        if (c.getCount() > 0) {
                            Toast.makeText(getApplicationContext(), "USER ALREADY EXITS", Toast.LENGTH_LONG).show();
                        } else {
                            if (password.equals(confirmPassword)) {
                                password = md5(password);

                                db.execSQL("INSERT INTO users(fname,lname,uname,contact,password) values('" + firstname + "','" + lastname + "','" + username.toLowerCase() + "','"+phone+"','"+ password + "')");
                                Toast.makeText(getApplicationContext(), "Successfully Registered. Please Login to continue", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(Registration.this,MainActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "Password did not match", Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
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
