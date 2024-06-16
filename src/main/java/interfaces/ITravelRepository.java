package interfaces;

import java.util.List;
import model.Travel;

/**
 *
 * @author VIAMATICA
 */
public interface ITravelRepository {
    boolean createTravel (Travel travel);
    List<Travel> getAllTravels();
    Travel getByIdTravel(int id);
    boolean updateTravel(Travel travel);
    boolean removeTravel (int id);
}
