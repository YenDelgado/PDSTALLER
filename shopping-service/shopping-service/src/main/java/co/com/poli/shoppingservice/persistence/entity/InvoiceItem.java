package co.com.poli.shoppingservice.persistence.entity;

import co.com.poli.shoppingservice.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "invoice_items")
@Getter
@Setter
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double quantity;
    private Double price;
    private Long productId;
    @Transient
    private Product product;
    @Transient
    private Double subTotal;

    public Double getSubTotal(){
        if(this.price>0 && this.quantity>0){
            return this.quantity*this.price;
        }
        return (double) 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
