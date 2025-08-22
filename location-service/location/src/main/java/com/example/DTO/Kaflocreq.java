package com.example.DTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Kaflocreq {
    private long userid;
    private double pickupLat;
    private double pickupLng;   
    private double dropLat;
    private double dropLng; 
    
    
}
