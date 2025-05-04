package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.model.Verification;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.repository.VerificationRepository;
import net.javaguides.springboot.security.JwtUtil;
import net.javaguides.springboot.security.userVerification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private userVerification verifyUser;

    private final PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton("USER"));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/logininit/{param}")
    public ResponseEntity<?> loginInitUser(@RequestBody User user, @PathVariable String param) {

        if(param.equals("resend")){
            verifyUser.sendVerificationMail(user);
            return ResponseEntity.ok("Verification email sent. Please check your inbox.");
        }
        
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());

        if(dbUser.isPresent() && passwordEncoder.matches(user.getPassword(), dbUser.get().getPassword())) {
            verifyUser.sendVerificationMail(dbUser.get());
            return ResponseEntity.ok("Verification email sent. Please check your inbox.");
        }

        return ResponseEntity.badRequest().body("Invalid email or password");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUsers(@RequestBody String code) {
        
        Verification verification = verificationRepository.findByVerificationCode(code.trim());

        if(verification == null){
            return ResponseEntity.badRequest().body("Invalid Verification Code");
        }
        else{
            verification.setVerificationStatus(true);
            verification.setVerificationCode(null);
            verificationRepository.save(verification);

            Optional<User> UserOpt = userRepository.findByEmail(verification.getEmail());
            if(UserOpt.isPresent()){
                String token = jwtUtil.generateToken(UserOpt.get().getEmail());
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.badRequest().body("User Not Found!");
        }
        
    }
}