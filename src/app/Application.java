package app;

import pages.DisplayPage;
import users.User;

public class Application {

    public static User currentUser = null; // User ID of current logged in user

    public static void main(String[] args) {
        startApplication();
    }

    private static void startApplication() {
        System.out.println("Welcome to CARS!");
        try {
            DisplayPage.homePage();
        } catch(Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

}
