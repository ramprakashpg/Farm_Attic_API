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
    private final UserAuthService userAuthService;
    private static final ModelMapper mapper = new ModelMapper();


    public ProductService(ProductRepository productRepository, UserAuthService userAuthService) {
        this.productRepository = productRepository;
        this.userAuthService = userAuthService;
    }

    public Product saveProductInformation(ProductRequest productRequest) {
        Product product=new Product();
        User user=userAuthService.getUser(productRequest.getUserId());
        product.setUser(user);
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        return productRepository.save(product);
    }
}
