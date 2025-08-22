package com.example.Kafka;

import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.example.DTO.Kaflocreq;


@Service
@RequiredArgsConstructor
public class Userkafka {
        private final KafkaTemplate<String, Kaflocreq> kafkaTemplate;
    
        @Value("${kafka.topic.user-location}")
        private String userLocationTopic;
    
        public void sendUserLocation(Kaflocreq event) {
            kafkaTemplate.send(userLocationTopic, String.valueOf(event.getUserid()), event);
            System.out.println("Published User Location: " + event);
        }


    
}
