/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package testModels;

import model.Client;
import model.TRSContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author CyborgK27
 */
public class ClientTest {
    
    @Test
    public void ValidateInsertionClient() {
        // Crear un cliente
        var client = new Client();
        client.setUserId(1);
        client.setUserName("Kevin Ceoeda");
        client.setUserEmail("slenderstalin@gmail.com");
        client.setUserPassword("password123");
        client.setCellphone("0991713814");
        // Agregar el cliente a la lista
        TRSContext.clients.add(client);
        // Verificar que el cliente se haya insertado correctamente
        assertEquals(client, TRSContext.clients.get(0));
    }
}
