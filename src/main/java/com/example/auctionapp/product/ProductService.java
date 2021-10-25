package com.example.auctionapp.product;

import java.util.List;

public interface ProductService {

    Product findProduct(String productName);

    Product findProductById(Long id) throws Exception;

    void save(Product newProduct);

    List<Product> getAllProducts();

    void updatePrice(Product product);
}
