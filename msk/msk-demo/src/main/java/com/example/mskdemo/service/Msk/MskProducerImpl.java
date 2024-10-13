package com.example.mskdemo.service.Msk;

import com.example.mskdemo.beans.User;
import com.example.mskdemo.service.MskProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MskProducerImpl implements MskProducer {
    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    @Transactional
    public void sendMessage(User user) {
        kafkaTemplate.send("think-topic-1", user.getUsername(), user);
    }
}
