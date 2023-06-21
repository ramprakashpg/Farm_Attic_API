package com.farmAttic.services;

import com.farmAttic.Dtos.ProductRequest;
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

    public ProductRequest saveProductImage(ProductRequest productRequest, Product productInformationResponse) {
        ProductImageDetails productImageDetails=new ProductImageDetails();
        productImageDetails.setImageData(productRequest.getImageData());
        productImageDetails.setProduct(productInformationResponse);
        ProductImageDetails productImageDetailsResponse = productImageRepository.save(productImageDetails);
        ProductRequest productRequestResponse = new ProductRequest();
        productRequestResponse.setProductName(productImageDetailsResponse.getProduct().getProductName());
        productRequestResponse.setImageData(productRequest.getImageData());
        return productRequestResponse;
    }
}
