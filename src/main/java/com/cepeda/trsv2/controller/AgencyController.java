package com.cepeda.trsv2.controller;

import com.cepeda.trsv2.interfaces.ITravelRepository;
import com.cepeda.trsv2.model.entities.Role;
import com.cepeda.trsv2.model.entities.Travel;
import com.cepeda.trsv2.model.entities.User;
import com.cepeda.trsv2.repository.TravelRepository;
import com.cepeda.trsv2.util.StateTypeString;
import com.cepeda.trsv2.util.TravelValidator;
import com.cepeda.trsv2.view.FrmAgency;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador de la vista de agencias que gestiona la interacción
 * entre la vista 
 * y los datos de los viajes.
 * <p>
 * Este controlador maneja las operaciones CRUD para los viajes, incluyendo 
 * la creación, edición, eliminación y visualización de viajes en la interfaz
 * gráfica.
 * </p>
 * 
 * @author CyborgK27
 */
public class AgencyController {

    private final User user;
    private final ITravelRepository travelRepository;
    private final FrmAgency view;

    /**
     * Constructor del controlador de agencias.
     * 
     * @param user El usuario actual que está gestionando los viajes.
     */
    public AgencyController(User user) {
        this.user = user;
        this.travelRepository = new TravelRepository();
        this.view = new FrmAgency();
        
        //initialize();
    }

    /**
     * Inicializa la vista y configura los eventos de los botones y la tabla.
     */
    public void initialize() {
        this.view.setVisible(true);
        fillDataWindows(user);
        view.btnSave.addActionListener(e -> registerTravel(user));
        view.btnEdit.addActionListener(e -> editTravel());
        view.btnRemove.addActionListener(e -> removeTravel());
        view.btnList.addActionListener(e -> fillTable());
        view.tblGeneric.getSelectionModel()
                .addListSelectionListener(e -> viewTravel());
        fillTable();
    }

    /**
     * Llena los datos de la ventana con la información del usuario.
     * 
     * @param user El usuario cuyas informaciones serán mostradas.
     */
    private void fillDataWindows(User user) {
        String userEmail = user.getEmail();
        String roleName = user.getRoles()
                .stream()
                .findFirst()
                .map(Role::getRoleName)
                .orElse("No role assigned");
        view.lblEmail.setText(userEmail);
        view.lblRole.setText(roleName);
        //fillTable();
    }

    /**
     * Limpia todas las filas de una tabla.
     * 
     * @param table La tabla a limpiar.
     */
    private void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Elimina todas las filas del modelo
    }

    /**
     * Rellena la tabla con la lista de viajes asociados al usuario actual.
     */
    private void fillTable() {
        var listTravels = travelRepository
                .findByUserIdAsync(user.getId()).join();
        var tblGeneric = view.tblGeneric;
        // Define los nombres de las columnas
        String[] columnNames = {
            "Código de Viaje",
            "Destino",
            "Creado", "Editado",
            "Eliminado",
            "Estado"
        };

        // Crea el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Todas las celdas no son editables
                return false;
            }
        };

        // Define un formateador para las fechas
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm");

        for (Travel travel : listTravels) {
            String codeTravel = travel.getCodeTravel();
            String destination = travel.getDestination();

            // Usa el formateador para convertir LocalDateTime a String
            String createDate = (travel.getAuditCreateDate() != null) ? travel
                    .getAuditCreateDate().format(formatter) : "";
            String updateDate = (travel.getAuditUpdateDate() != null) ? travel
                    .getAuditUpdateDate().format(formatter) : "";
            String removeDate = (travel.getAuditRemoveDate() != null) ? travel
                    .getAuditRemoveDate().format(formatter) : "";

            // Estado del viaje
            String stateString = StateTypeString
                    .stateToString(travel.getState());

            Object[] rowData = {
                codeTravel,
                destination,
                createDate,
                updateDate,
                removeDate,
                stateString
            };
            model.addRow(rowData);
        }

        // Asigna el modelo a la tabla
        tblGeneric.setModel(model);
        view.lblSize.setText(listTravels.size() + "");
    }

    /**
     * Registra un nuevo viaje basado en la información ingresada por el
     * usuario.
     * 
     * @param user El usuario que está creando el viaje.
     */
    private void registerTravel(User user) {
        var travel = createTravel(user);
        travelRepository.createAsync(travel);
        JOptionPane.showMessageDialog(view, "Viaje creado exitosamente.");
        fillTable();
    }

    /**
     * Crea un objeto {@link Travel} a partir de la información ingresada en la
     * vista.
     * 
     * @param user El usuario que está creando el viaje.
     * @return El objeto {@link Travel} creado.
     */
    private Travel createTravel(User user) {
        String travelCode = view.txtTravelCode.getText().trim();
        String travelDestination = view.txtDestination.getText().trim();
        String travelAvailablePlacesStr = view.txtAvaliablePlaces.getText()
                .trim();
        String travelDateStr = view.txtTravelDate.getText().trim();
        String travelReturnDateStr = view.txtReturnDate.getText().trim();
        String priceStr = view.txtPrice.getText().trim();

        // Validar el código de viaje
        if (!TravelValidator.isValidCodeTravel(travelCode)) {
            JOptionPane.showMessageDialog(view,
                    "El código de viaje debe tener exactamente 5 caracteres.");
        }

        // Validar el destino
        if (!TravelValidator.isValidDestination(travelDestination)) {
            JOptionPane.showMessageDialog(view,
                    "El destino no puede estar vacío.");
        }

        // Validar lugares disponibles
        if (!TravelValidator.isValidAvailablePlaces(travelAvailablePlacesStr)) {
            JOptionPane.showMessageDialog(view,
                    "Los lugares disponibles deben "
                            + "ser un número entero positivo.");
        }
        int travelAvailablePlaces = Integer.parseInt(travelAvailablePlacesStr);

        // Validar precio
        if (!TravelValidator.isValidPrice(priceStr)) {
            JOptionPane.showMessageDialog(view,
                    "El precio debe ser un número decimal positivo.");
        }
        float price = Float.parseFloat(priceStr);

        // Validar fechas
        if (!TravelValidator.isValidTravelDate(travelDateStr)) {
            JOptionPane.showMessageDialog(view, "La fecha de inicio de viaje "
                    + "debe estar en el formato yyyy-MM-dd HH:mm.");
        }
        if (!TravelValidator.isValidReturnDate(travelReturnDateStr)) {
            JOptionPane.showMessageDialog(view, "La fecha de retorno debe "
                    + "estar en el formato yyyy-MM-dd HH:mm.");
        }
        if (!TravelValidator.areDatesValid(travelDateStr, travelReturnDateStr))
        {
            JOptionPane.showMessageDialog(view, "La fecha de retorno debe ser "
                    + "después de la fecha de inicio.");
        }

        // Convertir las fechas a LocalDate
        LocalDateTime travelDate = LocalDateTime
                .parse(travelDateStr, TravelValidator.DATE_FORMATTER);
        LocalDateTime returnDate = LocalDateTime
                .parse(travelReturnDateStr, TravelValidator.DATE_FORMATTER);

        // Crear el objeto Travel
        return Travel.builder()
                .codeTravel(travelCode)
                .destination(travelDestination)
                .travelDate(travelDate)
                .returnDate(returnDate)
                .availablePlaces(travelAvailablePlaces)
                .price(price)
                .user(user)
                .build();
    }

    /**
     * Obtiene el código del viaje seleccionado en la tabla.
     * 
     * @return El código del viaje seleccionado o una cadena vacía si no hay
     * selección.
     */
    private String getSelectedCodeTravel() {
        JTable table = view.tblGeneric; // Especifica el tipo de variable
        int cellIndex = 0; // Usa camelCase para el índice de la columna
        int selectedRow = table.getSelectedRow(); // Usa camelCase para la fila
        //seleccionada
        
        if (selectedRow >= 0) {
            Object cellValue = table.getValueAt(selectedRow, cellIndex);
            return cellValue != null ? cellValue.toString() : ""; // Manejo del
            //caso null
        } else {
            return ""; // O manejar de alguna manera si no hay fila seleccionada
        }
    }

    /**
     * Muestra la información del viaje seleccionado en la vista.
     */
    private void viewTravel() {
        var travelCode = getSelectedCodeTravel();

        if (!travelCode.isEmpty()) {
            var travel = travelRepository
                    .findByCodeTravelAsync(travelCode).join();
            fillTravelExist(travel.get());

            if (!travel.isPresent()) {
                JOptionPane.showMessageDialog(view, "Ocurrio un error");
            }
        }

    }

    /**
     * Rellena los campos de la vista con la información del viaje dado.
     * 
     * @param travel El objeto {@link Travel} con la información a mostrar.
     */
    private void fillTravelExist(Travel travel) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd 00:00");

        view.txtTravelCode.setText(travel.getCodeTravel());
        view.txtDestination.setText(travel.getDestination());
        view.txtAvaliablePlaces.setText(String.valueOf(travel
                .getAvailablePlaces()));
        view.txtTravelDate.setText(travel.getTravelDate().format(formatter));
        view.txtReturnDate.setText(travel.getReturnDate().format(formatter));
        view.txtPrice.setText(String.valueOf(travel.getPrice()));
    }

    /**
     * Edita un viaje existente con la información actualizada proporcionada
     * por el usuario.
     */
    private void editTravel() {
        var travelCode = view.txtTravelCode.getText();
        var travelExistOpt = travelRepository
                .findByCodeTravelAsync(travelCode).join();
        if(!travelCode.isEmpty() || !travelExistOpt.isEmpty()){
            var travelUpdate = travelExistOpt.get();
            updateTravel(travelUpdate);
        }else{
            JOptionPane.showMessageDialog(view, "El código de viaje que "
                + "intenta editar no existe");
        }
    }

    /**
     * Elimina el viaje seleccionado de la base de datos.
     */
    private void removeTravel() {
        String codeTravel = getSelectedCodeTravel();
        if (!codeTravel.isEmpty()) {
            var travel = travelRepository
                    .findByCodeTravelAsync(codeTravel).join();
            if (!travel.isEmpty()) {
                travelRepository.removeAsync(travel.get().getId());
                JOptionPane.showMessageDialog(view, "El viaje se ha eliminado");
                clearTable(view.tblGeneric);
                fillTable();
            }
        }
    }

    /**
     * Actualiza un viaje existente con la información proporcionada por el
     * usuario.
     * 
     * @param travelUpdate El objeto {@link Travel} que será actualizado.
     */
    private void updateTravel(Travel travelUpdate) {
        String travelDestination = view.txtDestination.getText().trim();
        String travelAvailablePlacesStr = view.txtAvaliablePlaces.getText()
                .trim();
        String travelDateStr = view.txtTravelDate.getText().trim();
        String travelReturnDateStr = view.txtReturnDate.getText().trim();
        String priceStr = view.txtPrice.getText().trim();

        // Validar el destino
        if (!TravelValidator.isValidDestination(travelDestination)) {
            JOptionPane.showMessageDialog(view,
                    "El destino no puede estar vacío.");
        }

        // Validar lugares disponibles
        if (!TravelValidator.isValidAvailablePlaces(travelAvailablePlacesStr)) {
            JOptionPane.showMessageDialog(view,
                    "Los lugares disponibles deben "
                            + "ser un número entero positivo.");
        }
        int travelAvailablePlaces = Integer.parseInt(travelAvailablePlacesStr);

        // Validar precio
        if (!TravelValidator.isValidPrice(priceStr)) {
            JOptionPane.showMessageDialog(view,
                    "El precio debe ser un número decimal positivo.");
        }
        float price = Float.parseFloat(priceStr);

        // Validar fechas
        if (!TravelValidator.isValidTravelDate(travelDateStr)) {
            JOptionPane.showMessageDialog(view, "La fecha de inicio de viaje "
                    + "debe estar en el formato yyyy-MM-dd HH:mm.");
        }
        if (!TravelValidator.isValidReturnDate(travelReturnDateStr)) {
            JOptionPane.showMessageDialog(view, "La fecha de retorno debe "
                    + "estar en el formato yyyy-MM-dd HH:mm.");
        }
        if (!TravelValidator.areDatesValid(travelDateStr, travelReturnDateStr))
        {
            JOptionPane.showMessageDialog(view, "La fecha de retorno debe ser "
                    + "después de la fecha de inicio.");
        }

        // Convertir las fechas a LocalDate
        LocalDateTime travelDate = LocalDateTime
                .parse(travelDateStr, TravelValidator.DATE_FORMATTER);
        LocalDateTime returnDate = LocalDateTime
                .parse(travelReturnDateStr, TravelValidator.DATE_FORMATTER);
        
        travelUpdate.setDestination(travelDestination);
        travelUpdate.setAvailablePlaces(travelAvailablePlaces);
        travelUpdate.setTravelDate(travelDate);
        travelUpdate.setReturnDate(returnDate);
        travelUpdate.setPrice(price);
        
        var isSuccess = travelRepository.updateAsync(travelUpdate).join();
        
        if(isSuccess){
            fillTable();
        }
    }

}
