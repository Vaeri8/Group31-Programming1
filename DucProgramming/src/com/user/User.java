package src.com.user;

import java.io.Serializable;

import src.com.container.Container;
import src.com.port.Port;
import src.com.vehicle.Vehicle;

import java.sql.Date;

import java.util.HashMap;

public abstract class User implements Serializable {
    private String username;
    private String password;
    private static HashMap<String, User> allUsers = new HashMap<>();

    public static HashMap<String, User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(HashMap<String, User> allUsers) {
        User.allUsers = allUsers;
    }

    public static User login(String username, String password) {
        if (!allUsers.containsKey(username))
            return null;
        if (allUsers.get(username).getPassword().equals(password))
            return allUsers.get(username);
        return null;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        allUsers.put(username, this);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public abstract String writeFileString();

    public abstract void calculateFuel();

    public abstract void calculateWeightContainers();

    public abstract void listShipsPort(Port port);

    public abstract void listTrips(Date day);

    public abstract void listTripsRange(Date start, Date end);

}
