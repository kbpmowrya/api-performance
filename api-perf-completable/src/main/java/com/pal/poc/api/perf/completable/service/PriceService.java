package com.pal.poc.api.perf.completable.service;

import com.pal.poc.api.perf.completable.entity.Price;

import java.util.Optional;

public interface PriceService {
    Optional<Price> getPriceByProductId(Long productId);
}
