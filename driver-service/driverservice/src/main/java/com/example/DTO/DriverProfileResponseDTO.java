package com.example.DTO;
import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class DriverProfileResponseDTO  {

    private Long userId;
    private String name;
    private String licenseNumber;
    private String vehicleType;
    private String vehiclePlate;
    private String status;           
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}
