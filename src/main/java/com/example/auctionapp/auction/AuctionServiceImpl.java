package com.example.auctionapp.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository repository;

    @Autowired
    public AuctionServiceImpl(AuctionRepository repository) {
        this.repository = repository;
    }

    public void save(Auction newAuction) {
        this.repository.save(newAuction);
    }

    public List<Auction> getAllAuctions() {
        return this.repository.findAll();
    }

    public Auction findByProduct(Long productId) {
        return this.repository.findByProductId(productId);
    }

}
