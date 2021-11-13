package com.example.mad_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {
    Context myContext;
    int myResource;

    public BookAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Book> objects) {
        super(context, resource, objects);
        myContext = context;
        myResource = resource;
    }



    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title=getItem(position).getTitle();
        String author=getItem(position).getAuthor();
        String dept=getItem(position).getDept();
        String owner=getItem(position).getOwner();
        String phone=getItem(position).getPhone();
        int picture=getItem(position).getPicture();

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myResource,parent,false);

        ImageView img = (ImageView) convertView.findViewById(R.id.bookimage);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.booktitle);
        TextView txtAuthor = (TextView) convertView.findViewById(R.id.bookauthor);
        TextView txtDept = (TextView) convertView.findViewById(R.id.bookdep);
        TextView txtOwner = (TextView) convertView.findViewById(R.id.bookowners);
        TextView txtContact = (TextView) convertView.findViewById(R.id.bookownercnt);


        img.setImageResource(picture);
        txtTitle.setText(title);
        txtAuthor.setText(author);
        txtDept.setText(dept);
        txtOwner.setText(owner);
        txtContact.setText(phone);

        return convertView;
    }
}
