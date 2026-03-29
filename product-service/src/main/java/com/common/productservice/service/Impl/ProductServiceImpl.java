package com.common.productservice.service.Impl;

import com.common.productservice.dto.request.BaseProductRequestDTO;
import com.common.productservice.dto.request.InboundProductRequestDTO;
import com.common.productservice.dto.request.SizeVariantProductRequestDTO;
import com.common.productservice.dto.request.StyleVariantProductRequestDTO;
import com.common.productservice.dto.response.ProductDTO;
import com.common.productservice.model.Product;

import com.common.productservice.model.enums.ProductVariantType;
import com.common.productservice.repository.ProductRepository;
import com.common.productservice.service.ProductService;
import com.common.productservice.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final Logger log = Logger.getLogger(ProductServiceImpl.class.getName());
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void createOrUpdateProducts(InboundProductRequestDTO request) {
        for(BaseProductRequestDTO baseProductDTO : request.getProducts()){
            List<Product> styleVariants = new ArrayList<>();
            Product baseProduct= createOrUpdateBaseProduct(baseProductDTO);
            for(StyleVariantProductRequestDTO styleVariantProductRequestDTO : baseProductDTO.getStyleVariants()){
                List<Product> sizeVariants = new ArrayList<>();
                Product styleVariantProduct = checkStyleVariantProduct(baseProduct,styleVariantProductRequestDTO);
                styleVariants.add(styleVariantProduct);
                for(SizeVariantProductRequestDTO sizeVariantProductRequestDTO : styleVariantProductRequestDTO.getSizeVariants()){
                    Product sizeVariantProduct = checkSizeVariantProduct(styleVariantProduct,sizeVariantProductRequestDTO);
                    sizeVariants.add(sizeVariantProduct);
                }
                styleVariantProduct.setVariants(sizeVariants);
            }
            baseProduct.setVariants(styleVariants);
        }

    }

    private Product createOrUpdateBaseProduct(BaseProductRequestDTO requestDTO){
        Product baseProduct = productRepository.findByCode(requestDTO.getCode());
        if (Objects.isNull(baseProduct)){
            Product newProduct = new Product();
            newProduct.setCode(requestDTO.getCode());
            newProduct.setName(requestDTO.getName());
            newProduct.setVariantType(ProductVariantType.BASE_PRODUCT);
            newProduct.setDescription(requestDTO.getDescription());
            newProduct.setTitle(requestDTO.getTitle());
            productRepository.save(newProduct);
            return newProduct;
        }
        baseProduct.setName(requestDTO.getName());
        baseProduct.setDescription(requestDTO.getDescription());
        productRepository.save(baseProduct);
        return baseProduct;
    }

    private Product checkStyleVariantProduct(Product baseProduct, StyleVariantProductRequestDTO requestDTO) {
        Product styleVariant = productRepository.findByCode(requestDTO.getCode());
        if (Objects.isNull(styleVariant)) {
            Product newProduct = new Product();
            newProduct.setCode(requestDTO.getCode());
            newProduct.setName(requestDTO.getName());
            newProduct.setColor(requestDTO.getColor());
            newProduct.setDescription(requestDTO.getDescription());
            newProduct.setVariantType(ProductVariantType.STYLE_VARIANT);
            newProduct.setParentProduct(baseProduct);
            productRepository.save(newProduct);
            return newProduct;
        } else {
            styleVariant.setName(requestDTO.getName());
            styleVariant.setDescription(requestDTO.getDescription());
            styleVariant.setColor(requestDTO.getColor());
            styleVariant.setParentProduct(baseProduct);
            productRepository.save(styleVariant);
            return styleVariant;
        }
    }

    private Product checkSizeVariantProduct(Product styleVariantProduct , SizeVariantProductRequestDTO requestDTO){
        Product sizeVariantProduct = productRepository.findByCode(requestDTO.getCode());
        if (Objects.isNull(sizeVariantProduct)) {
            Product newProduct = new Product();
            newProduct.setCode(requestDTO.getCode());
            newProduct.setName(requestDTO.getName());
            newProduct.setSize(requestDTO.getSize());
            newProduct.setDescription(requestDTO.getDescription());
            newProduct.setVariantType(ProductVariantType.SIZE_VARIANT);
            newProduct.setParentProduct(styleVariantProduct);
            productRepository.save(newProduct);
            return newProduct;
        } else {
            sizeVariantProduct.setName(requestDTO.getName());
            sizeVariantProduct.setDescription(requestDTO.getDescription());
            sizeVariantProduct.setSize(requestDTO.getSize());
            productRepository.save(sizeVariantProduct);
            return sizeVariantProduct;
        }
    }

    @Override
    public ProductDTO getProductDetails(String productCode) {
        try {
            Product sizeVariantProduct = productRepository.findByCode(productCode);
            if(Objects.isNull(sizeVariantProduct)){
                log.info("Product not found for productCode : " + productCode);
            }
            return productMapper.toDto(sizeVariantProduct);
        }catch (Exception e){
            log.severe("Error during getting product for productCode: " + productCode);
        }
        return null;

    }

    @Override
    public boolean deleteProduct(String productCode) {
        try {
            Product product = productRepository.findByCode(productCode);
            if (Objects.nonNull(product)) {
                productRepository.delete(product);
                return Boolean.TRUE;
            } else {
                log.info("Product not found for delete productCode" + productCode);
                return Boolean.FALSE;
            }
        }catch (Exception e){
            log.severe("Error during deleting product for productCode" + productCode);
            return Boolean.FALSE;
        }
    }


}
