package com.example.Model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "register") 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder



public class Registermodel {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userid;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private String vehicleType;
    @Column(nullable = false, unique = true)
    private String vehiclePlate;
    @Column(nullable = false, columnDefinition = "varchar(20) default 'offline'")
    private String status = "offline";
    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
    @Column(nullable = false)
    private java.time.LocalDateTime updatedAt = java.time.LocalDateTime.now();



    
}

