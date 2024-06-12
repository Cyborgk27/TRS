package model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author CyborgK27
 */
@AllArgsConstructor
public @Data
class Client extends User {

    public Client() {
        reservations = new ArrayList<>();
        paymentMethods = new ArrayList<>();
        fillReservations();
    }

    public Client(String cellphone, int userId, String userName, String userEmail, String userPassword) {
        super(userId, userName, userEmail, userPassword);
        this.cellphone = cellphone;
    }

    public Client(String cellphone) {
        this.cellphone = cellphone;
    }

    private String cellphone;

    //List reservation and pm
    private List<Reservation> reservations;
    private List<PaymentMethod> paymentMethods;

    public List<Travel> searchTravels() {
        return TRSContext.travels;
    }

    public boolean bookTravel(int travelId, int clientId) {
        var reservation = new Reservation();
        //verify if reservation is empty
        verifyReservation(reservations);

        reservation.setReservationId(TRSContext.travels.size() + 1);
        reservation.setReservationDate(new Date());
        reservation.setState("Generado con éxito");
        reservation.setClientId(clientId);
        reservation.setClient(getClientById(clientId));
        reservation.setTravelId(travelId);
        minusTravel(travelId);
        reservation.setTravel(getTravelById(travelId));
        reservations.add(reservation);
        return true;
    }

    private boolean cancelReservation(int reserverationId) {
        var reservation = reservations.get(reserverationId);
        reservation.setState("Cancelado");
        reservations.add(reserverationId, reservation);
        return true;
    }

    private void verifyReservation(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("Ustede no tienen ninguna reservción");
        }
    }

    private Client getClientById(int clientId) {
        var c = new Client();
        for (Client client : TRSContext.clients) {
            if (client.getUserId() == clientId) {
                c = client;
            }
        }
        return c;
    }

    private Travel getTravelById(int travelId) {
        var t = new Travel();
        for (Travel travel : TRSContext.travels) {
            if (travel.getTravelId() == travelId) {
                t = travel;
            }
        }
        return t;
    }

    private void fillReservations() {
        var reservation = new Reservation(1, new Date(), "Generado con éxito", 1, null, 1, getTravelById(1));
        reservations.add(reservation);
    }

    private void minusTravel(int travelId) {
        var travel = getTravelById(travelId);
        
        if (travel.getPlacesAvaliable() >= 1){
            travel.setPlacesAvaliable(travel.getPlacesAvaliable() - 1);
        }
    }
}
