package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author CyborgK27
 */
@AllArgsConstructor
public @Data class User {

    public User() {

    }
    private int userId;
    private String userName;
    private String userEmail;
    private String userPassword;
}
