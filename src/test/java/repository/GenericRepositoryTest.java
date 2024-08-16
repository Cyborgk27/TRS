package repository;

import com.cepeda.trsv2.model.entities.Travel;
import com.cepeda.trsv2.repository.TravelRepository;
import com.cepeda.trsv2.util.StateTypes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 * Pruebas unitarias para la clase {@link TravelRepository}.
 * <p>
 * Esta clase de prueba verifica las operaciones CRUD asincrónicas en el repositorio de viajes.
 * Se utilizan mocks para simular las interacciones con la base de datos y se realizan
 * aserciones para confirmar que los métodos de la clase repositorio funcionan como se espera.
 * </p>
 * 
 * @author CyborgK27
 */
class GenericRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private TravelRepository travelRepository;

    /**
     * Configura los mocks y los comportamientos predeterminados antes de cada prueba.
     * Inicializa los mocks y configura el {@link EntityManager} para devolver un {@link EntityTransaction} simulado.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(entityManager.getTransaction()).thenReturn(transaction);
    }

    /**
     * Prueba el método {@link TravelRepository#getAllAsync()}.
     * <p>
     * Verifica que el método retorna la lista esperada de objetos {@link Travel}.
     * </p>
     */
    @Test
    void testGetAllAsync() {
        // Crea entidades de ejemplo
        Travel travel1 = new Travel();
        Travel travel2 = new Travel();
        HashSet<Travel> travels = new HashSet<>();
        travels.add(travel1);
        travels.add(travel2);

        // Mockea el TypedQuery
        TypedQuery<Travel> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Travel.class)))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>(travels));

        // Llama al método que se está probando
        CompletableFuture<HashSet<Travel>> future = travelRepository
                .getAllAsync();
        HashSet<Travel> result = future.join();

        // Verifica los resultados
        assertEquals(2, result.size());
        assertTrue(result.contains(travel1));
        assertTrue(result.contains(travel2));
    }

    /**
     * Prueba el método {@link TravelRepository#getByIdAsync(int)}.
     * <p>
     * Verifica que el método retorna un objeto {@link Travel} no nulo para un ID dado.
     * </p>
     */
    @Test
    void testGetByIdAsync() {
        int id = 1;
        Travel travel = new Travel();

        when(entityManager.find(eq(Travel.class), anyInt()))
                .thenReturn(travel);

        CompletableFuture<Travel> future = travelRepository
                .getByIdAsync(id);
        Travel result = future.join();

        assertNotNull(result);
    }

    /**
     * Prueba el método {@link TravelRepository#createAsync(Travel)}.
     * <p>
     * Verifica que el método crea un nuevo objeto {@link Travel} y persiste en la base de datos.
     * </p>
     */
    @Test
    void testCreateAsync() {
        Travel travel = new Travel();
        travel.setState(StateTypes.ACTIVE.ordinal());

        when(entityManager.getTransaction()).thenReturn(transaction);
        doNothing().when(transaction).begin();
        doNothing().when(transaction).commit();
        doNothing().when(transaction).rollback();

        CompletableFuture<Boolean> future = travelRepository
                .createAsync(travel);
        Boolean result = future.join();

        assertTrue(result);
        verify(entityManager).persist(travel);
    }

    /**
     * Prueba el método {@link TravelRepository#updateAsync(Travel)}.
     * <p>
     * Verifica que el método actualiza un objeto {@link Travel} existente en la base de datos.
     * </p>
     */
    @Test
    void testUpdateAsync() {
        Travel travel = new Travel();
        travel.setState(StateTypes.ACTIVE.ordinal());

        when(entityManager.getTransaction()).thenReturn(transaction);
        doNothing().when(transaction).begin();
        doNothing().when(transaction).commit();
        doNothing().when(transaction).rollback();

        CompletableFuture<Boolean> future = travelRepository
                .updateAsync(travel);
        Boolean result = future.join();

        assertTrue(result);
        verify(entityManager).merge(travel);
    }

    /**
     * Prueba el método {@link TravelRepository#removeAsync(int)}.
     * <p>
     * Verifica que el método elimina un objeto {@link Travel} de la base de datos, cambiando su estado a inactivo.
     * </p>
     */
    @Test
    void testRemoveAsync() {
        int id = 1;
        Travel travel = new Travel();
        travel.setState(StateTypes.ACTIVE.ordinal());

        when(entityManager.find(eq(Travel.class), anyInt())).thenReturn(travel);
        when(entityManager.getTransaction()).thenReturn(transaction);
        doNothing().when(transaction).begin();
        doNothing().when(transaction).commit();
        doNothing().when(transaction).rollback();

        CompletableFuture<Boolean> future = travelRepository.removeAsync(id);
        Boolean result = future.join();

        assertTrue(result);
        verify(entityManager).merge(travel);
        assertEquals(StateTypes.INACTIVE.ordinal(), travel.getState());
    }
}
