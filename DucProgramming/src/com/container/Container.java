package src.com.container;

import java.util.HashMap;


import src.com.vehicle.Ship;
import src.com.vehicle.Truck;
import src.com.vehicle.Vehicle;

public class Container {
    private String id;
    private double weight;
    private String type;
    private static HashMap<String, Container> allContainers = new HashMap<>();
    private static int idOrder = 0;

    public String getId() {
        return id;
    }

    public static int getIdOrder() {
        return idOrder;
    }

    public static void setIdOrder(int idOrder) {
        Container.idOrder = idOrder;
    }

    public static HashMap<String, Container> getAllContainers() {
        return allContainers;
    }

    public static void setAllContainers(HashMap<String, Container> allContainers) {
        Container.allContainers = allContainers;
    }

    public Container(double weight, String type) {
        this.id = "c-" + String.valueOf(idOrder);
        this.weight = weight;
        this.type = type;
        allContainers.put(this.id, this);
        idOrder++;
    }

    // Read from file
    public Container(String id, double weight, String type) {
        this.id = id;
        this.weight = weight;
        this.type = type;
        allContainers.put(id, this);
    }

    public double getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    public String writeFileString() {
        return id + "," + weight + "," + type;
    }

    public double calculateFuelConsumption(Vehicle vehicle, double distanceInKm) {
        double fuelConsumption = 0.0;

        if (vehicle instanceof Ship) {
            fuelConsumption = getFuelConsumptionPerKmForShip();
        } else if (vehicle instanceof Truck) {
            fuelConsumption = getFuelConsumptionPerKmForTruck();
        }
        System.out.println();
        return fuelConsumption * weight * distanceInKm;
    }

    private double getFuelConsumptionPerKmForShip() {
        switch (type) {
            case "dry storage":
                return 3.5;
            case "open top":
                return 2.8;
            case "open side":
                return 2.7;
            case "refrigerated":
                return 4.5;
            case "liquid":
                return 4.8;
            default:
                return 0.0;
        }
    }

    private double getFuelConsumptionPerKmForTruck() {

        switch (type) {
            case "dry storage":
                return 4.6;
            case "open top":
                return 3.2;
            case "open side":
                return 3.2;
            case "refrigerated":
                return 5.4;
            case "liquid":
                return 5.3;
            default:
                return 0.0;
        }
    }

    @Override
    public String toString() {
        return "Container ID: " + id +
                "\nWeight: " + weight +
                "\nType: " + type;
    }

}
