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
