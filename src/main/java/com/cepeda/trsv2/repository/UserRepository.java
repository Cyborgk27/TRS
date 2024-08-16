package com.cepeda.trsv2.repository;

import com.cepeda.trsv2.interfaces.IUserRepository;
import com.cepeda.trsv2.model.entities.User;
import com.cepeda.trsv2.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author CyborgK27
 */
public class UserRepository extends GenericRepository<User, Integer>
        implements IUserRepository {
    @PersistenceContext
    private final EntityManager entityManager = JpaUtil.getEntityManager();
    
    private final AtomicReference<User> currentUser = new AtomicReference<>();
    
    public UserRepository() {
        super(User.class);
    }
    
    @Override
    public CompletableFuture<Optional<User>> 
        login(String email, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                User user = entityManager.createQuery("SELECT u FROM User u "
                        + "WHERE u.email = :email AND u.password = :password",
                        User.class)
                                         .setParameter("email", email)
                                         .setParameter("password", password)
                                         .getSingleResult();
                currentUser.set(user);
                return Optional.ofNullable(user);
            } catch (Exception e) {
                return Optional.empty();
            }
        });
    }

    public CompletableFuture<Void> logout() {
        return CompletableFuture.runAsync(() -> {
            currentUser.set(null);
            System.out.println("User logged out");
        });
    }

    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(currentUser.get());
    }
}
