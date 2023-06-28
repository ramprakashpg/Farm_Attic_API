package com.farmAttic.repositories;

import com.farmAttic.models.Product;
import com.farmAttic.models.ProductImage;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImage, UUID> {

    @Executable
    @Query("select productImage from ProductImage  productImage where productImage.product=:product")
    List<ProductImage> findByProduct(Product product);

    @Executable
    @Query("delete from ProductImage productImage where productImage.product.productId=:productId")
    void deleteByProduct(UUID productId);
}
