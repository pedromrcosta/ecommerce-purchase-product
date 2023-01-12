package com.pedrocosta.ecommerceproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "purchase_product")
public class PurchaseProduct implements BaseEntity<PurchaseProduct> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    private int quantity;

    @Column(name = "unit_price")
    private Double unPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @Override
    public Integer getId() {
        return id;
    }
}
