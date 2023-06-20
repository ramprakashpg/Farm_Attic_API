package com.farmAttic.services;

import com.farmAttic.Dtos.ProductDetails;
import com.farmAttic.models.ProductImageDetails;
import com.farmAttic.models.ProductInfo;
import jakarta.inject.Singleton;
import com.farmAttic.repositories.ProductImageRepository;

@Singleton
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public ProductDetails saveProductImage(ProductDetails productRequest, ProductInfo productInformationResponse) {
        ProductImageDetails productImageDetails=new ProductImageDetails();
        productImageDetails.setImageData(productRequest.getImageData());
        productImageDetails.setProductInfo(productInformationResponse);
        ProductImageDetails productImageDetailsResponse = productImageRepository.save(productImageDetails);
        ProductDetails productDetailsResponse = new ProductDetails();
        productDetailsResponse.setProductName(productImageDetailsResponse.getProductInfo().getProductName());
        productDetailsResponse.setImageData(productRequest.getImageData());
        return productDetailsResponse;
    }
}
