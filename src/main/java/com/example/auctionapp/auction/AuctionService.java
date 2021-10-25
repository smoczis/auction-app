package com.example.auctionapp.auction;

import java.util.List;

public interface AuctionService {

    void save(Auction newAuction);

    List<Auction> getAllAuctions();

    Auction findByProduct(Long productId);
}
