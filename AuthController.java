package com.example.project.controller;

import com.example.project.dto.AuthRequest;
import com.example.project.dto.AuthResponse;
import com.example.project.model.LoyaltyStatus;
import com.example.project.model.Role;
import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import com.example.project.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Claims; // 👈 Import the Claims class


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/check")
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

            // Generate token
            String token = jwtService.generateToken(userDetails);
           
         // 👇 ADD THESE LOGGING STATEMENTS 👇
            System.out.println("-------------------------------------------");
            System.out.println("Generated JWT: " + token);

            // Extract and print the payload (claims)
            Claims claims = jwtService.extractAllClaims(token);
            System.out.println("Token Payload (Claims): " + claims.toString());
            System.out.println("-------------------------------------------");
            // 👆 END OF LOGGING STATEMENTS 👆
            
            return ResponseEntity.ok(new AuthResponse(token));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
//        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
//            return ResponseEntity.badRequest().body("Username already taken");
//        }
//
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        // user.setRole(Role.USER);
//
//        userRepository.save(user);
//        return ResponseEntity.ok("User registered successfully");
//    }
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody AuthRequest request) {
    	 if (!userRepository.findByUsername(request.getUsername()).isPresent()) {
             return ResponseEntity.badRequest().body("User Dosen`t exist!!!!!");
         }
    	 
    	 User user = new User();
//         user.setUsername(request.getUsername());
         
         user.setPhoneNo(request.getPhoneNo());
         
         user.setEmail(request.getEmail());
         
         user.setPassword(passwordEncoder.encode(request.getPassword()));
         
         Role role = request.getRole();
         // Check if the role is null or just an empty string ""
         if (role == null ) {
         role = Role.ROLE_USER; // Assign the default value
         }
         user.setRole(role);

         // ✅ set defaults
         LoyaltyStatus loyalty = request.getLoyalty();
         if (loyalty == null ) {
         	loyalty = LoyaltyStatus.no;
         }
         user.setLoyalty(loyalty);
         
         user.setAddresses(request.getAddresses());
         
         Integer loyaltyPoints = request.getLoyaltyPoints();
         if(loyaltyPoints == null) {
        	 loyaltyPoints = 0;
         }
         user.setLoyaltyPoints(loyaltyPoints);

         userRepository.save(user);
    	 
    	return ResponseEntity.ok("User updated successfully");
    
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        
        user.setPhoneNo(request.getPhoneNo());
        
        user.setEmail(request.getEmail());
        
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        Role role = request.getRole();
        // Check if the role is null or just an empty string ""
        if (role == null ) {
        role = Role.ROLE_USER; // Assign the default value
        }
        user.setRole(role);

        // ✅ set defaults
        LoyaltyStatus loyalty = request.getLoyalty();
        if (loyalty == null ) {
        	loyalty = LoyaltyStatus.no;
        }
        user.setLoyalty(loyalty);
        
        user.setAddresses(request.getAddresses());
        
        user.setLoyaltyPoints(0);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

}
