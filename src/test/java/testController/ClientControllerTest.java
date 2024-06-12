import controller.ClientController;
import model.TRSContext;
import model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ClientControllerTest {
    private final ClientController clientController = new ClientController();

    @Test
    public void testRegisterUser() {
        var listUser = TRSContext.users;
        clientController.registerUser("Manuel", "manuel@gmail.com", "password123");
        
        for(User user:listUser){
            assertEquals(user.getUserName(), "Manuel");
        }
    }

    @Test
    public void testLoginUser() {
        // Prueba de inicio de sesión válido
        int userId = clientController.loginUser("john@example.com", "password");
        assertTrue(userId > 0); // El ID del usuario debe ser positivo
    }

    @Test
    public void testSearchTravels() {
        // Prueba de búsqueda de viajes
        assertNotNull(clientController.searchTravels()); // La lista no debe ser nula
    }

    @Test
    public void testGetReservations() {
        // Prueba de obtención de reservas
        assertNotNull(clientController.getReservations()); // La lista no debe ser nula
    }
}
