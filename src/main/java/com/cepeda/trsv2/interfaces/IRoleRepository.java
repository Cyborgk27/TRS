package com.cepeda.trsv2.interfaces;

import com.cepeda.trsv2.model.entities.Role;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author CyborgK27
 */
public interface IRoleRepository extends IGenericRepository<Role, Integer> {
    CompletableFuture<Optional<Role>> findByRoleName(String roleName);
}
