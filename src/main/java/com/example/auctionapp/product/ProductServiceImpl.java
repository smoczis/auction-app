package com.example.auctionapp.product;

import com.example.auctionapp.exception.NoSuchProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    public Product findProduct(String productName) {
        return this.repository.findByProductName(productName);
    }

    public Product findProductById(Long id) throws Exception {
        return this.repository.findById(id).orElseThrow(() -> new NoSuchProductException("PRODUCT WITH GIVEN ID DOES NOT EXIST"));
    }

    public void save(Product newProduct) {
        repository.save(newProduct);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void updatePrice(Product product) {
        product.setPrice(product.getPrice().add(BigDecimal.valueOf(0.01)));
        repository.save(product);
    }
}
