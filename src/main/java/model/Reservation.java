package model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author CyborgK27
 */
@AllArgsConstructor
public @Data class Reservation {

    public Reservation() {
    }
    
    private int reservationId;
    private Date reservationDate;
    private String state;
    
    private int clientId;
    private Client client;
    
    private int travelId;
    private Travel travel;
}
