package com.example.Controller;
import com.example.Servicelayer.Registerlayer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.DTO.Message;
import com.example.DTO.Locdto;
import com.example.DTO.Registerdto;
import com.example.DTO.DriverProfileResponseDTO;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.core.Authentication;
import com.example.Config.Security;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/")


public class Test {

  @Autowired
  private Registerlayer registerlayer;

    @PostMapping("/driver/register")
    public String testuser(Authentication authentication, @Valid @RequestBody Registerdto register) {

        if (authentication == null) {
            return "No authentication found.";
        }
        
        boolean isDriver = authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_DRIVER"));

        if (isDriver) {
            

        registerlayer.registerDriver(register);
            return "Driver registration successful.";
        } else {
            return "User is not a driver.";
        }
       
      
    
    }
    @PutMapping("/driver/livelocation")
    public String Location(Authentication authentication, @Valid @RequestBody Locdto locdto) {
        if (authentication == null) {
            return "No authentication found.";
        }
        
        boolean isDriver = authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_DRIVER"));

        if (isDriver) {
           
            registerlayer.locationupdate(locdto);
           
        } else {
            return "User is not a driver.";
        }
        return "Driver location updated successfully.";
    }

        @GetMapping("driver/me") 
     public ResponseEntity<DriverProfileResponseDTO> me(Authentication authentication)
     {
            if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        boolean isDriver = authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_DRIVER"));

        if (!isDriver) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } 
       DriverProfileResponseDTO profile = registerlayer.getCurrentDriverProfile();
    return ResponseEntity.ok(profile);       
     }
   
    
}
