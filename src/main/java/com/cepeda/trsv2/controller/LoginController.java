package com.cepeda.trsv2.controller;

import com.cepeda.trsv2.interfaces.IUserRepository;
import com.cepeda.trsv2.repository.UserRepository;
import com.cepeda.trsv2.view.FrmLogin;
import javax.swing.JOptionPane;

/**
 * Controlador para la pantalla de inicio de sesión.
 * @author CyborgK27
 */
public class LoginController {

    private final FrmLogin view;
    private final IUserRepository userRepository;

    public LoginController() {
        this.view = new FrmLogin();
        this.userRepository = new UserRepository();
        this.view.setVisible(true);
        initialize();
    }

    private void initialize() {
        view.btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String email = view.jTextField1.getText();
        String password = new String(view.jPasswordField1.getPassword());

        var userOpt = userRepository.login(email, password).join();

        if (userOpt.isPresent()) {
            // Inicializa AgencyController localmente en lugar de como un campo
            AgencyController agencyController = new AgencyController(userOpt
                    .get());
            agencyController.initialize();
            JOptionPane.showMessageDialog(view, "El usuario existe");
        } else {
            JOptionPane.showMessageDialog(view, "El usuario no existe");
        }
    }

}
