package com.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * @author Philémon Globléhi <philemon.globlehi@gmail.com>
 */
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    @Length(min = 3, max = 20, message = "The length of the name must be between 3 and 20 characters")
    private String name;

    @Min(value = 1, message = "The price must be greater than or equal to 1")
    private int price;

    @JsonIgnore
    private int buyingPrice;

    public Product() {
    }

    public Product(int id, String name, int price, int buyingPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.buyingPrice = buyingPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(int buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", buyingPrice=" + buyingPrice +
                '}';
    }
}
