package pages;

import app.*;
import users.*;

import java.sql.Statement;
import java.util.*;
import java.util.regex.*;

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
        } else {
            System.out.println("Try again.");
            signUpUser();
        }
    }

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
        boolean insertSuccess = false;
        int customerID = 0;
        try {
            // Inserting in customer table
            String query = "insert into customer(customer_id, email, customer_name, telephone_no, address) values(customer_id_sequence.nextval, '" + email + "', '" + name + "', '" + phoneNumber + "', '" + address + "')";
            customerID = Application.stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            if(customerID > 0) {
                // Inserting in login table
                query = "insert into login(email, password, id) values('" + email + "', '" + password + "', '" + customerID + "')";
                Application.stmt.executeUpdate(query);
                insertSuccess = true;
            } else {
                insertSuccess = false;
            }

        } catch(Exception e) {
            insertSuccess = false;
            // Find SQL exception type & print appropriate error message
            // Example: Primary key violation
        }

        return insertSuccess;
    }

    private static boolean validateSignUpDetails(String email, String password, String name, String address, String phoneNumber) throws Exception {
        boolean valid = true;

        if(email == null || email.isEmpty()) {
            System.out.println("Email cannot be empty.");
            return false;
        } else {
            String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                System.out.println("Invalid email entered.");
                return false;
            }
        }

        if(password == null || password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return false;
        } else {
            if(password.length() < 8) {
                System.out.println("Password has to be at least 8 characters long.");
                return false;
            }
        }

        if(name == null || name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return false;
        }

        if(address == null || address.isEmpty()) {
            System.out.println("Address cannot be empty.");
            return false;
        }

        if(phoneNumber == null || phoneNumber.isEmpty()) {
            System.out.println("Phone number cannot be empty.");
            return false;
        } else {
            Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
            Matcher matcher = pattern.matcher(phoneNumber);

            if (!matcher.matches()) {
                System.out.println("Invalid phone number. (Format: 919-111-1111)");
            }
        }

        return valid;
    }

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
