package src.com.port;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import src.com.container.Container;
import src.com.vehicle.Vehicle;
import src.com.trip.Trip;

public class Port implements Serializable {
    private String id; // Unique ID (formatted as p-number)
    private String name;
    private double latitude;
    private double longitude;
    private double storingCapacity;
    private boolean landingAbility;
    private List<Container> containers;
    private List<Vehicle> vehicles;
    private List<Trip> trips;
    private static HashMap<String, Port> allPorts = new HashMap<>();
    private static int idOrder = 0;

    public static HashMap<String, Port> getAllPorts() {
        return allPorts;
    }

    public static void setAllPorts(HashMap<String, Port> allPorts) {
        Port.allPorts = allPorts;
    }

    public static void setIdOrder(int idOrder) {
        Port.idOrder = idOrder;
    }

    public static int getIdOrder() {
        return idOrder;
    }

    public Port(String name, double latitude, double longitude, double storingCapacity,
            boolean landingAbility) {
        this.id = "p-" + String.valueOf(idOrder);
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.containers = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.trips = new ArrayList<>();
        allPorts.put("p-" + String.valueOf(idOrder), this);
        idOrder++;
    }

    // Read data from file
    public Port(String id, String name, double latitude, double longitude, double storingCapacity,
            boolean landingAbility, List<Container> containers) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.containers = containers;
        this.vehicles = new ArrayList<>();
        this.trips = new ArrayList<>();
        allPorts.put(id, this);
    }

    private double getContainersWeight() {
        double weight = 0;
        for (Container container : containers) {
            weight += container.getWeight();
        }
        return weight;
    }

    public boolean addContainer(Container container) {
        if (containers.contains(container)) {
            System.out.println("Already have this container in the port.");
            return false;
        }
        double weight = getContainersWeight();
        if (weight + container.getWeight() > storingCapacity) {
            System.err.println("The port not having enough capacity to store new container.");
            return false;
        }
        containers.add(container);
        System.out.println("New container added to the port.");
        return true;
    }

    public boolean removeContainer(Container container) {
        if (containers.contains(container)) {
            containers.remove(container);
            System.out.println("Container removed from the port.");
            return true;
        }
        System.out.println("Container not found in the port.");
        return false;

    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public void addTrip(Trip trip) {
        trips.add(trip);
    }

    public double calculateDistanceTo(Port anotherPort) {

        double lon1 = Math.toRadians(this.getLongitude());
        double lon2 = Math.toRadians(anotherPort.getLongitude());
        double lat1 = Math.toRadians(this.getLatitude());
        double lat2 = Math.toRadians(anotherPort.getLatitude());

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                        * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return (c * r);
    }

    public boolean getLandingAbility() {
        return landingAbility;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public List<Trip> geTrips() {
        return trips;
    }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public double getStoringCapacity() {
        return storingCapacity;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }

    public String writeFileString() {
        String containersString = "";
        for (Container c : containers) {
            if (c != null)
                containersString += c.getId() + "|";
        }

        String vehiclesString = "";
        for (Vehicle v : vehicles) {
            vehiclesString += v.getId() + "|";
        }

        String tripsString = "";
        for (Trip t : trips) {
            tripsString += t.getId() + "|";
        }
        return id + "," + name + "," + latitude + "," + longitude + "," + storingCapacity + ","
                + (landingAbility ? "true" : "false") + ","
                + containersString + "," + vehiclesString + "," + tripsString;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Port ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Latitude: ").append(latitude).append("\n");
        sb.append("Longitude: ").append(longitude).append("\n");
        sb.append("Storing Capacity: ").append(storingCapacity).append("\n");
        sb.append("Landing Ability: ").append(landingAbility).append("\n");

        sb.append("Containers:\n");
        if (containers != null)
            for (Container container : containers) {
                sb.append("  - ").append(container.toString()).append("\n");
            }

        sb.append("Vehicles:\n");
        if (vehicles != null)
            for (Vehicle vehicle : vehicles) {
                sb.append("  - ").append(vehicle.toString()).append("\n");
            }

        sb.append("Trips:\n");
        if (trips != null)
            for (Trip trip : trips) {
                sb.append("  - ").append(trip.toString()).append("\n");
            }

        return sb.toString();
    }

}
