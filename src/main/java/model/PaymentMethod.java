package model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author CyborgK27
 */
@AllArgsConstructor
public @Data class PaymentMethod {
    private int paymentMethodId;
    private String type;
    private String cardNumber;
    private Date expirationDate;
    
    private int clientId;
    private Client client;
}
