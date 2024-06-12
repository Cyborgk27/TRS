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
        user.setUserId(TRSContext.users.size()+1);
        TRSContext.users.add(user);
        System.out.println("se registro el client");
        System.out.println("Lista: "+TRSContext.users.toString());
        System.out.println(user.toString());
        return true;
    }
    

    public int loginUser(String email, String password) {
        int userId = 0;
        System.out.println(TRSContext.clients);
        for (User user : TRSContext.users) {
            if(user.getUserEmail().equals(email) && user.getUserPassword().equals(password)){
                System.out.println(user.getUserId());
                userId = user.getUserId();
                System.out.println("sesion iniciada");
            }
        }
        return userId;
    }
    //RETURN CLIENT BY ID
    public User getByIdUser(int id){
        for(User user : TRSContext.users){
            if(user.getUserId() == id){
                return user;
            }
        }
        return null;
    }
}
