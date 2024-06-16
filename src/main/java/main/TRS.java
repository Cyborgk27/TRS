package main;

import controller.TravelController;
import interfaces.ITravelRepository;
import model.Travel;
import repostiory.TravelRepository;
import view.FrmTravel;


/**
 *
 * @author CyborgK27
 */
public class TRS {
    public static void main(String[] args) {
        Travel t = new Travel();
        FrmTravel view = new FrmTravel();
        ITravelRepository travelRepository = new TravelRepository();
        TravelController travelController = new TravelController(view, travelRepository);
        view.setVisible(true);
    }
}
