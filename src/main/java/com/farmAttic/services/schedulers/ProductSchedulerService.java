package com.farmAttic.services.schedulers;

import com.farmAttic.services.ProductService;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Singleton
public class ProductSchedulerService {

    private final ProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSchedulerService.class);

    public ProductSchedulerService(ProductService productService) {
        this.productService = productService;
    }

    @Scheduled(fixedDelay = "10s")
    public void clearExpiredProductQuantity(){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        LOGGER.info("Cleaning up the expired activity logs");
        Date currentDate= calendar.getTime();
        productService.clearProductQuantity(currentDate);
    }

}
