package pages;

import app.*;
import users.*;

import java.util.*;

public class EntryPage {

    static Scanner scanner = new Scanner(System.in);

    public static void signInUser() throws Exception {
        System.out.println("\nSIGN-IN");
        System.out.println("\tA. Enter email: ");
        String email = scanner.next();
        System.out.println("\tB. Enter password: ");
        String password = scanner.next();
        String userID = validateCredentials(email, password);
        if(userID == null) {
            System.out.println("Login Incorrect");
            signInUser();
        } else {
            String userType = getUserType(userID, email);
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
    private static String getUserType(String userID, String email) throws Exception {
        String userType = null;

        // Joining login table with customer table
        String query = "select * from customer inner join login on customer.email = login.email where login.email = '" + email + "'";
        Application.rs = Application.stmt.executeQuery(query);
        while(Application.rs.next()) {
            userType = "customer";
            return userType;
        }

        // Joining login table with manager table
        query = "select * from manager inner join login on manager.emp_id = login.id where login.id = '" + userID + "'";
        Application.rs = Application.stmt.executeQuery(query);
        while(Application.rs.next()) {
            userType = "manager";
            return userType;
        }

        // Joining login table with receptionist table
        query = "select * from receptionist inner join login on receptionist.emp_id = login.id where login.id = '" + userID + "'";
        Application.rs = Application.stmt.executeQuery(query);
        while(Application.rs.next()) {
            userType = "receptionist";
            return userType;
        }

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

    // TODO: query username password from db and validate credentials & return UserID
    private static String validateCredentials(String email, String password) throws Exception {
        String userID = null;
        String query = "select id from login where email = '" + email + "' and password = '" + password + "'";
        Application.rs = Application.stmt.executeQuery(query);
        while (Application.rs.next()) {
            userID = Application.rs.getString("id");
        }
        return userID;
    }

}
