package com.farmAttic.services.schedulers;

import com.farmAttic.services.ProductService;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;


@Singleton
public class ProductSchedulerService {

    private final ProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSchedulerService.class);

    public ProductSchedulerService(ProductService productService) {
        this.productService = productService;
    }

    @Scheduled(fixedDelay = "1d")
    public void clearExpiredProductQuantity(){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        LOGGER.info("Cleaning up the expired product quantity");
        Date currentDate= calendar.getTime();
        productService.clearProductQuantity(currentDate);
    }

}
