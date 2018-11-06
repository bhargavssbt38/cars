package pages;

import app.Application;

import java.util.*;

public class ReceptionistPage {

    static Scanner scanner = new Scanner(System.in);

    public static void landingPage() throws Exception {
        System.out.println("\nRECEPTIONIST LANDING PAGE:");
        System.out.println("\t1. Profile");
        System.out.println("\t2. View Customer Profile");
        System.out.println("\t3. Register Car");
        System.out.println("\t4. Service History");
        System.out.println("\t5. Schedule Service");
        System.out.println("\t6. Reschedule Service");
        System.out.println("\t7. Invoices");
        System.out.println("\t8. Daily Task-Update Inventory");
        System.out.println("\t9. Daily Task-Record Deliveries");
        System.out.println("\t10. Logout");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                EmployeePage.profile();
                break;
            case 2:
                EmployeePage.viewCustomerProfile();
                break;
            case 3:
                registerCar();
                break;
            case 4:
                serviceHistory();
                break;
            case 5:
                scheduleService();
                break;
            case 6:
                rescheduleService();
                break;
            case 7:
                invoices();
                break;
            case 8:
                updateInventory();
                break;
            case 9:
                recordDeliveries();
                break;
            case 10:
                Application.currentUser = null;
                DisplayPage.homePage();
                break;
            default:
                System.out.println("Invalid option");
                System.exit(0);
        }
    }

    private static void registerCar() throws Exception {

    }

    private static void serviceHistory() throws Exception {

    }

    private static void scheduleService() throws Exception {

    }

    private static void rescheduleService() throws Exception {

    }

    private static void invoices() throws Exception {

    }

    private static void updateInventory() throws Exception {

    }

    private static void recordDeliveries() throws Exception {

    }
}
