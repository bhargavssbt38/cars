package users;

import app.*;

import java.util.*;

public class Employee extends User {

    static Scanner scanner = new Scanner(System.in);

    public Employee(String userID, String userType) {
        super(userID, userType);
    }

    // Employee: Profile
    public void profile() throws Exception {
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
                this.landingPage();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // Employee: View Profile
    // TODO: query db & display following details
    //A. Employee ID
    //B. Name
    //C. Address
    //D. Email Address
    //E. Phone Number
    //F. Service Center
    //G. Role
    //H. Start Date
    //I. Compensation ($)
    //J. Compensation Frequency (monthly/hourly)
    private void viewProfile() throws Exception {
        System.out.println("\nVIEW PROFILE:");
        // TODO: display profile details
        System.out.println("MENU:");
        System.out.println("\t1. Go back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        if(option == 1) {
            profile();
        } else {
            System.out.println("Invalid option. Try again.");
            viewProfile();
        }
    }

    // Employee: Update Profile
    private void updateProfile() throws Exception {
        System.out.println("\nUPDATE PROFILE:");
        System.out.println("\t1. Name");
        System.out.println("\t2. Address");
        System.out.println("\t3. Email Address");
        System.out.println("\t4. Phone Number");
        System.out.println("\t5. Password");
        System.out.println("\t6. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        if(option >= 1 && option <= 5) {
            System.out.println("Enter new value: ");
            String newValue = scanner.next();
            updateProfileField(option, newValue);
            profile();
        } else if(option == 6) {
            profile();
        } else {
            System.out.println("Invalid option. Try again.");
            updateProfile();
        }
    }

    // TODO: update employee information in db for selected field with given value
    // (For Employee: Update Profile)
    private void updateProfileField(int field, String newValue) throws Exception {
        // TODO: validate value based on field type
        // TODO: update in db
    }

    // Employee: View Customer Profile
    public void viewCustomerProfile() throws Exception {
        System.out.println("\nVIEW CUSTOMER PROFILE:");
        System.out.println("Enter customer email address: ");
        String customerEmail = scanner.next();
        System.out.println("MENU:");
        System.out.println("\t1. Go back");
        int option = scanner.nextInt();
        displayCustomerProfile(customerEmail);
        if(option == 1) {
            this.landingPage();
        } else {
            System.out.println("Invalid option. Try again.");
            viewCustomerProfile();
        }
    }

    // (For Employee: View Customer Profile)
    // TODO: Query db & display following customer details
    //A. Customer ID
    //B. Name
    //C. Address
    //D. Email Address
    //E. Phone Number
    //F. List of All Cars (and their details)
    private void displayCustomerProfile(String customerEmail) throws Exception {
        
    }

}
