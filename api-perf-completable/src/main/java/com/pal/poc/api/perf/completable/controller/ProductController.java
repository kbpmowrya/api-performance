package com.pal.poc.api.perf.completable.controller;

import com.pal.poc.api.perf.completable.dto.ProductDetailDTO;
import com.pal.poc.api.perf.completable.facade.ProductFacade;
import com.pal.poc.api.perf.completable.facade.ProductAsyncFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductFacade productFacade;
    private final ProductAsyncFacade productAsyncFacade;

    public ProductController(ProductFacade productFacade, ProductAsyncFacade productAsyncFacade) {
        this.productFacade = productFacade;
        this.productAsyncFacade = productAsyncFacade;
    }

    @GetMapping("/{id}/sync")
    public ResponseEntity<ProductDetailDTO> getProductSync(@PathVariable Long id) {
        log.info("Rest request to get product by id sync: {}", id);
        return ResponseEntity.ok(productFacade.getProductDetail(id).orElseThrow());
    }

    @GetMapping("/{id}/async")
    public ResponseEntity<ProductDetailDTO> getProductAsync(@PathVariable Long id) {
        log.info("Rest request to get product by id async: {}", id);
        return ResponseEntity.ok(productAsyncFacade.getProductDetailById(id).orElseThrow());
    }
}
