package com.example.mad_project;

public class Book {
    String title;
    String author;
    String dept;
    String owner;
    String phone;
    int picture;

    public Book() {
        title="";
        author="";
        dept="";
        owner="";
        phone="";
        picture=0;
    }

    public Book(String title, String author, String dept, String owner, String phone, int picture) {
        this.title = title;
        this.author = author;
        this.dept = dept;
        this.owner = owner;
        this.phone = phone;
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDept() {
        return dept;
    }

    public String getOwner() {
        return owner;
    }

    public String getPhone() {
        return phone;
    }

    public int getPicture() {
        return picture;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
