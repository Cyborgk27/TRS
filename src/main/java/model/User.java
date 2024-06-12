package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author CyborgK27
 */
@AllArgsConstructor
public @Data class User {

    public User() {

    }
    private int userId;
    private String userName;
    private String userEmail;
    private String userPassword;

    public boolean registerUser(User user) {
        TRSContext.users.add(user);
        System.out.println("se registro el client");
        System.out.println("Lista: "+TRSContext.users.toString());
        System.out.println(user.toString());
        return true;
    }
    

    public boolean loginUser(String email, String password) {
        for (User user : TRSContext.users) {
            if(user.getUserEmail().equals(email) && user.getUserPassword().equals(password)){
                System.out.println("sesion iniciada");
                return true;
            }
        }
        return false;
    }
    
}
