package com.pal.poc.api.perf.completable.repository;

import com.pal.poc.api.perf.completable.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
