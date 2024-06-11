/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author VIAMATICA
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
