package main;

import controller.TravelController;
import interfaces.ITravelRepository;
import model.Travel;
import repository.TravelRepository;
import view.FrmTravel;

/**
 * Clase principal para la aplicación de Sistema de
 * Gestión de Viajes (Travel Reservation System - TRS).
 * Inicia la interfaz de usuario y configura los
 * controladores y repositorios necesarios.
 *
 * @author CyborgK27
 */
public class TRS {
    /**
     * Método principal para ejecutar la aplicación.
     *
     */
    public static void main(String[] args) {
        // Crear una instancia de Travel (aunque no se usa en este contexto)
        Travel t = new Travel();
        // Crear la vista principal
        FrmTravel view = new FrmTravel();
        // Crear el repositorio de viajes
        ITravelRepository travelRepository = new TravelRepository();
        // Crear el controlador de viajes y asociarlo con la vista y el
        //repositorio
        TravelController travelController = new TravelController(view,
                travelRepository);
        // Hacer visible la vista principal
        view.setVisible(true);
    }
}
