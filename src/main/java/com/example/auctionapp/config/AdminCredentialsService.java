package com.example.auctionapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminCredentialsService {

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.password}")
    private String password;

    public String getAdminName() {
        return adminName;
    }

    public String getPassword() {
        return password;
    }
}
