/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package testModels;

import model.Client;
import model.TRSContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author CyborgK27
 */
public class UserTest {
    
    @Test
    private void RegisterTest(){
        var client = new Client();
        client.setUserId(1);
        client.setUserName("Kevin Cepeda");
        client.setUserEmail("slenderstalin@gmail.com");
        client.setUserPassword("passowrd123");
        
        TRSContext.clients.add(client);
        
        assertEquals(client, TRSContext.clients.get(0));
    }
    
    @Test
    private void loginTest(){
        var client = new Client();
        client.setUserId(1);
        client.setUserName("Kevin Cepeda");
        client.setUserEmail("slenderstalin@gmail.com");
        client.setUserPassword("passowrd123");
        
        TRSContext.clients.add(client);
        var isSuccess = true;
        var userLogin = client.loginUser("slenderstalin@gmail.com", "password123");
        assertEquals(isSuccess, userLogin);
    }
}
