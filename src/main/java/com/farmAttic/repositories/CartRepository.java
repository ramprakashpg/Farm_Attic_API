package com.farmAttic.repositories;

import com.farmAttic.models.Cart;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends CrudRepository<Cart, UUID> {

    @Executable
    @Query("select user from Cart user where user.userInfo.userId=:userId")
    Optional<Cart> findByUserId(UUID userId);
}
