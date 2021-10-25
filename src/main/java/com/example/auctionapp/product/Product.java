package com.example.auctionapp.product;

import com.example.auctionapp.auction.Auction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "PRODUCTS")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String productName;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "product_auction",
            joinColumns =
                    {@JoinColumn(name = "product_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "auction_id", referencedColumnName = "id") })
    private Auction auction;

    public Product(String name, String description) {
        this.productName = name;
        this.description = description;
        this.price = BigDecimal.valueOf(0.01);
        this.price = price.setScale(2, RoundingMode.CEILING);
    }
}
