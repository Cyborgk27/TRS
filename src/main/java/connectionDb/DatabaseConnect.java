package connectionDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que se encarga de la conexión a la base de datos.
 * Proporciona métodos para conectar y desconectar.
 *
 * @author CyborgK27
 */
public class DatabaseConnect {
    private Connection cx = null;

    /**
     * Conecta a la base de datos.
     * 
     * @return Connection La conexión establecida.
     */
    public Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            cx = DriverManager.getConnection("jdbc:sqlite:trs.db");
            System.out.println("Success connection");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
        return cx;
    }

    /**
     * Desconecta de la base de datos.
     */
    public void disconnect() {
        try {
            if (cx != null && !cx.isClosed()) {
                cx.close();
                System.out.println("Disconnected successfully");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
