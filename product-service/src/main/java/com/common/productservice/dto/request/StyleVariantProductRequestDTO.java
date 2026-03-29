package com.common.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StyleVariantProductRequestDTO {
    private String code;
    private String color;
    private Boolean isActive;
    private String description;
    private String name;
    private List<SizeVariantProductRequestDTO> sizeVariants;
}
