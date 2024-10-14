package com.example.mskdemo.ctrl;


import com.example.mskdemo.beans.User;
import com.example.mskdemo.service.MskProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.imds.Ec2MetadataClient;
import software.amazon.awssdk.imds.Ec2MetadataResponse;

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
    private static final Logger log = LoggerFactory.getLogger(IndexCtrl.class);

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
        // aws availability zone id初始化为"",在无法获取到zone id作为默认值。这个可以兼容非AWS环境或者出现异常时会退到kafka默认设置。
        String availabilityZoneId = "";
        // https://docs.aws.amazon.com/zh_cn/AWSEC2/latest/UserGuide/ec2-instance-metadata.html
        // https://docs.aws.amazon.com/zh_cn/sdk-for-java/latest/developer-guide/migration-imds.html
        try (Ec2MetadataClient ec2MetadataClient = Ec2MetadataClient.create()) {
            // 通过查询placement/availability-zone-id获取aws availability zone id
            Ec2MetadataResponse response = ec2MetadataClient.get("/latest/meta-data/placement/availability-zone-id");
            availabilityZoneId = response.asString();
        } catch (Exception e) {
            // 本地环境、非AWS环境会出现异常，直接给出明显的警告信息即可跳过。后续会使用kafka默认设置。
            log.warn("Error retrieving AWS EC2 availability zone ID ");
        }

        log.info("AWS EC2 availability zone ID: {}", availabilityZoneId);

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
        data.put("zone-id", availabilityZoneId);
        return data;
    }
}