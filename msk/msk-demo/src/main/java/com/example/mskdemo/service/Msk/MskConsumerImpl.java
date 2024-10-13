package com.example.mskdemo.service.Msk;

import com.example.mskdemo.service.MskConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MskConsumerImpl implements MskConsumer {
    private static final Logger log = LoggerFactory.getLogger(MskConsumerImpl.class);

    @KafkaListener(topics = "think-topic-1")
    public void consumeMessage(String message) {
        log.info("Received message: {}", message);
    }
}
