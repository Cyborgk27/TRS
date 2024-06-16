package interfaces;

import java.util.List;
import model.Travel;

/**
 * Interfaz para la gestión de datos de viajes en el repositorio. Proporciona
 * métodos para crear, recuperar, actualizar y eliminar viajes.
 *
 * @author CyborgK27
 */
public interface ITravelRepository {

    /**
     * Crea un nuevo viaje en el repositorio.
     *
     * @param travel El objeto Travel que contiene los detalles del viaje.
     * @return boolean True si el viaje fue creado exitosamente, false en caso
     * contrario.
     */
    boolean createTravel(Travel travel);

    /**
     * Retorna una lista de todos los objetos Travel.
     *
     * @return Una lista de objetos Travel.
     */
    List<Travel> getAllTravels();

    /**
     * Recupera un viaje por su ID.
     *
     * @param id El ID del viaje a recuperar.
     * @return Travel El objeto Travel con el ID especificado, o null si no se
     * encuentra.
     */
    Travel getByIdTravel(int id);

    /**
     * Actualiza un viaje existente en el repositorio.
     *
     * @param travel El objeto Travel que contiene los detalles actualizados del
     * viaje.
     * @return boolean True si el viaje fue actualizado exitosamente, false en
     * caso contrario.
     */
    boolean updateTravel(Travel travel);

    /**
     * Elimina un viaje del repositorio por su ID.
     *
     * @param id El ID del viaje a eliminar.
     * @return boolean True si el viaje fue eliminado exitosamente, false en
     * caso contrario.
     */
    boolean removeTravel(int id);
}
