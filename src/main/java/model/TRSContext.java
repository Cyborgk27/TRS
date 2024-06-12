package model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import view.FrmLogin;

/**
 *
 * @author CyborgK27
 */
public class TRSContext {
    public static final List<User> users = new ArrayList<>();
    public static final List<Client> clients = new ArrayList<>();
    public static final List<Travel> travels = new ArrayList<>();
    
    public TRSContext(){
        fillUsers();
        fillClients();
        fillTravels();
    }   
    private void fillUsers(){
        var user = new User( 1, "Kevin", "slenderstalin@gmail.com", "password123");
        users.add(user);
        var user2 = new User( 2, "Alexis", "alexis@gmail.com", "123");
        users.add(user2);
    }
    
    private void fillClients() {
        var client = new Client("0991713814");
        clients.add(client);
        var client2 = new Client("0993629873");
        clients.add(client2);
    }

    private void fillTravels() {
        // Utiliza la fecha y hora actual para evitar desbordamiento
        var startDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        var endDate = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()); // un día después
        
        var e = new Travel(1, "Riobamba", startDate, endDate, 14f, 5);
        travels.add(e);
    }

    public static void main(String[] args) {
        TRSContext tsrContext = new TRSContext();
        System.out.println(clients.get(0));
        FrmLogin frm = new FrmLogin();
        frm.setVisible(true);
    }
}
