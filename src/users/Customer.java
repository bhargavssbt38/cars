package users;

import app.*;
import pages.*;

import java.util.*;

public class Customer extends User {

    static Scanner scanner = new Scanner(System.in);

    public Customer(String userID) {
        super(userID, "customer");
    }

    public void landingPage() throws Exception {
        System.out.println("\nCUSTOMER LANDING PAGE:");
        System.out.println("\t1. Profile");
        System.out.println("\t2. Register Car");
        System.out.println("\t3. Service");
        System.out.println("\t4. Invoices");
        System.out.println("\t5. Logout");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                profileMenu();
                break;
            case 2:
                registerCarMenu();
                break;
            case 3:
                serviceMenu();
                break;
            case 4:
                invoicesMenu();
                break;
            case 5:
                Application.currentUser = null;
                DisplayPage.homePage();
                break;
            default:
                System.out.println("Invalid option");
                System.exit(0);
        }
    }

    private void profileMenu() throws Exception {
        System.out.println("\nPROFILE:");
        System.out.println("\t1. View Profile");
        System.out.println("\t2. Update Profile");
        System.out.println("\t3. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                viewProfile();
                break;
            case 2:
                updateProfile();
                break;
            case 3:
                landingPage();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private void registerCarMenu() throws Exception {
        System.out.println("\nREGISTER CAR:");
        System.out.println("A. Enter license plate: ");
        String licensePlate = scanner.next();
        System.out.println("B. Enter purchase date: ");
        String purchaseDate = scanner.next();
        System.out.println("C. Enter make: ");
        String make = scanner.next();
        System.out.println("D. Enter model: ");
        String model = scanner.next();
        System.out.println("E. Enter year: ");
        String year = scanner.next();
        System.out.println("F. Enter current mileage: ");
        String currentMileage = scanner.next();
        System.out.println("G. Enter last service date: ");
        String lastServiceDate = scanner.next();
        System.out.println("\nMENU:");
        System.out.println("\t1. Register");
        System.out.println("\t2. Cancel");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                if(validateRegisterCarData(licensePlate, purchaseDate, make, model, year, currentMileage, lastServiceDate)) {
                    boolean registered = registerCar(licensePlate, purchaseDate, make, model, year, currentMileage, lastServiceDate);
                    if(registered) {
                        System.out.println("Car registered successfully");
                    }
                    landingPage();
                } else {
                    System.out.println("Invalid data entered. All fields except last service date are required. Please try again.");
                    registerCarMenu();
                }
                break;
            case 2:
                landingPage();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private void serviceMenu() throws Exception {
        System.out.println("\nSERVICE MENU:");
        System.out.println("\t1. View Service History");
        System.out.println("\t2. Schedule Service");
        System.out.println("\t3. Reschedule Service");
        System.out.println("\t4. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                viewServiceHistory();
                break;
            case 2:
                scheduleService();
                break;
            case 3:
                rescheduleService();
                break;
            case 4:
                landingPage();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private void invoicesMenu() {

    }

    // TODO: Section - Customer: View Profile
    private void viewProfile() throws Exception {
        System.out.println("\nVIEW PROFILE:");
        // TODO: display customer details
        System.out.println("\nMENU:");
        System.out.println("\t1. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                profileMenu();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // TODO: in all error messages for updating fields enter the rules
    // Example: Name should be 5 chars min, Phone number should only be numbers, etc.
    private void updateProfile() throws Exception {
        boolean updated = false;
        System.out.println("\nUPDATE PROFILE:");
        System.out.println("\t1. Name");
        System.out.println("\t2. Address");
        System.out.println("\t3. Phone Number");
        System.out.println("\t4. Password");
        System.out.println("\t3. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                System.out.println("Enter name: ");
                String name = scanner.next();
                updated = updateValue(1, name);
                if(updated) {
                    System.out.println("Name updated successfully");
                } else {
                    System.out.println("Error updating name");
                }
                updateProfile();
                break;
            case 2:
                System.out.println("Enter address: ");
                String address = scanner.next();
                updated = updateValue(2, address);
                if(updated) {
                    System.out.println("Address updated successfully");
                } else {
                    System.out.println("Error updating address");
                }
                updateProfile();
                break;
            case 3:
                System.out.println("Enter phone number: ");
                String phoneNumber = scanner.next();
                updated = updateValue(3, phoneNumber);
                if(updated) {
                    System.out.println("Phone number updated successfully");
                } else {
                    System.out.println("Error updating phone number");
                }
                updateProfile();
                break;
            case 4:
                System.out.println("Enter password: ");
                String password = scanner.next();
                updated = updateValue(3, password);
                if(updated) {
                    System.out.println("Password updated successfully");
                } else {
                    System.out.println("Error updating password");
                }
                updateProfile();
                break;
            case 5:
                profileMenu();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // TODO: write update query to update appropriate field with value in db & return true if update is success
    private boolean updateValue(int field, String value) throws Exception {
        boolean updated = false;

        return updated;
    }

    // TODO: validate all data
    // All fields mandatory except lastServiceDate; make lastServiceDate NULL if not entered
    private boolean validateRegisterCarData(String licensePlate, String purchaseDate, String make, String model, String year, String currentMileage, String lastServiceDate) throws Exception {
        boolean valid = false;

        return valid;
    }

    // TODO: write update query to register car. Display error message if not registered.
    private boolean registerCar(String licensePlate, String purchaseDate, String make, String model, String year, String currentMileage, String lastServiceDate) throws Exception {
        boolean registered = false;

        return registered;
    }

    // TODO: Section - Customer: View Service History
    // Query db & display details
    private void viewServiceHistory() throws Exception {
        System.out.println("\nVIEW SERVICE HISTORY:");
        // TODO: display details
        System.out.println("\nMENU:");
        System.out.println("\t1. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                serviceMenu();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private void scheduleService() throws Exception {
        System.out.println("\nSCHEDULE SERVICE:");
        System.out.println("A. Enter license plate: ");
        String licensePlate = scanner.next();
        System.out.println("B. Enter current mileage: ");
        String currentMileage = scanner.next();
        System.out.println("C. Enter mechanic name: ");
        String mechanicName = scanner.next();
        if(validateScheduleServiceData(licensePlate, currentMileage, mechanicName)) {
            System.out.println("\nMENU:");
            System.out.println("\t1. Schedule Maintenance");
            System.out.println("\t2. Schedule Repair");
            System.out.println("\t3. Go Back");
            System.out.println("Enter option: ");
            int option = scanner.nextInt();
            switch(option) {
                case 1:
                    scheduleMaintenance();
                    break;
                case 2:
                    scheduleRepair();
                    break;
                case 3:
                    serviceMenu();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } else {
            System.out.println("Enter valid input. All fields except mechanic name are required. Please try again.");
            scheduleService();
        }
    }

    private void rescheduleService() throws Exception {

    }

    // TODO: validate above data; ensure all details except mechanic name are required. Display error msg if not given.
    private boolean validateScheduleServiceData(String licensePlate, String currentMileage, String mechanicName) throws Exception {
        boolean valid = false;

        return valid;
    }

    // ..
    private void scheduleMaintenance() throws Exception {

    }

    // ..
    private void scheduleRepair() throws Exception {

    }

}
