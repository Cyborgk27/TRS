package com.cepeda.trsv2.interfaces;

import com.cepeda.trsv2.model.entities.Travel;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author CyborgK27
 */
public interface ITravelRepository extends
        IGenericRepository<Travel, Integer> {

    CompletableFuture<Optional<Travel>> 
        findByCodeTravelAsync(String codeTravel);

    CompletableFuture<List<Travel>> findByUserIdAsync(int userId);
}
