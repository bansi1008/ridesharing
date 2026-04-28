package com.example.Repository;

import com.example.Model.Registermodel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface driverCheck extends JpaRepository<Registermodel, Long> {

    Optional<Registermodel> findByVehiclePlate(String vehiclePlate);

}
