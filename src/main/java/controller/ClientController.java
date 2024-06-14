package controller;

import interfaces.IClientRepository;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import model.Client;
import model.Reservation;
import model.Travel;
import model.User;
import repostiory.ClientRepository;
import view.FrmLogin;
import view.FrmTRS;
import view.JPClient;

/**
 *
 * @author CyborK27
 */
public class ClientController implements ActionListener{
    // VARIABLES INITIALIZE
    IClientRepository _clientRepository;
    FrmTRS _view;

    public ClientController(FrmTRS view, IClientRepository clientRepository) {
        _view = view;
        _clientRepository = clientRepository;
        
        _view.btnLogin.addActionListener(this);
    }
    public void init(){
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String email = _view.txtEmail.getText();
            String password = new String(_view.pfPassword.getPassword());
            var loginSuccessful = _clientRepository.loginClient(email, password);
            
            if(loginSuccessful){
                System.out.println("Login exitoso");
            }else{
                System.out.println("Credenciales incorrectas");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
