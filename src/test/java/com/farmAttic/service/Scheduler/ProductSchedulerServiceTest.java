package com.farmAttic.service.Scheduler;


import com.farmAttic.services.ProductService;
import com.farmAttic.services.schedulers.ProductSchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProductSchedulerServiceTest {

    ProductService productService = mock(ProductService.class);

    ProductSchedulerService productSchedulerService;


    @BeforeEach
    void beforeEach() {
        productSchedulerService=new ProductSchedulerService(productService);
    }

    @Test
    void shouldClearQuantityOfExpiredProducts() {
        productSchedulerService.clearExpiredProductQuantity();

        verify(productService).clearProductQuantity(any(Date.class));
    }
}
