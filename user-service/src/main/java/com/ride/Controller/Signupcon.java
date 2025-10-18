package com.ride.Controller;
import com.ride.DTO.SignupDto;
import com.ride.DTO.LoginDTO;
import com.ride.DTO.MessageDTO;
import com.ride.DTO.SignUpresponsedto;
import com.ride.Utility.JWT;

import io.jsonwebtoken.Claims;
import com.ride.Model.Signupmodel;
import com.ride.Servicelayer.Signuplayer;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/api")
@Slf4j


public class Signupcon {

    @Autowired
    private Signuplayer signuplayer;

    private final JWT jwtUtility;

    public Signupcon(JWT jwtUtility) {
        this.jwtUtility = jwtUtility;
        
    }

    @PostMapping("/signup")
    public SignUpresponsedto signup(@Valid @RequestBody  SignupDto signupDto) {
        
        // Signupmodel signupModel = Signupmodel.builder()
        //         .email(signupDto.getEmail())
        //         .password(signupDto.getPassword())
        //         .name(signupDto.getName())
        //         .phone(signupDto.getPhone())
        //         .role(signupDto.getRole())
        //         .build();

       
    Signupmodel user=  signuplayer.signup(signupDto);

        return  new SignUpresponsedto("User registered successfully and user id is"+ user.getId().toString());
    }

    @PostMapping("/login")
    public ResponseEntity<MessageDTO> login(@Valid @RequestBody LoginDTO loginDTO,HttpServletResponse response) {

     String token= signuplayer.login(loginDTO.getEmail(), loginDTO.getPassword());
     String refreshToken = jwtUtility.generateRefreshToken(loginDTO.getEmail());

     Cookie cookie = new Cookie("ridesite_token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); 
        cookie.setSecure(true); 
        response.addCookie(cookie);

        Cookie refreshCookie = new Cookie("ridesite_refresh", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/api/auth/refresh");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60);
        refreshCookie.setSecure(true);
        response.addCookie(refreshCookie);

      log.info("User {} logged in successfully", loginDTO.getEmail());

        return ResponseEntity.ok( new MessageDTO("Login successful")); 
     }

        @GetMapping("/me")
        public MessageDTO getCurrentUser(HttpServletRequest request) {
            Claims claims = (Claims) request.getAttribute("claims");
            if (claims == null) {
                return new MessageDTO("No user information available");
            }
            String email = claims.getSubject();
            String name = claims.get("name", String.class);
            String role = claims.get("role", String.class);
            
            return new MessageDTO("Current user: " + name + " (" + email + ") with role: " + role);
        }

        @PostMapping("/logout")
        public ResponseEntity<MessageDTO> logout(HttpServletResponse response) {
            Cookie cookie = new Cookie("ridesite_token", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(0); 
            response.addCookie(cookie);

            return ResponseEntity.ok(new MessageDTO("Logout successful"));
        }

    
}
