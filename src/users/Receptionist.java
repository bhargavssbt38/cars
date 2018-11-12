package users;

import app.Application;
import pages.DisplayPage;

import java.sql.ResultSet;
import java.util.*;

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
                } else {
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
     System.out.println("The Service ID");
     System.out.println("Service Start Date/Time");
     System.out.println("Service End Date/Time");
     System.out.println("License Plate");
     System.out.println("Service Type");
     System.out.println("Mechanic Name");
     System.out.println("Parts Used in service with cost of each part");
     System.out.println("Total labor hours");
     System.out.println("Labor wages per hour");
     System.out.println("Total service cost");
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
    	System.out.println("Enter date :");
    	String date = scanner.next();
    	HashMap<String, Integer> parts = new HashMap<>();
    	int result  = 0;
    	Application.rs = Application.stmt.executeQuery("SELECT r.part_id,r.quantity from requires r,timeslot t where r.service_id = t.service_id and t.service_date = TO_DATE('"+date+"','MM-DD-YYYY')");
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
    	switch(option) {
    	case 1: System.out.println("Enter the how many order IDs that needs to be marked as delivered" );
    	        int count=scanner.nextInt(),completed = 0;
    	        int orderids[]=new int[count];
    	        System.out.println(" Now provide the order IDs");
    	        for(int i=0;i<count;i++)
    	        {
    	        	orderids[i]=scanner.nextInt();
    	        	System.out.println("UPDATE orders SET order_status = 'Completed' WHERE order_id = '"+orderids[i]+"'");
    	        	completed = Application.stmt.executeUpdate("UPDATE orders SET order_status = 'Completed' WHERE order_id = '"+orderids[i]+"'");
    	        }
    	        System.out.println("Running the task to update the pending orders");
    	        if(completed > 0)
    	        	System.out.println("Status of the task: Success"); 
    	        break;
    	case 2: landingPage();
    			break;
    	default: System.out.println("Invalid option");
    			 System.exit(0);
    	
    	   
    	}
    	}
    //Validate the register data from the receptionist
    private boolean validateRegisterCarData(String email, String licenseplate, String Pdate, String make, String model,String year, int currentMileage, String lastServiceDate, String lastservicetype) throws Exception {
        boolean valid = true;

        return valid;
    }
    //TODO: Register the car into the database
    private int registerCar(String email,String licenseplate, String Pdate, String make, String model, String year, int currentMileage, String lastServiceDate, String lastservicetype) throws Exception {
        int registered = 0;
        System.out.println("INSERT INTO CAR (LICENSE_NO,CAR_MAKE,CAR_MODEL,CAR_YEAR,DATE_OF_PURCHASE,LAST_RECORDED_MILEAGE,RECENT_SERVICE_TYPE,RECENT_SERVICE_DATE) " + "VALUES ('"+licenseplate+"','"+make+"','"+model+"','"+year+"',TO_DATE('"+Pdate+"','MM-DD-YYYY'),"+currentMileage+",'"+lastservicetype+"',TO_DATE('"+lastServiceDate+"','MM-DD-YYYY'));");
        registered = Application.stmt.executeUpdate("INSERT INTO CAR (LICENSE_NO,CAR_MAKE,CAR_MODEL,CAR_YEAR,DATE_OF_PURCHASE,LAST_RECORDED_MILEAGE,RECENT_SERVICE_TYPE,RECENT_SERVICE_DATE) " + "VALUES ('"+licenseplate+"','"+make+"','"+model+"','"+year+"',TO_DATE('"+Pdate+"','MM-DD-YYYY'),"+currentMileage+",'"+lastservicetype+"',TO_DATE('"+lastServiceDate+"','MM-DD-YYYY'))");
        return registered;
    }
    //TODO Display details of Service history of the customer based on the email-address
    private boolean DisplayDetails(String email) throws Exception {
    	boolean valid =false;
    	
    	return valid;
      }
    // TODO Validate email of the user
    private boolean validateemail(String email) throws Exception{
    	boolean valid=false;
    	
    	return valid;
    }
    //TODO Validate Service Request, here mechanic name is optional
    private boolean validateservicerequest(String email, String licenseplate, int currentmileage, String mechanicname) throws Exception{
    	boolean valid=false;
    	return valid;
    }
}
