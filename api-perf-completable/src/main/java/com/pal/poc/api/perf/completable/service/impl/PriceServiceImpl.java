package com.pal.poc.api.perf.completable.service.impl;

import com.pal.poc.api.perf.completable.entity.Price;
import com.pal.poc.api.perf.completable.repository.PriceRepository;
import com.pal.poc.api.perf.completable.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    private static final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);
    private final PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Optional<Price> getPriceByProductId(Long productId) {
        log.info("Getting price for the productId {}", productId);
        addDelay();
        return priceRepository.findByProductId(productId);
    }

    private void addDelay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
