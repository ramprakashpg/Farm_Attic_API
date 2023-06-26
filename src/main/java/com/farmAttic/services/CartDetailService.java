package com.farmAttic.services;

import com.farmAttic.Dtos.CartDto;
import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.models.CartDetails;
import com.farmAttic.models.CartDetailsId;
import com.farmAttic.repositories.CartDetailsRepository;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@Singleton
@AllArgsConstructor
public class CartDetailService {

    public CartDetailsRepository cartDetailsRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    public ProductRequest addToCart(CartDto cart) {
        CartDetailsId cartDetailsId = modelMapper.map(cart, CartDetailsId.class);
        CartDetails userCartDetails = cartDetailsRepository.findById(cartDetailsId).orElse(new CartDetails());
        if (userCartDetails.getCartDetailsId() == null) {
            CartDetails cartDetails = modelMapper.map(cart, CartDetails.class);
            cartDetails.setCartDetailsId(cartDetailsId);
            cartDetails.setPrice(cart.getQuantity() * cart.getProduct().getPrice());
            cartDetailsRepository.save(cartDetails);
            userCartDetails=cartDetails;
        } else {
            Integer existingQty = userCartDetails.getQuantity();
            userCartDetails.setQuantity(existingQty + cart.getQuantity());
            userCartDetails.setPrice((existingQty + cart.getQuantity()) * cart.getProduct().getPrice());
            cartDetailsRepository.update(userCartDetails);
        }
        return getProductResponse(userCartDetails);
    }

    private ProductRequest getProductResponse(CartDetails userCartDetails) {
        ProductRequest productResponse =new ProductRequest();
        productResponse.setQuantity(userCartDetails.getQuantity());
        productResponse.setPrice(userCartDetails.getPrice());
        productResponse.setProductId(userCartDetails.getCartDetailsId().getProduct().getProductId());
        return productResponse;
    }
}
