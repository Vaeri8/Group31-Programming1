package src.com.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.List;

import src.com.container.Container;
import src.com.port.Port;
import src.com.trip.Trip;
import src.com.user.Admin;
import src.com.user.PortManager;
import src.com.user.User;
import src.com.vehicle.BasicTruck;
import src.com.vehicle.ReeferTruck;
import src.com.vehicle.Ship;
import src.com.vehicle.TankerTruck;
import src.com.vehicle.Vehicle;

public class DataIO {

    public static void writeToFile() {
        // Container
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("containers.txt", false))) {
            // Write idOrder as metadata in the first line
            writer.write("IdOrder=" + Container.getIdOrder());
            writer.newLine();

            // Write header (optional, for reference)
            writer.write("ID,weight,type");
            writer.newLine();

            for (Container container : Container.getAllContainers().values()) {
                writer.write(container.writeFileString());
                writer.newLine();
            }
            System.out.println("Data written to containers.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Vehicle
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("vehicles.txt", false))) {
            // Write idOrder as metadata in the first line
            writer.write("IdOrder=" + Vehicle.getIdOrder());
            writer.newLine();

            // Write header (optional, for reference)
            writer.write("Type,id,name,currentFuel,fuelCapacity,carryingCapacity,loadedContainers");
            writer.newLine();

            for (Vehicle vehicle : Vehicle.getAllVehicles().values()) {
                writer.write(vehicle.writeFileString());
                writer.newLine();
            }
            System.out.println("Data written to vehicles.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Trip
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("trips.txt", false))) {
            // Write idOrder as metadata in the first line
            writer.write("IdOrder=" + Trip.getIdOrder());
            writer.newLine();

            // Write header (optional, for reference)
            writer.write("ID,vehicleId,departureTime,arrivalTime,departurePortId,arrivalPortId,status");
            writer.newLine();

            for (Trip trip : Trip.getAllTrips().values()) {
                writer.write(trip.writeFileString());
                writer.newLine();
            }
            System.out.println("Data written to trips.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Port
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ports.txt", false))) {
            // Write idOrder as metadata in the first line
            writer.write("IdOrder=" + Port.getIdOrder());
            writer.newLine();

            // Write header (optional, for reference)
            writer.write("ID,name,latitude,longtitude,storingCapacity,landingAbility,containers,vehicles,trips");
            writer.newLine();

            for (Port port : Port.getAllPorts().values()) {
                writer.write(port.writeFileString());
                writer.newLine();
            }
            System.out.println("Data written to ports.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // User
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", false))) {

            // Write header (optional, for reference)
            writer.write("Type,username,password,assignedPort(for PortManager)");
            writer.newLine();

            for (User user : User.getAllUsers().values()) {
                writer.write(user.writeFileString());
                writer.newLine();
            }
            System.out.println("Data written to users.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readFromFile() {
        int idOrder = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("containers.txt"))) {
            String metadataLine = reader.readLine();
            if (metadataLine != null && metadataLine.startsWith("IdOrder=")) {
                idOrder = Integer.parseInt(metadataLine.substring("IdOrder=".length()));
            }
            reader.readLine(); // read Header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] containerData = line.split(",");
                String id = containerData[0];
                double weight = Double.parseDouble(containerData[1]);
                String type = containerData[2];
                Container container = new Container(id, weight, type);
            }
            Container.setIdOrder(idOrder);
            System.out.println("Data read from posts.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Port
        try (BufferedReader reader = new BufferedReader(new FileReader("ports.txt"))) {
            String metadataLine = reader.readLine();
            if (metadataLine != null && metadataLine.startsWith("IdOrder=")) {
                idOrder = Integer.parseInt(metadataLine.substring("IdOrder=".length()));
            }
            reader.readLine(); // read Header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] ar = line.split(",");
            
                String id = ar[0];
                String name = ar[1];

                double latitude = Double.parseDouble(ar[2]);
                double longitude = Double.parseDouble(ar[3]);
                double storingCapacity = Double.parseDouble(ar[4]);
                boolean landingAbility = ar[5].equals("true") ? true : false;
                List<Container> containers = new ArrayList<>();
                if (ar.length >= 7) {
                    String[] containerAr = ar[6].split("\\|");
                    for (String cId : containerAr) {
                        if (Container.getAllContainers().containsKey(cId)) {
                            containers.add(Container.getAllContainers().get(cId));

                        }
                    }
                }

                Port newPort = new Port(id, name, latitude, longitude, storingCapacity, landingAbility, containers);
            }
            Port.setIdOrder(idOrder);
            System.out.println("Data read from ports.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("vehicles.txt"))) {
            String metadataLine = reader.readLine();
            if (metadataLine != null && metadataLine.startsWith("IdOrder=")) {
                idOrder = Integer.parseInt(metadataLine.substring("IdOrder=".length()));
            }
            reader.readLine(); // read Header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] ar = line.split(",");

                String id = ar[1];
                String name = ar[2];
                double currentFuel = Double.parseDouble(ar[3]);
                double fuelCapacity = Double.parseDouble(ar[4]);
                double carryingCapacity = Double.parseDouble(ar[5]);
                Port currentPort = null;
                if (Port.getAllPorts().containsKey(ar[6]))
                    currentPort = Port.getAllPorts().get(ar[6]);

                List<Container> loadedContainers = new ArrayList<>();
                if (ar.length >= 8) {
                    String[] arContainer = ar[7].split("\\|");
                    for (String cId : arContainer) {
                        if (Container.getAllContainers().containsKey(cId)) {
                            loadedContainers.add(Container.getAllContainers().get(cId));
                        }
                    }
                }

                Vehicle newVehicle;
                switch (ar[0]) {
                    case "Ship":
                        newVehicle = new Ship(id, name, currentFuel, fuelCapacity, carryingCapacity,
                                loadedContainers);
                        newVehicle.setCurrentPort(currentPort);
                        break;
                    case "Tanker":
                        newVehicle = new TankerTruck(id, name, currentFuel, fuelCapacity, carryingCapacity,
                                loadedContainers);
                        newVehicle.setCurrentPort(currentPort);
                        break;
                    case "Basic":
                        newVehicle = new BasicTruck(id, name, currentFuel, fuelCapacity, carryingCapacity,
                                loadedContainers);
                        newVehicle.setCurrentPort(currentPort);
                        break;
                    case "Reefer":
                        newVehicle = new ReeferTruck(id, name, currentFuel, fuelCapacity, carryingCapacity,
                                loadedContainers);
                        newVehicle.setCurrentPort(currentPort);
                        break;
                }

            }
            Vehicle.setIdOrder(idOrder);

            System.out.println("Data read from vehicles.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("trips.txt"))) {
            String metadataLine = reader.readLine();
            if (metadataLine != null && metadataLine.startsWith("IdOrder=")) {
                idOrder = Integer.parseInt(metadataLine.substring("IdOrder=".length()));
            }
            reader.readLine(); // read Header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] ar = line.split(",");

                String id = ar[0];
                Vehicle vehicle = null;
                if (Vehicle.getAllVehicles().containsKey(ar[1]))
                    vehicle = Vehicle.getAllVehicles().get(ar[1]);
                String departureDate = ar[2];
                String arrivalDate = ar[3];
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsedDepartureDate = null;
                Date parsedArrivalDate = null;
                try {
                    parsedDepartureDate = (Date) dateFormat.parse(departureDate);
                    parsedArrivalDate = (Date) dateFormat.parse(arrivalDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Port departurePort = null;
                Port arrivalPort = null;
                if (Port.getAllPorts().containsKey(ar[4]))
                    departurePort = Port.getAllPorts().get(ar[4]);

                if (Port.getAllPorts().containsKey(ar[4]))
                    arrivalPort = Port.getAllPorts().get(ar[5]);

                String status = ar[6];
                Trip.setIdOrder(idOrder);
                Trip newTrip = new Trip(id, vehicle, parsedDepartureDate, parsedArrivalDate, departurePort, arrivalPort,
                        status);
            }

            System.out.println("Data read from vehicles.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {

            reader.readLine(); // read Header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] ar = line.split(",");
                String username = ar[1];
                String pasword = ar[2];
                User newUser;
                if (ar[0].equals("ADMIN")) {
                    newUser = new Admin(username, pasword);

                } else {
                    Port port = null;
                    if (!ar[3].isEmpty()) {
                        port = Port.getAllPorts().get(ar[3]);
                    }
                    newUser = new PortManager(username, pasword, port);
                }

            }

            System.out.println("Data read from uesrs.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
