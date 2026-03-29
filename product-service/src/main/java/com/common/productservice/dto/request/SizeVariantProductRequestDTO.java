package com.common.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeVariantProductRequestDTO {
    private String code;
    private String size;
    private String name;
    private String description;
    private Boolean isActive;
}
