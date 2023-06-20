package com.farmAttic.repositories;

import com.farmAttic.models.Product;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface ProductInformationRepository extends CrudRepository<Product, UUID> {
}
