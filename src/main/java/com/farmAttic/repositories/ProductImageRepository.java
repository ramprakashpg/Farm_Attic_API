package com.farmAttic.repositories;

import com.farmAttic.models.ProductImage;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImage, UUID> {
}
