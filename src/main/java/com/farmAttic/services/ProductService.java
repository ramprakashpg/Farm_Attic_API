package com.farmAttic.services;

import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.models.Product;
import com.farmAttic.models.ProductImage;
import com.farmAttic.models.User;
import com.farmAttic.repositories.ProductRepository;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductImageService productImageService;
    private final UserAuthService userAuthService;
    private final CartService cartService;
    private static final ModelMapper modelMapper = new ModelMapper();


    public ProductDto saveProductInformation(ProductDto productRequest) {
        User user = userAuthService.getUser(productRequest.getUserId());
        Product product = modelMapper.map(productRequest, Product.class);
        product.setUser(user);
        productRepository.save(product);
        return saveImage(product, productRequest);
    }

    public List<ProductDto> getUserProducts(UUID userId) {
        List<ProductDto> productsResponse = getProducts();
        return productsResponse.stream().filter(eachProduct -> eachProduct.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public List<ProductDto> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productsResponse = new ArrayList<>();
        products.forEach(product -> {
            ProductDto response = modelMapper.map(product, ProductDto.class);
            List<byte[]> imageDtoList = getProductImages(product);
            response.setImageList(imageDtoList);
            productsResponse.add(response);
        });
        return productsResponse;
    }

    public ProductRequest saveToCart(ProductRequest productRequest, String loggedInUserEmail) {
        Product product = productRepository.findById(productRequest.getProductId()).orElse(new Product());
        User currentUser = userAuthService.getCurrentUser(loggedInUserEmail);
        if (product.getProductId() != null) {
            return cartService.addToCart(product, currentUser, productRequest);
        }
          return new ProductRequest();
    }

    private ProductDto saveImage(Product product, ProductDto productRequest) {
        for (byte[] productImageDto : productRequest.getImageList()) {
            ProductImage productImage = new ProductImage();
            productImage.setImageData(productImageDto);
            productImage.setProduct(product);
            productImageService.save(productImage);
        }
        return getProductResponse(product);
    }

    private ProductDto getProductResponse(Product product) {
        ProductDto productResponse = modelMapper.map(product, ProductDto.class);
        List<byte[]> imageDtoList = getProductImages(product);
        productResponse.setImageList(imageDtoList);
        return productResponse;
    }


    private List<byte[]> getProductImages(Product product) {
        List<byte[]> imageDtoList = new ArrayList<>();
        List<ProductImage> productImages = productImageService.findByProduct(product);
        productImages.forEach(productImage -> {
            imageDtoList.add(productImage.getImageData());
        });
        return imageDtoList;
    }


    public ProductDto updateProduct(UUID productId, ProductDto productRequest) {
        Product product = productRepository.findById(productId).orElseThrow();
        User user = userAuthService.getUser(productRequest.getUserId());
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setPricePerUnit(productRequest.getPricePerUnit());
        product.setQuantity(productRequest.getQuantity());
        product.setUser(user);
        product.setExpiryDate(productRequest.getExpiryDate());
        productRepository.update(product);
        return updateImage(product, productRequest);
    }

    private ProductDto updateImage(Product product, ProductDto productRequest) {
        if (productRequest.getImageList().size() != 0) {
            productImageService.deleteImages(product.getProductId());
            return saveImage(product, productRequest);
        } else {
            productImageService.deleteImages(product.getProductId());
            return getProductResponse(product);
        }
    }
}
