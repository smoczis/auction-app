package com.example.auctionapp.auction;

import com.example.auctionapp.userAuction.UserAuction;
import com.example.auctionapp.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AUCTIONS")
@NoArgsConstructor
@Getter
@Setter
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private LocalDateTime dateStarted;

    private int heartbeat;

    @OneToOne(mappedBy = "auction")
    private Product product;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private Set<UserAuction> userAuction = new HashSet<>();

    @Column
    private AuctionStatus status;

    public Auction(int heartbeat) {
        this.heartbeat = heartbeat;
        this.status = AuctionStatus.NOT_STARTED;
        this.dateStarted = null;
    }

    public void addAuction(UserAuction ua) {
        userAuction.add(ua);
    }

}
