package com.farmAttic.services;

import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.models.Product;
import com.farmAttic.models.User;
import com.farmAttic.repositories.ProductRepository;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@Singleton
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private static final ModelMapper mapper = new ModelMapper();


    public ProductService(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public Product saveProductInformation(ProductRequest productRequest) {
        System.out.println("request"+productRequest.getUserId());
        Product product =new Product();
        User user=userService.getUser(productRequest.getUserId());
        product.setUser(user);
        product.setProductName(productRequest.getProductName());
        product.setQuantity(productRequest.getQuantity());
        product.setProductDescription(productRequest.getProductDescription());
        product.setPrice(productRequest.getPrice());
        return productRepository.save(product);
    }
}
