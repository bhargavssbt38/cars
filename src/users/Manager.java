package users;

import java.text.*;
import java.lang.*;
import app.*;
//import com.sun.org.apache.xpath.internal.operations.Bool;
import pages.*;
import java.sql.*;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.util.*;
import java.util.Date;

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
                    System.out.println(mechanicInsertStmt);

                    String mechAtInsert = "insert into mechanicAt (mechanic_id, sc_id) values('"+mechCount+"','"+scId+"')";
                    System.out.println(mechAtInsert);

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
                r = Application.stmt.executeUpdate("insert into login values('"+emailaddr+"','12345678',"+recCount+")");

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
        Float earnings = Float.parseFloat(prComp);
       // if(prPeriod.equalsIgnoreCase("hourly"))
        String status = "";
        String hrs = "";
        Application.rs = Application.stmt.executeQuery("select mechanic_id, hours_worked from mechanic where emp_id = '"+eid+"'");
        while(Application.rs.next())
        {
            status = Application.rs.getString("mechanic_id");
            if(Application.rs.wasNull())
            {
                status = "";
                break;
            }
            hrs = Application.rs.getString("hours_worked");
        }

        if(status.equalsIgnoreCase(""))
            status = "mechanic";
        else
        {
            status = "receptionist";
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
    	System.out.print("Earnings: ");
    	if(status.equalsIgnoreCase("mechanic"))
        {
            System.out.println("Hours worked this payment period: "+hrs);
            Float h = Float.parseFloat(hrs);
            System.out.println(earnings*h);
        }
        else
        {
            System.out.println("This employee earns a monthly salary");
            System.out.println(prComp);
        }
        //System.out.println(prComp);
    	System.out.print("Units: ");
    	System.out.println("USD (United States Dollars)");


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
        String displayInvStmt = "select * from inventory i,parts p where p.part_id=i.part_id and i.sc_id = '"+scID+"'";
        System.out.println("Inventory");
        Application.rs = Application.stmt.executeQuery(displayInvStmt);
        Statement stat = null;
        ResultSet tmp = null;
        while(Application.rs.next())
        {
            String pid = Application.rs.getString("part_id");
            if(Application.rs.wasNull())
            {
                System.out.println("This service center has an empty inventory");
                break;
            }
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

    	String orderStmt = "select * from orders o, parts p, received_from r, distributor d, placed_to pl where o.part_id = p.part_id and o.order_id = r.order_id and r.distributor_id = d.distributor_id and o.order_id = pl.order_id and pl.sc_id = '"+getScId()+"'";
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
            System.out.println("5. Purchaser Name: "+getScId());
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

            String k = "select distributor_id,delivery_time from supplied_by where part_id = '"+partid+"'";
            Application.rs = Application.stmt.executeQuery(k);
            String estTime="",did="",chk="",oldDate="",newDate="";
            while(Application.rs.next())
            {

                estTime = Application.rs.getString("delivery_time");
                if(Application.rs.wasNull())
                    estTime = "No estimated date";
                did = Application.rs.getString("distributor_id");
            }
            if(estTime.equalsIgnoreCase(""))
                estTime = "No estimated date";

            if(estTime.equalsIgnoreCase("No estimated date")==false)
            {   System.out.println("asdfsdf\n\n");
                int et = Integer.parseInt(estTime);
                Date d = new Date();
                oldDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Calendar c = Calendar.getInstance();
                try{
                    //Setting the date to the given date
                    c.setTime(sdf.parse(oldDate));
                }catch(ParseException e){
                    e.printStackTrace();
                }

                //Number of Days to add
                c.add(Calendar.DAY_OF_MONTH, et);
                //Date after adding the days to the given date
                newDate = sdf.format(c.getTime());
            }

            System.out.println(oldDate + " " + newDate);

            String createOrderStmt = "insert into orders (order_id,qty,order_date,order_status,part_id,expected_delivery_date) values(";

    	String countQuery = "select count(*) as C from orders";
    	Application.rs = Application.stmt.executeQuery(countQuery);
    	String ct = "";

    	while(Application.rs.next())
        {
            ct = Application.rs.getString("C");

        }

        ct = String.valueOf(Integer.parseInt(ct)+1);
        createOrderStmt+=ct;
        if(estTime.equalsIgnoreCase("No estimated date"))
            createOrderStmt += (","+quantity+",'11-11-2018','Pending','"+partid+"','')");
        else
    	    createOrderStmt += (","+quantity+",'11-11-2018','Pending','"+partid+"','"+newDate+"')");
    	System.out.println(createOrderStmt);
    	int hh = Application.stmt.executeUpdate(createOrderStmt);
    	System.out.println("The order has been successfully placed with order ID: "+ct);

    	//What happens next?
        String id = getScId();
        int x = Application.stmt.executeUpdate("insert into placed_to(order_id,sc_id) values('"+ct+"','"+id+"')");


        if(estTime.equalsIgnoreCase("No estimated date"))
            System.out.println("There is no estimated date of delivery");
        else
            System.out.println("Estimated time of delivery: "+newDate);

        x = Application.stmt.executeUpdate("insert into received_from(order_id, distributor_id) values('"+ct+"','"+did+"')");

    	orders(); break;
    	case 2: orders(); break;
    	default: System.out.println("Invalid Option");System.exit(0);
    	    	
    	}
    	
    	
    }
//Manager: Notifications

    private void notifications() throws Exception {
       /*System.out.println("Notifications");
       System.out.println("1. Notification ID");
       System.out.println("2. Notification Date/Time");
       System.out.println("3. Order ID");
       System.out.println("4. Supplier Name");
       System.out.println("5. Expected Delivery Date");
       System.out.println("6. Delayed by Number of Days");*/
       // System.out.println(getScId());
       Application.rs = Application.stmt.executeQuery("select notif_date,message,sc_id from notification");
       while(Application.rs.next())
       {
           String date = Application.rs.getString("notif_date");
           String message = Application.rs.getString("message");
           String sc = Application.rs.getString("sc_id");
           //System.out.println(getScId());
           if(sc.equalsIgnoreCase(getScId()))
           {
               System.out.println("Date: "+date);
               System.out.println("Message: "+message);
               System.out.println("\n\n");
           }
           else continue;
       }


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

        String getDetails = "select * from orders where order_id = '"+oid+"'";

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

        getDetails = "select part_name,price from parts where part_id = '"+pid+"'";
        System.out.println(getDetails);
        Application.rs = Application.stmt.executeQuery(getDetails);
        while(Application.rs.next())
        {
            pname = Application.rs.getString("part_name");
            unitPrice = Application.rs.getString("price");
        }

        Application.rs = Application.stmt.executeQuery("select distributor_id from received_from where order_id = '"+oid+"'");
        while(Application.rs.next())
        {
            sname = Application.rs.getString("distributor_id");
        }

        Application.rs = Application.stmt.executeQuery("select distributor_name from distributor where distributor_id = '"+sname+"'");
        while(Application.rs.next())
        {
            sname = Application.rs.getString("distributor_name");
        }

        totCost = String.valueOf(Integer.parseInt(qty)*Integer.parseInt(unitPrice));
        getDetails = "select sc_id from placed_to where order_id = '"+oid+"'";


        Application.rs = Application.stmt.executeQuery(getDetails);
        while(Application.rs.next())
        {
            purName = Application.rs.getString("sc_id");
        }

        Application.rs = Application.stmt.executeQuery("select sc_name from servicecentre where sc_id = '"+purName+"'");
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
        scanner.nextLine();
        System.out.println("\tEnter basic services list (separated by a comma): ");
        String partsA = scanner.nextLine();
        System.out.println("Service B:");
        System.out.println("\tEnter miles: ");
        double milesB = scanner.nextDouble();
        System.out.println("\tEnter months: ");
        int monthsB = scanner.nextInt();
        scanner.nextLine();
        System.out.println("\tEnter additional basic services: ");
        String partsB = scanner.nextLine();
        System.out.println("Service C:");
        System.out.println("\tEnter miles: ");
        double milesC = scanner.nextDouble();
        System.out.println("\tEnter months: ");
        int monthsC = scanner.nextInt();
        scanner.nextLine();
        System.out.println("\tEnter additional basic services: ");
        String partsC = scanner.nextLine();
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
        int x;
        String createCar = "insert into service_type_lookup (lookup_id,car_make,car_model,car_year,service_a,service_b,service_c) values (";

        String recCt = "";
        String q = "select count(*) as C from service_type_lookup";
        Application.rs = Application.stmt.executeQuery(q);
        while(Application.rs.next())
        {
            recCt = Application.rs.getString("C");
        }
        recCt = String.valueOf(Integer.parseInt(recCt)+1);

        createCar+=recCt;
        createCar+=(",'"+make+"','"+model+"','"+year+"',"+milesA+","+milesB+","+milesC+")");
        System.out.println(createCar);
        int f=0;

        String sa[] =  partsA.split(",");
        String sb[] =  partsB.split(",");
        String sc[] =  partsC.split(",");

        String bidsa[]= new String[sa.length];
        String bidsb[]= new String[sa.length];
        String bidsc[]= new String[sa.length];


        for (int i = 0; i < sa.length; i++) {
            // Fetch the item, trim it and put it back in
            sa[i] = sa[i].trim();
            //get the basic service id
            String bid = "";
            System.out.println("select basic_service_id from basic_services where service_name='"+sa[i]+"'");
            Application.rs = Application.stmt.executeQuery("select basic_service_id from basic_services where service_name='"+sa[i]+"'");
            while(Application.rs.next())
            {
                bid = Application.rs.getString("basic_service_id");
                if(Application.rs.wasNull())
                {
                    f=1;
                    System.out.println("One of the basic services you entered in incorrect, please try again!\n");
                    break;
                }
                else
                    bidsa[i] = bid;
            }

        }


        //Service type B
        for (int i = 0; i < sb.length; i++) {
            // Fetch the item, trim it and put it back in
            sb[i] = sb[i].trim();
            //get the basic service id
            String bid = "";

            Application.rs = Application.stmt.executeQuery("select basic_service_id from basic_services where service_name='"+sb[i]+"'");
            while(Application.rs.next())
            {
                bid = Application.rs.getString("basic_service_id");
                if(Application.rs.wasNull())
                {
                    f=1;
                    System.out.println("One of the basic services you entered in incorrect, please try again!\n");
                    break;
                }
                else
                    bidsb[i] = bid;
            }

        }

        //Service type C
        System.out.println("before c\n");
        for (int i = 0; i < sc.length; i++) {
            // Fetch the item, trim it and put it back in
            sc[i] = sc[i].trim();
            //get the basic service id
            String bid = "";

            Application.rs = Application.stmt.executeQuery("select basic_service_id from basic_services where service_name='"+sc[i]+"'");
            while(Application.rs.next())
            {
                bid = Application.rs.getString("basic_service_id");
                if(Application.rs.wasNull())
                {
                    f=1;
                    System.out.println("One of the basic services you entered in incorrect, please try again!\n");
                    break;
                }
                else
                    bidsc[i] = bid;
            }

        }


        if(f==0) //If all details are valid
        {
            x = Application.stmt.executeUpdate(createCar);
            for(int i=0; i<sa.length;i++)
            {
                x = Application.stmt.executeUpdate("insert into service_type_services values("+recCt+",'A',"+bidsa[i]+")");
                x = Application.stmt.executeUpdate("insert into service_type_services values("+recCt+",'B',"+bidsa[i]+")");
                x = Application.stmt.executeUpdate("insert into service_type_services values("+recCt+",'C',"+bidsa[i]+")");
            }
            System.out.println("sdfsdf\n");
            for(int i=0; i<sb.length;i++)
            {

                x = Application.stmt.executeUpdate("insert into service_type_services values("+recCt+",'B',"+bidsb[i]+")");
                x = Application.stmt.executeUpdate("insert into service_type_services values("+recCt+",'C',"+bidsb[i]+")");
            }
            System.out.println("sdfsdf\n");
            for(int i=0; i<sc.length;i++)
            {
                x = Application.stmt.executeUpdate("insert into service_type_services values("+recCt+",'C',"+bidsc[i]+")");
            }

        }



    }

    // (For Manager: New Car Model)
    // TODO: validate new car models
    private boolean validateNewCar(String make, String model, int year, double milesA, int monthsA, String partsA, double milesB, int monthsB, String partsB, double milesC, int monthsC, String partsC) throws Exception {
        boolean valid = true;
        if(milesA >= 0 && milesB >=0 && milesC >= 0 && year>1900 && year<2018 && monthsA >= 0 && monthsB >= 0 && monthsC >= 0)
            ;
        else
        { valid = false;  }
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
        String dispQuery = "select car_make,car_model,car_year,service_a,service_b,service_c, b.basic_service_id, b.service_name, service_type from basic_services b, service_type_lookup sl, service_type_services sts where sl.lookup_id = sts.lookup_id and b.basic_service_id = sts.basic_service_id order by car_make,car_model,car_year" ;
        Application.rs = Application.stmt.executeQuery(dispQuery);
        HashMap<String, Integer> map = new HashMap<>();
        Boolean f1=false,f2=false,f3=false;
        String stype = "", make = "", model = "", year = "", amiles = "", aservices = "", bmiles = "", bservices = "", cmiles = "", cservices = "";
        System.out.println(dispQuery);
        while(Application.rs.next())
        {
            f1=false;
            f2=false;
            f3=false;
            String make1 = Application.rs.getString("car_make");
            if(!map.containsKey(make1))
            {
                make = make1;
                map.put(make,1);
                f1=true;
            }

            String model1 = Application.rs.getString("car_model");
            if(!map.containsKey(model1))
            {
                model = model1;
                map.put(model,1);
                f2=true;
            }

            String year1 = Application.rs.getString("car_year");

            if(!map.containsKey(year1))
            {
                year = year1;
                map.put(year,1);
                f3=true;
            }

            amiles = Application.rs.getString("service_a");
            bmiles = Application.rs.getString("service_b");
            cmiles = Application.rs.getString("service_c");
            stype = Application.rs.getString("service_type");

            if(stype.equalsIgnoreCase("a"))
                aservices+= (Application.rs.getString("service_name") + ", ");
            else if(stype.equalsIgnoreCase("b"))
                bservices+= (Application.rs.getString("service_name") + ", ");
            else if(stype.equalsIgnoreCase("c"))
                cservices+= (Application.rs.getString("service_name") + ", ");
            if(f1||f2||f3) {
                System.out.println("\n\n1. Car make: " + make);
                System.out.println("2. Car model: " + model);
                System.out.println("3. Car year: " + year);
                System.out.println("4. Service A details: ");
                System.out.println("Miles: " + amiles);
                System.out.println("Basic Services: " + aservices);
                System.out.println("5. Service B details: ");
                System.out.println("Miles: " + bmiles);
                System.out.println("Basic Services: " + bservices);
                System.out.println("6. Service C details: ");
                System.out.println("Miles: " + cmiles);
                System.out.println("Basic Services: " + cservices);
                System.out.println("\n");
                f1=false; f2=false; f3=false;
            }
        }
        if(f1||f2||f3) {
            System.out.println("\n\n1. Car make: " + make);
            System.out.println("2. Car model: " + model);
            System.out.println("3. Car year: " + year);
            System.out.println("4. Service A details: ");
            System.out.println("Miles: " + amiles);
            System.out.println("Basic Services: " + aservices);
            System.out.println("5. Service B details: ");
            System.out.println("Miles: " + bmiles);
            System.out.println("Basic Services: " + bservices);
            System.out.println("6. Service C details: ");
            System.out.println("Miles: " + cmiles);
            System.out.println("Basic Services: " + cservices);
            System.out.println("\n");
        }
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

        String serviceHistoryQuery = "select sr.license_no, t.service_time, t.end_date, t.mechanic_id, s.service_id, t.service_date, t. service_time, e.emp_name as mechanic_name, c.customer_name from servicereln sr, mechanic m, services s, customer c, timeslot t, employee e where sr.service_id = s.service_id and c.customer_id = sr.customer_id and t.service_id = s.service_id and m.mechanic_id = t.mechanic_id and m.emp_id = e.emp_id";
        String sid = "", cname = "", stype = "", mname = "", sdate = "", edate = "", status = "", lp = "";
        Application.rs = Application.stmt.executeQuery(serviceHistoryQuery);
        //System.out.println("sdfsd\n");
        while(Application.rs.next())
        {
            sid = Application.rs.getString("service_id");
            cname = Application.rs.getString("customer_name");
            lp = Application.rs.getString("license_no");
            //stype = Application.rs.getString("ser");
            mname = Application.rs.getString("mechanic_name");
            sdate = Application.rs.getString("service_date");
            edate = Application.rs.getString("end_date");
            status = "Completed";
            if(edate == null)
            { status = "Pending"; edate = "Still in progress - no end date yet"; }

            System.out.println("SID : "+sid);
            System.out.println("Customer Name : "+cname);
            System.out.println("License plate of the car serviced : "+lp);
            System.out.println("Mechanic name : "+mname);
            System.out.println("Start Date : "+sdate);
            System.out.println("End date : "+edate);
            System.out.println("Service Status : "+status);
            System.out.println("\n");
        }

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

        String scId = getScId();
        System.out.println(scId);
        Statement sql = Application.conn.createStatement();
        ResultSet rr = null;
        rr = sql.executeQuery("select service_id from services");
        while (rr.next()) {
            String serviceID = rr.getString("service_id");
            System.out.println(serviceID);
            String chk = "select c.sc_id from servicereln s, customer c where s.customer_id = c.customer_id and s.service_id = '"+serviceID+"'";
            Application.rs = Application.stmt.executeQuery(chk);
            while(Application.rs.next())
            {
                chk = Application.rs.getString("sc_id");
            }
            if(chk.equalsIgnoreCase(scId)==false)
            {
                System.out.println(serviceID+" not part of this service center!");
                continue;
            }
            Application.rs = Application.stmt.executeQuery("select license_no from servicereln where service_id=" + serviceID);
            String license_no = null;
            while (Application.rs.next()) {
                license_no = Application.rs.getString("license_no");

            }
            String car_make = null, car_model = null;
            Application.rs = Application.stmt.executeQuery("select car_make,car_model from car where license_no='" + license_no + "'");
            while (Application.rs.next()) {
                car_make = Application.rs.getString("car_make");

                car_model = Application.rs.getString("car_model");
            }

            Date startdate = null;
            Application.rs = Application.stmt.executeQuery("select service_date from timeslot where service_id=" + serviceID);
            while (Application.rs.next()) {
                startdate = Application.rs.getDate("service_date");
            }

            int lookup_id = 0;

            String make = car_make.toLowerCase();
            String model = car_model.toLowerCase();
            Application.rs = Application.stmt.executeQuery("select lookup_id from service_type_lookup where car_make='" + make + "' AND car_model='" + model + "'");
            System.out.println("The car make" + (make));
            System.out.println("The car model" + (model));


            while (Application.rs.next()) {
                lookup_id = Application.rs.getInt("lookup_id");

            }


            Application.rs = Application.stmt.executeQuery("select maintenance_type from maintenance where service_id=" + serviceID);
            String type = null;
            while (Application.rs.next()) {
                type = Application.rs.getString("maintenance_type");

            }
            List<Integer> bid = new ArrayList();


            Application.rs = Application.stmt.executeQuery("select basic_service_id from service_type_services where service_type='" + type + "'AND lookup_id=" + lookup_id);
            int basicid = 0;
            while (Application.rs.next()) {
                basicid = Application.rs.getInt("basic_service_id");
                bid.add(basicid);

            }


            List<Integer> part = new ArrayList();
            List<Integer> quantity = new ArrayList();

            for (int i = 0; i < bid.size(); i++) {
                Application.rs = Application.stmt.executeQuery("select part_id,quantity from basic_services_parts where basic_service_id=" + bid.get(i) + "AND car_make='" + car_make + "' AND car_model='" + car_model + "'");
                int parts = 0, count = 0;
                while (Application.rs.next()) {
                    parts = Application.rs.getInt("part_id");
                    count = Application.rs.getInt("quantity");
                    part.add(parts);
                    quantity.add(count);
                }


            }

            List<String> servicename = new ArrayList();
            List<String> rate = new ArrayList();
            List<Float> hours = new ArrayList();
            for (int k = 0; k < bid.size(); k++) {
                Application.rs = Application.stmt.executeQuery("select service_name,rate,time_hours from basic_services where basic_service_id=" + bid.get(k));
                while (Application.rs.next()) {
                    String sname = Application.rs.getString("service_name");
                    String cost = Application.rs.getString("rate");
                    float time = Application.rs.getFloat("time_hours");
                    servicename.add(sname);
                    rate.add(cost);
                    hours.add(time);
                }
            }

            float totalcost = 0;
            List<Float> servicecost = new ArrayList();
            List<Float> partcost = new ArrayList();
            List<Integer> warranty = new ArrayList();
            float pcost = 0;
            int months = 0;
            for (int a = 0; a < part.size(); a++) {
                Application.rs = Application.stmt.executeQuery("select warranty,price from parts where part_id=" + part.get(a));
                while (Application.rs.next()) {
                    pcost = Application.rs.getFloat("price");
                    months = Application.rs.getInt("warranty");
                    partcost.add(pcost);
                    warranty.add(months);
                }
            }

            Calendar c1 = Calendar.getInstance();

            int year = startdate.getYear();
            int month = startdate.getMonth();
            int date = startdate.getDate();
            c1.set(year, month, date);

            Calendar currentdate = Calendar.getInstance();

            List<String> inwarranty = new ArrayList();
            for (int l = 0; l < warranty.size(); l++) {
                c1.add(month, warranty.get(l));
                if (currentdate.before(c1)) {
                    String temp;
                    temp = "Yes";

                    inwarranty.add(temp);
                } else {
                    inwarranty.add("Warranty Expired");
                }
            }
            for (int b = 0; b < bid.size(); b++) {
                float temp = 0;
                float temp1 = 0;
                temp = quantity.get(b) * partcost.get(b);
                if (rate.get(b).equalsIgnoreCase("low")) {
                    temp1 = 50;
                } else {
                    temp1 = 65;
                }
                if (inwarranty.get(b).equalsIgnoreCase("Yes")) {
                    float temp2 = 0;
                    servicecost.add(b, temp);

                } else {
                    temp1 += temp1 * hours.get(b);
                    servicecost.add(temp + temp1);
                }
                totalcost += servicecost.get(b);
            }
            System.out.println("Basic service ID, Service Name, Part_id, quantity, Rate of labor, hours required, Part Cost, Warranty,ServiceCost InWarranty/Expired Warranty ");
            for (int j = 0; j < bid.size(); j++) {
                System.out.println();
                System.out.print("\t" + bid.get(j));
                System.out.print("\t" + servicename.get(j));
                System.out.print("\t" + part.get(j));
                System.out.print("\t" + quantity.get(j));
                System.out.print("\t" + rate.get(j));
                System.out.print("\t" + hours.get(j));
                System.out.print("\t" + partcost.get(j));
                System.out.print("\t" + warranty.get(j));
                System.out.print("\t" + servicecost.get(j));
                System.out.print("\t" + inwarranty.get(j));


            }
            float totalhours = 0;

            for (int c = 0; c < hours.size(); c++) {
                totalhours += hours.get(c);

            }
            System.out.print("\n");
            System.out.println("The total number of labor hours involved in this service: " + totalhours);
            System.out.println("The total cost involved in this service: " + totalcost);
            Application.rs = Application.stmt.executeQuery("select employee.emp_name,servicereln.license_no,servicereln.service_id,repair.actual_fees,repair.diagnostic_fees,repair.repair_id,timeslot.mechanic_id,timeslot.service_date,timeslot.service_time,timeslot.end_date,timeslot.end_time from employee,repair,servicereln,timeslot,mechanic where servicereln.service_id='" + serviceID + "' AND timeslot.service_id=servicereln.service_id AND servicereln.service_id = repair.service_id AND timeslot.mechanic_id= mechanic.mechanic_id AND mechanic.emp_id=employee.emp_id");

            while (Application.rs.next()) {

                String mechanic_name = Application.rs.getString("emp_name");
                String license = Application.rs.getString("license_no");
                String service_id = Application.rs.getString("service_id");
                String repair_id = Application.rs.getString("repair_id");
                String mechanic_id = Application.rs.getString("mechanic_id");
                Float actual_fees = Application.rs.getFloat("actual_fees");
                Float diagnostic_fees = Application.rs.getFloat("diagnostic_fees");
                Float totalfees = actual_fees + diagnostic_fees;
                Date start_date = Application.rs.getDate("service_date");
                String start_time = Application.rs.getString("service_time");
                Date end_date = Application.rs.getDate("end_date");
                String end_time = Application.rs.getString("end_time");
                //System.out.println("The Customer ID is:" + id);
                System.out.println("The Service ID for the service is:" + service_id);
                System.out.println("The License Plate for the car in this service is:" + license);
                System.out.println("The Service Type involved in this Service is Repair");
                System.out.println("The Mechanic Involved in this service is: " + mechanic_name);
                System.out.println("This Service Started on: " + start_date);
                System.out.println("Start Time:" + start_time);


                if (end_time == null || end_time.isEmpty()) {
                    System.out.println(" The Status of this Service is still ongoing");
                } else
                    System.out.println("This service is completed and ended on" + end_date + " end time" + end_time);
                System.out.println("The total service cost involved in this service:" + totalfees);


                System.out.println("\n\n\n");
            }
        /*

        String invoiceQuery = "select s.service_id, c.customer_name, service_date, end_date, license_no, e.emp_name, p.part_name, p.price, labor_charge, estimated_hours from services s,customer c, timeslot t, servicereln sr, employee e, requires r, mechanic m, parts p where s.service_id = r.service_id and p.part_id = r.part_id and sr.service_id = s.service_id and sr.customer_id = c.customer_id and t.service_id = s.service_id and m.mechanic_id = t.mechanic_id and e.emp_id = m.emp_id order by s.service_id";
        String sid = "", cname = "", sdate = "", edate = "", lno = "", stype = "", mname = "", p="", cost="", hrs = "", scost = "";
        Application.rs = Application.stmt.executeQuery(invoiceQuery);
        String flag = "";

        while(Application.rs.next())
        {
            sid = Application.rs.getString("service_id");
            cname = Application.rs.getString("customer_name");
            sdate = Application.rs.getString("service_date");
            edate = Application.rs.getString("end_date");

            if(Application.rs.wasNull())
                edate = "Still in progress";
            lno = Application.rs.getString("license_no");
            mname = Application.rs.getString("emp_name");
            p = Application.rs.getString("part_name");
            cost = Application.rs.getString("price");
            hrs = Application.rs.getString("estimated_hours");
            scost = Application.rs.getString("labor_charge");

            Float totalCost = Float.parseFloat(hrs) * Float.parseFloat(scost);

            if(sid.equalsIgnoreCase(flag))
            {
                System.out.println("Part name: " + p + " Cost: "+cost);
            }
            else
            {
                flag = sid;
                System.out.println("Service id: "+sid);
                System.out.println("Customer name: "+cname);
                System.out.println("Start Date: "+sdate);
                System.out.println("End Date: "+edate);
                System.out.println("Car License No: "+lno);
                System.out.println("Mechanic Name: "+mname);
                System.out.println("Labor hours: "+hrs);
                System.out.println("Charge per hour: "+scost);
                System.out.println("Total Labor Charge: "+totalCost);
                System.out.println("Parts Required:-");
                System.out.println("Part name: " + p + " Cost: "+cost);
            }

        }
        */
        }
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
