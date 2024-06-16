package controller;

import interfaces.ITravelRepository;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Travel;
import view.FrmTravel;

/**
 * Controlador para gestionar las acciones en la vista de viajes.
 * Implementa ActionListener y ListSelectionListener
 * para manejar eventos de acciones y selecciones.
 *
 * @author CyborgK27
 */
public class TravelController implements ActionListener, ListSelectionListener {

    private final FrmTravel _view;
    private final ITravelRepository _travelRepository;
    /**
     * Constructor de TravelController.
     *
     * @param view La vista FrmTravel asociada.
     * @param travelRepository El repositorio de
     * viajes para la gestión de datos.
     */
    public TravelController(FrmTravel view,
            ITravelRepository travelRepository) {
        _view = view;
        _travelRepository = travelRepository;
        _view.btnAdd.addActionListener(this);
        _view.btnDelete.addActionListener(this);
        _view.btnCancel.addActionListener(this);
        _view.btnEdit.addActionListener(this);
        _view.TblGeneric.getSelectionModel().addListSelectionListener(this);
        fill(_view.TblGeneric, _travelRepository.getAllTravels());
    }

    /**
     * Método para inicializar el controlador.
     */
    public void init() {
    }

    /**
     * Maneja los eventos de acciones en la vista.
     *
     * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _view.btnAdd &&_view.
                btnAdd.getText().equals("AGREGAR")) {
            buttonRegister();
        }

        if (e.getSource() == _view.btnDelete) {
            buttonDelete();
        }
        if (e.getSource() == _view.btnCancel &&
                !"AGREGAR".equals(_view.btnAdd.getText())) {
            _view.btnAdd.setText("AGREGAR");
        }
        if (e.getSource() == _view.TblGeneric) {
            fillFrm();
        }
        if (e.getSource() == _view.btnEdit) {
            editTravel();
        }
    }

    /**
     * Convierte una cadena de texto a una fecha.
     * 
     * @param date La cadena de texto con la fecha.
     * @return Date La fecha convertida.
     * @throws ParseException Si la cadena no se puede analizar.
     */
    public Date parseStringToDate(String date) throws ParseException {
        String format = "dd MMM yyyy";
        var dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        return dateFormat.parse(date);
    }

    /**
     * Llena el JTable con los datos de todos los viajes.
     *
     * @param TblGeneric El JTable a llenar.
     * @param allTravels La lista de todos los viajes.
     */
    private void fill(final JTable tblGeneric, List<Travel> allTravels) {
        String[] columnNames = {"ID", "Destino", "Fecha de Viaje", "Fecha de retorno", "Precio", "Asientos Disponibles"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        
        for (Travel t : allTravels) {
            Object[] row = {
                t.getTravelId(),
                t.getDestiny(),
                dateFormat.format(t.getTravelDate()),
                dateFormat.format(t.getReturnDate()),
                t.getTravelPrice(),
                t.getPlacesAvaliable()
            };
            model.addRow(row);
        }

        tblGeneric.setModel(model);
    }

    /**
     * Maneja el registro de un nuevo viaje.
     */
    public void buttonRegister() {
        String destination = _view.txtDestination.getText();
        String departure_date = ((JTextField) _view.jcDepartureDate.getDateEditor()
                .getUiComponent()).getText();
        String return_date = ((JTextField) _view.jcReturnDate.getDateEditor().
                getUiComponent()).getText();
        float price = Float.parseFloat(_view.txtPrice.getText());
        int avaliable_seats = (int) _view.jsAvaliableSeats.getValue();

        try {
            Date departureDate = parseStringToDate(departure_date);
            Date returnDate = parseStringToDate(return_date);

            var travel = new Travel(0, destination, departureDate, returnDate, price, avaliable_seats);
            _travelRepository.createTravel(travel);
            fill(_view.TblGeneric, _travelRepository.getAllTravels());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Maneja la eliminación de un viaje.
     */
    private void buttonDelete() {
        int id = getIdFromTable();

        if (id != 0) {
            _travelRepository.removeTravel(id);
            fill(_view.TblGeneric, _travelRepository.getAllTravels());
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila dentro de la tabla para eliminar");
        }
    }

    /**
     * Método para llenar el formulario con los datos del viaje seleccionado.
     */
    private void fillFrm() {
        int id = getIdFromTable();

        if (id != 0) {
            var travel = _travelRepository.getByIdTravel(id);
            _view.txtDestination.setText(travel.getDestiny());
            _view.jcDepartureDate.setDate(travel.getTravelDate());
            _view.jcReturnDate.setDate(travel.getReturnDate());
            _view.txtPrice.setText(String.valueOf(travel.getTravelPrice()));
            _view.jsAvaliableSeats.setValue(travel.getPlacesAvaliable());
        }
    }

    /**
     * Obtiene el ID del viaje seleccionado en la tabla.
     *
     * @return int El ID del viaje seleccionado.
     */
    public int getIdFromTable() {
        int selectedRow = _view.TblGeneric.getSelectedRow();
        int id = 0;
        if (selectedRow != -1) {
            id = (int) _view.TblGeneric.getValueAt(selectedRow, 0);
            System.out.println(id);
        }
        return id;
    }

    /**
     * Maneja el cambio de selección en la tabla.
     *
     * @param e El evento de cambio de selección.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == _view.TblGeneric.getSelectionModel()) {
            fillFrm();
        }
    }

    /**
     * Maneja la edición de un viaje existente.
     */
    private void editTravel() {
        int id = getIdFromTable();
        String destination = _view.txtDestination.getText();
        String departureDate = ((JTextField)
                _view.jcDepartureDate.getDateEditor()
                        .getUiComponent()).getText();
        String returnDate = ((JTextField)
                _view.jcReturnDate.getDateEditor()
                        .getUiComponent()).getText();
        float price = Float.parseFloat(_view.txtPrice.getText());
        int avaliableSeats = (int) _view.jsAvaliableSeats.getValue();

        try {
            Date departureDate2 = parseStringToDate(departureDate);
            Date returnDate2 = parseStringToDate(returnDate);

            var editTravel = new Travel(id, destination,
                    departureDate2, returnDate2, price, avaliableSeats);
            _travelRepository.updateTravel(editTravel);
            fill(_view.TblGeneric, _travelRepository.getAllTravels());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}
