package com.farmAttic.repositories;

import com.farmAttic.models.ProductImageDetails;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImageDetails, UUID> {
}
