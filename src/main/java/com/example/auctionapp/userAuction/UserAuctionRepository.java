package com.example.auctionapp.userAuction;

import com.example.auctionapp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuctionRepository extends JpaRepository<UserAuction, Long> {

    UserAuction findFirstByAuctionIdOrderByOfferedDesc(Long auctionId);

    List<UserAuction> findAllByUserId(String userId);

    List<UserAuction> findAllByUser(User user);
}
