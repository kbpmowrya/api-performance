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
import java.util.concurrent.CompletableFuture;

@Component
public class ProductAsyncFacade {

    private static final Logger log = LoggerFactory.getLogger(ProductAsyncFacade.class);

    private final ProductService productService;

    private final InventoryService inventoryService;

    private final PriceService priceService;

    public ProductAsyncFacade(ProductService productService, InventoryService inventoryService, PriceService priceService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.priceService = priceService;
    }

    // Future for fetching the product details by productId
    private CompletableFuture<Optional<Product>> getProductById(Long productId) {
        return CompletableFuture.supplyAsync(() -> productService.findById(productId));
    }

    // Future for fetching the price details by productId
    public CompletableFuture<Optional<Price>> getPriceByProductId(Long productId) {
        return CompletableFuture.supplyAsync(() -> priceService.getPriceByProductId(productId));
    }

    // Future for fetching the inventory details by productId
    public CompletableFuture<Optional<Inventory>> getInventoryByProductId(Long productId) {
        return CompletableFuture.supplyAsync(() -> inventoryService.getInventoryByProductId(productId));
    }

    /**
     * Fetching product details asynchronously
     *
     * @param productId the input productId
     * @return ProductDetailsDTO
     */
    public Optional<ProductDetailDTO> getProductDetailById(Long productId) {
        // Fetch all asynchronously
        CompletableFuture<Optional<Product>> productFuture = getProductById(productId);
        CompletableFuture<Optional<Price>> priceFuture = getPriceByProductId(productId);
        CompletableFuture<Optional<Inventory>> inventoryFuture = getInventoryByProductId(productId);

        // Wait for all futures to complete
        CompletableFuture.allOf(productFuture, priceFuture, inventoryFuture).join();

        // Combine the results
        Optional<Product> productOpt = productFuture.join();
        Optional<Price> priceOpt = priceFuture.join();
        Optional<Inventory> inventoryOpt = inventoryFuture.join();

        int availableQuantity = inventoryOpt.map(Inventory::getAvailableQuantity).orElse(0);
        double price = priceOpt.map(Price::getPrice).orElse(0.0);

        // Build and return ProductDetail
        return productOpt.map(product -> new ProductDetailDTO(productId, product.getCategory().getName(),
                product.getName(), product.getDescription(), availableQuantity,
                price, product.getStatus()));
    }
}
