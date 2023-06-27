package com.farmAttic.services;

import com.farmAttic.Dtos.CartDto;
import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.models.Cart;
import com.farmAttic.models.CartDetails;
import com.farmAttic.models.CartDetailsId;
import com.farmAttic.models.Product;
import com.farmAttic.repositories.CartDetailsRepository;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Singleton
@AllArgsConstructor
public class CartDetailService {

    public CartDetailsRepository cartDetailsRepository;
    public ProductService productService;
    private static final ModelMapper modelMapper = new ModelMapper();

    public ProductRequest addToCart(CartDto cart) {
        CartDetailsId cartDetailsId = modelMapper.map(cart, CartDetailsId.class);
        CartDetails userCartDetails = cartDetailsRepository.findById(cartDetailsId).orElse(new CartDetails());
        if (userCartDetails.getCartDetailsId() == null) {
            CartDetails cartDetails = modelMapper.map(cart, CartDetails.class);
            cartDetails.setCartDetailsId(cartDetailsId);
            cartDetails.setPrice(cart.getQuantity() * cart.getProduct().getPricePerUnit());
            cartDetailsRepository.save(cartDetails);
            userCartDetails=cartDetails;
        } else {
            Integer existingQty = userCartDetails.getQuantity();
            userCartDetails.setQuantity(existingQty + cart.getQuantity());
            userCartDetails.setPrice((existingQty + cart.getQuantity()) * cart.getProduct().getPricePerUnit());
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

    public List<CartDetails> getUserCartDetails(UUID cart) {
        return cartDetailsRepository.findByCartId(cart);

    }

    public CartDetails updateUserCart(Cart cart, UUID productId, Integer quantity) throws Throwable {
        CartDetailsId cartDetailsId = new CartDetailsId();
        Product product = productService.getProduct(productId);
        cartDetailsId.setCart(cart);
        cartDetailsId.setProduct(product);
        CartDetails cartDetails = getCartDetails(cartDetailsId);
        if(cartDetails.getCartDetailsId() != null){
            cartDetails.setQuantity(quantity);
            cartDetails = cartDetailsRepository.update(cartDetails);
        }
        return cartDetails;
    }

    private CartDetails getCartDetails(CartDetailsId cartDetailsId) throws Throwable {
        return cartDetailsRepository.findById(cartDetailsId).orElseThrow(new Supplier<Throwable>() {
            @Override
            public Throwable get() {
                return new Exception("Product not found");
            }
        });
    }

    public void deleteProductFromCart(Cart cart, UUID productId) throws Throwable {
        Product product = productService.getProduct(productId);
        CartDetailsId id = new CartDetailsId();
        id.setCart(cart);
        id.setProduct(product);
        CartDetails cartDetails = getCartDetails(id);
        cartDetailsRepository.delete(cartDetails);
    }
}
