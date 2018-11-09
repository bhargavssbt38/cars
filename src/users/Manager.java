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

    private void newCarModel() throws Exception {

    }

    private void carServiceDetails() throws Exception {

    }

    private void serviceHistory() throws Exception {

    }

    private void invoices() throws Exception {

    }
}
