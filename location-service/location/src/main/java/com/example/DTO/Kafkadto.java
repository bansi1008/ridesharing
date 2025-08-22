package com.example.DTO;

import java.time.Instant;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Kafkadto {
    private Long driverId;
    private String latitude;
    private String longitude;
    private Instant timestamp;
    
}
