package com.example.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.Model.Requestride;

public interface Riderepo extends JpaRepository<Requestride, Long> {
    Optional<Requestride> findBydriverid(Long driverid);
    
}
