package model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author CyborgK27
 */
@AllArgsConstructor
public @Data class Travel {
    public Travel(){
    
    }
    private int travelId;
    private String destiny;
    private Date travelDate;
    private Date returnDate;
    private float travelPrice;
    private int placesAvaliable;
}
