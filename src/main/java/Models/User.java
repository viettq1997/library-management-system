package Models;

import java.util.HashSet;
import java.util.Set;

public class User {

    private static User instance;

    private String userName;

    private User() {
    }

    private User(String userName) {
        this.userName = userName;
    }

    public static User getInstace() {
        if (instance == null) {
            instance = new User();
        }

        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserSession(String userName) {
        this.userName = userName;
    }

    public void cleanUserSession() {
        userName = null;// or null
    }

    @Override
    public String toString() {
        return "UserSession{"
                + "userName='" + userName
                + '}';
    }
}
