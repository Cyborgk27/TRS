package repository;

import connectionDb.DatabaseConnect;
import interfaces.ITravelRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Travel;

/**
 * Clase repositorio para gestionar los datos de viajes en la base de datos.
 * Implementa la interfaz ITravelRepository.
 * 
 * @autor CyborgK27
 */
public class TravelRepository implements ITravelRepository {

    final DatabaseConnect cx;

    /**
     * Construye un TravelRepository con una conexión a la base de datos.
     */
    public TravelRepository() {
        this.cx = new DatabaseConnect();
    }

    /**
     * Crea una nueva entrada de viaje en la base de datos.
     *
     * @param travel El objeto Travel que contiene los detalles del viaje.
     * @return boolean True si el viaje se creó con éxito, false en caso
     * contrario.
     */
    @Override
    public boolean createTravel(Travel travel) {
        PreparedStatement ps = null;
        String sql = "INSERT INTO trips(destination, departure_date, return_date, price, avaliable_seats) VALUES(?, ?, ?, ?, ?)";
        try {
            ps = cx.connect().prepareStatement(sql);
            ps.setString(1, travel.getDestiny());
            ps.setString(2, converDateToString(travel.getTravelDate()));
            ps.setString(3, converDateToString(travel.getReturnDate()));
            ps.setFloat(4, travel.getTravelPrice());
            ps.setInt(5, travel.getPlacesAvaliable());
            ps.executeUpdate();
            cx.disconnect();
            return true;
        } catch (Exception e) {
            System.out.println("CREATE FAILED: " + e);
            return false;
        }
    }

    /**
     * Recupera todas las entradas de viaje de la base de datos.
     *
     * @return List<Travel> Una lista de todos los objetos Travel.
     */
    @Override
    public List<Travel> getAllTravels() {
        List<Travel> listTravels = new ArrayList<>();
        String sql = "SELECT id, destination, departure_date, return_date, price, avaliable_seats FROM trips";

        try {
            PreparedStatement ps = cx.connect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta

            while (rs.next()) {
                // Crear objetos Travel y llenarlos con los datos del resultado
                int travelId = rs.getInt("id");
                String destiny = rs.getString("destination");
                java.util.Date departureDate = convertStringToDate(rs.getString("departure_date"));
                java.util.Date returnDate = convertStringToDate(rs.getString("return_date"));
                float prices = rs.getFloat("price");
                int availableSeats = rs.getInt("avaliable_seats");
                var travel = new Travel(travelId, destiny, departureDate, returnDate, prices, availableSeats);
                listTravels.add(travel);
            }

            return listTravels;
        } catch (SQLException | ParseException e) {
            System.out.println("FAILED LIST: " + e + "\n");
            return null;
        }
    }

    /**
     * Recupera una entrada de viaje por su ID.
     *
     * @param id El ID del viaje a recuperar.
     * @return Travel El objeto Travel con el ID especificado, o null si no se encuentra.
     */
    @Override
    public Travel getByIdTravel(int id) {
        String sql = "SELECT id, destination, departure_date, return_date, price, avaliable_seats FROM trips WHERE id = ?";
        Travel newTravel = new Travel();
        try {
            PreparedStatement ps = cx.connect().prepareStatement(sql);
            ps.setInt(1, id); // Establecer el valor del parámetro
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int travelId = rs.getInt("id");
                String destination = rs.getString("destination");
                java.util.Date departureDate = convertStringToDate(rs.getString("departure_date"));
                java.util.Date returnDate = convertStringToDate(rs.getString("return_date"));
                float prices = rs.getFloat("price");
                int availableSeats = rs.getInt("avaliable_seats");
                newTravel = new Travel(travelId, destination, departureDate, returnDate, prices, availableSeats);
                cx.disconnect();
            } else {
                return null; // No se encontró ningún viaje con el ID especificado
            }
        } catch (Exception e) {
            System.out.println("ERROR GET TRAVEL BY ID");
            e.printStackTrace();
            return null;
        }
        return newTravel;
    }

    /**
     * Actualiza una entrada de viaje en la base de datos.
     *
     * @param travel El objeto Travel que contiene los detalles del viaje actualizados.
     * @return boolean True si el viaje se actualizó con éxito, false en caso contrario.
     */
    @Override
    public boolean updateTravel(Travel travel) {
        String sql = "UPDATE trips SET destination = ?, departure_date = ?, return_date = ?, price = ?, avaliable_seats = ? WHERE id = ?";

        try {
            PreparedStatement ps = cx.connect().prepareStatement(sql);
            ps.setString(1, travel.getDestiny());
            ps.setString(2, converDateToString(travel.getTravelDate()));
            ps.setString(3, converDateToString(travel.getReturnDate()));
            ps.setFloat(4, travel.getTravelPrice());
            ps.setInt(5, travel.getPlacesAvaliable());
            ps.setInt(6, travel.getTravelId());
            int rowsUpdated = ps.executeUpdate();
            System.out.println(rowsUpdated);
            return rowsUpdated > 0; // Devuelve true si al menos una
            //fila fue actualizada
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una entrada de viaje de la base de datos por su ID.
     *
     * @param id El ID del viaje a eliminar.
     * @return boolean True si el viaje se eliminó con éxito, false
     * en caso contrario.
     */
    @Override
    public boolean removeTravel(int id) {
        String sql = "DELETE FROM trips WHERE id = ?";

        try {
            PreparedStatement ps = cx.connect().prepareStatement(sql);
            ps.setInt(1, id); // Establecer el valor del parámetro
            int rowsDeleted = ps.executeUpdate();
            cx.disconnect();
            return rowsDeleted > 0; // Devuelve true si al menos
            //una fila fue eliminada
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Convierte un objeto Date a su representación en String.
     *
     * @param date El objeto Date a convertir.
     * @return String La representación en String de la fecha.
     */
    public String converDateToString(java.util.Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * Convierte una representación en String de una fecha a un objeto Date.
     *
     * @param dateString La representación en String de la fecha.
     * @return java.util.Date El objeto Date.
     * @throws ParseException Si la cadena de fecha no se puede analizar.
     */
    public java.util.Date convertStringToDate(String dateString) 
            throws ParseException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(dateString);
    }
}
