package main;

import controller.ClientController;
import interfaces.IClientRepository;
import repostiory.ClientRepository;
import view.FrmLogin;
import view.FrmTRS;
import view.JPClient;

/**
 *
 * @author CyborgK27
 */
public class TRS {
    public static void main(String[] args) {
        FrmTRS view = new FrmTRS();
        IClientRepository clientRepository = new ClientRepository();
        ClientController clientController = new ClientController(view, clientRepository);
        
        view.setVisible(true);
    }
}
