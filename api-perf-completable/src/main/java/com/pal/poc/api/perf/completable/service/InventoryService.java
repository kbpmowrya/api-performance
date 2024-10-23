package com.pal.poc.api.perf.completable.service;

import com.pal.poc.api.perf.completable.entity.Inventory;

import java.util.Optional;

public interface InventoryService {
    Optional<Inventory> getInventoryByProductId(Long productId);
}
