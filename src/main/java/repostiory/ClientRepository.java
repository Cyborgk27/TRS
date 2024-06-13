package repostiory;

import connectionDb.DatabaseConnect;
import interfaces.IClientRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Client;

/**
 *
 * @author CyborgK27
 */
public class ClientRepository implements IClientRepository {
    
    private DatabaseConnect cx;

    public ClientRepository() {
        this.cx = new DatabaseConnect();
    } 
    // Register client
    public boolean registerClient(Client client) {
        PreparedStatement ps = null;
        String sql = "INSERT INTO Clients(UserName, UserEmail, UserPassword, ClientCellphone) VALUES( ?, ?, ?, ?)";
        try {
            ps = cx.connect().prepareStatement(sql);
            ps.setString(1, client.getUserName());
            ps.setString(2, client.getUserEmail());
            ps.setString(3, client.getUserPassword());
            ps.setString(4, client.getCellphone());
            ps.executeUpdate();
            cx.disconnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Login client
    public boolean loginClient(String email, String password) {
        PreparedStatement ps = null;
        String sql = "SELECT * FROM Clients WHERE UserEmail = ? AND UserPassword = ?";
        
        try {
            ps = cx.connect().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            
            return true; // True si se encontró un registro que coincide
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // False si ocurrió un error
        }
    }
}

