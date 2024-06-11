/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author VIAMATICA
 */
@AllArgsConstructor
public @Data
class User {

    public User() {

    }
    private int userId;
    private String userName;
    private String userEmail;
    private String userPassword;

    public boolean registerUser(Client client) {
        TRSContext.clients.add(client);
        System.out.println("se registro el client");
        return true;
    }

    public boolean loginUser(String email, String password) {
        for (Client client : TRSContext.clients) {
            if(email.equals(client.getUserEmail()) && password.equals(client.getUserPassword())){
                return true;
            }
        }
        return false;
    }
}
