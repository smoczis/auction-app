package com.example.auctionapp.product;

import com.example.auctionapp.aggregate.AggregateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final AggregateService service;

    @Autowired
    public ProductController(AggregateService service) {
        this.service = service;
    }

    @ApiOperation(value = "List all products",
            notes = "Get all products")
    @GetMapping
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(service.getAllProducts().stream().map(this::createNewProductResponse).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Create new Product",
            notes = "Require admin credentials to be able to create new Product; " +
                    "Admin credentials are stroed in encrypted properties")
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO newProduct) throws Exception{
        service.createNewProduct(newProduct);
        return ResponseEntity.ok(createNewProductResponse(service.findProductByName(newProduct.getProductName())));
    }

    private Map<String, Object> createNewProductResponse(Product newProduct) {
        Map<String, Object> retVal = new HashMap<>();
        retVal.put("productId", newProduct.getId());
        retVal.put("productName", newProduct.getProductName());
        retVal.put("productDescription", newProduct.getDescription());
        return retVal;
    }
}
