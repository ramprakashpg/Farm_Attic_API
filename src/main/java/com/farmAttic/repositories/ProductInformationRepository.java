package com.farmAttic.repositories;

import com.farmAttic.models.ProductInfo;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ProductInformationRepository extends CrudRepository<ProductInfo,Integer> {
}
