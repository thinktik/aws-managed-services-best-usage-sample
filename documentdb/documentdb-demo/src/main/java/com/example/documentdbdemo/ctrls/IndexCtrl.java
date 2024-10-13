package com.example.documentdbdemo.ctrls;


import com.example.documentdbdemo.beans.User;
import com.example.documentdbdemo.daos.MongoOpsDao;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/")
class IndexCtrl {
    private static final Logger log = LoggerFactory.getLogger(IndexCtrl.class);

    /**
     * 应用程序名
     */
    @Value("${spring.application.name}")
    private String appName;

    /**
     * 描述
     */
    @Value("${spring.application.description}")
    private String description;

    /**
     * index 主页URL
     */
    @Value("${spring.application.url}")
    private String indexUrl;

    @Autowired
    private MongoOpsDao mongoOpsDao;


    /**
     * RESTFul API for path: "/","index","ping"
     *
     * @return standard response
     */
    @GetMapping(value = {"", "index", "ping"})
    public Map<String, String> index() {
        long currentTimeMillis = System.currentTimeMillis();
        String id = "id-" + RandomStringUtils.secureStrong().nextAlphanumeric(10) + currentTimeMillis;
        String username = "guest-" + currentTimeMillis;
        int age = new Random().nextInt(100);
        User user = new User(id, username, age);
        mongoOpsDao.addUser(user);
        user = mongoOpsDao.findUser(id);

        if (currentTimeMillis % 50 == 0) {
            log.info("Get user from aws documentdb replica cluster,the username is {}", user.getUsername());
        }

        return standardResponse(user.getId());
    }

    /**
     * 产生标准项目描述response
     *
     * @return standard response
     */
    private Map<String, String> standardResponse(String id) {
        // 获取当前时间戳
        long timestamp = System.currentTimeMillis();
        // 获取运行当前操作系统的CPU架构
        String osArch = System.getProperties().getProperty("os.arch");
        // 获取当选OS名称
        String osName = System.getProperties().getProperty("os.name");
        // 获取当选OS内核版本
        String osVersion = System.getProperties().getProperty("os.version");
        Map<String, String> data = HashMap.newHashMap(8);
        data.put("appName", appName);
        data.put("description", description);
        data.put("indexUrl", indexUrl);
        data.put("osName", osName);
        data.put("osVersion", osVersion);
        data.put("osArch", osArch);
        data.put("timestamp", String.valueOf(timestamp));
        data.put("userId", id);
        return data;
    }
}
