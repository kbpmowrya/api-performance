package com.pal.poc.api.perf.completable.service;

import com.pal.poc.api.perf.completable.entity.Product;

import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Long id);
}
