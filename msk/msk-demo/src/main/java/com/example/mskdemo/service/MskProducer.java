package com.example.mskdemo.service;

import com.example.mskdemo.beans.User;

public interface MskProducer {
    void sendMessage(User user);
}
