package com.common.productservice.service;

import com.common.productservice.dto.request.InboundProductRequestDTO;
import com.common.productservice.dto.response.ProductDTO;
import com.common.productservice.model.Product;

public interface ProductService {
    void createOrUpdateProducts(InboundProductRequestDTO request);
    ProductDTO getProductDetails(final String productCode);
    boolean deleteProduct(String productCode);
}

