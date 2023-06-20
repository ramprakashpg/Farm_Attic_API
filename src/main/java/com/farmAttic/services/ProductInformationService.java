package com.farmAttic.services;

import com.farmAttic.Dtos.ProductDetails;
import com.farmAttic.models.ProductInfo;
import com.farmAttic.repositories.ProductInformationRepository;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

import java.sql.SQLOutput;

@Singleton
public class ProductInformationService {
    private final ProductInformationRepository productInformationRepository;
    private static final ModelMapper mapper = new ModelMapper();


    public ProductInformationService(ProductInformationRepository productInformationRepository) {
        this.productInformationRepository = productInformationRepository;
    }

    public ProductInfo saveProductInformation(ProductDetails productRequest) {
        ProductInfo productInfo =new ProductInfo();
        productInfo.setProductDescription(productRequest.getProductDescription());
        productInfo.setProductName(productRequest.getProductName());
        productInfo.setQuantity(productRequest.getQuantity());
        return productInformationRepository.save(productInfo);
    }
}
