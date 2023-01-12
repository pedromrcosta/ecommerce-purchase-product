package com.pedrocosta.ecommerceproject.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "Purchase.findByProductType",
        query = "SELECT DISTINCT p FROM Purchase p JOIN p.products pp JOIN pp.product pr WHERE pr.type = :type")
@Entity
@Data
@Table(name = "purchases")
@NoArgsConstructor
public class Purchase implements BaseEntity<Purchase> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "dt_purchase")
    private LocalDate purchaseDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "dt_delivery")
    private LocalDate deliveryDate;

    @Column(name = "observations")
    private String deliveryObs;

    @Column(name = "delivery_city")
    private String deliveryCity;

    @Column(name = "client_name")
    private String clientName;

    @JsonManagedReference
    @JsonBackReference
    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PurchaseProduct> products = new ArrayList<>();

    public Purchase(LocalDate purchaseDate, LocalDate deliveryDate, String clientName) {
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.clientName = clientName;
    }


}
