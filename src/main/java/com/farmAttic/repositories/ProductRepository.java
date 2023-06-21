package com.farmAttic.repositories;

import com.farmAttic.models.Product;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {

    @Executable
    @Query("select product from Product product")
    List<Product> findAll();
}
