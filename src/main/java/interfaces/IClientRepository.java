package interfaces;

import model.Client;

/**
 *
 * @author CyborgK27
 */
public interface IClientRepository {
    boolean registerClient (Client Client);
    boolean loginClient(String email, String password);
}
