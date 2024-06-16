/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import controller.TravelController;
import interfaces.IClientRepository;
import interfaces.ITravelRepository;
import java.util.List;
import java.util.ArrayList;
import model.Travel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repostiory.TravelRepository;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author CyborgK27
 */
public class testReposiotry {
    private final ITravelRepository travelRepository = new TravelRepository();
    
    @Test
    public void testList(){
        List<Travel> listTravels = new ArrayList<>();
        
        listTravels = travelRepository.getAllTravels();
        System.out.println(listTravels);
        assertEquals(4, listTravels.size());
    }
}
