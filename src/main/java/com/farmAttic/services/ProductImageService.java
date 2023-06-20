package com.farmAttic.services;

import com.farmAttic.Dtos.ProductDetails;
import com.farmAttic.models.Product;
import com.farmAttic.models.ProductImageDetails;
import com.farmAttic.repositories.ProductImageRepository;
import jakarta.inject.Singleton;

@Singleton
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public ProductDetails saveProductImage(ProductDetails productRequest, Product productInformationResponse) {
        ProductImageDetails productImageDetails=new ProductImageDetails();
        productImageDetails.setImageData(productRequest.getImageData());
        productImageDetails.setProduct(productInformationResponse);
        ProductImageDetails productImageDetailsResponse = productImageRepository.save(productImageDetails);
        ProductDetails productDetailsResponse = new ProductDetails();
        productDetailsResponse.setProductName(productImageDetailsResponse.getProduct().getProductName());
        productDetailsResponse.setImageData(productRequest.getImageData());
        return productDetailsResponse;
    }
}
