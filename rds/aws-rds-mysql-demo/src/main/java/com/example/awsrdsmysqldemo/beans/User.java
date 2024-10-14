package com.example.awsrdsmysqldemo.beans;

public class User {
    private int id;
    private String username;
    private String mail;
    private int age;


    public User() {
    }

    public User(int id, String username, String mail, int age) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
