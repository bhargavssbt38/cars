package users;

import app.Application;
import pages.DisplayPage;

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

    private void profileMenu() {

    }

    private void registerCarMenu() {

    }

    private void serviceMenu() {

    }

    private void invoicesMenu() {

    }

}
