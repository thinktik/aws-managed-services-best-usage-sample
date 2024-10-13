package com.example.mskdemo.ctrl;


import com.example.mskdemo.beans.User;
import com.example.mskdemo.service.MskProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * index controller
 *
 * @author ThinkTik
 */
@RestController
@RequestMapping("/")
class IndexCtrl {

    /**
     * project's name
     */
    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private MskProducer mskProducer;

    /**
     * RESTFul API for path: "/","index","ping"
     *
     * @return standardResponse
     */
    @GetMapping(value = {"", "index", "ping"})
    Map<String, String> index() {
        return standardResponse();
    }

    /**
     * build a standard response
     *
     * @return standard response
     */
    private Map<String, String> standardResponse() {

        // get current time in milliseconds
        long timestamp = System.currentTimeMillis();
        String username = "user-" + timestamp;
        int age = new Random().nextInt(100);
        String email = username + "@example.com";
        User user = new User(username, age, email);
        mskProducer.sendMessage(user);
        Map<String, String> data = new HashMap<>(8);
        data.put("appName", appName);
        data.put("username", user.getUsername());
        data.put("age", String.valueOf(user.getAge()));
        data.put("email", user.getEmail());
        return data;
    }
}