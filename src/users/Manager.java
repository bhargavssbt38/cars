package users;

import app.*;
import pages.*;

import java.util.*;

public class Manager extends Employee {

    static Scanner scanner = new Scanner(System.in);

    public Manager(String userID) {
        super(userID, "manager");
    }

    public void landingPage() throws Exception {
        System.out.println("\nMANAGER LANDING PAGE:");
        System.out.println("\t1. Profile");
        System.out.println("\t2. View Customer Profile");
        System.out.println("\t3. Add New Employees");
        System.out.println("\t4. Payroll");
        System.out.println("\t5. Inventory");
        System.out.println("\t6. Orders");
        System.out.println("\t7. Notifications");
        System.out.println("\t8. New Car Model");
        System.out.println("\t9. Car Service Details");
        System.out.println("\t10. Service History");
        System.out.println("\t11. Invoices");
        System.out.println("\t12. Logout");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                this.profile();
                break;
            case 2:
                this.viewCustomerProfile();
                break;
            case 3:
                addNewEmployees();
                break;
            case 4:
                payroll();
                break;
            case 5:
                inventory();
                break;
            case 6:
                orders();
                break;
            case 7:
                notifications();
                break;
            case 8:
                newCarModel();
                break;
            case 9:
                carServiceDetails();
                break;
            case 10:
                serviceHistory();
                break;
            case 11:
                invoices();
                break;
            case 12:
                Application.currentUser = null;
                DisplayPage.homePage();
                break;
            default:
                System.out.println("Invalid option");
                System.exit(0);
        }
    }

    private void addNewEmployees() throws Exception {

    }

    private void payroll() throws Exception {

    }

    private void inventory() throws Exception {

    }

    private void orders() throws Exception {

    }

    private void notifications() throws Exception {

    }

    // Manager: New Car Model
    private void newCarModel() throws Exception {
        System.out.println("\nNEW CAR MODEL:");
        System.out.println("Enter make: ");
        String make = scanner.next();
        System.out.println("Enter model: ");
        String model = scanner.next();
        System.out.println("Enter year: ");
        int year = scanner.nextInt();
        System.out.println("Service A:");
        System.out.println("\tEnter miles: ");
        double milesA = scanner.nextDouble();
        System.out.println("\tEnter months: ");
        int monthsA = scanner.nextInt();
        System.out.println("\tEnter parts list: ");
        String partsA = scanner.next();
        System.out.println("Service B:");
        System.out.println("\tEnter miles: ");
        double milesB = scanner.nextDouble();
        System.out.println("\tEnter months: ");
        int monthsB = scanner.nextInt();
        System.out.println("\tEnter additional parts: ");
        String partsB = scanner.next();
        System.out.println("Service C:");
        System.out.println("\tEnter miles: ");
        double milesC = scanner.nextDouble();
        System.out.println("\tEnter months: ");
        int monthsC = scanner.nextInt();
        System.out.println("\tEnter additional parts: ");
        String partsC = scanner.next();
        System.out.println("MENU:");
        System.out.println("\t1. Add Car");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                if(validateNewCar(make, model, year, milesA, monthsA, partsA, milesB, monthsB, partsB, milesC, monthsC, partsC)) {
                    saveNewCar(make, model, year, milesA, monthsA, partsA, milesB, monthsB, partsB, milesC, monthsC, partsC);
                } else {
                    System.out.println("Enter valid details.");
                }
                landingPage();
                break;
            case 2:
                landingPage();
                break;
            default:
                System.out.println("Invalid option");
                landingPage();
                break;
        }
    }

    // (For Manager: New Car Model)
    // TODO: insert details into db
    //A. Make
    //B. Model
    //C. Year
    //D. Service A:
        //a. Miles
        //b. Months
        //c. Parts List
    //E. Service B
        //a. Miles
        //b. Months
        //c. Additional Parts
    //F. Service C
        //a. Miles
        //b. Months
        //c. Additional Parts
    private void saveNewCar(String make, String model, int year, double milesA, int monthsA, String partsA, double milesB, int monthsB, String partsB, double milesC, int monthsC, String partsC) throws Exception {

    }

    // (For Manager: New Car Model)
    // TODO: validate new car models
    private boolean validateNewCar(String make, String model, int year, double milesA, int monthsA, String partsA, double milesB, int monthsB, String partsB, double milesC, int monthsC, String partsC) throws Exception {
        boolean valid = true;

        return valid;
    }

    // Manager: Car Service Details
    private void carServiceDetails() throws Exception {
        System.out.println("\nCAR SERVICE DETAILS:");
        displayCarServiceDetails();
        System.out.println("MENU:");
        System.out.println("\t1. Go back");
        int option = scanner.nextInt();
        if(option == 1) {
            landingPage();
        } else {
            System.out.println("Invalid option. Try again.");
            invoices();
        }
    }

    // (For Manager: Car Service Details)
    // TODO: display following details for all car models registered in the system
    //A. Make
    //B. Model
    //C. Year
    //D. Service A:
        //a. Miles
        //b. List of Basic Services (Service ID)
    //E. Service B
        //a. Miles
        //b. List of Basic Services(Service ID)
    //F. Service C
        //a. Miles
        //b. List of Basic Services (Service ID)
    private void displayCarServiceDetails() throws Exception {

    }

    // Manager: Service History
    private void serviceHistory() throws Exception {
        System.out.println("\nSERVICE HISTORY:");
        displayServiceHistory();
        System.out.println("MENU:");
        System.out.println("\t1. Go back");
        int option = scanner.nextInt();
        if(option == 1) {
            landingPage();
        } else {
            System.out.println("Invalid option. Try again.");
            invoices();
        }
    }

    // (For Manager: Service History)
    // TODO: display the following details for all cars that were serviced at this service center
    //A. Service ID
    //B. Customer Name
    //C. License Plate
    //D. Service Type
    //E. Mechanic Name
    //F. Service Start Date/Time
    //G. Service End Date/Time (expected or actual)
    //H. Service Status (Pending, Ongoing, or Complete)
    private void displayServiceHistory() throws Exception {

    }


    // Manager: Invoices
    private void invoices() throws Exception {
        System.out.println("\nINVOICES:");
        displayInvoices();
        System.out.println("MENU:");
        System.out.println("\t1. Go back");
        int option = scanner.nextInt();
        if(option == 1) {
            landingPage();
        } else {
            System.out.println("Invalid option. Try again.");
            invoices();
        }
    }

    // (For Manager: Invoices)
    // TODO: display following details for all services that are complete at this service center
    //A. Service ID
    //B. Customer Name
    //C. Service Start Date/Time
    //D. Service End Date/Time
    //E. Licence Plate
    //F. Service Type
    //G. Mechanic Name
    //H. Parts Used in service with cost of each part
    //I. Total labor hours
    //J. Labor wages per hour
    //K. Total Service Cost
    private void displayInvoices() throws Exception {

    }

}
