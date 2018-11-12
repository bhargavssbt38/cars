package users;

import app.*;
import pages.*;
import java.sql.*;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.ResultSet;
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
    	        String phone=scanner.next();
    	        System.out.println("Enter the Role of the employee");
    	        String role=scanner.next();
    	        System.out.println("Enter the Start Date in the format - 'DD-MM-YY'");
    	        String startdate=scanner.next();
    	        System.out.println("Enter the Compensation of the employee in USD");
    	        int compensation=scanner.nextInt();

    	        String insertStmt = "insert into employee (emp_id, emp_name, addr, phone, email) values (";

    	        Application.rs = Application.stmt.executeQuery("select count(*) as C from employee");
    	        String recCount = "";
                while( Application.rs.next() )
                {
                    recCount = Application.rs.getString("C");
                   // System.out.println(recCount+" is the count");
                }
                int recCt = Integer.parseInt(recCount);
                recCt++;
                recCount = String.valueOf(recCt);
                insertStmt+=recCount;
                insertStmt+=(","+"'"+name+"'"+","+"'"+address+"'"+","+"'"+phone+"'"+","+"'"+emailaddr+"'"+")");
                System.out.println(insertStmt);

                String scId = "";
                String managerId="";

                String getManagerId = "select manager_id from manager where emp_id = '"+Application.currentUser.userID+"'";
                Application.rs = Application.stmt.executeQuery(getManagerId);
                while( Application.rs.next() )
                {
                    managerId = Application.rs.getString("manager_id");
                    // System.out.println(recCount+" is the count");
                }
                System.out.println("Manager is " + managerId);

                String getScId = "select sc_id from manages where manager_id = '"+managerId+"'";
                Application.rs = Application.stmt.executeQuery(getScId);
                while( Application.rs.next() )
                {
                    scId = Application.rs.getString("sc_id");
                    // System.out.println(recCount+" is the count");
                }
                System.out.println("Service Centre is " + scId);


                //if it is a receptionist, check if the SC already has a receptionist
                String chk = "select count(*) as C from receptionistAt where sc_id = '"+scId+"'";
                Application.rs = Application.stmt.executeQuery(chk);
                String chkCt = "";
                while( Application.rs.next() )
                {
                    chkCt = Application.rs.getString("C");
                    // System.out.println(recCount+" is the count");
                }
                int chk_ct = Integer.parseInt(chkCt);
                if(chk_ct > 0 && role.toLowerCase().equals("receptionist"))
                {
                    System.out.println("There is already a receptionist for this service center..");
                    landingPage();
                    break;
                }


                int r = Application.stmt.executeUpdate(insertStmt);

                //Inserting into the particular role's table

                if(role.toLowerCase().equals("mechanic"))
                {
                    String mechanicInsertStmt = "insert into mechanic (mechanic_ID, hours_worked, emp_id) values(";
                    Application.rs = Application.stmt.executeQuery("select count(*) as C from mechanic");
                    String mechCount = "";
                    while( Application.rs.next() )
                    {
                        mechCount = Application.rs.getString("C");
                        // System.out.println(recCount+" is the count");
                    }
                    int mechCt = Integer.parseInt(mechCount);
                    mechCt++;
                    mechCount = String.valueOf(mechCt);
                    mechanicInsertStmt+=mechCount;
                    mechanicInsertStmt+=(","+"0,"+recCount+")");
                    //System.out.println(mechanicInsertStmt);

                    String mechAtInsert = "insert into mechanicAt (mechanic_id, sc_id) values("+mechCount+","+scId+")";
                    //System.out.println(mechAtInsert);

                    r = Application.stmt.executeUpdate(mechanicInsertStmt);
                    r = Application.stmt.executeUpdate(mechAtInsert);
                }
                else if(role.toLowerCase().equals("receptionist"))//Receptionist
                {

                    String receptionistInsertStmt = "insert into receptionist (receptionist_ID, emp_id) values(";
                    Application.rs = Application.stmt.executeQuery("select count(*) as C from receptionist");
                    String recpCount = "";
                    while( Application.rs.next() )
                    {
                        recpCount = Application.rs.getString("C");
                        // System.out.println(recCount+" is the count");
                    }
                    int recpCt = Integer.parseInt(recpCount);
                    recpCt++;

                    recpCount = String.valueOf(recpCt);
                    receptionistInsertStmt+=recpCount;
                    receptionistInsertStmt+=(","+recCount+")");
                    //System.out.println(receptionistInsertStmt);

                    String recpAtInsert = "insert into receptionistAt (receptionist_id, sc_id) values("+recpCount+","+scId+")";
                    //System.out.println(recpAtInsert);

                    r = Application.stmt.executeUpdate(receptionistInsertStmt);
                    r = Application.stmt.executeUpdate(recpAtInsert);
                }

                String payrollInsertStmt = "insert into payroll (wages,start_date,frequency,emp_id) values(";
                String freq = "";
                if(role.toLowerCase().equals("receptionist"))
                    freq = "Monthly";
                else freq = "Hourly";


                payrollInsertStmt += ("'"+compensation+"'"+","+"'"+startdate+"'"+","+"'"+freq+"'"+","+"'"+recCount+"'"+")");
                System.out.println(payrollInsertStmt);
                r = Application.stmt.executeUpdate(payrollInsertStmt);


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

    	String getPayrollStmt = "select * from payroll where emp_id = '"+eid+"'";
        Application.rs = Application.stmt.executeQuery(getPayrollStmt);
        String prDate="",prEid="",prPeriod="",prName="",prComp="",prUnits="",prEarnings="";
        while(Application.rs.next())
        {
            prEarnings = Application.rs.getString("wages");
            prPeriod = Application.rs.getString("frequency");
        }
        //System.out.println(getPayrollStmt);
        String getEmpDetails = "select * from employee where emp_id = '"+eid+"'";
        Application.rs = Application.stmt.executeQuery(getEmpDetails);

        while(Application.rs.next())
        {
            prName = Application.rs.getString("emp_name");

        }

        String getPayDetails = "select * from payroll where emp_id = '"+eid+"'";
        Application.rs = Application.stmt.executeQuery(getPayDetails);

        while(Application.rs.next())
        {
            prComp = Application.rs.getString("wages");

        }

    	System.out.print("Payroll for employee id: ");
        System.out.println(eid);
    	System.out.print("Paycheck date: ");
        System.out.println("1st, 15th of the month");
    	System.out.print("Pay period: ");
        System.out.println(prPeriod);
    	System.out.print("Employee ID: ");
        System.out.println(eid);
    	System.out.print("Employee Name: ");
        System.out.println(prName);
    	System.out.print("Compensation: ");
        System.out.println(prComp);
    	System.out.print("Units: ");
    	System.out.println("What is units?");
    	System.out.print("Earnings: ");
        System.out.println(prComp);

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

        String scID = getScId();
        String displayInvStmt = "select * from inventory i,parts p where p.part_id=i.part_id and i.sc_id = "+scID;
        System.out.println("Inventory");
        Application.rs = Application.stmt.executeQuery(displayInvStmt);
        Statement stat = null;
        ResultSet tmp = null;
        while(Application.rs.next())
        {
            String pid = Application.rs.getString("part_id");
            String qty = Application.rs.getString("current_qty");
            String minqty = Application.rs.getString("min_qty");
            String pname = "";
            String minOrder = "";
            String unitPrice = "";
            pname = Application.rs.getString("part_name");
            minOrder = Application.rs.getString("min_threshold");
            unitPrice = Application.rs.getString("price");

            System.out.println("1. Part ID : "+pid);
            System.out.println("2. Part Name : "+pname);
            System.out.println("3. Quantity : "+qty);
            System.out.println("4. Unit Price : "+unitPrice);
            System.out.println("5. Minimum Quantity Threshold : "+minqty);
            System.out.println("6. Minimum Order Threshold : "+minOrder);
            System.out.println("\n\n");
        }


     
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

    	String orderStmt = "select * from orders o, parts p, received_from r, distributor d where o.part_id = p.part_id and o.order_id = r.order_id and r.distributor_id = d.distributor_id";
        Application.rs = Application.stmt.executeQuery(orderStmt);
    	String oid="",dt="",pname="",sname="",purchname="",qty="",unitPrice="",totCost="",oStatus="";

        Statement stt = null;
        stt = Application.conn.createStatement();

    	while(Application.rs.next())
        {
            oid = Application.rs.getString("order_id");
            dt = Application.rs.getString("order_date");
            pname = Application.rs.getString("part_name");

            sname = Application.rs.getString("distributor_name");
            qty = Application.rs.getString("qty");
            unitPrice = Application.rs.getString("price");
            totCost = String.valueOf(Integer.parseInt(qty)*Integer.parseInt(unitPrice));
            oStatus = Application.rs.getString("order_status");

            System.out.println("1. OrderID: "+oid);
            System.out.println("2. Date: "+dt);
            System.out.println("3. Part Name: "+pname);
            System.out.println("4. Supplier Name: "+sname);
            System.out.println("5. Purchaser Name: ");
            System.out.println("6. Quantity: "+qty);
            System.out.println("7. Unit Price: "+unitPrice);
            System.out.println("8. Total Cost: "+unitPrice);
            System.out.println("9. Order Status: "+oStatus+"\n\n");

        }



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

    	String createOrderStmt = "insert into orders (order_id,qty,order_date,order_status,part_id) values(";

    	String countQuery = "select count(*) as C from orders";
    	Application.rs = Application.stmt.executeQuery(countQuery);
    	String ct = "";
    	while(Application.rs.next())
        {
            ct = Application.rs.getString("C");
        }

        ct = String.valueOf(Integer.parseInt(ct)+1);
        createOrderStmt+=ct;
    	createOrderStmt += (","+quantity+",'11-11-2018','Pending',"+partid+")");
    	System.out.println(createOrderStmt);
    	Application.rs = Application.stmt.executeQuery(createOrderStmt);
    	System.out.println("The order has been successfully placed with order ID: "+ct+" and estimated date: ");

    	//What happens next?

    	orders(); break;
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
    {

        String getDetails = "select * from orders where order_id = "+oid;

        String orderDate = "", pname = "", sname = "", purName = "", qty = "", unitPrice = "", totCost = "", status = "";

        String pid = "";
        Application.rs = Application.stmt.executeQuery(getDetails);
        while(Application.rs.next())
        {
            orderDate = Application.rs.getString("order_date");
            status = Application.rs.getString("order_status");
            pid = Application.rs.getString("part_id");
            qty = Application.rs.getString("qty");
        }

        getDetails = "select part_name,price from parts where part_id = "+pid;
        //System.out.println(getDetails);
        Application.rs = Application.stmt.executeQuery(getDetails);
        while(Application.rs.next())
        {
            pname = Application.rs.getString("part_name");
            unitPrice = Application.rs.getString("price");
        }

        Application.rs = Application.stmt.executeQuery("select distributor_id from received_from where order_id = "+oid);
        while(Application.rs.next())
        {
            sname = Application.rs.getString("distributor_id");
        }

        Application.rs = Application.stmt.executeQuery("select distributor_name from distributor where distributor_id = "+sname);
        while(Application.rs.next())
        {
            sname = Application.rs.getString("distributor_name");
        }

        totCost = String.valueOf(Integer.parseInt(qty)*Integer.parseInt(unitPrice));
        getDetails = "select sc_id from placed_to where order_id = "+oid;

//        System.out.println("sdfs");



        Application.rs = Application.stmt.executeQuery(getDetails);
        while(Application.rs.next())
        {
            purName = Application.rs.getString("sc_id");
        }

        Application.rs = Application.stmt.executeQuery("select sc_name from servicecentre where sc_id = "+purName);
        while(Application.rs.next())
        {
            purName = Application.rs.getString("sc_name");
        }


      System.out.println("1. Order ID: "+oid);
      System.out.println("2. Date: " + orderDate);
      System.out.println("3. Part Name: " + pname);
      System.out.println("4. Supplier Name: " + sname);
      System.out.println("5. Purchaser Name: " + purName);
      System.out.println("6. Quantity: " + qty);
      System.out.println("7. Unit Price: " + unitPrice);
      System.out.println("8. Total Cost: " + totCost);
      System.out.println("9. Order Status: " + status);
      
      System.out.println("\n \n Menu");
      System.out.println("1. Go Back");
      int option=scanner.nextInt();
      switch(option)
      {
      case 1: notifications();break;
      default: System.out.println("Invalid Option");System.exit(0);
      }
    	
    }

    // Manager: New Car Model
    private void newCarModel() throws Exception {
        System.out.println("\nNEW CAR MODEL:");
        System.out.println("Enter make: ");
        String make = scanner.next();
        System.out.println("Enter model: ");
        String model = scanner.next();
        System.out.println("Enter year: ");
        int year = scanner.nextInt();
        System.out.println("Service A:");
        System.out.println("\tEnter miles: ");
        double milesA = scanner.nextDouble();
        System.out.println("\tEnter months: ");
        int monthsA = scanner.nextInt();
        System.out.println("\tEnter parts list: ");
        String partsA = scanner.next();
        System.out.println("Service B:");
        System.out.println("\tEnter miles: ");
        double milesB = scanner.nextDouble();
        System.out.println("\tEnter months: ");
        int monthsB = scanner.nextInt();
        System.out.println("\tEnter additional parts: ");
        String partsB = scanner.next();
        System.out.println("Service C:");
        System.out.println("\tEnter miles: ");
        double milesC = scanner.nextDouble();
        System.out.println("\tEnter months: ");
        int monthsC = scanner.nextInt();
        System.out.println("\tEnter additional parts: ");
        String partsC = scanner.next();
        System.out.println("MENU:");
        System.out.println("\t1. Add Car");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                if(validateNewCar(make, model, year, milesA, monthsA, partsA, milesB, monthsB, partsB, milesC, monthsC, partsC)) {
                    saveNewCar(make, model, year, milesA, monthsA, partsA, milesB, monthsB, partsB, milesC, monthsC, partsC);
                } else {
                    System.out.println("Enter valid details.");
                }
                landingPage();
                break;
            case 2:
                landingPage();
                break;
            default:
                System.out.println("Invalid option");
                landingPage();
                break;
        }
    }

    // (For Manager: New Car Model)
    // TODO: insert details into db
    //A. Make
    //B. Model
    //C. Year
    //D. Service A:
        //a. Miles
        //b. Months
        //c. Parts List
    //E. Service B
        //a. Miles
        //b. Months
        //c. Additional Parts
    //F. Service C
        //a. Miles
        //b. Months
        //c. Additional Parts
    private void saveNewCar(String make, String model, int year, double milesA, int monthsA, String partsA, double milesB, int monthsB, String partsB, double milesC, int monthsC, String partsC) throws Exception {

    }

    // (For Manager: New Car Model)
    // TODO: validate new car models
    private boolean validateNewCar(String make, String model, int year, double milesA, int monthsA, String partsA, double milesB, int monthsB, String partsB, double milesC, int monthsC, String partsC) throws Exception {
        boolean valid = true;

        return valid;
    }

    // Manager: Car Service Details
    private void carServiceDetails() throws Exception {
        System.out.println("\nCAR SERVICE DETAILS:");
        displayCarServiceDetails();
        System.out.println("MENU:");
        System.out.println("\t1. Go back");
        int option = scanner.nextInt();
        if(option == 1) {
            landingPage();
        } else {
            System.out.println("Invalid option. Try again.");
            invoices();
        }
    }

    // (For Manager: Car Service Details)
    // TODO: display following details for all car models registered in the system
    //A. Make
    //B. Model
    //C. Year
    //D. Service A:
        //a. Miles
        //b. List of Basic Services (Service ID)
    //E. Service B
        //a. Miles
        //b. List of Basic Services(Service ID)
    //F. Service C
        //a. Miles
        //b. List of Basic Services (Service ID)
    private void displayCarServiceDetails() throws Exception {

    }

    // Manager: Service History
    private void serviceHistory() throws Exception {
        System.out.println("\nSERVICE HISTORY:");
        displayServiceHistory();
        System.out.println("MENU:");
        System.out.println("\t1. Go back");
        int option = scanner.nextInt();
        if(option == 1) {
            landingPage();
        } else {
            System.out.println("Invalid option. Try again.");
            invoices();
        }
    }

    // (For Manager: Service History)
    // TODO: display the following details for all cars that were serviced at this service center
    //A. Service ID
    //B. Customer Name
    //C. License Plate
    //D. Service Type
    //E. Mechanic Name
    //F. Service Start Date/Time
    //G. Service End Date/Time (expected or actual)
    //H. Service Status (Pending, Ongoing, or Complete)
    private void displayServiceHistory() throws Exception {
        String serviceHistoryQuery = "select sr.license_no, t.end_date, t.mechanic_id, s.service_id, t.service_date, t. service_time, e.emp_name as mechanic_name, c.customer_name \n" +
                "from servicereln sr, mechanic m, services s, customer c, timeslot t, employee e\n" +
                "where sr.service_id = s.service_id and c.customer_id = sr.customer_id and t.service_id = s.service_id and \n" +
                "m.mechanic_id = t.mechanic_id and m.emp_id = e.emp_id";
    }


    // Manager: Invoices
    private void invoices() throws Exception {
        System.out.println("\nINVOICES:");
        displayInvoices();
        System.out.println("MENU:");
        System.out.println("\t1. Go back");
        int option = scanner.nextInt();
        if(option == 1) {
            landingPage();
        } else {
            System.out.println("Invalid option. Try again.");
            invoices();
        }
    }

    // (For Manager: Invoices)
    // TODO: display following details for all services that are complete at this service center
    //A. Service ID
    //B. Customer Name
    //C. Service Start Date/Time
    //D. Service End Date/Time
    //E. Licence Plate
    //F. Service Type
    //G. Mechanic Name
    //H. Parts Used in service with cost of each part
    //I. Total labor hours
    //J. Labor wages per hour
    //K. Total Service Cost
    private void displayInvoices() throws Exception {

        System.out.println("")

    }

    private String getScId() throws Exception
    {
        String scId = "";
        String managerId="";

        String getManagerId = "select manager_id from manager where emp_id = '"+Application.currentUser.userID+"'";
        Application.rs = Application.stmt.executeQuery(getManagerId);
        while( Application.rs.next() )
        {
            managerId = Application.rs.getString("manager_id");
            // System.out.println(recCount+" is the count");
        }


        String getScId = "select sc_id from manages where manager_id = '"+managerId+"'";
        Application.rs = Application.stmt.executeQuery(getScId);
        while( Application.rs.next() )
        {
            scId = Application.rs.getString("sc_id");
            // System.out.println(recCount+" is the count");
        }
        return scId;
    }



}
