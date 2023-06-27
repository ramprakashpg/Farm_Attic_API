package com.farmAttic.repositories;

import com.farmAttic.models.Cart;
import com.farmAttic.models.CartDetails;
import com.farmAttic.models.CartDetailsId;
import com.farmAttic.models.Product;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface CartDetailsRepository extends CrudRepository<CartDetails, CartDetailsId> {


    @Executable
    @Query("select cd from CartDetails cd where cd.cartDetailsId.cart=:cart")
    List<CartDetails> getDetails(Cart cart);
}
