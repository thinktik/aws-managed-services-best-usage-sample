package com.example.documentdbdemo.daos;

import com.example.documentdbdemo.beans.User;

public interface MongoOpsDao {

    String addUser(User user);

    User findUser(String id);
}
