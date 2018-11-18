package users;

import app.*;
import pages.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Pattern;

public class Customer extends User {

    static Scanner scanner = new Scanner(System.in);
    public static ResultSet rs2 = null;
    public static Statement stmt2 = null;
    public Customer(String userID) {
        super(userID, "customer");
    }

    public void landingPage() throws Exception {
        System.out.println("\nCUSTOMER LANDING PAGE:");
        System.out.println("\t1. Profile");
        System.out.println("\t2. Register Car");
        System.out.println("\t3. Service");
        System.out.println("\t4. Invoices");
        System.out.println("\t5. Logout");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                profileMenu();
                break;
            case 2:
                registerCarMenu();
                break;
            case 3:
                serviceMenu();
                break;
            case 4:
                invoicesMenu();
                break;
            case 5:
                Application.currentUser = null;
                DisplayPage.homePage();
                break;
            default:
                System.out.println("Invalid option");
                System.exit(0);
        }
    }

    private void profileMenu() throws Exception {
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
                landingPage();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private void registerCarMenu() throws Exception {
    	String id=Application.currentUser.userID;
    	System.out.println("\nREGISTER CAR:");
        String a=scanner.nextLine();
        System.out.println("A. Enter license plate: ");
        String licensePlate = scanner.nextLine();
        System.out.println("B. Enter purchase date: ");
        String purchaseDate = scanner.nextLine();
        System.out.println("C. Enter make: ");
        String make = scanner.nextLine();
        System.out.println("D. Enter model: ");
        String model = scanner.nextLine();
        System.out.println("E. Enter year: ");
        String year = scanner.nextLine();
        System.out.println("F. Enter current mileage: ");
        String currentMileage = scanner.nextLine();
        System.out.println("G. Enter last service date: ");
        String lastServiceDate = scanner.nextLine();
        System.out.println("Enter the recent service type");
        String recent_service_type=scanner.nextLine();
        System.out.println("\nMENU:");
        System.out.println("\t1. Register");
        System.out.println("\t2. Cancel");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option)
        {
        case 1: if(validatelicenseplate(licensePlate))
               {if(licensePlate == null || licensePlate.length()==0||licensePlate.isEmpty()) {
                   System.out.println("license cannot be empty."); registerCarMenu();
       	              }
                  licensePlate=licensePlate;
                 } else {
                	 System.out.println("Invalid License plate format, Please enter Correct information"); registerCarMenu(); 
                 }
              if(purchaseDate==null || purchaseDate.isEmpty())
              {
            	  System.out.println("Purchase Date cannot be Empty"); registerCarMenu();
            	   }
              if (make==null || make.isEmpty())
              {
            	  System.out.println("Make Cannot be Empty"); registerCarMenu();
            	  
              }
              if(make.equalsIgnoreCase("Honda")|| make.equalsIgnoreCase("Toyota")||make.equalsIgnoreCase("Nissan"))
              {
            	  make=make;
              }
              else
              {System.out.println("Make of the car needs to be either Honda or Toyota or Nissan");
              
              }
              if(model==null||model.isEmpty())
              { System.out.println("Model Cannot be Empty"); registerCarMenu();
              
              }
              if(year==null||year.isEmpty())
              {
            	  System.out.println("Year Cannot be Empty"); registerCarMenu();
              }
              if(currentMileage==null||currentMileage.isEmpty())
              {
            	  System.out.println("Current Mileage Cannot be Empty");
              }
             
             int  mileage=Integer.parseInt(currentMileage);
             Application.stmt.executeUpdate("INSERT INTO \"ARAVICH7\".\"CAR\" (LICENSE_NO, CAR_MAKE, CAR_MODEL, CAR_YEAR, DATE_OF_PURCHASE, LAST_RECORDED_MILEAGE, RECENT_SERVICE_TYPE, Recent_Service_Date, CUSTOMER_ID) VALUES ('"+licensePlate+"', '"+make+"', '"+model+"', '"+year+"',TO_DATE('"+purchaseDate+"', 'YYYY-MM-DD HH24:MI:SS'), '"+mileage+"', '"+recent_service_type+"', '"+lastServiceDate+"','"+id+"')");
        	 System.out.println("Car Registered Successfully");  
             landingPage(); break;
        case 2: landingPage(); break;
        default: System.out.println("Invalid Option"); landingPage(); break;
        
        }
       
        }
    
    public static boolean validatelicenseplate(String license)
    {
    	return Pattern.matches("[A-Z]{1,3}-[A-Z][0-9]{1,4}",license);
    }     

    // Customer: Service
    private void serviceMenu() throws Exception {
        System.out.println("\nSERVICE MENU:");
        System.out.println("\t1. View Service History");
        System.out.println("\t2. Schedule Service");
        System.out.println("\t3. Reschedule Service");
        System.out.println("\t4. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                viewServiceHistory();
                break;
            case 2:
                scheduleService();
                break;
            case 3:
                rescheduleService();
                break;
            case 4:
                landingPage();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // Customer: Invoice
    private void invoicesMenu() throws Exception {
        System.out.println("\nINVOICES:");
        // TODO: display details for all services that are complete
        displayCompletedServiceDetails();
        System.out.println("\nMENU:");
        System.out.println("\t1. View Invoice Details");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                viewInvoiceDetails();
                break;
            case 2:
                landingPage();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // Customer: View Invoice Details
    private void viewInvoiceDetails() throws Exception {
        System.out.println("\nVIEW INVOICE DETAILS:");
        System.out.println("Enter service ID for services, which are complete ");
        int serviceID = scanner.nextInt();
        // TODO: display service details for this ID
        showServiceDetailsForID(serviceID);
        System.out.println("\nMENU:");
        System.out.println("\t1. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                invoicesMenu();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // (For Customer: View Invoice Details)
    // TODO: query db & display following details for given serviceID
    //A. Service ID
    //B. Service Start Date/Time
    //C. Service End Date/Time
    //D. Licence Plate
    //E. Service Type
    //F. Mechanic Name
    //G. Parts Used in service with cost of each part
    //H. Total labor hours
    //I. Labor wages per hour
    //J. Total Service Cost
    private void showServiceDetailsForID(int serviceID) throws Exception {
    	String id=Application.currentUser.userID;
    	Application.rs=Application.stmt.executeQuery("select car_model,car_make from car where customer_id='"+id+"'");
    	String car_make = null,car_model=null;
    	while(Application.rs.next())
    	{
    		car_make=Application.rs.getString("car_make");
            car_model=Application.rs.getString("car_model");
    	}
    	String make=car_make.toLowerCase();
    	String model=car_model.toLowerCase();
    	Application.rs=Application.stmt.executeQuery("select lookup_id from service_type_lookup where car_make='"+make+"' AND car_model='"+model+"'");
    	System.out.println("The car make"+(make));
    	System.out.println("The car model"+(model));
    	
    	int lookup_id=0;
    	while(Application.rs.next())
    	{
    		lookup_id=Application.rs.getInt("lookup_id");
    	}
    	System.out.println("The lookup ID for this service is"+lookup_id);
    	Application.rs=Application.stmt.executeQuery("select maintenance_type from maintenance where service_id="+serviceID);
    	String type=null;
    	while(Application.rs.next())
    	{type=Application.rs.getString("maintenance_type");
    		
    	}
    List<Integer>bid=new ArrayList();
    	Application.rs=Application.stmt.executeQuery("select basic_service_id from service_type_services where service_type='"+type+"'AND lookup_id="+lookup_id);
    	int basicid=0;
    	while(Application.rs.next())
    	{basicid=Application.rs.getInt("basic_service_id");
    	 bid.add(basicid);
    		
    	}
    	
     List<Integer>part=new ArrayList();
     List<Integer>quantity=new ArrayList();
     
    	for(int i=0;i<bid.size();i++)
    	{ Application.rs=Application.stmt.executeQuery("select part_id,quantity from basic_services_parts where basic_service_id="+bid.get(i)+"AND car_make='"+car_make+"' AND car_model='"+car_model+"'");
    	  int parts=0,count=0;
    	  while(Application.rs.next())
    	  {
    		  parts=Application.rs.getInt("part_id");
    		  count=Application.rs.getInt("quantity");
    		  part.add(parts);
    		  quantity.add(count);
    	  }
    	  
    		
    	}
    	
    	List<String>servicename=new ArrayList();
    	List<String>rate=new ArrayList();
    	List<Float>hours=new ArrayList();
    	for(int k=0;k<bid.size();k++)
    	{
    	Application.rs=Application.stmt.executeQuery("select service_name,rate,time_hours from basic_services where basic_service_id="+bid.get(k));
    	while(Application.rs.next())
    	{
    	String sname=Application.rs.getString("service_name");
    	String cost=Application.rs.getString("rate");
    	float time=Application.rs.getFloat("time_hours");
    	servicename.add(sname);
    	rate.add(cost);
    	hours.add(time);
    	}
    	}
    	
    	float totalcost;
    	List<Float>partcost=new ArrayList();
    	List<Integer>warranty=new ArrayList();
    	float pcost=0;
    	int months=0;
    	for(int a=0;a<part.size();a++)
    	{
    	Application.rs=Application.stmt.executeQuery("select warranty,price from parts where part_id="+part.get(a));
    	 while(Application.rs.next())
    	 {
    		 pcost=Application.rs.getFloat("price");
    		 months=Application.rs.getInt("warranty");
    		 partcost.add(pcost);
    		 warranty.add(months);
    	 }
    	}
    	System.out.println("The basic service ID, Service Name, Part_id, quantity, Rate of labor, hours required for this service");
    	for(int j=0;j<bid.size();j++)
    	{
    		System.out.print("\t"+bid.get(j));
    		System.out.print("\t"+servicename.get(j));
    		System.out.print("\t"+part.get(j));
    		System.out.print("\t"+quantity.get(j));
    		System.out.print("\t"+rate.get(j));
    		System.out.print("\t"+hours.get(j));
    		System.out.print("\t" );
    		
    		System.out.println(" ");
    	}
    	Application.rs=Application.stmt.executeQuery("select employee.emp_name,servicereln.license_no,servicereln.service_id,repair.actual_fees,repair.diagnostic_fees,repair.repair_id,timeslot.mechanic_id,timeslot.service_date,timeslot.service_time,timeslot.end_date,timeslot.end_time from employee,repair,servicereln,timeslot,mechanic where servicereln.service_id='"+serviceID+"' AND timeslot.service_id=servicereln.service_id AND servicereln.service_id = repair.service_id AND timeslot.mechanic_id= mechanic.mechanic_id AND mechanic.emp_id=employee.emp_id");
        
       while (Application.rs.next()) {
        
        	String mechanic_name = Application.rs.getString("emp_name");
  		    String license = Application.rs.getString("license_no");
  		    String service_id = Application.rs.getString("service_id");
  		    String repair_id = Application.rs.getString("repair_id");
  		    String mechanic_id = Application.rs.getString("mechanic_id");
  		    Float actual_fees=Application.rs.getFloat("actual_fees");
  		    Float diagnostic_fees=Application.rs.getFloat("diagnostic_fees");
  		    Float totalfees=actual_fees+diagnostic_fees;
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
  		  	  System.out.println("The total service cost involved in this service:"+totalfees);
  		  
  		  
  		 System.out.println("\n\n\n");
  		 
  		 		 
  		 
        }
        Application.rs=Application.stmt.executeQuery("select employee.emp_name,servicereln.license_no,servicereln.service_id,maintenance.maintenance_id,timeslot.mechanic_id,timeslot.service_date,timeslot.service_time,timeslot.end_date,timeslot.end_time from employee, mechanic,maintenance,servicereln,timeslot where servicereln.service_id='"+serviceID+"' AND timeslot.service_id=servicereln.service_id AND servicereln.service_id = maintenance.service_id AND timeslot.mechanic_id=mechanic.mechanic_id AND mechanic.emp_id=employee.emp_id");
  		 while(Application.rs.next())
  		 {String mechanic_name1 = Application.rs.getString("emp_name");
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

    	
    }
    	
    	
	/*List<Integer> servicesIDs = new ArrayList<>();  
	Application.rs=Application.stmt.executeQuery("select service_id from servicereln where customer_id='"+id+"'" );
	while(Application.rs.next())
	{int serviceid=Application.rs.getInt("service_id");
	 servicesIDs.add(serviceid);
	}
	List<String>mtype=new ArrayList<>();
for (int i=0;i<servicesIDs.size();i++)
{
	String s=" select maintenance_type from maintenance where service_id=";
	s+=servicesIDs.get(i);
	System.out.println(""+s);
	Application.rs=Application.stmt.executeQuery(s);
	String type=Application.rs.getString("maintenance_type");
	if (Application.rs.wasNull())
	   { System.out.println("Null Value got" );
   mtype.add("null");
	break;
 }else
	while (Application.rs.next())
	{ 
	
       	 System.out.println(type);
    	 mtype.add(type);
     
}
}
System.out.println("Service IDs");
for(int j=0;j<servicesIDs.size();j++)
{System.out.println(" "+servicesIDs.get(j));
	
}
System.out.println("Service Types");
for(int k=0;k<servicesIDs.size();k++)
{System.out.println(" "+mtype.get(k));
	
}
}*/
	
	
	
	
	
	
/*    Application.rs=Application.stmt.executeQuery("select employee.emp_name,servicereln.license_no,servicereln.service_id,requires.quantity,repair.actual_fees,repair.diagnostic_fees,repair.repair_id,timeslot.mechanic_id,timeslot.service_date,timeslot.service_time,timeslot.end_date,timeslot.end_time,requires.part_id,parts.price,parts.warranty from parts,employee,repair, requires,servicereln,timeslot,mechanic where servicereln.customer_id='"+id+"' AND timeslot.service_id=servicereln.service_id AND servicereln.service_id = repair.service_id AND timeslot.mechanic_id= mechanic.mechanic_id AND mechanic.emp_id=employee.emp_id AND servicereln.service_id=requires.service_id AND requires.part_id=parts.part_id");
   
    System.out.println("The customer Invoice History:");
    
    
    while (Application.rs.next()) {
    
    	String mechanic_name = Application.rs.getString("emp_name");
		    String license = Application.rs.getString("license_no");
		    String service_id = Application.rs.getString("service_id");
		    float actual_fees = Application.rs.getFloat("actual_fees");
		    float diagonstic_fees = Application.rs.getFloat("diagnostic_fees");
		    float parts_price=Application.rs.getFloat("price");
		    int warranty=Application.rs.getInt("warranty");
		    int quantity=Application.rs.getInt("quantity");
		    Date start_date = Application.rs.getDate("service_date");
		    String start_time=Application.rs.getString("service_time");
		    Date end_date=Application.rs.getDate("end_date");
		    String end_time=Application.rs.getString("end_time");
		    float total_fees=actual_fees+diagonstic_fees;
		    if(warranty==0)
		    {float parts_fees=quantity*parts_price;
		      total_fees=total_fees+parts_fees;
		    	  		    }
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
	  {  System.out.println("This service is completed and ended on"+end_date+" end time" +end_time);
		      System.out.println("The Total fees involved in this service is "+total_fees);
	  }
	  
	 System.out.println("\n\n\n");*/


    

    // TODO: Section - Customer: View Profile
    private void viewProfile() throws Exception {
        System.out.println("\nVIEW PROFILE:");
        // TODO: display customer details
        String id=Application.currentUser.userID;
        Application.rs = Application.stmt.executeQuery("select customer.customer_id,customer.sc_id,customer.customer_name,customer.email,customer.address,customer.telephone_no, car.license_no, car.car_make,car.car_model,car.car_year,car.customer_id, car.date_of_purchase,car.last_recorded_mileage,car.recent_service_date,recent_service_type from customer,car where customer.customer_id='"+id+"' AND customer.customer_id=car.customer_id");
    	//String Query="select customer.customer_id,customer.customer_name,customer.email,customer.address,customer.telephone_no, car.license_no, car.car_make,car.car_model,car.car_year,car.customer_id, car.date_of_purchase,car.last_recorded_mileage,car.recent_service_date,recent_service_type from customer,car where customer.email='"+customerEmail +"' AND customer.customer_id=car.customer_id";
    	//System.out.println(Query);
    	while (Application.rs.next()) {
		    int cid= Application.rs.getInt("customer_id");
		    String name = Application.rs.getString("customer_name");
		    String addr = Application.rs.getString("address");
		    String phone = Application.rs.getString("telephone_no");
		    String license_no = Application.rs.getString("license_no");
		    String email= Application.rs.getString("email");
		    String car_make = Application.rs.getString("car_make");
		    String car_year = Application.rs.getString("car_year");
		    String sc_id= Application.rs.getString("sc_id");
		    Date purchasedate=Application.rs.getDate("date_of_purchase");
		    String car_model = Application.rs.getString("car_model");
		    int mileage = Application.rs.getInt("last_recorded_mileage");
		    Date latest_service_date = Application.rs.getDate("recent_service_date");
		    String recent_service_type = Application.rs.getString("recent_service_type");
		    
		    
		    System.out.println("Customer ID: "+cid);
		    System.out.println("The Customer is registered with the service center:" +sc_id);
		    System.out.println("Customer Name: " +name);
		    System.out.println("Address: " +addr);
		    System.out.println("Phone: " +phone);
		    System.out.println("email: " +email);
		    System.out.println("\n\nCar Details");
		    System.out.println("license no: " +license_no);
		    System.out.println("Car_Make:  " +car_make);
		    System.out.println("Car Year " +car_year);
		    System.out.println("Purchase Date:" +purchasedate);
		    System.out.println("Car Model: " +car_model);
		    System.out.println("Last recorder mileage: " +mileage);
		    System.out.println("latest_service_date: " +latest_service_date);
		    System.out.println("Recent Service Type: " +recent_service_type);
    	}
        System.out.println("\nMENU:");
        System.out.println("\t1. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                profileMenu();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    	
    }

    // TODO: in all error messages for updating fields enter the rules
    // Example: Name should be 5 chars min, Phone number should only be numbers, etc.
    private void updateProfile() throws Exception {
          	
        System.out.println("\nUPDATE PROFILE:");
        System.out.println("\t1. Name");
        System.out.println("\t2. Address");
        System.out.println("\t3. Phone Number");
        System.out.println("\t4. Password");
        System.out.println("\t5. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        if(option >= 1 && option <= 4) {
            System.out.println("Enter new value: ");
            String newValue = scanner.next();
            updateProfileField(option, newValue);
            profileMenu();
        } else if(option == 5) {
            profileMenu();
        } else {
            System.out.println("Invalid option. Try again.");
            updateProfile();
        }
               
        }
    private void updateProfileField(int field, String newValue) throws Exception {
        // TODO: validate value based on field type
        // TODO: update in db
    	String id=Application.currentUser.userID;
    	if(field==1)
    	{ if(newValue.length()<5)
    		{ System.out.println("Name should be atleast 5 characters");
    		  updateProfile();
    		}
    		 
    	   else {
    		 if(isWord(newValue))
    		
    	 {Application.stmt.executeUpdate("update customer set customer_name='"+newValue+"' where customer_id="+id);
    		System.out.println("Profile Details Successfully updated into the database");
    	    
    	  }
    	else {
    		System.out.println("Invalid Data");
    		updateProfile();
    	}
    	   }
    	}
    	
    	if(field==2) { if(newValue == null || newValue.isEmpty()) {
            System.out.println("Address cannot be empty.");}
    	{Application.stmt.executeUpdate("update customer set address='"+newValue+"' where customer_id="+id);
    		System.out.println("Profile Details Successfully Updated");
    	
    	}
    	}
    	
    	if(field==3) {if(validatePhone(newValue))
    	{
    		Application.stmt.executeUpdate("update customer set telephone_no='"+newValue+"' where customer_id="+id);
    		System.out.println("Profile Details Successfully Updated");
    	}
    	else {
    		System.out.println("Invalid Data");
    	}
    		
    	}
    	if(field==4) {if(newValue.length()<8) {
    		System.out.println("Password cannot be less than 8 characters");
        	} else {Application.stmt.executeUpdate("update login set password='"+newValue+"' where id="+id);
        	System.out.println("Profile Details Updated");
        		
        	}
    		
    	}
    	}
    	
    	
    
    public static boolean validatePhone(String phone)
    {
    	return Pattern.matches("\\d{3}-\\d{3}-\\d{4}",phone);
    }
    public static boolean validateEmail(String email)
    {
    	return Pattern.matches("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$",email);
    }
    
    public static boolean isWord(String in)
    {
    	return Pattern.matches("[a-zA-z]+", in);
    }

    
    

    


    // Customer: View Service History
    // TODO: Section - Customer: View Service History
    // Query db & display details
    private void viewServiceHistory() throws Exception {
        System.out.println("\nVIEW SERVICE HISTORY:");
        // TODO: display details
        String id=Application.currentUser.userID;
        Application.rs=Application.stmt.executeQuery("select employee.emp_name,servicereln.license_no,servicereln.service_id,repair.repair_id,timeslot.mechanic_id,timeslot.service_date,timeslot.service_time,timeslot.end_date,timeslot.end_time from employee,repair,servicereln,timeslot,mechanic where servicereln.customer_id='"+id+"' AND timeslot.service_id=servicereln.service_id AND servicereln.service_id = repair.service_id AND timeslot.mechanic_id= mechanic.mechanic_id AND mechanic.emp_id=employee.emp_id");
        
        System.out.println("The customer service History:");
        
        
        while (Application.rs.next()) {
        
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
  		 {String mechanic_name1 = Application.rs.getString("emp_name");
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
  		       
        System.out.println("\nMENU:");
        System.out.println("\t1. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                serviceMenu();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // Customer: Schedule Service
    private void scheduleService() throws Exception {
        System.out.println("\nSCHEDULE SERVICE:");
        System.out.println("A. Enter license plate: ");
        String licensePlate = scanner.next();
        System.out.println("B. Enter current mileage: ");
        int currentMileage = scanner.nextInt();
        System.out.println("C. Enter mechanic name: ");
        String mechanicName = scanner.next();
        if(validateScheduleServiceData(licensePlate, currentMileage, mechanicName)) {
            System.out.println("\nMENU:");
            System.out.println("\t1. Schedule Maintenance");
            System.out.println("\t2. Schedule Repair");
            System.out.println("\t3. Go Back");
            System.out.println("Enter option: ");
            int option = scanner.nextInt();
            switch(option) {
                case 1:
                    scheduleMaintenance(licensePlate, currentMileage, mechanicName);
                    break;
                case 2:
                    scheduleRepair(licensePlate, currentMileage, mechanicName);
                    break;
                case 3:
                    serviceMenu();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } else {
            System.out.println("Enter valid input. All fields except mechanic name are required. Please try again.");
            scheduleService();
        }
    }

    // Customer: Reschedule Service (Page 1)
    private void rescheduleService() throws Exception {
        System.out.println("\nRESCHEDULE SERVICE (Page 1):");
        System.out.println("Your upcoming services:");
        displayUpcomingServices();
        System.out.println("MENU:");
        System.out.println("\t1. Pick a service");
        System.out.println("\t2. Go back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                System.out.println("Enter service ID to reschedule: ");
                int serviceID = scanner.nextInt();
                List<Date> dates = findRescheduleDate(serviceID);
                rescheduleService2(serviceID, dates);
                break;
            case 2:
                serviceMenu();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // TODO: find 2 earliest available maintenance/repair dates that are at least one day after the current service date
    // (For Customer: Reschedule Service (Page 1))
    private List<Date> findRescheduleDate(int serviceID) throws Exception {
        List<Date> dates = new ArrayList<>();

        return dates;
    }

    // Customer: Reschedule Service (Page 2)
    private void rescheduleService2(int serviceID, List<Date> dates) throws Exception {
        System.out.println("\nRESCHEDULE SERVICE (Page 2):");
        System.out.println("Service dates: ");
        System.out.println("\t1. " + dates.get(0).toString());
        System.out.println("\t2. " + dates.get(1).toString());
        String mechanicName = ""; // TODO: How to find mechanic name?
        System.out.println("Mechanic name: " + mechanicName);
        System.out.println("MENU:");
        System.out.println("\t1. Reschedule Date");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                System.out.println("Choose one of the dates listed above (enter 1 or 2): ");
                int dateNum = scanner.nextInt();
                if(dateNum == 1 || dateNum == 2) {
                    rescheduleServiceToDate(serviceID, dates.get(dateNum-1));
                    serviceMenu();
                } else {
                    System.out.println("Invalid date chosen. Try again.");
                    rescheduleService2(serviceID, dates);
                }
                break;
            case 2:
                rescheduleService();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // TODO: reschedule service to chosen date making adjustments to the parts commitment in the inventory
    // (For Customer: Reschedule Service (Page 2))
    private void rescheduleServiceToDate(int serviceID, Date toDate) throws Exception {

    }

    // TODO: query db & display upcoming services for this customer
    // (For Customer: Reschedule Service (Page 1))
    // Display the following details
    //A. License Plate
    //B. Service ID
    //C. Service Date
    //D. Service Type(Maintenance/Repair)
    //E. Service Details (Service A/B/C or Problem)
    private void displayUpcomingServices() throws Exception {

    }

    // TODO: validate above data; ensure all details except mechanic name are required. Display error msg if not given.
    private boolean validateScheduleServiceData(String licensePlate, int currentMileage, String mechanicName) throws Exception {
        boolean valid = false;

        return valid;
    }

    // Customer: Schedule Maintenance (Page 1)
    private void scheduleMaintenance(String licensePlate, int currentMileage, String mechanicName) throws Exception {
        System.out.println("\nSCHEDULE MAINTENANCE (Page 1)");
        System.out.println("\t1. Find Service Date");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                List<Date> dates = findServiceDates(licensePlate, currentMileage, mechanicName);
                if(dates.size() == 2) {
                    scheduleMaintenance2(dates, licensePlate, currentMileage, mechanicName);
                } else {
                    // If a service date cannot be found due to insufficient parts, place an order (if required) and show a message to the user asking him to try again after a specific date (calculated based on when the order will be fulfilled). Do not place an order if an existing order can fulfill the requirement, but show a message to the user asking him to try again after a specific date.

                    // If parts are not sufficient
                    if(!checkPartsSufficient()) {
                        // Check if an order exists that fulfills this requirement; If it doesn't - place the order
                        Date orderFulfilledDate = getExistingOrderForPartsDate();
                        if(orderFulfilledDate == null) {
                            orderFulfilledDate = placeOrder();
                        }
                        System.out.println("Please try again after " + orderFulfilledDate.toString());
                        scheduleService();
                    }

                }
                break;
            case 2:
                scheduleService();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // Customer: Schedule Maintenance (Page 2)
    private void scheduleMaintenance2(List<Date> dates, String licensePlate, int currentMileage, String mechanicName) throws Exception {
        System.out.println("\nSCHEDULE MAINTENANCE (Page 2):");
        System.out.println("Service dates: ");
        System.out.println("\t1. " + dates.get(0).toString());
        System.out.println("\t2. " + dates.get(1).toString());
        System.out.println("Mechanic name: " + mechanicName);
        System.out.println("MENU:");
        System.out.println("\t1. Schedule on Date");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                System.out.println("Choose one of the service dates listed above (enter 1 or 2): ");
                int dateNum = scanner.nextInt();
                if(dateNum == 1 || dateNum == 2) {
                    createMaintenanceService(dates.get(dateNum-1), licensePlate, currentMileage, mechanicName);
                    scheduleService();
                } else {
                    System.out.println("Invalid date chosen. Try again.");
                    scheduleMaintenance2(dates, licensePlate, currentMileage, mechanicName);
                }
                break;
            case 2:
                scheduleMaintenance(licensePlate, currentMileage, mechanicName);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // TODO: find two earliest available service dates
    // (Comes under Customer: Schedule Maintenance (Page 1))
    private List<Date> findServiceDates(String licensePlate, int currentMileage, String mechanicName) throws Exception {
        List<Date> dates = new ArrayList<Date>();

        return dates;
    }

    // TODO: query db and check if parts are sufficient
    private boolean checkPartsSufficient() throws Exception {
        boolean sufficient = false;

        return sufficient;
    }

    // TODO: query db & check if an order exists that satisfies parts requirements
    // Return the date when order will be fulfilled
    // If no such order exists - return null
    private Date getExistingOrderForPartsDate() throws Exception {
        Date orderDate = null;

        return orderDate;
    }

    // TODO: insert into db new order for parts required
    // Return the date when the order will be fulfilled
    private Date placeOrder() throws Exception {

        return null;
    }

    // TODO: create a new service record for maintenance service for given date
    // Insert into db
    // (For Customer: Schedule Maintenance (Page 2))
    private void createMaintenanceService(Date date, String licensePlate, int currentMileage, String mechanicName) throws Exception {

    }

    // Customer: Schedule Repair (Page 1)
    private void scheduleRepair(String licensePlate, int currentMileage, String mechanicName) throws Exception {
        System.out.println("\nSCHEDULE REPAIR (Page 1):");
        System.out.println("\t1. Engine knock");
        System.out.println("\t2. Car drifts in a particular direction");
        System.out.println("\t3. Battery does not hold charge");
        System.out.println("\t4. Black/unclean exhaust");
        System.out.println("\t5. A/C-Heater not working");
        System.out.println("\t6. Headlamps/Tail lamps not working");
        System.out.println("\t7. Check engine light");
        System.out.println("\t8. Go back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        if(option >= 1 && option <= 7) {
            displayDiagnosticReport(option);
            List<Date> dates = findRepairDates(licensePlate, currentMileage, mechanicName);
            if(dates.size() == 2) {
                scheduleRepair2(option, dates, licensePlate, currentMileage, mechanicName);
            } else {
                // If a repair date cannot be found due to insufficient parts, place an order (if required) and show a message to the user asking him to try again after a specific date (calculated based on when the order will be fulfilled). Do not place an order if an existing order can fulfill the requirement, but show a message to the user asking him to try again after a specific date.

                // If parts are not sufficient
                if(!checkPartsSufficient()) {
                    // Check if an order exists that fulfills this requirement; If it doesn't - place the order
                    Date orderFulfilledDate = getExistingOrderForPartsDate();
                    if(orderFulfilledDate == null) {
                        orderFulfilledDate = placeOrder();
                    }
                    System.out.println("Please try again after " + orderFulfilledDate.toString());
                    scheduleService();
                }
            }
        } else if(option == 8) {
            scheduleService();
        } else {
            System.out.println("Invalid option");
            scheduleRepair(licensePlate, currentMileage, mechanicName);
        }
    }

    // Customer: Schedule Repair (Page 2)
    private void scheduleRepair2(int problem, List<Date> dates, String licensePlate, int currentMileage, String mechanicName) throws Exception {
        System.out.println("\nSCHEDULE REPAIR (Page 2):");
        displayDiagnosticReport(problem);
        System.out.println("Repair dates: ");
        System.out.println("\t1. " + dates.get(0).toString());
        System.out.println("\t2. " + dates.get(1).toString());
        System.out.println("Mechanic name: " + mechanicName);
        System.out.println("MENU:");
        System.out.println("\t1. Repair on Date");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                System.out.println("Choose one of the service dates listed above (enter 1 or 2): ");
                int dateNum = scanner.nextInt();
                if(dateNum == 1 || dateNum == 2) {
                    createRepairService(dates.get(dateNum-1), licensePlate, currentMileage, mechanicName);
                    scheduleService();
                } else {
                    System.out.println("Invalid date chosen. Try again.");
                    scheduleRepair2(problem, dates, licensePlate, currentMileage, mechanicName);
                }
                break;
            case 2:
                scheduleRepair(licensePlate, currentMileage, mechanicName);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    // TODO: display report with list of causes & parts needed to resolve the problem given
    // (For Customer: Schedule Repair (Page 1))
    private void displayDiagnosticReport(int problem) {

    }

    // TODO: find two earliest available repair dates
    // (Comes under Customer: Schedule Repair (Page 1))
    private List<Date> findRepairDates(String licensePlate, int currentMileage, String mechanicName) throws Exception {
        List<Date> dates = new ArrayList<Date>();

        return dates;
    }

    // TODO: create a new service record for repair service for given date
    // Insert into db
    // (For Customer: Schedule Repair (Page 2))
    private void createRepairService(Date date, String licensePlate, int currentMileage, String mechanicName) throws Exception {

    }

    // (For Customer: Invoice)
    // TODO: query db & display following details for all completed services
    //A. Service ID
    //B. Service Start Date/Time
    //C. Service End Date/Time
    //D. Licence Plate
    //E. Service Type
    //F. Mechanic Name
    //G. Total Service Cost
    private void displayCompletedServiceDetails() throws Exception {
    	 String id=Application.currentUser.userID;
         Application.rs=Application.stmt.executeQuery("select employee.emp_name,servicereln.license_no,servicereln.service_id,repair.repair_id,timeslot.mechanic_id,timeslot.service_date,timeslot.service_time,timeslot.end_date,timeslot.end_time from employee,repair,servicereln,timeslot,mechanic where servicereln.customer_id='"+id+"' AND timeslot.service_id=servicereln.service_id AND servicereln.service_id = repair.service_id AND timeslot.mechanic_id= mechanic.mechanic_id AND mechanic.emp_id=employee.emp_id");
         
         System.out.println("The customer service History for the customer ");
         
         
         while (Application.rs.next()) {
         
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
   		 {String mechanic_name1 = Application.rs.getString("emp_name");
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
    	
    }  		 
  		  
        
    
  		 
    	
    	
    	

    
    static void close(ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } catch(Throwable whatever) {}
        }
}  static void close(Statement st) {
    if(st != null) {
        try { st.close(); } catch(Throwable whatever) {}
    }
}
}
