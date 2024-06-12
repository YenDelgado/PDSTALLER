package co.com.poli.shoppingservice.model;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private Double stock;
    private Double price;
}
