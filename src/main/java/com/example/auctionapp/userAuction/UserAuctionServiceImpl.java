package com.example.auctionapp.userAuction;

import com.example.auctionapp.auction.Auction;
import com.example.auctionapp.auction.AuctionDTO;
import com.example.auctionapp.auction.AuctionService;
import com.example.auctionapp.auction.AuctionStatus;
import com.example.auctionapp.exception.AuctionAlreadyFinishedException;
import com.example.auctionapp.exception.NoSufficientBidsException;
import com.example.auctionapp.exception.UserAlreadyWinningException;
import com.example.auctionapp.user.User;
import com.example.auctionapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAuctionServiceImpl implements UserAuctionService {
    private final UserAuctionRepository repository;
    private final UserService userService;
    private final AuctionService auctionService;

    @Autowired
    public UserAuctionServiceImpl(UserAuctionRepository repository, UserService userService, AuctionService auctionService) {
        this.repository = repository;
        this.userService = userService;
        this.auctionService = auctionService;
    }

    public Map<String, Object> createAuctionResponse(Auction auction) {
        updateStatus(auction);
        Map<String, Object> retVal = new HashMap<>();
        retVal.put("product", auction.getProduct().getProductName());
        retVal.put("currentPrice", auction.getProduct().getPrice());
        retVal.put("status", auction.getStatus());
        retVal.put("timeToFinish", String.format("%d s", getTimeToFinishInSeconds(auction)));
        User currWinning = getCurrentlyWinningUser(auction);
        retVal.put("currentlyWinningUser", currWinning != null ? currWinning.getUsername() : "NOT YET BID");
        return retVal;
    }

    public UserAuction getLastBidUser(Long auctionId) {
        return this.repository.findFirstByAuctionIdOrderByOfferedDesc(auctionId);
    }

    private User getCurrentlyWinningUser(Auction auction) {
        UserAuction currWinning = getLastBidUser(auction.getId());
        return currWinning != null ? currWinning.getUser() : null;
    }

    public void liftPrice(AuctionDTO auctionDTO, BigDecimal currPrice) throws Exception {
        User user = userService.findUserById(auctionDTO.getUserToken());
        Auction auction = auctionService.findByProduct(auctionDTO.getProductId());
        if (auction.getStatus() == AuctionStatus.FINISHED) {
            throw new AuctionAlreadyFinishedException("AUCTION FOR PRODUCT ALREADY FINISHED");
        }
        if (user.getBids() == 0) {
            throw new NoSufficientBidsException("NOT ENOUGH BIDS");
        }
        if (getCurrentlyWinningUser(auction) == user) {
            throw new UserAlreadyWinningException("USER HAS ALREADY THE HIGHEST CURRENT OFFER");
        }
        user.setBids(user.getBids() - 1);
        UserAuction ua = new UserAuction(user, auction);
        ua.setCurrentPrice(currPrice);
        userService.save(user);
        auctionService.save(auction);
        repository.save(ua);
    }

    private long getTimeToFinishInSeconds(Auction auction) {
        UserAuction ua = repository.findFirstByAuctionIdOrderByOfferedDesc(auction.getId());
        if (ua != null) {
            long secsPassed = ChronoUnit.SECONDS.between(LocalDateTime.now(), ua.getOffered().plusSeconds(auction.getHeartbeat()));
            return secsPassed > 0 ? secsPassed : 0;
        }
        return auction.getHeartbeat();
    }

    private void updateStatus(Auction auction) {
        if (getLastBidUser(auction.getId()) == null) {
            auction.setStatus(AuctionStatus.NOT_STARTED);
        } else if (getTimeToFinishInSeconds(auction) == 0) {
            auction.setStatus(AuctionStatus.FINISHED);
        } else {
            auction.setStatus(AuctionStatus.IN_PROGRESS);
        }
    }
}
