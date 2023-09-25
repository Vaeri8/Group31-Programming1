package src.com.vehicle;

import java.util.List;

import src.com.container.Container;
import src.com.port.Port;

public abstract class Truck extends Vehicle {

    public Truck(String name, double fuelCapacity, double carryingCapacity) {
        super("tr-" + String.valueOf(Vehicle.getIdOrder()), name, fuelCapacity, carryingCapacity);
    }

    public Truck(String id, String name,double currentFuel, double fuelCapacity, double carryingCapacity,List<Container> containers) {
        super(id, name,currentFuel, fuelCapacity, carryingCapacity, containers);
    }
}
