package com.example.DTO;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Registerdto {

    
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must be less than 100 characters")
  private String name;
    @NotBlank(message = "License number cannot be blank")
    private String licenseNumber;
    @NotBlank(message = "Vehicle type cannot be blank")
    private String vehicleType;
    @NotBlank(message = "Vehicle plate cannot be blank")
    private String vehiclePlate;
    private String status = "offline";
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
    private java.time.LocalDateTime updatedAt = java.time.LocalDateTime.now();

    
}
