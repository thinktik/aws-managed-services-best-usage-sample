package com.example.awsrdsmysqldemo.ctrls;


import com.example.awsrdsmysqldemo.beans.User;
import com.example.awsrdsmysqldemo.daos.UserDao;
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
    private UserDao userDao;


    /**
     * RESTFul API for path: "/","index","ping"
     *
     * @return standard response
     */
    @GetMapping(value = {"", "index", "ping"})
    public Map<String, String> index() {
        User user = new User();
        String username = RandomStringUtils.randomAlphabetic(8);
        user.setUsername(username);
        user.setMail(username + "@outlook.com");
        user.setAge(new Random().nextInt(100));
        userDao.insertUser(user);
        user = userDao.queryUserByName(user.getUsername());
        if (user.getId() % 50 == 0) {
            log.info("Get user from aws rds multi-az cluster,the username is {}", user.getUsername());
        }

        return standardResponse();
    }

    /**
     * 产生标准项目描述response
     *
     * @return standard response
     */
    private Map<String, String> standardResponse() {
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
        return data;
    }
}
