package com.example.auctionapp.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);

    Optional<Product> findById(Long id);
}
