package com.pal.poc.api.perf.completable.facade;

import com.pal.poc.api.perf.completable.dto.ProductDetailDTO;
import com.pal.poc.api.perf.completable.entity.Inventory;
import com.pal.poc.api.perf.completable.entity.Price;
import com.pal.poc.api.perf.completable.entity.Product;
import com.pal.poc.api.perf.completable.service.InventoryService;
import com.pal.poc.api.perf.completable.service.PriceService;
import com.pal.poc.api.perf.completable.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductFacade {

    private static final Logger log = LoggerFactory.getLogger(ProductFacade.class);

    private final ProductService productService;

    private final InventoryService inventoryService;

    private final PriceService priceService;

    public ProductFacade(ProductService productService, InventoryService inventoryService, PriceService priceService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.priceService = priceService;
    }

    /**
     * Getting product details
     *
     * @param productId the input productId
     * @return ProductDetailDTO
     */
    public Optional<ProductDetailDTO> getProductDetail(Long productId) {
        log.info("Facade for getting product detail for the productId {}", productId);
        Optional<Product> productOpt = productService.findById(productId);
        if (productOpt.isEmpty()) {
            return Optional.empty();
        }
        // Fetch Price detail for the product
        Optional<Price> priceOpt = priceService.getPriceByProductId(productId);

        // Fetch Inventory detail for the product
        Optional<Inventory> inventoryOpt = inventoryService.getInventoryByProductId(productId);

        // Combine the details into ProductDetail if product exists
        return productOpt.map(product -> {
            Price price = priceOpt.orElse(null);
            Inventory inventory = inventoryOpt.orElse(null);
            assert inventory != null;
            assert price != null;
            return new ProductDetailDTO(productId, product.getCategory().getName(),
                    product.getName(), product.getDescription(), inventory.getAvailableQuantity(),
                    price.getPrice(), product.getStatus());
        });
    }
}
