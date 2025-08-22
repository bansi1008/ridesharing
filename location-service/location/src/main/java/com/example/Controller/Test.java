package com.example.Controller;
import com.example.Model.Locationmodel;
import com.example.Repository.Locationrepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.DTO.DriverLocationEvent;
import com.example.Utility.JWT;
import com.example.DTO.Kafkadto;
import com.example.Kafka.LocationEventProducer;
import com.example.DTO.Reqdto;
import com.example.Repository.Riderepo;
import com.example.Model.Requestride;
import com.example.DTO.Kaflocreq;
import com.example.Kafka.Userkafka;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;



@RestController
@RequestMapping("/auth/api")
public class Test {

@Autowired
private Locationrepo locrepo;

@Autowired
private Riderepo riderepo;
   
@Autowired
private  LocationEventProducer producer;

@Autowired
private ObjectMapper objectMapper; 

@Autowired
private JWT jwt;

@Autowired
private Userkafka userkafka;


   

    @PostMapping("/location")
    public String location(HttpServletRequest request,@Valid @RequestBody DriverLocationEvent dto){
      
         Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

         System.out.println("userid"+userId);
        
Locationmodel locmodel = Locationmodel.builder()
               .driverid(userId)
               .lat(dto.getLatitude())
               .lang(dto.getLongitude())
               .build();
                
       locrepo.save(locmodel);

       Kafkadto kafkadto = Kafkadto.builder()
               .driverId(userId)
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .timestamp(LocalDateTime.now().toInstant(java.time.ZoneOffset.UTC))
                .build();

      producer.sendDriverLocation(kafkadto);
        return "location changed succesfully";
}





    @PostMapping("/locationrequest")
    public String locationRequest(@Valid @RequestBody Reqdto reqdto) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();



    Requestride requestride = Requestride.builder()
            .userid(userId)
        .pickupLat(reqdto.getPickupLat())
        .pickupLng(reqdto.getPickupLng())  
        .dropLat(reqdto.getDropLat())
        .dropLng(reqdto.getDropLng())      
        .status("PENDING")
        .requestedAt(LocalDateTime.now())
        .build();
        
            riderepo.save(requestride);

         Kaflocreq kaflocreq = Kaflocreq.builder()
            .userid(userId)
            .pickupLat(reqdto.getPickupLat())
            .pickupLng(reqdto.getPickupLng())
            .dropLat(reqdto.getDropLat())
            .dropLng(reqdto.getDropLng())
            .build();

            userkafka.sendUserLocation(kaflocreq);
        

        
            return "location request sent successfully";
        }

    
    
    
}

