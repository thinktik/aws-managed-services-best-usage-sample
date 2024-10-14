package com.example.awsrdsmysqldemo.daos;


import com.example.awsrdsmysqldemo.beans.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    int insertUser(User user);

    User queryUserByName(String username);
}
