package com.common.productservice.service.mapper;

import com.common.productservice.dto.response.ProductDTO;
import com.common.productservice.model.Product;
import org.mapstruct.Mapper;

import javax.annotation.processing.Generated;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-09-16T20:29:28+0300",
        comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.jar, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Mapper(componentModel = "spring")

public interface ProductMapper {
    ProductDTO toDto(Product product);
}
