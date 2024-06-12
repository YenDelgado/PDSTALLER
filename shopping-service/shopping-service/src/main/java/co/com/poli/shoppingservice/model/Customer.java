package co.com.poli.shoppingservice.model;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String nit;
    private String name;
    private String email;
}
