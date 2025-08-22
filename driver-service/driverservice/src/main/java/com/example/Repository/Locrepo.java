package com.example.Repository;
import com.example.Model.Locationmodel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface Locrepo extends JpaRepository<Locationmodel, Long> {
    
    Optional<Locationmodel> findBydriverId(Long driverId);

    

    
}
