package com.farmAttic.services;

import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.Dtos.ProductImageDto;
import com.farmAttic.models.Product;
import com.farmAttic.models.ProductImage;
import com.farmAttic.models.User;
import com.farmAttic.repositories.ProductImageRepository;
import com.farmAttic.repositories.ProductRepository;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;
    private final UserAuthService userAuthService;
    private static final ModelMapper mapper = new ModelMapper();


    public ProductService(ProductRepository productRepository, ProductImageRepository productImageRepository, UserAuthService userAuthService) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.userAuthService = userAuthService;
    }

    public Product saveProductInformation(ProductDto productRequest) {
        Product product=new Product();
        User user=userAuthService.getUser(productRequest.getUserId());
        product.setUser(user);
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        return productRepository.save(product);
    }

    public List<ProductDto> getProducts() {
        List<Product> products=productRepository.findAll();
        List<ProductDto> productResponse =new ArrayList<>();
        products.forEach(product ->{
            ProductDto response = mapper.map(product,ProductDto.class);
            List<ProductImageDto> imageDtoList=getProductImages(product);
            response.setImageList(imageDtoList);
            productResponse.add(response);
        });
        return productResponse;
    }

    private List<ProductImageDto> getProductImages(Product product) {
        List<ProductImageDto> imageDtoList=new ArrayList<>();
        List<ProductImage> productImages=productImageRepository.findByProduct(product);
        productImages.forEach(productImage -> {
            ProductImageDto productImageDto=new ProductImageDto();
            productImageDto.setImageData(productImage.getImageData());
            imageDtoList.add(productImageDto);
        });
        return imageDtoList;
    }

}
