package com.farmAttic.repositories;

import com.farmAttic.models.ProductImageDetails;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImageDetails,Integer> {
}
