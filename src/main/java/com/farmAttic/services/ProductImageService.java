package com.farmAttic.services;

import com.farmAttic.models.Product;
import com.farmAttic.models.ProductImage;
import com.farmAttic.repositories.ProductImageRepository;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.UUID;

@Singleton
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public ProductImage save(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    public List<ProductImage> findByProduct(Product product) {
        return productImageRepository.findByProduct(product);
    }

    public void deleteImages(UUID productId) {
        productImageRepository.deleteByProduct(productId);
    }
}
