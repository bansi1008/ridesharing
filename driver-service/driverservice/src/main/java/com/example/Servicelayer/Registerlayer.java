package com.example.Servicelayer;
import com.example.DTO.Registerdto;
import com.example.DTO.Locdto;
import com.example.Repository.Registerrepo;
import com.example.Repository.Locrepo;
import com.example.Model.Registermodel;
import com.example.Model.Locationmodel;
import com.example.Utility.JWT;
import com.example.DTO.DriverProfileResponseDTO;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class Registerlayer {
    @Autowired
    private Registerrepo registerrepo;

    @Autowired
    private JWT jwt;

    @Autowired
     private Locrepo locrepo;

    public String getTokenFromContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
        throw new RuntimeException("No authentication found");
    }
    Object details = authentication.getDetails();
    if (details instanceof String) {
        return (String) details; 
    }
    throw new RuntimeException("Token not found in authentication details");
}

    public String registerDriver(Registerdto register) {
        String token = getTokenFromContext();
        
        Registermodel registerr = Registermodel.builder()
                .userid(jwt.getUserId(token))
                .name(register.getName())
                .licenseNumber(register.getLicenseNumber())
                .vehicleType(register.getVehicleType())
                .vehiclePlate(register.getVehiclePlate())
                .status(register.getStatus())
                .createdAt(register.getCreatedAt() != null ? register.getCreatedAt() : java.time.LocalDateTime.now())
                .updatedAt(register.getUpdatedAt() != null ? register.getUpdatedAt() : java.time.LocalDateTime.now())
                .build();
        registerrepo.save(registerr);
        return "Driver registration successful";
    }
    
    public String locationupdate(Locdto locdto) {
        String token = getTokenFromContext();
        Long userIdd = jwt.getUserId(token);
        
       Locationmodel loc = Locationmodel.builder()
                      .driverId(userIdd)
                      .latitude(locdto.getLatitude())
                      .longitude(locdto.getLongitude())
                      
                      .build();

             locrepo.save(loc);

        return "Driver location updated successfully";
    }

   public  DriverProfileResponseDTO getCurrentDriverProfile(){
    String token = getTokenFromContext(); 
    Long userId = jwt.getUserId(token);

    Registermodel driver = registerrepo.findByUserid(userId)
        .orElseThrow(() -> new RuntimeException("Driver profile not found"));

   
    return DriverProfileResponseDTO.builder()
            .userId(driver.getUserid())
            .name(driver.getName())
            .licenseNumber(driver.getLicenseNumber())
            .vehicleType(driver.getVehicleType())
            .vehiclePlate(driver.getVehiclePlate())
            .status(driver.getStatus())
            .createdAt(driver.getCreatedAt())
            .updatedAt(driver.getUpdatedAt())
            .build();
   }
}
