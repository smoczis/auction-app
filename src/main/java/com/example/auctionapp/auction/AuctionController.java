package com.example.auctionapp.auction;

import com.example.auctionapp.product.Product;
import com.example.auctionapp.product.ProductService;
import com.example.auctionapp.userAuction.UserAuctionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;
    private final UserAuctionService uaService;
    private final ProductService productService;


    @Autowired
    public AuctionController(AuctionService auctionService, UserAuctionService uaService, ProductService productService) {
        this.auctionService = auctionService;
        this.uaService = uaService;
        this.productService = productService;
    }

    @ApiOperation(value = "Get all auctions",
            notes = "Retrieve all auctions; " +
                    "Returns custom response")
    @GetMapping
    public ResponseEntity<?> getAllAuctions() {
        return ResponseEntity.ok(auctionService.getAllAuctions().stream().map(uaService::createAuctionResponse));
    }

    @ApiOperation(value = "Participate in auction",
            notes = "User (based on userToken) takes part in auction; " +
                    "User's balance is reduced by 1 BID and product price is being increased by 0.01" +
                    "Returns custom response")
    @PostMapping
    public ResponseEntity<?> offer(@RequestBody AuctionDTO auctionDTO) throws Exception {
        Product product = productService.findProductById(auctionDTO.getProductId());
        productService.updatePrice(product);
        uaService.liftPrice(auctionDTO, product.getPrice());
        return ResponseEntity.ok(null);

    }


}
