package com.ride.Servicelayer;

import com.ride.Model.Signupmodel;
import com.ride.DTO.SignupDto;
import com.ride.Repository.Signuprepo;
import com.ride.Utility.JWT;
import com.ride.Model.Role;
import com.ride.CustomException.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpStatus;

@Service
@Slf4j
public class Signuplayer {

    @Autowired
    private Signuprepo signupRepo;

    // private final HttpStatus status;

    @Autowired
    private JWT jwtUtility;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Signupmodel signup(SignupDto dto) {

        signupRepo.findByEmail(dto.getEmail())
                .ifPresent(existingUser -> {
                    throw new AppException("User with email " + dto.getEmail() + " already exists",
                            HttpStatus.CONFLICT);
                });

        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        Signupmodel signupModel = Signupmodel.builder()
                .email(dto.getEmail())
                .password(encryptedPassword)
                .name(dto.getName())
                .phone(dto.getPhone())
                .role(dto.getRole())
                .build();

        Signupmodel saveduser = signupRepo.save(signupModel);

        log.info("User registered successfully with ID: {}", saveduser.getId());

        return saveduser;

    }

    public String login(String email, String password) {
        Signupmodel user = signupRepo.findByEmail(email)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AppException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
        System.out.println("Generating token for user: " + user.getRole());
        return jwtUtility.generateToken(
                user.getEmail(),
                user.getRole(),
                user.getName(),
                user.getId());

    }

}
