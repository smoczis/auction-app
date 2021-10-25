package com.example.auctionapp.userAuction;

import com.example.auctionapp.auction.Auction;
import com.example.auctionapp.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "USERSAUCTIONS")
@Getter
@Setter
@NoArgsConstructor
public class UserAuction {

    @EmbeddedId
    private UserAuctionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("auctionId")
    private Auction auction;

    @Column(name = "offered")
    private LocalDateTime offered;

    @Column
    private BigDecimal currentPrice;

    public UserAuction(User user, Auction auction) {
        this.user = user;
        this.auction = auction;
        this.offered = LocalDateTime.now();
        this.id = new UserAuctionId(user.getId(), auction.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserAuction that = (UserAuction) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(auction, that.auction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, auction);
    }

}
