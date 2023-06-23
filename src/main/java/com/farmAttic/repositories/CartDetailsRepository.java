package com.farmAttic.repositories;

import com.farmAttic.models.CartDetails;
import com.farmAttic.models.CartDetailsId;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface CartDetailsRepository extends CrudRepository<CartDetails, CartDetailsId> {

}
