package com.example.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.example.Model.Locationmodel;

public interface Locationrepo extends JpaRepository<Locationmodel, Long>{
    Optional<Locationmodel> findBydriverid(Long driverid);
  
}
