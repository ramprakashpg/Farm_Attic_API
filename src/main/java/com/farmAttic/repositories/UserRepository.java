package com.farmAttic.repositories;

import com.farmAttic.models.User;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@Repository

public interface UserRepository extends CrudRepository<User, UUID> {

    @Executable
    @Query("select user from User user where user.email =:email")
    User findByEmail(String email);
}
