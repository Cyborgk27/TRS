package controller;

import model.Client;
import model.TRSContext;
import model.User;

/**
 *
 * @author CyborK27
 */
public class ClientController {

    public void registerUser(String userName, String userEmail, String userPassword) {
        if (isValidRegister(userName, userEmail, userPassword)) {
            var user = new User(0, userName, userEmail, userPassword);
            user.registerUser(user);
        }
    }

    public boolean loginUser(String email, String password) {
        var user = new User();
        return user.loginUser(email, password);

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
}
