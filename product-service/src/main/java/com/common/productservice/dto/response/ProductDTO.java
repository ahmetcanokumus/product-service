package com.common.productservice.dto.response;

import com.common.productservice.model.enums.ProductVariantType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private String code;
    private String name;
    private String description;
    private String size;
    private Boolean isActive;
    private String color;
    private List<ProductDTO> variants;
    private ProductVariantType variantType;
}
