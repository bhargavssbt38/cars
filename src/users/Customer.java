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

    // Customer: Service
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

    // Customer: Invoice
    private void invoicesMenu() throws Exception {
        System.out.println("\nINVOICES:");
        // TODO: display details for all services that are complete
        displayCompletedServiceDetails();
        System.out.println("\nMENU:");
        System.out.println("\t1. View Invoice Details");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                viewInvoiceDetails();
                break;
            case 2:
                landingPage();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // Customer: View Invoice Details
    private void viewInvoiceDetails() throws Exception {
        System.out.println("\nVIEW INVOICE DETAILS:");
        System.out.println("Enter service ID: ");
        int serviceID = scanner.nextInt();
        // TODO: display service details for this ID
        showServiceDetailsForID(serviceID);
        System.out.println("\nMENU:");
        System.out.println("\t1. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                invoicesMenu();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // (For Customer: View Invoice Details)
    // TODO: query db & display following details for given serviceID
    //A. Service ID
    //B. Service Start Date/Time
    //C. Service End Date/Time
    //D. Licence Plate
    //E. Service Type
    //F. Mechanic Name
    //G. Parts Used in service with cost of each part
    //H. Total labor hours
    //I. Labor wages per hour
    //J. Total Service Cost
    private void showServiceDetailsForID(int serviceID) throws Exception {

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

    // Customer: View Service History
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

    // Customer: Schedule Service
    private void scheduleService() throws Exception {
        System.out.println("\nSCHEDULE SERVICE:");
        System.out.println("A. Enter license plate: ");
        String licensePlate = scanner.next();
        System.out.println("B. Enter current mileage: ");
        int currentMileage = scanner.nextInt();
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
                    scheduleMaintenance(licensePlate, currentMileage, mechanicName);
                    break;
                case 2:
                    scheduleRepair(licensePlate, currentMileage, mechanicName);
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

    // Customer: Reschedule Service (Page 1)
    private void rescheduleService() throws Exception {
        System.out.println("\nRESCHEDULE SERVICE (Page 1):");
        System.out.println("Your upcoming services:");
        displayUpcomingServices();
        System.out.println("MENU:");
        System.out.println("\t1. Pick a service");
        System.out.println("\t2. Go back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                System.out.println("Enter service ID to reschedule: ");
                int serviceID = scanner.nextInt();
                List<Date> dates = findRescheduleDate(serviceID);
                rescheduleService2(serviceID, dates);
                break;
            case 2:
                serviceMenu();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // TODO: find 2 earliest available maintenance/repair dates that are at least one day after the current service date
    // (For Customer: Reschedule Service (Page 1))
    private List<Date> findRescheduleDate(int serviceID) throws Exception {
        List<Date> dates = new ArrayList<>();

        return dates;
    }

    // Customer: Reschedule Service (Page 2)
    private void rescheduleService2(int serviceID, List<Date> dates) throws Exception {
        System.out.println("\nRESCHEDULE SERVICE (Page 2):");
        System.out.println("Service dates: ");
        System.out.println("\t1. " + dates.get(0).toString());
        System.out.println("\t2. " + dates.get(1).toString());
        String mechanicName = ""; // TODO: How to find mechanic name?
        System.out.println("Mechanic name: " + mechanicName);
        System.out.println("MENU:");
        System.out.println("\t1. Reschedule Date");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                System.out.println("Choose one of the dates listed above (enter 1 or 2): ");
                int dateNum = scanner.nextInt();
                if(dateNum == 1 || dateNum == 2) {
                    rescheduleServiceToDate(serviceID, dates.get(dateNum-1));
                    serviceMenu();
                } else {
                    System.out.println("Invalid date chosen. Try again.");
                    rescheduleService2(serviceID, dates);
                }
                break;
            case 2:
                rescheduleService();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // TODO: reschedule service to chosen date making adjustments to the parts commitment in the inventory
    // (For Customer: Reschedule Service (Page 2))
    private void rescheduleServiceToDate(int serviceID, Date toDate) throws Exception {

    }

    // TODO: query db & display upcoming services for this customer
    // (For Customer: Reschedule Service (Page 1))
    // Display the following details
    //A. License Plate
    //B. Service ID
    //C. Service Date
    //D. Service Type(Maintenance/Repair)
    //E. Service Details (Service A/B/C or Problem)
    private void displayUpcomingServices() throws Exception {

    }

    // TODO: validate above data; ensure all details except mechanic name are required. Display error msg if not given.
    private boolean validateScheduleServiceData(String licensePlate, int currentMileage, String mechanicName) throws Exception {
        boolean valid = false;

        return valid;
    }

    // Customer: Schedule Maintenance (Page 1)
    private void scheduleMaintenance(String licensePlate, int currentMileage, String mechanicName) throws Exception {
        System.out.println("\nSCHEDULE MAINTENANCE (Page 1)");
        System.out.println("\t1. Find Service Date");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                List<Date> dates = findServiceDates(licensePlate, currentMileage, mechanicName);
                if(dates.size() == 2) {
                    scheduleMaintenance2(dates, licensePlate, currentMileage, mechanicName);
                } else {
                    // If a service date cannot be found due to insufficient parts, place an order (if required) and show a message to the user asking him to try again after a specific date (calculated based on when the order will be fulfilled). Do not place an order if an existing order can fulfill the requirement, but show a message to the user asking him to try again after a specific date.

                    // If parts are not sufficient
                    if(!checkPartsSufficient()) {
                        // Check if an order exists that fulfills this requirement; If it doesn't - place the order
                        Date orderFulfilledDate = getExistingOrderForPartsDate();
                        if(orderFulfilledDate == null) {
                            orderFulfilledDate = placeOrder();
                        }
                        System.out.println("Please try again after " + orderFulfilledDate.toString());
                        scheduleService();
                    }

                }
                break;
            case 2:
                scheduleService();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // Customer: Schedule Maintenance (Page 2)
    private void scheduleMaintenance2(List<Date> dates, String licensePlate, int currentMileage, String mechanicName) throws Exception {
        System.out.println("\nSCHEDULE MAINTENANCE (Page 2):");
        System.out.println("Service dates: ");
        System.out.println("\t1. " + dates.get(0).toString());
        System.out.println("\t2. " + dates.get(1).toString());
        System.out.println("Mechanic name: " + mechanicName);
        System.out.println("MENU:");
        System.out.println("\t1. Schedule on Date");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                System.out.println("Choose one of the service dates listed above (enter 1 or 2): ");
                int dateNum = scanner.nextInt();
                if(dateNum == 1 || dateNum == 2) {
                    createMaintenanceService(dates.get(dateNum-1), licensePlate, currentMileage, mechanicName);
                    scheduleService();
                } else {
                    System.out.println("Invalid date chosen. Try again.");
                    scheduleMaintenance2(dates, licensePlate, currentMileage, mechanicName);
                }
                break;
            case 2:
                scheduleMaintenance(licensePlate, currentMileage, mechanicName);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // TODO: find two earliest available service dates
    // (Comes under Customer: Schedule Maintenance (Page 1))
    private List<Date> findServiceDates(String licensePlate, int currentMileage, String mechanicName) throws Exception {
        List<Date> dates = new ArrayList<Date>();

        return dates;
    }

    // TODO: query db and check if parts are sufficient
    private boolean checkPartsSufficient() throws Exception {
        boolean sufficient = false;

        return sufficient;
    }

    // TODO: query db & check if an order exists that satisfies parts requirements
    // Return the date when order will be fulfilled
    // If no such order exists - return null
    private Date getExistingOrderForPartsDate() throws Exception {
        Date orderDate = null;

        return orderDate;
    }

    // TODO: insert into db new order for parts required
    // Return the date when the order will be fulfilled
    private Date placeOrder() throws Exception {

        return null;
    }

    // TODO: create a new service record for maintenance service for given date
    // Insert into db
    // (For Customer: Schedule Maintenance (Page 2))
    private void createMaintenanceService(Date date, String licensePlate, int currentMileage, String mechanicName) throws Exception {

    }

    // Customer: Schedule Repair (Page 1)
    private void scheduleRepair(String licensePlate, int currentMileage, String mechanicName) throws Exception {
        System.out.println("\nSCHEDULE REPAIR (Page 1):");
        System.out.println("\t1. Engine knock");
        System.out.println("\t2. Car drifts in a particular direction");
        System.out.println("\t3. Battery does not hold charge");
        System.out.println("\t4. Black/unclean exhaust");
        System.out.println("\t5. A/C-Heater not working");
        System.out.println("\t6. Headlamps/Tail lamps not working");
        System.out.println("\t7. Check engine light");
        System.out.println("\t8. Go back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        if(option >= 1 && option <= 7) {
            displayDiagnosticReport(option);
            List<Date> dates = findRepairDates(licensePlate, currentMileage, mechanicName);
            if(dates.size() == 2) {
                scheduleRepair2(option, dates, licensePlate, currentMileage, mechanicName);
            } else {
                // If a repair date cannot be found due to insufficient parts, place an order (if required) and show a message to the user asking him to try again after a specific date (calculated based on when the order will be fulfilled). Do not place an order if an existing order can fulfill the requirement, but show a message to the user asking him to try again after a specific date.

                // If parts are not sufficient
                if(!checkPartsSufficient()) {
                    // Check if an order exists that fulfills this requirement; If it doesn't - place the order
                    Date orderFulfilledDate = getExistingOrderForPartsDate();
                    if(orderFulfilledDate == null) {
                        orderFulfilledDate = placeOrder();
                    }
                    System.out.println("Please try again after " + orderFulfilledDate.toString());
                    scheduleService();
                }
            }
        } else if(option == 8) {
            scheduleService();
        } else {
            System.out.println("Invalid option");
            scheduleRepair(licensePlate, currentMileage, mechanicName);
        }
    }

    // Customer: Schedule Repair (Page 2)
    private void scheduleRepair2(int problem, List<Date> dates, String licensePlate, int currentMileage, String mechanicName) throws Exception {
        System.out.println("\nSCHEDULE REPAIR (Page 2):");
        displayDiagnosticReport(problem);
        System.out.println("Repair dates: ");
        System.out.println("\t1. " + dates.get(0).toString());
        System.out.println("\t2. " + dates.get(1).toString());
        System.out.println("Mechanic name: " + mechanicName);
        System.out.println("MENU:");
        System.out.println("\t1. Repair on Date");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                System.out.println("Choose one of the service dates listed above (enter 1 or 2): ");
                int dateNum = scanner.nextInt();
                if(dateNum == 1 || dateNum == 2) {
                    createRepairService(dates.get(dateNum-1), licensePlate, currentMileage, mechanicName);
                    scheduleService();
                } else {
                    System.out.println("Invalid date chosen. Try again.");
                    scheduleRepair2(problem, dates, licensePlate, currentMileage, mechanicName);
                }
                break;
            case 2:
                scheduleRepair(licensePlate, currentMileage, mechanicName);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // TODO: display report with list of causes & parts needed to resolve the problem given
    // (For Customer: Schedule Repair (Page 1))
    private void displayDiagnosticReport(int problem) {

    }

    // TODO: find two earliest available repair dates
    // (Comes under Customer: Schedule Repair (Page 1))
    private List<Date> findRepairDates(String licensePlate, int currentMileage, String mechanicName) throws Exception {
        List<Date> dates = new ArrayList<Date>();

        return dates;
    }

    // TODO: create a new service record for repair service for given date
    // Insert into db
    // (For Customer: Schedule Repair (Page 2))
    private void createRepairService(Date date, String licensePlate, int currentMileage, String mechanicName) throws Exception {

    }

    // (For Customer: Invoice)
    // TODO: query db & display following details for all completed services
    //A. Service ID
    //B. Service Start Date/Time
    //C. Service End Date/Time
    //D. Licence Plate
    //E. Service Type
    //F. Mechanic Name
    //G. Total Service Cost
    private void displayCompletedServiceDetails() throws Exception {

    }

}
