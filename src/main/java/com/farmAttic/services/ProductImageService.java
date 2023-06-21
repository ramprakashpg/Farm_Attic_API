package com.farmAttic.services;

import com.farmAttic.Dtos.ProductImageDto;
import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.models.Product;
import com.farmAttic.models.ProductImage;
import com.farmAttic.repositories.ProductImageRepository;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private static final ModelMapper modelMapper = new ModelMapper();


    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public ProductDto saveProductImage(ProductDto productRequest, Product product) {
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

    private ProductDto getProductDetailsResponse(List<ProductImageDto> imageDtoList, Product product) {
        ProductDto productResponse = modelMapper.map(product, ProductDto.class);
        productResponse.setImageList(imageDtoList);
        return productResponse;
    }


}
