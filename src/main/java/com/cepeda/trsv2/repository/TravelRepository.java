package com.cepeda.trsv2.repository;

import com.cepeda.trsv2.interfaces.ITravelRepository;
import com.cepeda.trsv2.model.entities.Travel;
import com.cepeda.trsv2.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author CyborgK27
 */
public class TravelRepository extends GenericRepository<Travel, Integer> 
        implements ITravelRepository {

    @PersistenceContext
    private EntityManager entityManager = JpaUtil.getEntityManager();

    public TravelRepository() {
        super(Travel.class);
    }

    @Override
    public CompletableFuture<Optional<Travel>> 
        findByCodeTravelAsync(String codeTravel) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Travel travel = entityManager.createQuery(
                        "SELECT t FROM Travel t WHERE t.codeTravel = "
                                + ":codeTravel", Travel.class)
                        .setParameter("codeTravel", codeTravel)
                        .getSingleResult();
                return Optional.ofNullable(travel);
            } catch (Exception e) {
                return Optional.empty();
            }
        });
    }

    public CompletableFuture<List<Travel>> findByUserIdAsync(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Limpia el contexto de persistencia para asegurar 
                //datos frescos
                entityManager.clear();

                // Realiza la consulta
                List<Travel> travels = entityManager
                        .createQuery("SELECT t FROM Travel t WHERE t.user.id = "
                                + ":userId", Travel.class)
                        .setParameter("userId", userId)
                        .getResultList();

                return travels; // Devuelve la lista de viajes
            } catch (Exception e) {
                e.printStackTrace(); // Imprime la excepción para depuración
                return List.of(); // Devuelve una lista vacía en caso de error
            }
        });
    }
}
