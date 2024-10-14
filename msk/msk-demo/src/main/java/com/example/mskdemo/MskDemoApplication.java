package com.example.mskdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.imds.Ec2MetadataClient;
import software.amazon.awssdk.imds.Ec2MetadataResponse;

@SpringBootApplication
public class MskDemoApplication {
    private static final Logger log = LoggerFactory.getLogger(MskDemoApplication.class);

    // 本程序的EC2宿主机所在的aws availability zone id,EC2的整个生命周期内zone id不会发生改变。
    private static final String AVAILABILITY_ZONE_ID;

    static {
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
        AVAILABILITY_ZONE_ID = availabilityZoneId;
        log.info("AWS EC2 availability zone ID: {}", AVAILABILITY_ZONE_ID);
        // 在程序实际启动前写入到系统环境变量，供程序初始化时读取到application.yaml
        System.setProperty("AWS_AVAILABILITY_ZONE_ID", AVAILABILITY_ZONE_ID);
    }

    public static void main(String[] args) {
        SpringApplication.run(MskDemoApplication.class, args);
    }

}
