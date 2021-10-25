package com.example.auctionapp.aggregate;

import com.example.auctionapp.auction.Auction;
import com.example.auctionapp.auction.AuctionService;
import com.example.auctionapp.product.Product;
import com.example.auctionapp.exception.ProductAlreadyExistsException;
import com.example.auctionapp.product.ProductDTO;
import com.example.auctionapp.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregateService {

    private final ProductService productService;
    private final AuctionService auctionService;

    @Autowired
    public AggregateService(ProductService productService, AuctionService auctionService) {
        this.productService = productService;
        this.auctionService = auctionService;
    }

    public void createNewProduct(ProductDTO productDto) throws Exception {
        Product product = productService.findProduct(productDto.getProductName());
        if (product == null) {
            Product newProduct = new Product(productDto.getProductName(), productDto.getDescription());
            Auction newAuction = new Auction(productDto.getHeartbeat());
            newProduct.setAuction(newAuction);
            newAuction.setProduct(newProduct);
            productService.save(newProduct);
            auctionService.save(newAuction);
        } else {
            throw new ProductAlreadyExistsException("PRODUCT ALREADY EXISTS");
        }
    }

    public Product findProductByName(String productName) {
        return productService.findProduct(productName);
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

}
