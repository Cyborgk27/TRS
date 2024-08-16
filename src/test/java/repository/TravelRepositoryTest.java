package repository;

import com.cepeda.trsv2.model.entities.Travel;
import com.cepeda.trsv2.model.entities.User;
import com.cepeda.trsv2.repository.TravelRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 * Pruebas unitarias para la clase {@link TravelRepository}.
 * <p>
 * Esta clase de prueba verifica los métodos del repositorio de viajes relacionados con la búsqueda de viajes 
 * por código y por ID de usuario. Se utilizan mocks para simular la interacción con el {@link EntityManager} 
 * y se realizan aserciones para confirmar que los métodos de la clase repositorio funcionan como se espera.
 * </p>
 * 
 * @author CyborgK27
 */
class TravelRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TravelRepository travelRepository;

    /**
     * Configura los mocks antes de cada prueba.
     * Inicializa los mocks necesarios para las pruebas.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba el método {@link TravelRepository#findByCodeTravelAsync(String)}.
     * <p>
     * Verifica que el método retorna un {@link Optional} que contiene un objeto {@link Travel} 
     * con el código de viaje especificado.
     * </p>
     */
    @Test
    void testFindByCodeTravelAsync() {
        String codeTravel = "TR123";
        Travel travel = new Travel();
        travel.setCodeTravel(codeTravel);

        TypedQuery<Travel> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Travel.class)))
                .thenReturn(query);
        when(query.setParameter(eq("codeTravel"), anyString()))
                .thenReturn(query);
        when(query.getSingleResult()).thenReturn(travel);

        CompletableFuture<Optional<Travel>> future = travelRepository
                .findByCodeTravelAsync(codeTravel);
        Optional<Travel> result = future.join();

        assertTrue(result.isPresent());
        assertEquals(codeTravel, result.get().getCodeTravel());
    }

    /**
     * Prueba el método {@link TravelRepository#findByUserIdAsync(int)}.
     * <p>
     * Verifica que el método retorna una lista de objetos {@link Travel} asociados con el ID de usuario especificado.
     * </p>
     */
    @Test
    void testFindByUserIdAsync() {
        int userId = 1;

        Travel travel = new Travel();
        travel.setUser(new User());
        travel.getUser().setId(userId);

        Travel travel2 = new Travel();
        travel2.setUser(new User());
        travel2.getUser().setId(userId);

        List<Travel> travels = List.of(travel, travel2);

        TypedQuery<Travel> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Travel.class)))
                .thenReturn(query);
        when(query.setParameter(eq("userId"), anyInt())).thenReturn(query);
        when(query.getResultList()).thenReturn(travels);

        CompletableFuture<List<Travel>> future = travelRepository
                .findByUserIdAsync(userId);
        List<Travel> result = future.join();

        assertEquals(2, result.size());
        assertTrue(result.contains(travel));
        assertTrue(result.contains(travel2));
    }
}
