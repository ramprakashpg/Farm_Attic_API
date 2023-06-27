package com.farmAttic.repositories;

import com.farmAttic.models.CartDetails;
import com.farmAttic.models.CartDetailsId;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartDetailsRepository extends CrudRepository<CartDetails, CartDetailsId> {

    @Executable
    @Query("select userCartDetail from CartDetails userCartDetail where userCartDetail.cartDetailsId.cart.cartId=:cartId")
    List<CartDetails> findByCartId(UUID cartId);

}
