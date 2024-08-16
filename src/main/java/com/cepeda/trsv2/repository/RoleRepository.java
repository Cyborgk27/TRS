package com.cepeda.trsv2.repository;

import com.cepeda.trsv2.interfaces.IRoleRepository;
import com.cepeda.trsv2.model.entities.Role;
import com.cepeda.trsv2.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author CyborgK27
 */
public class RoleRepository extends GenericRepository<Role, Integer>
        implements IRoleRepository {

    @PersistenceContext
    private EntityManager entityManager = JpaUtil.getEntityManager();

    public RoleRepository() {
        super(Role.class);
    }

    @Override
    public CompletableFuture<Optional<Role>> findByRoleName(String roleName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Role role = entityManager.createQuery("SELECT r FROM Role r "
                        + "WHERE r.roleName = :roleName", Role.class)
                        .setParameter("roleName", roleName)
                        .getSingleResult();
                return Optional.ofNullable(role);
            } catch (Exception e) {
                return Optional.empty();
            }
        });
    }

}
