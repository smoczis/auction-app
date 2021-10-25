package com.example.auctionapp.userAuction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserAuctionId implements Serializable {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "auction_id")
    private Long auctionId;

    @Override
    public int hashCode() {
        return Objects.hash(userId, auctionId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserAuctionId that = (UserAuctionId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(auctionId, that.auctionId);
    }
}
