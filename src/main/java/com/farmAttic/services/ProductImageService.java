package com.farmAttic.services;

import com.farmAttic.Dtos.ProductImageDto;
import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.models.Product;
import com.farmAttic.models.ProductImage;
import com.farmAttic.repositories.ProductImageRepository;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public ProductRequest saveProductImage(ProductRequest productRequest, Product product) {
        List<ProductImageDto> imageDtoList=new ArrayList<>();
        for(ProductImageDto productImageDto : productRequest.getImageList()) {
            ProductImage productImage = new ProductImage();
            productImage.setImageData(productImageDto.getImageData());
            productImage.setProduct(product);
            ProductImage productImage1 = productImageRepository.save(productImage);
            ProductImageDto productImageResponse=new ProductImageDto();
            productImageResponse.setImageData(productImage1.getImageData());
            imageDtoList.add(productImageResponse);
        }
        return  getProductDetailsResponse(imageDtoList,product);
    }

    private ProductRequest getProductDetailsResponse(List<ProductImageDto> imageDtoList, Product product) {
        ProductRequest productResponse=new ProductRequest();
        productResponse.setUserId(product.getUser().getUserId());
        productResponse.setProductName(product.getProductName());
        productResponse.setProductDescription(product.getProductDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setImageList(imageDtoList);
        return productResponse;
    }


}
