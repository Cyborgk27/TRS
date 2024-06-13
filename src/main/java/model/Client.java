package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author CyborgK27
 */

public @Data
class Client extends User {

    public Client() {
    }

    public Client(String cellphone, int userId, String userName, String userEmail, String userPassword) {
        super(userId, userName, userEmail, userPassword);
        this.cellphone = cellphone;
    }

    public Client(String cellphone) {
        this.cellphone = cellphone;
    }

    private String cellphone;
}
