package src.com.trip;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;

import src.com.vehicle.Vehicle;
import src.com.port.Port;

public class Trip implements Serializable {
    private String id;
    private Vehicle vehicle;
    private Date departureDate;
    private Date arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private String status;
    private static HashMap<String, Trip> allTrips = new HashMap<>();
    private static int idOrder = 0;

    public static int getIdOrder() {
        return idOrder;
    }

    public static void setIdOrder(int idOrder) {
        Trip.idOrder = idOrder;
    }

    public static HashMap<String, Trip> getAllTrips() {
        return allTrips;
    }

    public void setAllTrips(HashMap<String, Trip> allTrips) {
        Trip.allTrips = allTrips;
    }

    public Trip(Vehicle vehicle, Port departurePort, Port arrivalPort,
            String status) {
        this.id = String.valueOf(idOrder);
        this.vehicle = vehicle;
        this.departureDate = new Date();
        this.arrivalDate = new Date();
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = status;
        idOrder++;
        allTrips.put(this.id, this);
        departurePort.addTrip(this);
        arrivalPort.addTrip(this);
    }

    // Read data from file
    public Trip(String id, Vehicle vehicle, Date departureDate, Date arrivalDate, Port departurePort, Port arrivalPort,
            String status) {
        this.id = id;
        this.vehicle = vehicle;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = status;
        allTrips.put(this.id, this);
    }

    public String getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Trip: " + departurePort.getName() + " to " + arrivalPort.getName() +
                ", Vehicle: " + vehicle.getName() +
                ", Status: " + status;
    }

    public double getTripDistance() {
        // Calculate the distance between departure and arrival ports (you need a method
        // for this)
        double distance = departurePort.calculateDistanceTo(arrivalPort);
        return distance;
    }

    public String writeFileString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDepature = dateFormat.format(departureDate);
        String formattedArrival = dateFormat.format(arrivalDate);
        return id + "," + vehicle.getId() + "," + formattedDepature + "," + formattedArrival + ","
                + departurePort.getId() + "," + arrivalPort.getId() + "," + status;
    }
}
