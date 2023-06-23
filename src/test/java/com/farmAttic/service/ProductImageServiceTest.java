package com.farmAttic.service;

import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.models.Product;
import com.farmAttic.models.ProductImage;
import com.farmAttic.models.User;
import com.farmAttic.repositories.ProductImageRepository;
import com.farmAttic.services.ProductImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ProductImageServiceTest {
    private ProductImageRepository productImageRepository;
    private ProductImageService productImageService;

    ProductDto productRequest = new ProductDto();


    ProductImage productImage=new ProductImage();
    UUID uuid = UUID.randomUUID();

    User user = new User();

    Product product=new Product();

    @BeforeEach
    void beforeEach() {
        productImageRepository=mock(ProductImageRepository.class);
        productImageService=new ProductImageService(productImageRepository);

        List<byte[]> productImageDtoList = new ArrayList<>();
        productRequest.setProductDescription("description");
        productRequest.setProductName("productName");
        productRequest.setPrice(13);
        productRequest.setQuantity(13);
        productRequest.setUserId(uuid);
        byte[] byteArray = new byte[36];
        productImageDtoList.add(byteArray);
        productRequest.setImageList(productImageDtoList);

        user.setUserId(uuid);
        user.setEmail("smssaddepalli@gmail.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");

        product = Product.builder().productId(uuid).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).price(productRequest.getPrice()).user(user).build();

        productImage.setProduct(product);
        productImage.setImageId(uuid);
        productImage.setImageData(byteArray);
    }

    @Test
    void shouldSaveProductImage() {
        when(productImageRepository.save(productImage)).thenReturn(productImage);

        productImageService.save(productImage);

        verify(productImageRepository).save(productImage);
    }

    @Test
    void shouldAbleToFindTheProductById() {
        List<ProductImage> productImages=new ArrayList<>();
        productImages.add(productImage);

        when(productImageRepository.findByProduct(product)).thenReturn(productImages);

        productImageService.findByProduct(product);

        verify(productImageRepository).findByProduct(product);
    }

    @Test
    void shouldDeleteProductImages() {

        doNothing().when(productImageRepository).deleteByProduct(product.getProductId());

        productImageService.deleteImages(product.getProductId());

        verify(productImageRepository).deleteByProduct(product.getProductId());
    }
}
