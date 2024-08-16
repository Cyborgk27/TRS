package com.cepeda.trsv2.interfaces;

import com.cepeda.trsv2.model.entities.User;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author CyborgK27
 */
public interface IUserRepository extends IGenericRepository<User, Integer> {
    CompletableFuture<Optional<User>> login(String email, String password);
    CompletableFuture<Void> logout();
}
