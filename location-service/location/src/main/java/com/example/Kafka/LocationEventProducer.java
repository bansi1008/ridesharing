package com.example.Kafka;

import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.example.DTO.Kafkadto;


@Service
@RequiredArgsConstructor
public class LocationEventProducer  {
     private final KafkaTemplate<String, Kafkadto> kafkaTemplate;

    @Value("${kafka.topic.driver-location}")
    private String driverLocationTopic;

    public void sendDriverLocation(Kafkadto event) {
        kafkaTemplate.send(driverLocationTopic, String.valueOf(event.getDriverId()), event);
        System.out.println("Published Driver Location: " + event);
    }


    
}
