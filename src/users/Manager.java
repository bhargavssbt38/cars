package users;

import app.Application;
import pages.DisplayPage;

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
//Manager: Add New Employees
    //TODO: Add new employees, default password is 12345678
    private void addNewEmployees() throws Exception {
    	System.out.println("Menu");
    	System.out.println("1. Add New Employees");
    	System.out.println("2. Go Back");
    	int option=scanner.nextInt();
    	switch(option) {
    	case 1: System.out.println("Enter the Name of the employee");
    	        String name=scanner.next();
    	        System.out.println("Enter the Address of the employee");
    	        String address=scanner.next();
    	        System.out.println("Enter the Email Address of the employee");
    	        String emailaddr=scanner.next();
    	        System.out.println("Enter the phone number");
    	        int phone=scanner.nextInt();
    	        System.out.println("Enter the Role of the employee");
    	        String role=scanner.next();
    	        System.out.println("Enter the Start Date");
    	        String startdate=scanner.next();
    	        System.out.println("Enter the Compensation of the employee in USD");
    	        int compensation=scanner.nextInt();
    	        System.out.println("Employee successfully created");
    	        landingPage();
    	        break;
    	case 2: landingPage(); break;
    	default: System.exit(0);
    	}
    	

    }
//Manager: Payroll
    //TODO: Write a query to retrieve the payroll of an employee
    private void payroll() throws Exception {
    	System.out.println("Enter the employee ID for which you want to retrieve the Payroll");
    	String eid=scanner.next();
    	System.out.println("Payroll for employee id:");
    	System.out.println("Paycheck date");
    	System.out.println("Pay period");
    	System.out.println("Employee ID");
    	System.out.println("Employee Name");
    	System.out.println("Compensation");
    	System.out.println("Units");
    	System.out.println("Earnings");
    	System.out.println("\n\n Menu");
    	System.out.println("1. Go back");
    	int option=scanner.nextInt();
    	switch(option) {
    	case 1: landingPage();
    	default: System.out.println("Invalid Option"); System.exit(0);
    	}

    }
//Manager: Inventory
    //TODO: Display details for each part in the inventory.
    private void inventory() throws Exception {
     System.out.println("Inventory");
     System.out.println("1. Part ID");
     System.out.println("2.Part Name");
     System.out.println("3. Quantity");
     System.out.println("4. Unit Price");
     System.out.println("5.Minimum Quantity Threshold");
     System.out.println("6. Minimum Order Threshold");
     
     System.out.println("Menu");
     System.out.println("1. Go Back");
     int option=scanner.nextInt();
     switch(option) {
     case 1: landingPage(); break;
     default: System.out.println("Invalid Option"); System.exit(0);
     }
     
    }
    //Manager: Orders
    private void orders() throws Exception {
       System.out.println("Menu");
       System.out.println("1. Order History");
       System.out.println("2. New Order");
       System.out.println("3. Go Back");
       int option=scanner.nextInt();
       switch(option) {
       case 1: orderhistory(); break;
       case 2: neworder(); break;
       case 3: landingPage();break;
       default: System.out.println("Invalid Option"); System.exit(0);
       }
       
    }
    //Manager: Order History
    //TODO: Write a query to retrieve Order history.
    private void orderhistory() throws Exception{
    	System.out.println("Order History");
    	System.out.println("1.OrderID");
    	System.out.println("2.Date");
    	System.out.println("3. Part Name");
    	System.out.println("4. Supplier Name");
    	System.out.println("5.Purchaser Name");
    	System.out.println("6.Quantity");
    	System.out.println("7.Unit Price");
    	System.out.println("8. Total Cost");
    	System.out.println("9.Order Status");
    	
    	System.out.println("Menu");
    	System.out.println("1. Go Back");
    	int option=scanner.nextInt();
    	switch(option)
    	{
    	case 1: landingPage(); break;
    	default: System.out.println("Invalid Option"); System.exit(0);
    	
    	}
    }
    //Manager:New Order
    //TODO: write query to place a new order 
    private void neworder() throws Exception{
    	System.out.println("\n \n Menu");
    	System.out.println("1. Place Order");
    	System.out.println("2. Go Back");
    	int option=scanner.nextInt();
    	switch(option)
    	{
    	case 1: System.out.println(" New Order");
    	System.out.println(" Please Enter the Part ID");
    	String partid=scanner.next();
    	System.out.println(" Please enter the quantity");
    	int quantity=scanner.nextInt(); 
    	System.out.println("The order has been successfully placed with order ID:  and estimated date: "); orders();break;
    	case 2: orders(); break;
    	default: System.out.println("Invalid Option");System.exit(0);
    	    	
    	}
    	
    	
    }
//Manager: Notifications

    private void notifications() throws Exception {
       System.out.println("Notifications");
       System.out.println("1. Notification ID");
       System.out.println("2. Notification Date/Time");
       System.out.println("3. Order ID");
       System.out.println("4. Supplier Name");
       System.out.println("5. Expected Delivery Date");
       System.out.println("6. Delayed by Number of Days");
       System.out.println("\n \n Menu");
       System.out.println("1. Order ID");
       System.out.println("2. Go back");
       int option=scanner.nextInt();
       switch(option)
       {
       case 1: System.out.println("Enter the order ID");
               String oid=scanner.next();
               notificationsdetail(oid);break;
       case 2: landingPage(); break;
       default: System.out.println("Invalid Option");System.exit(0);
       }
    }
    
   //Manager: Notification Detail
   //TODO: Retrieve details for the given order id
    private void notificationsdetail(String oid) throws Exception
    { System.out.println("Order ID:");
      System.out.println("2.Date");
      System.out.println("3. Part Name");
      System.out.println("4. Supplier Name");
      System.out.println("5. Purchaser Name");
      System.out.println("6.Quantity");
      System.out.println("7. Unit Price");
      System.out.println("8. Total Cost");
      System.out.println("9.Order Status");
      
      System.out.println("\n \n Menu");
      System.out.println("1. Go Back");
      int option=scanner.nextInt();
      switch(option)
      {
      case 1: notifications();break;
      default: System.out.println("Invalid Option");System.exit(0);
      }
    	
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
