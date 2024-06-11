/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 *
 * @author VIAMATICA
 */
public class TRSContext {
    
    public TRSContext(){
        fillClients();
        fillTravels();
    }
    
    public static final List<Client> clients = new ArrayList<>();
    public static final List<Travel> travels = new ArrayList<>();

    private void fillClients() {
        var client = new Client("0991713814", 0, "Kevin", "slenderstalin@gmail.com", "password123");
        clients.add(client);
    }

    private void fillTravels() {
        var e = new Travel(0, "Riobamba", Date.from(Instant.MIN), Date.from(Instant.MIN), 14f, 5);
        travels.add(e);
    }
}
