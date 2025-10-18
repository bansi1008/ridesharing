package com.ride.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.ride.Model.Signupmodel;

public interface Signuprepo extends JpaRepository<Signupmodel, Long> {
    
    
     Optional<Signupmodel> findByEmail(String email);
    
    

    
}
     