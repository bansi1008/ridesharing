package com.example.DTO;



import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reqdto {

   
    private double pickupLat;
    private double pickupLng;   
    private double dropLat;
    private double dropLng;

    
}
