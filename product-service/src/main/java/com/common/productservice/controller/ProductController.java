package com.common.productservice.controller;

import com.common.productservice.dto.request.InboundProductRequestDTO;
import com.common.productservice.service.Impl.ProductServiceImpl;
import com.common.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final Logger log = Logger.getLogger(ProductServiceImpl.class.getName());
    @Autowired
    private final ProductService productService;

    @PostMapping("/inbound-product")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> createProduct(@RequestBody InboundProductRequestDTO productDTO) {
        if(Objects.isNull(productDTO)){
            log.severe("İnbound Product Request Cannot be empty");
        }
        try {
            productService.createOrUpdateProducts(productDTO);
            return ResponseEntity.ok("Product created successfully");
        }catch (Exception e){
         log.severe("Error during getting inbound products idocNo: " + productDTO.getIdocNo());
         return null;
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getSizeVariantProduct(@PathVariable String code) {
        return Optional.ofNullable(productService.getProductDetails(code))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable String code) {
        if(BooleanUtils.isTrue(productService.deleteProduct(code))){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Bir hata oluştu.");
    }

}
