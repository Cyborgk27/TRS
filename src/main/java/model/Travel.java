package model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Representa un objeto de viaje con sus atributos principales.
 * Utiliza Lombok para generar automáticamente
 * constructores y métodos getter/setter.
 *
 * <p>
 * La clase {@code Travel} encapsula detalles
 * sobre un viaje, incluyendo su
 * destino, fechas de viaje, precio
 * y asientos disponibles.
 * </p>
 *
 * <p>
 * Esta clase está diseñada para ser utilizada
 * con una base de datos o como un objeto
 * de transferencia de datos (DTO) para
 * manejar información de viajes en una aplicación.
 * </p>
 *
 * @author CyborgK27
 * @version 1.0
 */
@AllArgsConstructor
public @Data class Travel {
    /**
     * Constructor por defecto para la clase {@code Travel}.
     */
    public Travel() {
    }

    /**
     * Identificador único del viaje.
     */
    private int travelId;

    /**
     * Destino del viaje.
     */
    private String destiny;

    /**
     * Fecha de salida del viaje.
     */
    private Date travelDate;

    /**
     * Fecha de retorno del viaje.
     */
    private Date returnDate;

    /**
     * Precio del viaje.
     */
    private float travelPrice;

    /**
     * Número de asientos disponibles para el viaje.
     */
    private int placesAvaliable;
}
