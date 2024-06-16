package repostiory;

import connectionDb.DatabaseConnect;
import interfaces.ITravelRepository;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Travel;

/**
 *
 * @author VIAMATICA
 */
public class TravelRepository implements ITravelRepository {

    private final DatabaseConnect cx;

    public TravelRepository() {
        this.cx = new DatabaseConnect();
    }

    /*
        @params createTravel
        @return boolean
     */
    @Override
    public boolean createTravel(Travel travel) {
        PreparedStatement ps = null;
        String sql = "INSERT INTO trips( destination, departure_date, return_date, price, avaliable_seats) VALUES( ?, ?, ?, ?, ?)";
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
            System.out.println("CREATE FAILED" + e);
            return false;
        }
    }

    @Override
    public List<Travel> getAllTravels() {
        List<Travel> listTravels = new ArrayList<>();
        String sql = "SELECT id, destination, departure_date, return_date, price, avaliable_seats FROM trips";

        try {
            PreparedStatement ps = cx.connect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta

            while (rs.next()) {
                // Crear objetos Travel y llenarlos con los datos del resultado
                int travelId = rs.getInt("Id");
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
                java.util.Date returnsDate = convertStringToDate(rs.getString("return_date"));
                float prices = rs.getFloat("price");
                int availableSeats = rs.getInt("avaliable_seats");
                newTravel = new Travel(travelId, destination, departureDate, returnsDate, prices, availableSeats);
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
            return rowsUpdated > 0; // Devuelve true si al menos una fila fue actualizada
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeTravel(int id) {
        String sql = "DELETE FROM trips WHERE id = ?";

        try {
            PreparedStatement ps = cx.connect().prepareStatement(sql);
            ps.setInt(1, id); // Establecer el valor del parámetro
            int rowsDeleted = ps.executeUpdate();
            cx.disconnect();
            return rowsDeleted > 0; // Devuelve true si al menos una fila fue eliminada
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String converDateToString(java.util.Date returnDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(returnDate);
    }

    public java.util.Date convertStringToDate(String dateString) throws ParseException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(dateString);
    }
}
