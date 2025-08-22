package com.example.Consumer;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service

public class Userlocationconsumer {
    private final RedisTemplate<String, String> redisTemplate;

    public Userlocationconsumer(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "user-location", groupId = "matching-service-group")
    public void consumeUserLocation(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(message);

            String userId = node.get("userid").asText();
            double lat = node.get("pickupLat").asDouble();
            double lng = node.get("pickupLng").asDouble();

            redisTemplate.opsForValue().set(userId, lat + "," + lng);

            System.out.println("Stored user " + userId + " in Redis");
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    
}
}
