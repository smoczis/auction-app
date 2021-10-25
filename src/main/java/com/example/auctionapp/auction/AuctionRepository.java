package com.example.auctionapp.auction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    Auction findByProductId(Long productId);
}
