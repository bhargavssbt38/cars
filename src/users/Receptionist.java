package users;

import app.Application;
import pages.DisplayPage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Receptionist extends Employee {

    static Scanner scanner = new Scanner(System.in);

    public Receptionist(String userID) {
        super(userID, "receptionist");
    }

    public void landingPage() throws Exception {
        System.out.println("\nRECEPTIONIST LANDING PAGE:");
        System.out.println("\t1. Profile");
        System.out.println("\t2. View Customer Profile");
        System.out.println("\t3. Register Car");
        System.out.println("\t4. Service History");
        System.out.println("\t5. Schedule Service");
        System.out.println("\t6. Reschedule Service");
        System.out.println("\t7. Invoices");
        System.out.println("\t8. Daily Task-Update Inventory");
        System.out.println("\t9. Daily Task-Record Deliveries");
        System.out.println("\t10. Logout");
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
                registerCar();
                break;
            case 4:
                serviceHistory();
                break;
            case 5:
                scheduleService();
                break;
            case 6:
                rescheduleService();
                break;
            case 7:
                invoices();
                break;
            case 8:
                updateInventory();
                break;
            case 9:
                recordDeliveries();
                break;
            case 10:
                Application.currentUser = null;
                DisplayPage.homePage();
                break;
            default:
                System.out.println("Invalid option");
                System.exit(0);
        }
    }
//Receptionist: Register Car
    private void registerCar() throws Exception {
    	System.out.println("\n Register Car");
    	System.out.println("\n Enter the Customer email address");
    	String email= scanner.next();
    	System.out.println("\n Enter the License plate");
    	String licenseplate=scanner.next();
    	System.out.println("\n Enter the Purchase date");
    	String Pdate=scanner.next();
    	System.out.println("\n Enter the Make of the car");
        String make=scanner.next();
        System.out.println("\n Enter the Model of the car");
        String model=scanner.next();
        System.out.println("\n Enter the Year");
        String year=scanner.next();
        System.out.println("\n Enter the Current Mileage");
        int currentmileage=scanner.nextInt();
        System.out.println("\n Enter the Last Service Type");
        String lastservicetype=scanner.next();
        System.out.println("\n Enter the Last Service Date");
        String lastservicedate=scanner.next();
        System.out.println("\n \n \n \nMENU:");
        System.out.println("\t1. Register");
        System.out.println("\t2. Cancel");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
        case 1:
            if(validateRegisterCarData(email,licenseplate, Pdate, make,model,year,currentmileage,lastservicedate,lastservicetype)) {
                int registered = registerCar(email,licenseplate, Pdate, make, model, year, currentmileage, lastservicedate,lastservicetype);
                
                if(registered > 0) {
                    System.out.println("Car registered successfully");
                }
                landingPage();
                } 
            else {
            	System.out.println("Invalid data entered. All fields except last service date are required. Please try again.");
            	registerCar();
               }
             break;
        case 2: 
        	landingPage();
        	break;
        default: 
        	System.out.println("\nInvalid Option");
        	System.exit(0);
        }
        	
        }
    //Receptionist: Service History
    private void serviceHistory() throws Exception {
      System.out.println("\n Enter the Customer Email address");
      String email=scanner.next();
      if (validateemail(email)) {
       DisplayDetails(email);
      }
      else {
    	  System.out.println("Enter valid Email address"); 
    	  serviceHistory();
      }
      System.out.println("\n \n Menu");
      System.out.println("1. Go Back");
      int option=scanner.nextInt();
      switch(option) {
    	  case 1: landingPage();
    	  break;
    	  default:
    		  System.out.println("\n Invalid Option"); System.exit(0);
      }
    }
    //Receptionist: Schedule Service
    private void scheduleService() throws Exception {
     System.out.println(" Enter the customer email address");
     String email=scanner.next();
     System.out.println("Enter the License Plate");
     String licenseplate=scanner.next();
     System.out.println("Enter the Current Mileage");
     int currentmileage=scanner.nextInt();
     System.out.println(" Enter the Mechanic Name, It is optional");
     String mechanicname=scanner.next();
     if(validateservicerequest(email,licenseplate,currentmileage,mechanicname)) {
    	 System.out.println("\n Menu");
    	 System.out.println("1. Schedule Maintenance");
    	 System.out.println("2. Schedule Repair");
    	 System.out.println("3. Go Back");
    	 int option=scanner.nextInt();
    	 switch(option) {
    	 case 1: schedulemaintenance(email,licenseplate,currentmileage,mechanicname); break;
    	 case 2: schedulerepair(email,licenseplate,currentmileage,mechanicname); break;
    	 case 3: landingPage(); break;
    	 default: System.out.println("Invalid Option"); System.exit(0);
    	 }
     }
     else {
    	System.out.println("Invalid Data check the data entered, Re enter the data" );
    	scheduleService();
        	 
     }
     }
    //Receptionist: Schedule Maintenance - mechanic name optional
    //TODO: Find two earliest available service dates
    private void schedulemaintenance(String email, String licenseplate,int currentmileage, String mechanicname) throws Exception{
    	System.out.println("\n Menu");
    	System.out.println("1. Find Service Date");
    	System.out.println("2. Go Back");
    	int option=scanner.nextInt();
    	switch(option)
    	{ case 1: //Write a query to find two earliest available service dates and go to the receptionist schedule maintenance page 2
    		schedulemaintenance2(email,licenseplate,currentmileage,mechanicname); break;
    		    // If service date cannot be found due to insufficient parts then place order and show a message to the user asking him to try again after a specific date.
    		 //Uncomment the below line based on the result of the above query.     
    		//scheduleService(); break;
    		    		  
    	 case 2:  scheduleService();break;
    	 default: System.out.println("Invalid Option");System.exit(0);
    	}
    	
    	
    }
    //TODO: Write a query to display the two identified service dates and mechanic name 
    private void schedulemaintenance2(String email, String licenseplate, int currentmileage, String mechanicname)throws Exception {
    	System.out.println("The two available dates are:");
    	System.out.println("Menu");
    	System.out.println("1.Schedule on Date");
    	System.out.println("2.Go back");
    	int option=scanner.nextInt();
    	switch(option)
    	{
    	case 1: System.out.println("Choose one date from the available options shown above");
    	       int choice=scanner.nextInt();
    	       switch (choice) {
    	       case 1: System.out.println("You have chosen the first date");break;
    	       case 2: System.out.println("You have chosen the second date"); break;
    	       }scheduleService();break;
    	case 2: schedulemaintenance(email,licenseplate,currentmileage,mechanicname);break;
    	default: System.out.println("Invalid Option");System.exit(0);
    	}
    }
    //Receptionist: Schedule Repair
    //TODO: Find two repair dates that are available - write a query for the same.
    private void schedulerepair(String email,String licenseplate,int currentmileage,String mechanicname) throws Exception{
    	System.out.println("\n Menu");
    	System.out.println("1. Engine Knock");
    	System.out.println("2. Car drifts in a particular direction");
    	System.out.println("3.Battery does not hold charge");
    	System.out.println("4.Black/Unclean Exhaust");
    	System.out.println("5.A/C Heater not working");
    	System.out.println("6. Headlamps/Tail lamps not Working");
    	System.out.println("7. Check engine light");
    	System.out.println("8.Go Back");
    	int option=scanner.nextInt();
    	switch(option) {
    	case 1: System.out.println("Diagonstic Report"); System.out.println("Available Dates for Repair"); schedulerepair2(email,licenseplate,currentmileage,mechanicname); // Uncomment this line if you cannot find available dates for repair scheduleService();
    	        break;
    	case 2: System.out.println("Diagonstic Report"); System.out.println("Available Dates for Repair");schedulerepair2(email,licenseplate,currentmileage,mechanicname);// Uncomment this line if you cannot find available dates for repair scheduleService();
    	break;
    	case 3: System.out.println("Diagonsitc Report"); System.out.println("Available Dates for Repair");schedulerepair2(email,licenseplate,currentmileage,mechanicname);// Uncomment this line if you cannot find available dates for repair scheduleService();
    	break;
    	case 4: System.out.println("Diagonstic Report"); System.out.println("Available Dates for Repair");schedulerepair2(email,licenseplate,currentmileage,mechanicname); // Uncomment this line if you cannot find available dates for repair scheduleService();
    	break;
    	case 5: System.out.println("Diagonstic Report"); System.out.println("Available Dates for Repair");schedulerepair2(email,licenseplate,currentmileage,mechanicname);// Uncomment this line if you cannot find available dates for repair scheduleService();
    	break;
    	case 6: System.out.println("Diagonstic Report"); System.out.println("Available Dates for Repair");schedulerepair2(email,licenseplate,currentmileage,mechanicname);// Uncomment this line if you cannot find available dates for repair scheduleService();
    	break;
    	case 7: System.out.println("Diagonstic Report"); System.out.println("Available Dates for Repair");schedulerepair2(email,licenseplate,currentmileage,mechanicname); // Uncomment this line if you cannot find available dates for repair scheduleService();
    	break;
       	case 8: scheduleService(); break;	
       	default: System.out.println("Invalid Option"); System.exit(0);
    	
    	}
    	
    }
    
    // Receptionist: Schedule Repair
    //TODO: Write a query for diagonsitic report and available dates along with mechanic name
    //TODO: Write query to book a repair on the choosen date
    private void schedulerepair2(String email,String licenseplate,int currentmileage,String mechanicname) throws Exception
    {
    	System.out.println("Diagonstic Report and available dates for repair and mechanic name");
    	System.out.println("Menu");
    	System.out.println("1. Repair on Date");
    	System.out.println("2. Go Back");
    	int option=scanner.nextInt();
    	switch(option) {
    	case 1: System.out.println("Enter which date you want to repair your car from the 2 options" );
    	        int choice=scanner.nextInt();
    	        switch(choice)
    	        {
    	        case 1: System.out.println("You have choosen the first available date for repairing car"); scheduleService(); break;
    	        case 2: System.out.println("You have choosen the second available date for repairing car"); scheduleService();break;
    	        default: System.out.println("Invalid choice"); break;
    	        } break;
    	case 2:  schedulerepair(email,licenseplate,currentmileage,mechanicname); break;     
    	default: System.out.println("Invalid Option"); System.exit(0);
    	}
    }
    //Receptionist: Reschedule Service
    //Query: Write a query to retrieving details of the customer(license plate, service id, service date, service type, service details
    private void rescheduleService() throws Exception {
     System.out.println("Customer Enter your email address");
     String mail=scanner.next();
     System.out.println("Customer Details");
     System.out.println("License Plate");
     System.out.println("Service ID");
     System.out.println("Service Date");
     System.out.println("Service Type");
     System.out.println("Service Details");
     System.out.println("Menu");
     System.out.println("1. Pick a service");
     System.out.println("2. Go back");
     int option = scanner.nextInt();
     switch(option) {
     case 1: System.out.println("Choose one of the Service IDs to reschedule the service");
             String sid=scanner.next();
             rescheduleservice2(sid); break;
     
     case 2: landingPage();break;
     default: System.out.println("Invalid option");System.exit(0);
     }
    
    }
    //Receptionist: Reschedule Service
    //TODO: Write a query to identify 2 available dates based on the service id  and display them.
    //TODO: Write a query to reschedule the service date based on the choice of the customer.
    private void rescheduleservice2(String sid)throws Exception{
    	System.out.println("The two available dates for this service ID are:");
    	System.out.println("Menu");
    	System.out.println("1. Reschedule Date");
    	System.out.println("2. Go Back");
    	int option=scanner.nextInt();
    	switch(option)
    	{
    	case 1: System.out.println("Choose the date that you want to reschedule your service");
    	      String date=scanner.next();  
    	      System.out.println(" Your Service has been successfully rescheduled");
    	      landingPage(); break;
    	      
    	case 2: rescheduleService(); break;  
    	default: System.out.println("Invalid Option");System.exit(0);
    	      
    	}
    	
    }
//Receptionist: Invoices
 //TODO: Write a query to display service id, service start, service end, licenseplate,service type, mechnanic name, parts used in service with cost for each part, total labor hours, labor wages per hour and total service cost. 
    private void invoices() throws Exception {
     System.out.println("Enter the customer email address");
     String mail=scanner.next();
     
     
     
     String scId = getScId();
     System.out.println(scId);
     String customer_id = "";
     Statement sql = Application.conn.createStatement();
     ResultSet rr = null;
     Application.rs = Application.stmt.executeQuery("select customer_id from customer where email = '"+mail+"'");
     while (Application.rs.next())
     {
    	 customer_id = Application.rs.getString("customer_id");
     }
     rr = sql.executeQuery("select service_id from services");
     while (rr.next()) {
         String serviceID = rr.getString("service_id");
         
         String chk = "select c.sc_id from servicereln s, customer c where s.customer_id = c.customer_id and s.service_id = '"+serviceID+"' and c.customer_id = "+customer_id;
         Application.rs = Application.stmt.executeQuery(chk);
         //System.out.println(chk);
         while(Application.rs.next())
         {
             chk = Application.rs.getString("sc_id");
         }
         if(chk.equalsIgnoreCase(scId)==false)
         {
             //System.out.println(serviceID+" not part of this service center!");
             continue;
         }
         System.out.println(serviceID);
         Application.rs = Application.stmt.executeQuery("select license_no from servicereln where service_id=" + serviceID+" and customer_id = "+customer_id);
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
     
     }
     
     System.out.println("\n \n Menu");
     System.out.println("1. Go Back");
     int option = scanner.nextInt();
     switch(option)
     { case 1: landingPage(); break;
       default: System.out.println("Invalid option"); break;
          
     }
    
     
     
     
     
    }
//Receptionist: Daily Task-Update Inventory
 // TODO: Run a task to update the counts of parts to be used that day, basically adjusted to reflect the fact the parts will be removed and actually used that day.   
    private void updateInventory() throws Exception {
    	System.out.println("Running Update Inventory Task");
    	//System.out.println("Enter date :");
    	//String date = scanner.next();
    	long millis=System.currentTimeMillis();  
    	java.sql.Date date=new java.sql.Date(millis);
    	System.out.println(date);
    	HashMap<String, Integer> parts = new HashMap<>();
    	int result  = 0;
    	Application.rs = Application.stmt.executeQuery("SELECT r.part_id,r.quantity from requires r,timeslot t where r.service_id = t.service_id and t.service_date = TO_DATE('"+date+"','YYYY-MM-DD')");
		while (Application.rs.next()) {
		    String s = Application.rs.getString("part_id");
		    int qty = Application.rs.getInt("quantity");
		    if (parts.containsKey(s))  
	        { 
	            Integer a = parts.get(s); 
	            parts.put(s,a+qty);
	        }
		    else
		    	parts.put(s, qty);
		}
		System.out.println(parts);
		for(String key : parts.keySet())
		{
			Application.rs = Application.stmt.executeQuery("SELECT current_qty FROM inventory WHERE part_id = "+key);
			while(Application.rs.next())
			{
				int current_qty = Application.rs.getInt("current_qty");
				result = Application.stmt.executeUpdate("UPDATE inventory SET current_qty = "+String.valueOf(current_qty - parts.get(key))+" WHERE part_id = "+key);
			
			}
			if(result > 0)
				System.out.println("Status of the task: Success");
			
		}
    	
    	
    	
    	System.out.println("\nMenu");
    	System.out.println("1. Go back");
    	int choice=scanner.nextInt();
    	switch(choice) {
    	case 1: landingPage(); 
    			break;
    	default: System.out.println("Invalid Option"); 
    			 System.exit(0);
    	}

    }
// Receptionist: Daily Task-Record Deliveries
    // TODO: Run a task to update the status of any pending orders
    private void recordDeliveries() throws Exception {
        System.out.println("\n Menu");
    	System.out.println("1. Enter the order ID CSV");
    	System.out.println("2.Go Back");
    	int option=scanner.nextInt();
    	Statement sql = Application.conn.createStatement();
        ResultSet rr = null;
    	switch(option) {
    	case 1: System.out.println("Enter the how many order IDs that needs to be marked as delivered" );
    	        int count=scanner.nextInt(),completed = 0,completed2 = 0;
    	        int orderids[]=new int[count];
    	        System.out.println(" Now provide the order IDs");
    	        for(int i=0;i<count;i++)
    	        {
    	        	orderids[i]=scanner.nextInt();
    	        	//System.out.println("UPDATE orders SET order_status = 'Completed' WHERE order_id = '"+orderids[i]+"'");
    	        	completed = Application.stmt.executeUpdate("UPDATE orders SET order_status = 'Completed' WHERE order_id = '"+orderids[i]+"' and order_status = 'Pending'");
    	        	if(completed>0)
    	        	{
    	        		Calendar cal = Calendar.getInstance();
    	        		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    	        		//System.out.println(cal.getTime());
    	        		// Output "Wed Sep 26 14:23:28 EST 2012"

    	        		String formatted = format1.format(cal.getTime());
    	        		System.out.println(formatted);
    	        		System.out.println("UPDATE orders SET actual_delivery_date = TO_DATE('"+formatted+"','YYYY-MM-DD') where order_id = '"+orderids[i]+"'");
    	        		completed2 = Application.stmt.executeUpdate("UPDATE orders SET actual_delivery_date = TO_DATE('"+formatted+"','YYYY-MM-DD') where order_id = '"+orderids[i]+"'");
    	        	}
    	        		
    	        }
    	        System.out.println("Running the task to update the pending orders");
    	        if(completed > 0 && completed2>0)
    	        {
    	        	System.out.println("Status of the task: Success");
    	        	
    	        }
    	        else
    	        	System.out.println("The order ID is invalid or has already been completed");
    	        Application.rs = Application.stmt.executeQuery("select s.part_id,s.delivery_time,o.order_date,o.order_id,o.qty from orders o, supplied_by s where o.part_id = s.part_id and o.order_status = 'Pending'");
    	        while(Application.rs.next())
    	        {
    	        	int days = Application.rs.getInt("delivery_time");
    	        	//String qty = Application.rs.getString("qty");
    	        	String order_id = Application.rs.getString("order_id");
    	        	java.sql.Date order_date = Application.rs.getDate("order_date");
    	        	long millis=System.currentTimeMillis();  
    	        	java.sql.Date currentdate=new java.sql.Date(millis); 
    	        	Calendar c = Calendar.getInstance();
    	        	c.setTime(order_date);
    	        	c.add(Calendar.DATE,days);
    	        	java.sql.Date expected_date = new java.sql.Date(c.getTimeInMillis());
    	        	if(currentdate.after(expected_date))
    	        	{
    	        		completed = sql.executeUpdate("UPDATE orders SET order_status = 'Delayed' where order_status = 'Pending' and order_id = '"+order_id+"'");
    	        		if(completed > 0)
    	        			System.out.println("Updated successfully");
    	        		rr=sql.executeQuery("SELECT pt.sc_id,p.part_name,r.distributor_id,o.qty from orders o, placed_to pt,parts p,received_from r where o.order_id = '"+order_id+"' and o.order_status = 'Delayed' and pt.order_id = o.order_id and o.part_id = p.part_id and o.order_id = r.order_id");
    	        		while(rr.next())
    	        		{
    	        			String sc_id = rr.getString("sc_id");
    	        			String part_name = rr.getString("part_name");
    	        			String distributor_id = rr.getString("distributor_id");
    	        			String qty = rr.getString("qty");
    	        			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    	        			String formatted = format1.format(currentdate);
    	        			System.out.println(formatted+", "+expected_date);
    	        			int diff_days = currentdate.getDate() - expected_date.getDate();
    	        			String message = "'"+qty+" "+part_name+" from "+distributor_id+" delayed by "+diff_days+" business days'";
    	        			//System.out.println(message);
    	        			int completed3 =0;
    	        			String sqlStmt  = "Insert into notification(notification_id,notif_date,sc_id,message) values(NOTIFICATION_ID_SEQUENCE.NEXTVAL,TO_DATE('"+formatted+"','YYYY-MM-DD'),'"+sc_id+"',"+message+")";
    	        			System.out.println(sqlStmt);
    	        			completed3 = sql.executeUpdate("Insert into notification(notification_id,notif_date,sc_id,message) values(NOTIFICATION_ID_SEQUENCE.NEXTVAL,TO_DATE('"+formatted+"','YYYY-MM-DD'),'"+sc_id+"',"+message+")");
    	        			if(completed3>0)
    	        				System.out.println("Success");
    	        		}
    	        	}
    	        }
    	        	
    	        landingPage();
    	        break;
    	case 2: landingPage();
    			break;
    	default: System.out.println("Invalid option");
    			 System.exit(0);
    	
    	   
    	}
    	}
    //Validate the register data from the receptionist
    private boolean validateRegisterCarData(String email, String licenseplate, String Pdate, String make, String model,String year, int currentMileage, String lastServiceDate, String lastservicetype) throws Exception {
        boolean valid = false;
        valid = Pattern.matches("[A-Z]{1,2}-[0-9]{1,4}",licenseplate);
        if(!valid)
        	System.out.println("Please enter valid license plate number");
        if(licenseplate == null || licenseplate.length()==0||licenseplate.isEmpty()) {
            System.out.println("license cannot be empty."); 
	    }
        else 
        	valid = valid&&true;
       
        if(Pdate==null || Pdate.isEmpty())
        {	
        	System.out.println("Purchase Date cannot be Empty"); 
        	valid = false;
        }
        else
        	valid = valid&&true;
        if (make==null || make.isEmpty())
        {
        	System.out.println("Make Cannot be Empty");  
        	valid = false;
        }
        if(make.equalsIgnoreCase("Honda")|| make.equalsIgnoreCase("Toyota")||make.equalsIgnoreCase("Nissan"))
        {
        	valid = valid&&true;
        }
        else
        {
        	System.out.println("Make of the car needs to be either Honda or Toyota or Nissan");
        	valid = false;
        }
        if(model==null||model.isEmpty())
        { 
        	System.out.println("Model Cannot be Empty"); 
       	   	valid = false;
        }
        else 
        	valid &= true;
        if(year==null||year.isEmpty())
        {
        	System.out.println("Year Cannot be Empty"); 
        	valid = false;
        }
        else
        	valid = valid&&true;
        if(Integer.toString(currentMileage)==null||Integer.toString(currentMileage).isEmpty())
        {
     	  System.out.println("Current Mileage Cannot be Empty");
     	  valid = false;
        }
        else
        	valid = valid&&true;
        boolean emailValid = validateemail(email);
        return (valid&&emailValid);
    }
    //TODO: Register the car into the database
    private int registerCar(String email,String licenseplate, String Pdate, String make, String model, String year, int currentMileage, String lastServiceDate, String lastservicetype) throws Exception {
        int registered = 0;
        String cust_id = "";
        Application.rs = Application.stmt.executeQuery("SELECT customer_id FROM customer WHERE email = '"+email+"'");
        while(Application.rs.next())
        {
        	 cust_id = Application.rs.getString("customer_id");
        }
        System.out.println("INSERT INTO CAR (LICENSE_NO,CAR_MAKE,CAR_MODEL,CAR_YEAR,DATE_OF_PURCHASE,LAST_RECORDED_MILEAGE,RECENT_SERVICE_TYPE,RECENT_SERVICE_DATE,CUSTOMER_ID) " + "VALUES ('"+licenseplate+"','"+make+"','"+model+"','"+year+"',TO_DATE('"+Pdate+"','MM-DD-YYYY'),"+currentMileage+",'"+lastservicetype+"',TO_DATE('"+lastServiceDate+"','MM-DD-YYYY'),"+cust_id+");");
        registered = Application.stmt.executeUpdate("INSERT INTO CAR (LICENSE_NO,CAR_MAKE,CAR_MODEL,CAR_YEAR,DATE_OF_PURCHASE,LAST_RECORDED_MILEAGE,RECENT_SERVICE_TYPE,RECENT_SERVICE_DATE,CUSTOMER_ID) " + "VALUES ('"+licenseplate+"','"+make+"','"+model+"','"+year+"',TO_DATE('"+Pdate+"','MM-DD-YYYY'),"+currentMileage+",'"+lastservicetype+"',TO_DATE('"+lastServiceDate+"','MM-DD-YYYY'),"+cust_id+")");
        return registered;
    }
    //TODO Display details of Service history of the customer based on the email-address
    private boolean DisplayDetails(String email) throws Exception {
    	boolean valid =true;
    	String id = "";
    	Application.rs=Application.stmt.executeQuery("select customer_id from customer where email = '"+email+"'");
    	while(Application.rs.next())
    	{
    		id = Application.rs.getString("customer_id");
    		System.out.println(id);
    	}
        Application.rs=Application.stmt.executeQuery("select employee.emp_name,servicereln.license_no,servicereln.service_id,repair.repair_id,timeslot.mechanic_id,timeslot.service_date,timeslot.service_time,timeslot.end_date,timeslot.end_time from employee,repair,servicereln,timeslot,mechanic where servicereln.customer_id='"+id+"' AND timeslot.service_id=servicereln.service_id AND servicereln.service_id = repair.service_id AND timeslot.mechanic_id= mechanic.mechanic_id AND mechanic.emp_id=employee.emp_id");      
        
        System.out.println("The customer service History:");
        while (Application.rs.next()) 
        {
        
        	String mechanic_name = Application.rs.getString("emp_name");
  		    String license = Application.rs.getString("license_no");
  		    String service_id = Application.rs.getString("service_id");
  		    String repair_id = Application.rs.getString("repair_id");
  		    String mechanic_id = Application.rs.getString("mechanic_id");
  		    Date start_date = Application.rs.getDate("service_date");
  		    String start_time=Application.rs.getString("service_time");
  		    Date end_date=Application.rs.getDate("end_date");
  		    String end_time=Application.rs.getString("end_time");
  		    System.out.println("The Customer ID is:" +id);
  		    System.out.println("The Service ID for the service is:" +service_id);
  		    System.out.println("The License Plate for the car in this service is:" +license);
  		    System.out.println("The Service Type involved in this Service is Repair");
  		    System.out.println("The Mechanic Involved in this service is: " +mechanic_name);
  		    System.out.println("This Service Started on: "+start_date);
  		    System.out.println("Start Time:"+start_time);
  		  
  		    if(end_time== null || end_time.isEmpty())
  		    {
  		    	System.out.println(" The Status of this Service is still ongoing");
  		    }
  		    else
  		    	System.out.println("This service is completed and ended on"+end_date+" end time" +end_time);
  		    System.out.println("\n\n\n");
        }
        Application.rs=Application.stmt.executeQuery("select employee.emp_name,servicereln.license_no,servicereln.service_id,maintenance.maintenance_id,timeslot.mechanic_id,timeslot.service_date,timeslot.service_time,timeslot.end_date,timeslot.end_time from employee, mechanic,maintenance,servicereln,timeslot where servicereln.customer_id='"+id+"' AND timeslot.service_id=servicereln.service_id AND servicereln.service_id = maintenance.service_id AND timeslot.mechanic_id=mechanic.mechanic_id AND mechanic.emp_id=employee.emp_id");
  		while(Application.rs.next())
  		{
  			 String mechanic_name1 = Application.rs.getString("emp_name");
  			 String license1 = Application.rs.getString("license_no");
  			 String service_id1 = Application.rs.getString("service_id");
  			 String maintenance_id1 = Application.rs.getString("maintenance_id");
  			 String mechanic_id1 = Application.rs.getString("mechanic_id");
  			 Date start_date1 = Application.rs.getDate("service_date");
  			 String start_time1=Application.rs.getString("service_time");
  			 Date end_date1=Application.rs.getDate("end_date");
  			 String end_time1=Application.rs.getString("end_time");
  			 System.out.println("The Customer ID is:" +id);
  			 System.out.println("The Service ID for the service is:" +service_id1);
  			 System.out.println("The License Plate for the car in this service is:" +license1);
  			 System.out.println("The Service Type involved in this Service is Maintenance");
  			 System.out.println("The Mechanic Involved in this service is: " +mechanic_name1);
  			 System.out.println("This Service Started on: "+start_date1);
  			 System.out.println("Start Time:"+start_time1);
  			 if(end_time1== null || end_time1.isEmpty())
  			 {
  				 System.out.println(" The Status of this Service is still ongoing");
  			 }
  			 else
  				 System.out.println("This service is completed and ended on"+end_date1+" end time" +end_time1);
  			 System.out.println("\n\n\n");
  		}
    	return valid;
      }
    // TODO Validate email of the user
    private boolean validateemail(String email) throws Exception{
    	String regex = "^(.+)@(.+)$";
    	Pattern pattern = Pattern.compile(regex);
    	if(email!=null)
    	{
    		Matcher matcher = pattern.matcher(email);
        	//System.out.println(matcher.matches());
        	if(matcher.matches())
        		return true;
        	else 
        	{
        		System.out.println("Please enter valid email ID");
        		return false;
        	}
        		
    	}
    	return false;
    }
    //TODO Validate Service Request, here mechanic name is optional
    private boolean validateservicerequest(String email, String licenseplate, int currentmileage, String mechanicname) throws Exception{
    	boolean valid=false;
    	boolean emailValid = validateemail(email);
    	valid = Pattern.matches("[A-Z]{1,2}-[0-9]{1,4}",licenseplate);
        if(!valid)
        	System.out.println("Please enter valid license plate number");
        if(Integer.toString(currentmileage)==null||Integer.toString(currentmileage).isEmpty())
        {
        	valid = false;
        	System.out.println("Current mileage cannot be empty");
        }	
        else
        	valid = (valid&&true);
    	return (valid&&emailValid);
    }
    
    private String getScId() throws Exception
    {
        String scId = "";
        String ReceptionistId="";

        String getReceptionistId = "select receptionist_id from receptionist where emp_id = '"+Application.currentUser.userID+"'";
        Application.rs = Application.stmt.executeQuery(getReceptionistId);
        while( Application.rs.next() )
        {
            ReceptionistId = Application.rs.getString("receptionist_id");
            // System.out.println(recCount+" is the count");
        }


        String getScId = "select sc_id from receptionistAt where receptionist_id = '"+ReceptionistId+"'";
        Application.rs = Application.stmt.executeQuery(getScId);
        while( Application.rs.next() )
        {
            scId = Application.rs.getString("sc_id");
            // System.out.println(recCount+" is the count");
        }
        return scId;
    }
    
    
    
    
}
