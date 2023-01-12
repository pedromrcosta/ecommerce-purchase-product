package com.pedrocosta.ecommerceproject.entities;

import com.pedrocosta.ecommerceproject.enums.PRODUCT_TYPE;
import com.pedrocosta.ecommerceproject.enums.MESSAGES;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor

public class Product implements BaseEntity<Product> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 8)
	private String code;

	@Column(name = "barcode", length = 13)
	private String barcode;

	@NotBlank(message = MESSAGES.NAME_NOT_EMPTY)
	@Column(length = 30)
	private String name;

	@Enumerated(EnumType.ORDINAL)
	private PRODUCT_TYPE type;

	@NotNull
	@Column(columnDefinition="Decimal(7,2)")
	private Double price;


	public Product(String name, Double price) {
		this.name = name;
		this.price = price;
	}

	public Product(String name, PRODUCT_TYPE type, Double price) {
		this.name = name;
		this.price = price;
		this.type = type;
	}

	public Product(Integer id, String name, PRODUCT_TYPE type, Double price) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
	}

	@Override
	public Integer getId() {
		return id;
	}
}

