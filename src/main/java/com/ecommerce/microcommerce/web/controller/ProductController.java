package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.exceptions.ProductNotFoundException.ProductNotFoundException;
import com.ecommerce.microcommerce.model.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @author Philémon Globléhi <philemon.globlehi@gmail.com>
 */
@RestController
@RequestMapping(name = "api_products_", path = "/api/v1/rest/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Api( description="API pour les opérations CRUD sur les produits.")
public class ProductController {
    private final ProductDao productDao;

    @Autowired
    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping(name ="list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Récupère les produits en stock!")
    public List<Product> listProducts() {
        return productDao.findAll();
    }

    @GetMapping(value ="/{id}", name = "read")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    public Optional<Product> readProduct(@PathVariable int id) {
        return productDao.findById(id);
    }

    @GetMapping(value = "/{priceLimit}/limits", name = "price_limit")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Récupère les produit en stock dont le prix est supérieur au paramètre saisit!")
    public List<Product> listProductsWhosePriceGreaterThan(@PathVariable int priceLimit) {
        return productDao.findByPriceGreaterThan(priceLimit);
    }

    @GetMapping(value = "/{name}/search", name = "search")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Récupère les produits en stock qui contiennent les caractères saisient!")
    public List<Product> searchProductByName(@PathVariable String name) {
        return productDao.findByNameLike("%"+name+"%");
    }

    @PostMapping(name = "create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Créer un produit en stock!")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody Product product) {
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

    @PutMapping(name = "update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Mettre a jour un produit en stock!")
    public void updateProduct(@RequestBody Product product) {
        productDao.save(product);
    }

    @DeleteMapping(value = "/{id}", name = "remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Supprimer un produit grâce à son ID à condition que celui-ci soit en stock!")
    public void removeProduct(@PathVariable int id) {
        productDao.deleteById(id);
    }

}
