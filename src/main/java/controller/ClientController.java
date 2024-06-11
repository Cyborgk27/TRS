package controller;

import model.Client;
import model.TRSContext;

/**
 *
 * @author CyborK27
 */
public class ClientController {

    public void registerUser(String userName, String userEmail, String userPassword, String cellphone) {
        if (isValidRegister(userName, userEmail, userPassword, cellphone)) {
            var client = new Client(cellphone, 0, userName, userEmail, userPassword);
            client.registerUser(client);
        }
    }

    public void loginUser(String email, String password) {
        var client = new Client();
        if (isValidLogin(email, password)) {
            client.loginUser(email, password);
            System.out.println("Has iniciado sesión con éxito");
        }
    }

    private boolean isValidRegister(String userName, String userEmail, String userPassword, String cellphone) {
        if (!userName.isEmpty() && !userEmail.isEmpty() && !userPassword.isEmpty() && !cellphone.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean isValidLogin(String email, String password) {
        if(!email.isEmpty() && !password.isEmpty()){
            return true;
        }
        return false;
    }
}
