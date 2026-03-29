package com.common.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseProductRequestDTO {
    private String code;
    private String name;
    private String description;
    private String title;
    private List<StyleVariantProductRequestDTO> styleVariants;
}
