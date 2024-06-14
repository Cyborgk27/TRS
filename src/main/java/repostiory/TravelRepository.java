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
        String sql = "INSERT INTO Travels(DepartureDate, ReturnsDate, Prices, AvaliableSeats) VALUES(?, ?, ?, ?)";
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
    String sql = "SELECT TravelId, Destination, DepartureDate, ReturnsDate, Prices, AvailableSeats FROM Travels";

    try {
        PreparedStatement ps = cx.connect().prepareStatement(sql);
        ResultSet rs = ps.executeQuery(); // Ejecutar la consulta

        while (rs.next()) {
            // Crear objetos Travel y llenarlos con los datos del resultado
            int travelId = rs.getInt("TravelId");
            String Destiny = rs.getString("Destination");
            Date departureDate = rs.getDate("DepartureDate");
            Date returnsDate = rs.getDate("ReturnsDate");
            float prices = rs.getFloat("Prices");
            int availableSeats = rs.getInt("AvailableSeats");

            Travel travel = new Travel(travelId, Destiny,departureDate, returnsDate, prices, availableSeats);
            listTravels.add(travel);
        }
        return listTravels;
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}


    @Override
    public Travel getByIdTravel(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean updateTravel(Travel travel) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean removeTravel(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Date converDateToSqlDate(java.util.Date dateJavaa) {
        java.sql.Date dateConvert = new java.sql.Date(dateJavaa.getTime());
        return dateConvert;
    }

}
