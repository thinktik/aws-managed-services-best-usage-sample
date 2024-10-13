package com.example.documentdbdemo.daos.impl;

import com.example.documentdbdemo.beans.User;
import com.example.documentdbdemo.daos.MongoOpsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoOpsDapImpl implements MongoOpsDao {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public String addUser(User user) {
        user = mongoTemplate.insert(user);
        return user.getId();
    }

    @Override
    public User findUser(String id) {
        User user = mongoTemplate.findById(id, User.class);
        return user;
    }
}
