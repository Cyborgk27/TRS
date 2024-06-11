/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 *
 * @author VIAMATICA
 */
@AllArgsConstructor
public @Data class Client extends User{
    
    public Client() {
        reservations = new ArrayList<>();
        paymentMethods = new ArrayList<>();
    }

    public Client(String cellphone, int userId, String userName, String userEmail, String userPassword) {
        super(userId, userName, userEmail, userPassword);
        this.cellphone = cellphone;
    }
    
    private String cellphone;
    
    //List reservation and pm
    private List<Reservation> reservations;
    private List<PaymentMethod> paymentMethods;
    
    public List<Travel> searchTravels(){
        return TRSContext.travels;
    }
    
    public boolean bookTravel(Travel travel, int clientId){
        //verify if reservation is empty
        verifyReservation(reservations);
        
        //add reservation
        var reservation = new Reservation();
        reservation.setReservationId(reservations.size());
        reservation.setReservationDate(Date.from(Instant.MIN));
        reservation.setState("Procesado");
        reservation.setClientId(clientId);
        reservation.setClient(TRSContext.clients.get(clientId));
        reservations.add(reservation);
        return true;
    }
    
    private boolean cancelReservation(int reserverationId){
        var reservation = reservations.get(reserverationId);
        reservation.setState("Cancelado");
        reservations.add(reserverationId, reservation);
        return true;
    }

    private void verifyReservation(List<Reservation> reservations) {
        if(reservations.isEmpty()){
            System.out.println("Ustede no tienen ninguna reservción");
        }
    }
}
