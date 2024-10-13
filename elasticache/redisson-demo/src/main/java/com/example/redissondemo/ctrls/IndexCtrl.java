package com.example.redissondemo.ctrls;


import com.example.redissondemo.daos.RedisOpsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    @Value("${description}")
    private String description;

    /**
     * index 主页URL
     */
    @Value("${indexUrl}")
    private String indexUrl;

    @Autowired
    private RedisOpsDao redisOpsDao;


    /**
     * RESTFul API for path: "/","index","ping"
     *
     * @return standard response
     */
    @GetMapping(value = {"", "index", "ping"})
    public Map<String, String> index() {
        String key = "test" + System.currentTimeMillis();
        redisOpsDao.set(key, String.valueOf(System.currentTimeMillis()));
        String testVal = redisOpsDao.get(key);
        if (Long.parseLong(testVal) % 50 == 0) {
            log.info("Get key {},from aws elasticache redis cluster,the value is {}", key, testVal);
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
