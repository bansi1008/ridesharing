package com.example.Model;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "driverlocationhistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Locationmodel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "driver_id")
     private Long driverId;



    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    

   
   



    
}
