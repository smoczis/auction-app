package com.example.auctionapp.userAuction;

import com.example.auctionapp.auction.Auction;
import com.example.auctionapp.auction.AuctionDTO;

import java.math.BigDecimal;
import java.util.Map;

public interface UserAuctionService {

    Map<String, Object> createAuctionResponse(Auction auction);

    UserAuction getLastBidUser(Long auctionId);

    void liftPrice(AuctionDTO auctionDTO, BigDecimal currPrice) throws Exception;

}
