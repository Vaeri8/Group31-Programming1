package src.com;

import java.util.Scanner;

import src.com.container.Container;
import src.com.io.DataIO;
import src.com.port.Port;
import src.com.user.Admin;
import src.com.user.PortManager;
import src.com.user.User;
import src.com.vehicle.BasicTruck;
import src.com.vehicle.ReeferTruck;
import src.com.vehicle.Ship;
import src.com.vehicle.TankerTruck;
import src.com.vehicle.Vehicle;

public class Main {
    public static void example() {
        boolean first = true;
        User newAdmin = new Admin("admin", "root");
        Vehicle ship1 = new Ship("ship1", 123456789, 123456789);
        Vehicle ship2 = new Ship("ship2", 123456789, 123456789);
        Vehicle btruck = new BasicTruck("basicTruck", 123456789, 123456789);
        Vehicle ttruck = new TankerTruck("tankerTruck", 123456789, 123456789);
        Vehicle rtruck = new ReeferTruck("reeferTruck", 123456789, 123456789);

        Port p1 = new Port("p1", 40, 90, 100000000, true);
        Port p2 = new Port("p2", 41, 89, 100000000, true);
        Port p3 = new Port("p3", 42, 88, 100000000, false);
        Port p4 = new Port("p4", 43, 87, 100000000, true);
        Port p5 = new Port("p5", 44, 86, 100000000, false);

        Container c1 = new Container(100, "dry storage");
        Container c2 = new Container(100, "open top");
        Container c3 = new Container(100, "open side");
        Container c4 = new Container(100, "liquid");
        Container c5 = new Container(100, "refrigerated");

        p1.addContainer(c1);
        p2.addContainer(c5);
        p2.addContainer(c5);
        p2.addContainer(c5);

        ship1.setCurrentPort(p1);
        ship1.loadContainer(c1);

        ship2.setCurrentPort(p2);
        btruck.setCurrentPort(p3);
        ttruck.setCurrentPort(p4);
        rtruck.setCurrentPort(p5);

        ship1.moveToPort(p5);

        ship1.moveToPort(p3);
        ship1.moveToPort(p3);
        ship1.moveToPort(p1);
        ship1.moveToPort(p2);
        ship1.loadContainer(c5);
        ship1.moveToPort(p4);
        ship1.unLoadContainer(c5);

        User curUser = null;

        // while (curUser == null) {
        // if (first) {
        // System.out.println("""
        // COSC2081 GROUP ASSIGNMENT
        // CONTAINER PORT MANAGEMENT SYSTEM
        // Instructor: Mr. Minh Vu & Dr. Phong Ngo
        // Group: Group Name
        // sXXXXXXX, Student Name
        // sXXXXXXX, Student Name
        // sXXXXXXX, Student Name
        // """);
        // first = false;
        // }
        // System.out.println("Please Log In");
        // System.out.println("Username:");
        // String username = scanner.nextLine();
        // System.out.println("Password:");
        // String password = scanner.nextLine();
        // curUser = User.login(username, password);
        // }
    }

    public static void a(String[] args) {

        // example();

        Scanner scanner = new Scanner(System.in);

        scanner.close();
    }

    public static void main(String[] args) {
        DataIO.readFromFile();
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("CONTAINER PORT MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu & Dr. Phong Ngo");
        System.out.println("Group: Team 31");
        System.out.println("s3929899, Nguyen The Duc");
        System.out.println("s3980288, Luong Tuan Kiet");
        System.out.println("s3798789, Dang Nhat Phu");
        System.out.println("s3979293, Nguyen Thanh Quan");

        String username = "";
        String password = "";
        User curUser = null;
        Scanner scanner = new Scanner(System.in);
        while (curUser == null) {
            System.out.println("enter username");
            username = scanner.nextLine();
            System.out.println("enter password");
            password = scanner.nextLine();
            curUser = User.login(username, password);
            if (curUser == null)
                System.err.println("Wrong username or password");
        }
        while (true) {
            displayMainMenu();
            String choice = readScanner(scanner);
            switch (choice) {
                case "1":
                    performPortCRUD(scanner);
                    break;
                case "2":
                    performVehicleCRUD(scanner);
                    break;
                case "3":
                    performContainerCRUD(scanner);
                    break;
                case "4":
                    performManagerCRUD(scanner);
                    break;
                case "5":
                    // performTripCRUD(scanner);
                    break;
                case "6":
                    // performStatisticsOperations(scanner);
                    break;
                case "7":
                    otherFeature(scanner);
                    break;
                case "8":
                    DataIO.writeToFile();
                    scanner.close();
                    System.out.println("Logging out. ");
                    return; // Exit the program
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Manage Ports");
        System.out.println("2. Manage Vehicles");
        System.out.println("3. Manage Containers");
        System.out.println("4. Manage Managers");
        System.out.println("5. Manage Trips");
        System.out.println("6. Perform Statistics Operations");
        System.out.println("7. Other Features (based on user roles)");
        System.out.println("8. Logout");
        System.out.println("Enter your choice: ");
    }

    private static String readScanner(Scanner scanner) {
        String res = scanner.nextLine();
        return res;
    }

    private static void performPortCRUD(Scanner scanner) {
        System.out.println("1. Create new Port");
        System.out.println("2. Search Port by id");
        String choice = readScanner(scanner);
        switch (choice) {
            case "1":
                System.out.println("Enter port name:");
                String name = scanner.nextLine();
                System.out.println("Enter latitude:");
                double latitude = scanner.nextDouble();
                System.out.println("Enter longitude:");
                double longitude = scanner.nextDouble();
                System.out.println("Enter storing capacity:");
                double storingCapacity = scanner.nextDouble();
                System.out.println("Does it have landing ability (true/false):");
                boolean landingAbility = scanner.nextBoolean();
                Port newPort = new Port(name, latitude, longitude, storingCapacity, landingAbility);
                System.out.println("New Port created with id: " + newPort.getId());
                break;
            case "2":
                System.out.println("Enter Port ID:");
                String id = readScanner(scanner);
                if (!Port.getAllPorts().containsKey(id)) {
                    System.err.println("Not contain port with id: " + id);
                    break;
                }
                System.out.println(Port.getAllPorts().get(id).toString());
                ;
                break;

        }

    }

    private static void performContainerCRUD(Scanner scanner) {
        System.out.println("1. Create new Container");
        System.out.println("2. Search Container by id");
        String choice = readScanner(scanner);
        switch (choice) {
            case "1":
                System.out.println("Enter weight:");
                double weight = scanner.nextDouble();
                System.out.println("Choose Container type:");
                System.out.println("1.dry storage");
                System.out.println("2.open top");
                System.out.println("3.open side");
                System.out.println("4.refrigerated");
                System.out.println("5.liquid");
                String newChoice = scanner.nextLine();
                String type;
                switch (newChoice) {
                    case "1":
                        type = "dry storage";
                        break;
                    case "2":
                        type = "open top";
                        break;
                    case "3":
                        type = "open side";
                        break;
                    case "4":
                        type = "refrigerated";
                        break;
                    case "5":
                        type = "liquid";
                        break;
                    default:
                        System.err.println("Invalid choice");
                        return;
                }

                Container newContainer = new Container(weight, type);
                System.out.println("New Port created with id: " + newContainer.getId());
                break;
            case "2":
                System.out.println("Enter Container ID:");
                String id = readScanner(scanner);
                if (!Container.getAllContainers().containsKey(id)) {
                    System.err.println("Not contain container with id: " + id);
                    break;
                }
                System.out.println(Container.getAllContainers().get(id).toString());
                ;
                break;

        }
    }

    private static void performManagerCRUD(Scanner scanner) {
        System.out.println("1. Create new Port Manager");
        System.out.println("2. Search PortManager by username");
        String choice = readScanner(scanner);
        switch (choice) {
            case "1":
                System.out.println("Enter username:");
                String username = readScanner(scanner);

                System.out.println("Enter password:");
                String password = readScanner(scanner);

                System.out.println("Enter port id assign to this PM");
                String pId = readScanner(scanner);

                if (!Port.getAllPorts().containsKey(pId)) {
                    System.err.println("No port with id: " + pId);
                    return;
                }
                User newPortManager = new PortManager(username, password, null);
                System.out.println("New Port Manager created with username: " + username);
                break;
            case "2":
                System.out.println("Enter Port Manager username:");
                String usernameS = readScanner(scanner);
                if (!User.getAllUsers().containsKey(usernameS)) {
                    System.err.println("Not contain PM with username: " + usernameS);
                    break;
                }
                System.out.println(User.getAllUsers().get(usernameS).toString());
                ;
                break;

        }

    }

    private static void performTripCRUD(Scanner scanner) {
        // System.out.println("1. Search Trip by id");
    }

    private static void performVehicleCRUD(Scanner scanner) {
        System.out.println("1. Create new Vehicle");
        System.out.println("2. Search Vehicle by id");
        String choice = readScanner(scanner);
        switch (choice) {
            case "1":
                System.out.println("Enter name:");
                String name = readScanner(scanner);
                System.out.println("Enter fuel capacity:");
                double fuelCapacity = scanner.nextDouble();
                System.out.println("Enter carrying capacity");
                double carryingCapacity = scanner.nextDouble();

                System.out.println("Choose Vehicle type:");
                System.out.println("1.Ship");
                System.out.println("2.BasicTruck");
                System.out.println("3.TankerTruck");
                System.out.println("4.ReeferTruck");
                choice = readScanner(scanner);

                Vehicle vehicle;
                switch (choice) {
                    case "1":
                        vehicle = new Ship(name, fuelCapacity, carryingCapacity);
                        break;
                    case "2":
                        vehicle = new BasicTruck(name, fuelCapacity, carryingCapacity);
                        break;
                    case "3":
                        vehicle = new TankerTruck(name, fuelCapacity, carryingCapacity);
                        break;
                    case "4":
                        vehicle = new ReeferTruck(name, fuelCapacity, carryingCapacity);
                        break;

                    default:
                        System.err.println("Invalid choice");
                        return;
                }
                System.out.println("New Vehicle created with id: " + vehicle.getId());
                break;
            case "2":
                System.out.println("Enter Vehicle ID:");
                String id = readScanner(scanner);
                if (!Vehicle.getAllVehicles().containsKey(id)) {
                    System.err.println("Not contain vehicle with id: " + id);
                    break;
                }
                System.out.println(Vehicle.getAllVehicles().get(id).toString());
                ;
                break;

        }

    }

    private static void otherFeature(Scanner scanner) {
        System.out.println("1. Load Container to Vehicle");
        System.out.println("2. Unload Container from Vehicle");
        System.out.println("3. Move Vehicle to a Port");
        System.out.println("4. Refuel Vehicle");
        String choice = readScanner(scanner);
        Vehicle vehicle;
        String id;
        Container container;
        switch (choice) {
            case "1":
                System.out.println("Enter vehicle id:");
                id = scanner.nextLine();
                if (Vehicle.getAllVehicles().containsKey(id))
                    vehicle = Vehicle.getAllVehicles().get(id);
                else {
                    System.err.println("No Vehicle with id " + id);
                    break;
                }

                System.out.println("Enter Container Id to load");
                id = scanner.nextLine();

                if (Container.getAllContainers().containsKey(id))
                    container = Container.getAllContainers().get(id);
                else {
                    System.err.println("No Container with id " + id);
                    break;
                }
                vehicle.loadContainer(container);
                break;
            case "2":
                System.out.println("Enter vehicle id:");
                id = scanner.nextLine();
                if (Vehicle.getAllVehicles().containsKey(id))
                    vehicle = Vehicle.getAllVehicles().get(id);
                else {
                    System.err.println("No Vehicle with id " + id);
                    break;
                }
                System.out.println("Enter Container Id to load");
                id = scanner.nextLine();

                if (Container.getAllContainers().containsKey(id))
                    container = Container.getAllContainers().get(id);
                else {
                    System.err.println("No Container with id " + id);
                    break;
                }
                vehicle.unLoadContainer(container);
                break;
            case "3":
                System.out.println("Enter vehicle id:");
                id = scanner.nextLine();
                if (Vehicle.getAllVehicles().containsKey(id))
                    vehicle = Vehicle.getAllVehicles().get(id);
                else {
                    System.err.println("No Vehicle with id " + id);
                    break;
                }
                System.out.println("Enter Port Id to move");
                id = scanner.nextLine();
                Port port;
                if (Port.getAllPorts().containsKey(id))
                    port = Port.getAllPorts().get(id);
                else {
                    System.err.println("No Container with id " + id);
                    break;
                }
                vehicle.moveToPort(port);
                break;
            case "4":
                System.out.println("Enter vehicle id:");
                id = scanner.nextLine();
                if (Vehicle.getAllVehicles().containsKey(id))
                    vehicle = Vehicle.getAllVehicles().get(id);
                else {
                    System.err.println("No Vehicle with id " + id);
                    break;
                }
                vehicle.reFuel();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }

    }
}