package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {
    private final ProductDao productDao;

    @Autowired
    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping(value ="/products")
    public List<Product> listProducts() {
        return productDao.findAll();
    }

    @GetMapping(value ="/products/{id}")
    public Product showProduct(@PathVariable int id) {
        return productDao.findById(id);
    }

    @PostMapping(value ="/products")
    public ResponseEntity<Void> addProduct(@RequestBody Product product) {
        Product productAdded = productDao.save(product);
        if (null == productAdded) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
