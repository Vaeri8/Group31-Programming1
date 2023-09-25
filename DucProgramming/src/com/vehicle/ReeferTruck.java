package src.com.vehicle;

import java.util.List;

import src.com.container.Container;
import src.com.port.Port;

public class ReeferTruck extends Truck {

    public ReeferTruck(String name, double fuelCapacity, double carryingCapacity) {
        super(name, fuelCapacity, carryingCapacity);
    }

    public ReeferTruck(String id, String name, double currentFuel, double fuelCapacity, double carryingCapacity,
            List<Container> containers) {
        super(id, name, currentFuel, fuelCapacity, carryingCapacity, containers);
    }

    @Override
    public boolean canCarryContainer(Container container) {
        if (container.getType() == "refrigerated")
            return true;
        return false;
    }

    @Override
    public String writeFileString() {
        String loadedContaineString = "";
        for (Container c : super.getLoadedContainers()) {
            loadedContaineString += c.getId() + "|";
        }
        return "Reefer," + super.getId() + "," + super.getName() + "," + super.getCurrentFuel() + ","
                + super.getFuelCapacity() + "," + super.getCarryingCapacity() + "," + super.getCurrentPort().getId()
                + "," + loadedContaineString;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reefer Truck Information:\n");
        sb.append("ID: ").append(getId()).append("\n");
        sb.append("Name: ").append(getName()).append("\n");
        sb.append("Current Fuel: ").append(getCurrentFuel()).append("\n");
        sb.append("Fuel Capacity: ").append(getFuelCapacity()).append("\n");
        sb.append("Carrying Capacity: ").append(getCarryingCapacity()).append("\n");

        sb.append("Loaded Containers: ");
        List<Container> loadedContainers = getLoadedContainers();
        if (!loadedContainers.isEmpty()) {
            for (Container container : loadedContainers) {
                sb.append(container.getId()).append(", ");
            }
            sb.setLength(sb.length() - 2); // Remove the trailing ", "
        } else {
            sb.append("None");
        }

        sb.append("\nCurrent Port ID: ").append(getCurrentPort().getId()).append("\n");

        return sb.toString();
    }

}
