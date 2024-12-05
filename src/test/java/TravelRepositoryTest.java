/**
 * Pruebas unitarias para verificar el funcionamiento de los métodos en
 * TravelRepository.
 */
package repository;

import model.Travel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para el repositorio TravelRepository.
 */
public class TravelRepositoryTest {

    private TravelRepository repository;

    /**
     * Configuración inicial para cada prueba.
     */
    @BeforeEach
    public void setUp() {
        repository = new TravelRepository();
        // Puedes considerar el uso de mockito para mockear DatabaseConnect 
        //y la conexión
    }

    /**
     * Prueba para verificar la creación de un viaje en la base de datos.
     *
     * @throws SQLException Si hay un error de SQL durante la prueba.
     */
    @Test
    public void testCreateTravel() throws SQLException {
        Travel travel = new Travel(0, "Paris", new Date(), new Date(), 
                500.0f, 50);
        assertTrue(repository.createTravel(travel));
    }

    /**
     * Prueba para verificar la recuperación de todos los viajes de la base
     * de datos.
     *
     * @throws SQLException Si hay un error de SQL durante la prueba.
     */
    @Test
    public void testGetAllTravels() throws SQLException {
        List<Travel> travels = repository.getAllTravels();
        assertNotNull(travels);
        // Puedes agregar más validaciones aquí si lo deseas
    }

    /**
     * Prueba para verificar la recuperación de un viaje por su ID en la
     * base de datos.
     *
     * @throws SQLException Si hay un error de SQL durante la prueba.
     */
    @Test
    public void testGetByIdTravel() throws SQLException {
        Travel travel = repository.getByIdTravel(5);
        assertNotNull(travel); // Aquí asumimos que no hay viaje con ID 1
        //para probar el caso
    }

    /**
     * Prueba para verificar la actualización de un viaje en la base de datos.
     *
     * @throws SQLException Si hay un error de SQL durante la prueba.
     */
    @Test
    public void testUpdateTravel() throws SQLException {
        Travel travel = new Travel(8, "Samborondón", new Date(), new Date(),
                100.0f, 20);
        assertTrue(repository.updateTravel(travel));
    }

    /**
     * Prueba para verificar la eliminación de un viaje de la base de datos.
     *
     * @throws SQLException Si hay un error de SQL durante la prueba.
     */
    @Test
    public void testRemoveTravel() throws SQLException {
        assertTrue(repository.removeTravel(8)); // Aquí asumimos que se 
        //elimina correctamente un viaje con ID 1
    }
}
