/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repostiory;

import connectionDb.DatabaseConnect;
import interfaces.ITravelRepository;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Travel;

/**
 *
 * @author VIAMATICA
 */
public class TravelRepository implements ITravelRepository {

    private DatabaseConnect cx;

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
        String sql = "INSERT INTO Travels(DepartureDate, ReturnDate, Prices, AvaliableSeats) VALUES(?, ?, ?, ?)";
        try {
            ps = cx.connect().prepareStatement(sql);
            ps.setDate(1, converDateToSqlDate(travel.getTravelDate()));
            ps.setDate(2, converDateToSqlDate(travel.getReturnDate()));
            ps.setFloat(3, travel.getTravelPrice());
            ps.setInt(4, travel.getPlacesAvaliable());
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
        String sql = "SELECT TravelId, Destination, DepartureDate, ReturnDate, Prices, AvailableSeats FROM Travels";

        try {
            PreparedStatement ps = cx.connect().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta

            while (rs.next()) {
                // Crear objetos Travel y llenarlos con los datos del resultado
                int travelId = rs.getInt("TravelId");
                String destiny = rs.getString("Destination");
                Date departureDate = rs.getDate("DepartureDate");
                Date returnDate = rs.getDate("ReturnDate");
                float prices = rs.getFloat("Prices");
                int availableSeats = rs.getInt("AvailableSeats");

                Travel travel = new Travel(travelId, destiny, departureDate, returnDate, prices, availableSeats);
                listTravels.add(travel);
            }
            return listTravels;
        } catch (SQLException e) {
            System.out.println("FAILED LIST" + e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Travel getByIdTravel(int id) {
        String sql = "SELECT TravelId, Destination, DepartureDate, ReturnDate, Prices, AvailableSeats FROM Travel WHERE TravelId = ?";

        try {
            PreparedStatement ps = cx.connect().prepareStatement(sql);
            ps.setInt(1, id); // Establecer el valor del parámetro
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int travelId = rs.getInt("TravelId");
                String destination = rs.getString("Destination");
                Date departureDate = rs.getDate("DepartureDate");
                Date returnsDate = rs.getDate("ReturnDate");
                float prices = rs.getFloat("Prices");
                int availableSeats = rs.getInt("AvailableSeats");

                return new Travel(travelId, destination, departureDate, returnsDate, prices, availableSeats);
            } else {
                return null; // No se encontró ningún viaje con el ID especificado
            }
        } catch (Exception e) {
            System.out.println("ERROR GET TRAVEL BY ID");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateTravel(Travel travel) {
        String sql = "UPDATE Travel SET Destination = ?, DepartureDate = ?, ReturnsDate = ?, Prices = ?, AvailableSeats = ? WHERE TravelId = ?";

        try {
            PreparedStatement ps = cx.connect().prepareStatement(sql);
            ps.setString(1, travel.getDestiny());
            ps.setDate(2, converDateToSqlDate(travel.getTravelDate()));
            ps.setDate(3, converDateToSqlDate(travel.getReturnDate()));
            ps.setFloat(4, travel.getTravelPrice());
            ps.setInt(5, travel.getPlacesAvaliable());
            ps.setInt(6, travel.getTravelId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Devuelve true si al menos una fila fue actualizada
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeTravel(int id) {
        String sql = "DELETE FROM Travel WHERE TravelId = ?";

        try {
            PreparedStatement ps = cx.connect().prepareStatement(sql);
            ps.setInt(1, id); // Establecer el valor del parámetro
            int rowsDeleted = ps.executeUpdate();

            return rowsDeleted > 0; // Devuelve true si al menos una fila fue eliminada
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Date converDateToSqlDate(java.util.Date dateJavaa) {
        java.sql.Date dateConvert = new java.sql.Date(dateJavaa.getTime());
        return dateConvert;
    }
}
