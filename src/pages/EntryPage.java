package pages;

import app.*;
import users.*;

import java.util.*;

public class EntryPage {

    static Scanner scanner = new Scanner(System.in);

    public static void signInUser() throws Exception {
        System.out.println("\nSIGN-IN");
        System.out.println("\tA. Enter user ID: ");
        String userID = scanner.next();
        System.out.println("\tB. Enter password: ");
        String password = scanner.next();
        boolean valid = validateCredentials(userID, password);
        if(!valid) {
            System.out.println("Login Incorrect");
            signInUser();
        } else {
            String userType = getUserType(userID);
            if("manager".equalsIgnoreCase(userType)) {
                Application.currentUser = new Manager(userID);
                Application.currentUser.landingPage();
            } else if("customer".equalsIgnoreCase(userType)) {
                Application.currentUser = new Customer(userID);
                Application.currentUser.landingPage();
            } else if("receptionist".equalsIgnoreCase(userType)) {
                Application.currentUser = new Receptionist(userID);
                Application.currentUser.landingPage();
            }
        }
    }

    public static void signUpUser() throws Exception {
        System.out.println("\nSIGN UP");
        System.out.println("\tA. Enter email address: ");
        String email = scanner.next();
        System.out.println("\tB. Enter password: ");
        String password = scanner.next();
        System.out.println("\tC. Enter name: ");
        String name = scanner.next();
        System.out.println("\tD. Enter address: ");
        String address = scanner.next();
        System.out.println("\tE. Enter phone number: ");
        String phoneNumber = scanner.next();

        boolean validData = validateSignUpDetails(email, password, name, address, phoneNumber);
        if(validData) {
            boolean userSaved = saveUserToDB(email, password, name, address, phoneNumber);
            if(userSaved) {
                System.out.println("User account created. Login with same credentials.");
                DisplayPage.loginPage();
            }
        }
    }

    // TODO: return "manager", "customer", "receptionist" based on userID by querying db; return null if invalid user
    private static String getUserType(String userID) throws Exception {
        // UNCOMMENT NEXT LINE
        //String userType = null;
        String userType = "receptionist"; // RETURNING DEFAULT VALUE FOR TESTING

        return userType;
    }

    private static boolean saveUserToDB(String email, String password, String name, String address, String phoneNumber) {
        // UNCOMMENT NEXT LINE
        //boolean insertSuccess = false;
        boolean insertSuccess = true; // RETURNING TRUE BY DEFAULT FOR TESTING - REMOVE LATER
        try {
            // TODO: insert query to save user to db
        } catch(Exception e) {
            insertSuccess = false;
            // Find SQL exception type & print appropriate error message
            // Example: Primary key violation
        }
        return insertSuccess;
    }

    // TODO: validate all fields
    // Example: validate email format, minimum password length & special characters, valid phoneNumber, address & name
    // Display appropriate error message if invalid & return false
    // Return true if valid
    private static boolean validateSignUpDetails(String email, String password, String name, String address, String phoneNumber) throws Exception {

        return true;
    }

    // TODO: query username password from db and validate credentials
    private static boolean validateCredentials(String userID, String password) throws Exception {
        // UNCOMMENT NEXT LINE
        //boolean valid = false;
        boolean valid = true; // RETURNING VALID TRUE BY DEFAULT FOR TESTING - REMOVE LATER

        return valid;
    }

}
