package connectionDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author CyborgK27
 */
public class DatabaseConnect  {
    Connection cx = null;
    
    public Connection connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            cx = DriverManager.getConnection("jdbc:sqlite:trs.db");
            System.out.println("Success connection");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return cx;
    }
    
    public void disconnect(){
        try {
            cx.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        DatabaseConnect dbconnect = new DatabaseConnect();
        dbconnect.connect();
        
    }
}
