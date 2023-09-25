package src.com.user;

import java.sql.Date;

import src.com.port.Port;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    public void createManager(String username, String password, Port port) {
        User newUser = new PortManager(username, password, port);
    }

    @Override
    public void calculateFuel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateFuel'");
    }

    @Override
    public void calculateWeightContainers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateWeightContainers'");
    }

    @Override
    public void listShipsPort(Port port) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listShipsPort'");
    }

    @Override
    public void listTrips(Date day) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listTrips'");
    }

    @Override
    public void listTripsRange(Date start, Date end) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listTripsRange'");
    }

    @Override
    public String writeFileString() {
        return "ADMIN," + super.getUsername() + "," + super.getPassword();
    }

}
