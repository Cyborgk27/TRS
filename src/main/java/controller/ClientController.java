package controller;

import java.util.List;
import javax.swing.JOptionPane;
import model.Client;
import model.Reservation;
import model.Travel;
import model.User;

/**
 *
 * @author CyborK27
 */
public class ClientController {
    
    private static User user = new User();
    private static Reservation reservation = new Reservation();
    private static Client client = new Client();
    // REGISTER USER
    public void registerUser(String userName, String userEmail, String userPassword) {
        if (isValidRegister(userName, userEmail, userPassword)) {
            user = new User(0, userName, userEmail, userPassword);
            user.registerUser(user);
        }
    }
    //LOGIN USER
    public int loginUser(String email, String password) {
        user = new User();
        int userId = user.loginUser(email, password);
        return userId;
    }
    
    //SEARCH TRAVEL
    public List<Travel> searchTravels(){
        client = new Client();
        var listTravels= client.searchTravels();
        return listTravels;
    }

    private boolean isValidRegister(String userName, String userEmail, String userPassword) {
        if (!userName.isEmpty() && !userEmail.isEmpty() && !userPassword.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean isValidLogin(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            return true;
        }
        return false;
    }
    
    public User getByIdUser(int id){
        var u = user.getByIdUser(id);
        System.out.println("EL user es: "+user.toString());
        return u;
    }
    
    public void createReservation(int userId, int travelId){
        if(client.bookTravel(travelId, userId)){
            JOptionPane.showMessageDialog(null, "La reserva se ha creado con éxito");
        }
    }
    
    public List<Reservation> getReservations(){
        return client.getReservations();
    }
}
