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
 *
 * @author CyborgK27
 */
public class TravelController implements ActionListener, ListSelectionListener {

    private FrmTravel _view;
    private ITravelRepository _travelRepository;

    public TravelController(FrmTravel view, ITravelRepository travelRepository) {
        _view = view;
        _travelRepository = travelRepository;
        _view.btnAdd.addActionListener(this);
        _view.btnDelete.addActionListener(this);
        _view.btnCancel.addActionListener(this);
        _view.btnEdit.addActionListener(this);
        _view.TblGeneric.getSelectionModel().addListSelectionListener(this);
        fill(_view.TblGeneric, _travelRepository.getAllTravels());
    }

    public void init() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _view.btnAdd && _view.btnAdd.getText() == "AGREGAR") {
            buttonRegister();
            
        }

        if (e.getSource() == _view.btnDelete) {
            buttonDelete();
        }
        if (e.getSource() == _view.btnCancel && !"AGREGAR".equals(_view.btnAdd.getText())){
            _view.btnAdd.setText("AGREGAR");  
        }
        if (e.getSource() == _view.TblGeneric) {
            fillFrm();
        }
        if(e.getSource() == _view.btnEdit){
            editTravel();
        }
    }

    public Date parseStringToDate(String date) throws ParseException {
        String format = "dd MMM yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        return dateFormat.parse(date);
    }

    // Método para llenar el JTable
    private void fill(JTable TblGeneric, List<Travel> allTravels) {
        // Definir columnas de la tabla
        String[] columnNames = {"ID", "Destino", "Fecha de Viaje", "Fecha de retorno", "Precio", "Asientos Disponibles"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        String DATE_FORMAT = "yyyy-MM-dd";
        // Iterar sobre los datos y agregarlos a la tabla
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        for (Travel t : allTravels) {
            Object[] row = {
                t.getTravelId(),
                t.getDestiny(),
                dateFormat.format(t.getTravelDate()), // Formatear fecha
                dateFormat.format(t.getReturnDate()),
                t.getTravelPrice(),
                t.getPlacesAvaliable()
            // Agregar más atributos según sea necesario
            };
            model.addRow(row); // Agregar una fila al modelo de tabla
        }

        // Asignar el modelo a la tabla
        TblGeneric.setModel(model);
    }

    public void buttonRegister() {
        String destination = _view.txtDestination.getText();
        String departure_date = ((JTextField) _view.jcDepartureDate.getDateEditor().getUiComponent()).getText();
        String return_date = ((JTextField) _view.jcReturnDate.getDateEditor().getUiComponent()).getText();
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

    private void buttonDelete() {
        int id = getIdFromTable();

        if (id != 0) {
            _travelRepository.removeTravel(id);
            fill(_view.TblGeneric, _travelRepository.getAllTravels());
        }
        JOptionPane.showMessageDialog(null, "Seleccione una fila dentro de la tabla para eliminar");
    }

    private void buttonEdit() {
        
    }

    private void fillFrm() {
        int id = getIdFromTable();

        if (id != 0) {
            var travel = _travelRepository.getByIdTravel(id);
            _view.txtDestination.setText(travel.getDestiny());
            _view.jcDepartureDate.setDate(travel.getTravelDate());
            _view.jcReturnDate.setDate(travel.getReturnDate());
            _view.txtPrice.setText(travel.getTravelPrice() + "");
            _view.jsAvaliableSeats.setValue(travel.getPlacesAvaliable());
        }
    }

    public int getIdFromTable() {
        int selectedRow = _view.TblGeneric.getSelectedRow();
        int id = 0;
        if (selectedRow != -1) {
            id = (int) _view.TblGeneric.getValueAt(selectedRow, 0);
            System.out.println(id);
        }
        return id;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == _view.TblGeneric.getSelectionModel()) {
            fillFrm();
        }
    }

    private void editTravel() {
        
        int id = getIdFromTable();
        String destination = _view.txtDestination.getText();
        String departure_date = ((JTextField)_view.jcDepartureDate.getDateEditor().getUiComponent()).getText();
        String return_date = ((JTextField) _view.jcReturnDate.getDateEditor().getUiComponent()).getText();
        float price = Float.parseFloat(_view.txtPrice.getText());
        int avaliable_seats = (int) _view.jsAvaliableSeats.getValue();

        try {
            Date departureDate = parseStringToDate(departure_date);
            Date returnDate = parseStringToDate(return_date);

            var editTravel = new Travel(id, destination, departureDate, returnDate, price, avaliable_seats);
            _travelRepository.updateTravel(editTravel);
            fill(_view.TblGeneric, _travelRepository.getAllTravels());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
    }
}
