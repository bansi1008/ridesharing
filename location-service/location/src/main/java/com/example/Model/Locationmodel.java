package com.example.Model;
import lombok.*;
import jakarta.annotation.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "driverlocationhistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Locationmodel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="driver_id",nullable = false)
    private Long driverid;
    @Column(name="latitude",nullable = false)
    private String lat;
    @Column(name="longitude",nullable = false)
    private String lang;

    
}
