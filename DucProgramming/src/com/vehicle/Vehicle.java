package src.com.vehicle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import src.com.container.Container;
import src.com.port.Port;
import src.com.trip.Trip;

public abstract class Vehicle implements Serializable {
    private String id;
    private String name;
    private double currentFuel;
    private double carryingCapacity;
    private double fuelCapacity;
    private Port currentPort = null;
    private List<Container> loadedContainers;
    private static HashMap<String, Vehicle> allVehicles = new HashMap<>();
    private static int idOrder = 0;

    public static int getIdOrder() {
        return idOrder;
    }

    public static void setIdOrder(int idOrder) {
        Vehicle.idOrder = idOrder;
    }

    public Vehicle(String id, String name, double fuelCapacity, double carryingCapacity) {
        this.id = id;
        this.name = name;
        this.currentFuel = fuelCapacity;
        this.fuelCapacity = fuelCapacity;
        this.carryingCapacity = carryingCapacity;
        this.loadedContainers = new ArrayList<>();
        allVehicles.put(id, this);
        idOrder++;
    }

    public Vehicle(String id, String name, double currentFuel, double fuelCapacity, double carryingCapacity,
            List<Container> containers) {
        this.id = id;
        this.name = name;
        this.currentFuel = currentFuel;
        this.fuelCapacity = fuelCapacity;
        this.carryingCapacity = carryingCapacity;
        this.loadedContainers = containers;
        allVehicles.put(id, this);
    }

    public abstract boolean canCarryContainer(Container container);

    public static HashMap<String, Vehicle> getAllVehicles() {
        return allVehicles;
    }

    public void setAllVehicles(HashMap<String, Vehicle> allVehicles) {
        Vehicle.allVehicles = allVehicles;
    }

    private double getContainersWeight() {
        double weight = 0;
        if (loadedContainers.size() > 0)
            for (Container container : loadedContainers) {
                weight += container.getWeight();
            }
        return weight;
    }

    public void loadContainer(Container container) {
        if (currentPort == null) {
            System.err.println("Currently at sailaway");
            return;
        }
        if (!canCarryContainer(container)) {
            System.err.println("Can not carry this type of container");
            return;
        }

        if (currentPort.getContainers().contains(container)) {
            double weight = getContainersWeight();
            if (weight + container.getWeight() > carryingCapacity) {
                System.err.println("Not enough capacity to load this container.");
                return;
            }
            boolean check = currentPort.removeContainer(container);

            if (check) {
                loadedContainers.add(container);
                System.out.println("New container loaded to the vehicle.");
            }
            return;
        }

        System.err.println("The container not available at the current port.");

    }

    public void unLoadContainer(Container container) {
        if (loadedContainers.size() == 0) {
            System.err.println("No container loaded.");
            return;
        }
        if (currentPort == null) {
            System.err.println("Can not unload at sailaway.");
            return;
        }
        if (loadedContainers.contains(container)) {
            boolean check = currentPort.addContainer(container);
            if (check) {
                loadedContainers.remove(container);
                System.out.println("Container unloaded.");
            }
            return;
        }
        System.err.println("The container not exist in this vehicle");

    }

    public void reFuel() {
        currentFuel = fuelCapacity;
        System.out.println("Refueled.");
    }

    public void moveToPort(Port port) {
        if (currentPort == null) {
            System.err.println("Can not move beacause the vehical is sailaway.");
            return;
        }
        if (currentPort.equals(port)) {
            System.err.println("Can not move to same port");
            return;
        }
        if (this instanceof Truck && !port.getLandingAbility()) {
            System.err.println("Landing is not available for Truck");
            return;
        }

        double distance = currentPort.calculateDistanceTo(port);
        double calculateFuelForTrip = 0;
        for (Container container : loadedContainers) {
            calculateFuelForTrip += container.calculateFuelConsumption(this, distance);
        }

        if (calculateFuelForTrip > fuelCapacity) {
            System.err.println("Even full fuel will not be enough for the trip.");
            return;
        }
        if (calculateFuelForTrip > currentFuel) {
            System.err.println("Current fuel will not be enough for the trip.");
            return;
        }

        currentFuel -= calculateFuelForTrip;
        currentPort.removeVehicle(this);
        port.addVehicle(this);
        Trip newTrip = new Trip(this, currentPort, port, "arriving");
        currentPort.addTrip(newTrip);
        port.addTrip(newTrip);
        currentPort = port;
        System.out.println("Successfully moved.");
    }

    public abstract String writeFileString();

    public String getId() {
        return id;
    }

    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public double getCurrentFuel() {
        return currentFuel;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public String getName() {
        return name;
    }

    public List<Container> getLoadedContainers() {
        return loadedContainers;
    }

    public void setCarryingCapacity(double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    public void setCurrentPort(Port newPort) {
        if (this.currentPort != null)
            this.currentPort.removeVehicle(this);
        if (newPort != null) {

            this.currentPort = newPort;
            newPort.addVehicle(this);
        }
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
