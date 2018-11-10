package pages;

import java.util.*;

public class DisplayPage {

    static Scanner scanner = new Scanner(System.in);

    public static void homePage() throws Exception {
        System.out.println("\nHOMEPAGE:");
        System.out.println("\t1. Login");
        System.out.println("\t2. Sign Up");
        System.out.println("\t3. Exit");
        System.out.println("Enter option number: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                loginPage();
                break;
            case 2:
                signupPage();
                break;
            case 3:
                System.out.println("Exiting application.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    public static void loginPage() throws Exception {
        System.out.println("\nLOGIN PAGE:");
        System.out.println("\t1. Sign-in");
        System.out.println("\t2. Go back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                EntryPage.signInUser();
                break;
            case 2:
                homePage();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private static void signupPage() throws Exception {
        System.out.println("\nSIGN UP PAGE:");
        System.out.println("\t1. Sign up");
        System.out.println("\t2. Go back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                EntryPage.signUpUser();
                break;
            case 2:
                homePage();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

}
