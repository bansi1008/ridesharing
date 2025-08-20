package com.example.Consumer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class LocationConsumer {

    private final RedisTemplate<String, String> redisTemplate;

    public LocationConsumer(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "driver-location",groupId = "matching-service-group")
    public void consumeDriverLocation(String message) {
     

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(message);

            String driverId = node.get("driverId").asText();
            double lat = node.get("latitude").asDouble();
            double lng = node.get("longitude").asDouble();

       
            redisTemplate.opsForValue().set(driverId, lat + "," + lng);

            System.out.println("Stored driver " + driverId + " in Redis");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
