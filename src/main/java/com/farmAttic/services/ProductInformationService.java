package com.farmAttic.services;

import com.farmAttic.Dtos.ProductDetails;
import com.farmAttic.models.Product;
import com.farmAttic.repositories.ProductInformationRepository;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@Singleton
public class ProductInformationService {
    private final ProductInformationRepository productInformationRepository;
    private static final ModelMapper mapper = new ModelMapper();


    public ProductInformationService(ProductInformationRepository productInformationRepository) {
        this.productInformationRepository = productInformationRepository;
    }

    public Product saveProductInformation(ProductDetails productRequest) {
        Product product =new Product();
        product.setProductDescription(productRequest.getProductDescription());
        product.setProductName(productRequest.getProductName());
        product.setQuantity(productRequest.getQuantity());
        return productInformationRepository.save(product);
    }
}
