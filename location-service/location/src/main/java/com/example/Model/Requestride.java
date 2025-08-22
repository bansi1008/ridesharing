package com.example.Model;
import lombok.*;

import java.time.LocalDateTime;


import jakarta.annotation.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;


@Entity
@Table(name = "riderequest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Requestride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="user_id",nullable = false)
    private long  userid;
    @Column(name="pickup_latitude", nullable = false)
    private double pickupLat;
@Column(name="pickup_longitude", nullable = false)
private double pickupLng;
    @Column(name="dropoff_latitude", nullable = false)
    private double dropLat;
   @Column(name="dropoff_longitude", nullable = false)
private double dropLng;
    @Column(name="status", nullable = false)
    private String status = "PENDING";
    @Column(name="requested_at", nullable = false)
    private LocalDateTime requestedAt;
    @Column(name="matched_driver_id")
    private Long driverid;
    @Column(name="matched_at")
    private LocalDateTime matchedAt;
    @Column(name="completed_at")
    private LocalDateTime completedAt;

  @PrePersist
    public void prePersist() {
        if (requestedAt == null) requestedAt = LocalDateTime.now();
    }
    
}
