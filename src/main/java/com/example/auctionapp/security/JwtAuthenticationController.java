package com.example.auctionapp.security;

import com.example.auctionapp.config.JwtTokenUtil;
import com.example.auctionapp.user.JwtUserDetailsService;
import com.example.auctionapp.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private final JwtTokenUtil tokenUtil;
    private final JwtUserDetailsService service;

    @Autowired
    public JwtAuthenticationController(JwtTokenUtil tokenUtil, JwtUserDetailsService service) {
        this.tokenUtil = tokenUtil;
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createAuthToken(@RequestBody UserDTO user) throws Exception {
        service.save(user);
        return ResponseEntity.ok(createResponse(user.getUsername()));
    }

    private JwtResponse createResponse(String username) {
        final String token = tokenUtil.generateToken(service.loadUserByUsername(username));
        return new JwtResponse(token);
    }
}
