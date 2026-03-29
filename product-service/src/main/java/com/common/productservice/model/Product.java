package com.common.productservice.model;

import com.common.productservice.model.enums.ProductVariantType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    @Column(unique = true)
    protected String code;

    protected String name;
    protected String description;
    protected String title;
    protected String color;
    @ManyToOne
    @JoinColumn(name = "parent_product_id")
    private Product parentProduct;
    @OneToMany(mappedBy = "parentProduct")
    protected List<Product> variants;
    @Enumerated(EnumType.STRING)
    private ProductVariantType variantType;
    private String size;


}
