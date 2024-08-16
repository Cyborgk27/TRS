package com.cepeda.trsv2.repository;

import com.cepeda.trsv2.interfaces.IGenericRepository;
import com.cepeda.trsv2.model.entities.BaseEntity;
import com.cepeda.trsv2.util.JpaUtil;
import com.cepeda.trsv2.util.StateTypes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author CyborgK27
 */
public abstract class GenericRepository<T extends BaseEntity<ID>, ID>
        implements IGenericRepository<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> persistentClass;

    public GenericRepository(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        this.entityManager = JpaUtil.getEntityManager();
    }

    /**
     *
     * @return
     */
    @Override
    public CompletableFuture<HashSet<T>> getAllAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new HashSet<>(entityManager
                        .createQuery("from " + persistentClass.getName(),
                                persistentClass)
                        .getResultList());
            } catch (Exception e) {
                e.printStackTrace();
                return new HashSet<>();
            }
        });
    }

    @Override
    public CompletableFuture<T> getByIdAsync(ID id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return entityManager.find(persistentClass, id);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    @Override
    @Transactional
    public CompletableFuture<Boolean> createAsync(T entity) {
        entity.setAuditCreateUserId(1);
        entity.setAuditCreateDate(LocalDateTime.now());
        return CompletableFuture.supplyAsync(() -> {
            try {
                entityManager.getTransaction().begin();
                entity.setState(StateTypes.ACTIVE.ordinal());
                entityManager.persist(entity);
                entityManager.getTransaction().commit();
                return true;
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                e.printStackTrace();
                return false;
            }
        });
    }

    @Override
    @Transactional
    public CompletableFuture<Boolean> updateAsync(T entity) {
        entity.setAuditUpdateUserId(1);
        entity.setAuditUpdateDate(LocalDateTime.now());
        return CompletableFuture.supplyAsync(() -> {
            try {
                entityManager.getTransaction().begin();
                entityManager.merge(entity);
                entityManager.getTransaction().commit();
                return true;
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                e.printStackTrace();
                return false;
            }
        });
    }

    @Override
    @Transactional
    public CompletableFuture<Boolean> removeAsync(ID id) {
        var entity = getByIdAsync(id).join();
        entity.setAuditRemoveUserId(1);
        entity.setAuditRemoveDate(LocalDateTime.now());
        entity.setState(StateTypes.INACTIVE.ordinal());
        return CompletableFuture.supplyAsync(() -> {
            try {
                entityManager.getTransaction().begin();
                entityManager.merge(entity);
                entityManager.getTransaction().commit();
                return true;
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                e.printStackTrace();
                return false;
            }
        });
    }
}