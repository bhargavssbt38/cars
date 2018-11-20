package users;

import app.*;
import pages.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Customer extends User {

    static Scanner scanner = new Scanner(System.in);
    static DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
    static Date lastDay = new Date();

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
              {System.out.println("Make of the car needs to be either Honda or Toyota or Nissan");registerCarMenu();
              
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
    	Application.rs=Application.stmt.executeQuery("select license_no from servicereln where service_id="+serviceID);
    	String license_no=null;
    	while(Application.rs.next())
    	{license_no=Application.rs.getString("license_no");
    		
    	}
    	String car_make = null,car_model=null;
    	Application.rs=Application.stmt.executeQuery("select car_make,car_model from car where license_no='"+license_no+"'");
    	while(Application.rs.next())
    	{
    		car_make=Application.rs.getString("car_make");
    		
            car_model=Application.rs.getString("car_model");
    	}
    	
    	Date startdate = null;
    	Application.rs=Application.stmt.executeQuery("select service_date from timeslot where service_id="+serviceID);
    	while(Application.rs.next())
    	{startdate=Application.rs.getDate("service_date");
    		 	}
    	
    	int lookup_id=0;
    	
    		String make=car_make.toLowerCase();
        	String model=car_model.toLowerCase();
    	Application.rs=Application.stmt.executeQuery("select lookup_id from service_type_lookup where car_make='"+make+"' AND car_model='"+model+"'");
    	System.out.println("The car make"+(make));
    	System.out.println("The car model"+(model));
    	
    	
    	while(Application.rs.next())
    	{
    		lookup_id=Application.rs.getInt("lookup_id");
    		
    	}
    	
    
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
    	
    	float totalcost=0;
    	List<Float>servicecost=new ArrayList();
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
    	
    	Calendar c1=Calendar.getInstance();
    	
    	int year=startdate.getYear();
    	int month=startdate.getMonth();
    	int date=startdate.getDate();
    	java.sql.Date d1=new java.sql.Date(year, month, date);
    	    	
    	long millis=System.currentTimeMillis();  
    	java.sql.Date currentdate=new java.sql.Date(millis); 
    	
    	List<String>inwarranty=new ArrayList();
    	for(int l=0;l<warranty.size();l++)
    	{
    	 c1.add(month, warranty.get(l));
    	 if(currentdate.before(d1))
    	 {String temp;
    		 temp="Yes";
    		 
    		 inwarranty.add(temp);
    	 }else
    	 {
    		 inwarranty.add("Warranty Expired");
    	 }
    	}
    	for(int b=0;b<bid.size();b++)
    	{
    		float temp=0;
    		float temp1=0;
    		temp=quantity.get(b)*partcost.get(b);
    		if(rate.get(b).equalsIgnoreCase("low"))
    		{
    			temp1=50;
    		}else
    		{
    			temp1=65;
    		}
    		if(inwarranty.get(b).equalsIgnoreCase("Yes"))
    		{
    			float temp2=0;
    			servicecost.add(b, temp);
    			    			
    		}else 
    		 {temp1+=temp1*hours.get(b);
    		servicecost.add(temp+temp1);
    		 }totalcost+=servicecost.get(b);
    	}
    	System.out.println("Basic service ID, Service Name, Part_id, quantity, Rate of labor, hours required, Part Cost, Warranty,ServiceCost InWarranty/Expired Warranty ");
    	for(int j=0;j<bid.size();j++)
    	{  System.out.println();
    		System.out.print("\t"+bid.get(j));
    		System.out.print("\t"+servicename.get(j));
    		System.out.print("\t"+part.get(j));
    		System.out.print("\t"+quantity.get(j));
    		System.out.print("\t"+rate.get(j));
    		System.out.print("\t"+hours.get(j));
    		System.out.print("\t" +partcost.get(j));
    		System.out.print("\t" +warranty.get(j));
    		System.out.print("\t"+servicecost.get(j));
    		System.out.print("\t"+inwarranty.get(j));
    	
    		
    		
    	}
    	float totalhours=0;
    	
    	for(int c=0;c<hours.size();c++)
    	{totalhours+=hours.get(c);
    		
    	}
    	System.out.print("\n");
    	System.out.println("The total number of labor hours involved in this service: "+totalhours);
    	System.out.println("The total cost involved in this service: " +totalcost);
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
            String a=scanner.nextLine();
            String newValue = scanner.nextLine();
            
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
    		 
    	  if(isWord(newValue))
    		
    	    {Application.stmt.executeUpdate("update customer set customer_name='"+newValue+"' where customer_id="+id);
    		System.out.println("Profile Details Successfully updated into the database");
    	    
    	      }
    	else {
    		System.out.println("Invalid Data");
    		updateProfile();
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
    	return true;
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
        int currentMileage = 0;
        try {
            currentMileage = scanner.nextInt();
        } catch(InputMismatchException ime) {
            System.out.println("Enter a valid number.");
            return;
        }

        scanner.nextLine();
        System.out.println("C. Enter mechanic name: ");
        String mechanicName = scanner.nextLine();
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
            scheduleService();
        }
    }

    // Customer: Reschedule Service (Page 1)
    private void rescheduleService() throws Exception {
    	String id=Application.currentUser.userID;
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
    	String id=Application.currentUser.userID;
    	long millis=System.currentTimeMillis();  
    	java.sql.Date date=new java.sql.Date(millis);  
    	System.out.println("Current Date"+date); 
    	//System.out.println("The current date is"+dateFormat.format(currentdate));
    	Application.rs=Application.stmt.executeQuery("select license_no, service_id from servicereln where customer_id='"+id+"'");
    	List<String>license_no=new ArrayList();
    	List<Integer>service_id = new ArrayList();
    	while(Application.rs.next())
    	{ String temp;
    	  int temp1;
    	  temp=Application.rs.getString("license_no");
    	  temp1=Application.rs.getInt("service_id");
    	  license_no.add(temp);
    	  service_id.add(temp1);
    	}
    	List<Date>upcoming_services=new ArrayList();
    	
    	for(int i=0;i<service_id.size();i++)
    	{Application.rs=Application.stmt.executeQuery("select service_date from timeslot where service_id="+service_id.get(i));
    	  
    	  while(Application.rs.next())
    	  {//SimpleDateFormat tmp=new SimpleDateFormat("dd-MMM-yy");
    	    Date temp;
    	   temp=Application.rs.getDate("service_date");
    	   
    	   if(date.before(temp))
    	   {   	   upcoming_services.add(temp);
    	   }  
    	  }
    	}
    	List<Integer>sid=new ArrayList();
    	SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yy");
    	
    	
    	for(int a=0;a<upcoming_services.size();a++)
    	{  String s=sdf.format(upcoming_services.get(a));
    		String query= "select service_id from timeslot where service_date='"+s+"'";
    		System.out.println("Query" +query);
    	    Application.rs=Application.stmt.executeQuery(query);
    	    while(Application.rs.next())
    	    {
    		int service=Application.rs.getInt("service_id");
    		sid.add(service);
    	}
    	}
    	List<String>lplate=new ArrayList();
    	List<Integer>ehours=new ArrayList();
    	List <String>problem=new ArrayList();
    	List<String>mtype=new ArrayList();
    	for (int b=0;b<sid.size();b++)
    	{ Application.rs=Application.stmt.executeQuery("select license_no from servicereln where service_id="+sid.get(b));
    	  while(Application.rs.next())
    	  {String x=Application.rs.getString("license_no");
    	    lplate.add(x);
       	  }
    	  Application.rs=Application.stmt.executeQuery("select estimated_hours from services where service_id="+sid.get(b));
    	  while(Application.rs.next())
    	  {int y=Application.rs.getInt("estimated_hours");
    		  ehours.add(y);
    	  }
    	  Application.rs=Application.stmt.executeQuery("select problem from repair where service_id="+sid.get(b));
    	  if(Application.rs==null)
    	  {
    		  problem.add("No Repair");
    	  }else {
    	 	  while(Application.rs.next())
    	  { String c= Application.rs.getString("problem");
    		problem.add(c);
    	  }
    	  }
    	  Application.rs=Application.stmt.executeQuery("select maintenance_type from maintenance where service_id="+sid.get(b));
    	  if(Application.rs==null)
    	  { System.out.println("Hello");
    		  mtype.add("No Maintenance");
    	  }else {
    	 	  while(Application.rs.next())
    	  { String d= Application.rs.getString("maintenance_type");
    	     System.out.println("watha");
    		mtype.add(d);
    		System.out.print("\t"+d);
    	  }
    	  }
    		
    	}
    		
    	System.out.println("Upcoming Services for this customer" +id);
    	System.out.println();
  	  for(int j=0;j<upcoming_services.size();j++)
  	  {System.out.print("Date: "+upcoming_services.get(j));
  	   System.out.print("\t Service ID: " +sid.get(j));
  	   System.out.print("\t License Plate: "+lplate.get(j));
  	   System.out.print("\t Estimated hours for this service: "+ehours.get(j));
  	   System.out.print("\t Repair: "+problem.get(j));
  	   //System.out.print("\t Maintenance Type: "+mtype.get(j));
  	   System.out.println();
  	  }

    }

    private boolean validateScheduleServiceData(String licensePlate, int currentMileage, String mechanicName) throws Exception {
        boolean valid = true;

        if(licensePlate == null || licensePlate.isEmpty()) {
            System.out.println("License plate is required. Try again.");
            return false;
        } else {
            // Check if license plate is valid
            String query = "select license_no from car where license_no = '" + licensePlate + "'";
            Application.rs = Application.stmt.executeQuery(query);
            boolean licenseExists = false;
            while(Application.rs.next()) {
                licenseExists = true;
                break;
            }
            if(!licenseExists) {
                System.out.println("Invalid license plate entered. Try again.");
            }
            valid = licenseExists;
        }

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
                scheduleMaintenanceProcess(licensePlate, currentMileage, mechanicName);
                break;
            case 2:
                scheduleService();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private void scheduleMaintenanceProcess(String licensePlate, int currentMileage, String mechanicName) throws Exception {
        // Find type of service & list of basic services based on car details & mileage
        // Find last recorded mileage
        int lastMileage = 0, carYear = 0;
        String carMake = "", carModel = "", lastServiceType = "";
        int customerID = Integer.parseInt(this.userID);
        String carQuery = "select * from car where customer_id = " + customerID + " and license_no = '" + licensePlate + "'";
        Application.rs = Application.stmt.executeQuery(carQuery);
        while(Application.rs.next()) {
            lastMileage = Application.rs.getInt("last_recorded_mileage");
            carMake = Application.rs.getString("car_make").toLowerCase();
            carModel = Application.rs.getString("car_model").toLowerCase();
            carYear = Integer.parseInt(Application.rs.getString("car_year"));
            lastServiceType = Application.rs.getString("recent_service_type");
            break;
        }
        int miles = currentMileage - lastMileage;
        // Find service using mileage and car details
        String serviceLookupQuery = "select * from service_type_lookup where car_make = '" + carMake + "' and car_model = '" + carModel + "' and car_year = " + carYear;
        Application.rs = Application.stmt.executeQuery(serviceLookupQuery);
        int aLimit = -1, bLimit = -1, cLimit = -1;
        int lookupID = -1;
        while(Application.rs.next()) {
            lookupID = Application.rs.getInt("lookup_id");
            aLimit = Application.rs.getInt("service_a");
            bLimit = Application.rs.getInt("service_b");
            cLimit = Application.rs.getInt("service_c");
            break;
        }

        String serviceType = "A";
        if(aLimit > 0 && bLimit > 0 && cLimit > 0) {
            if(miles >= aLimit && miles < bLimit) {
                serviceType = "A";
            } else if(miles >= bLimit && miles < cLimit) {
                serviceType = "B";
            } else if(miles >= cLimit) {
                serviceType = "C";
            }
        }

        if(lookupID < 0) {
            System.out.println("Unable to schedule service. Try again.");
            serviceMenu();
            return;
        }

        // Find list of basic services for this service type
        String basicServicesQuery = "select * from service_type_services where lookup_id = " + lookupID + " and (service_type = 'A' ";
        if("B".equalsIgnoreCase(serviceType)) {
            basicServicesQuery += " or service_type = 'B' ";
        } else if("C".equalsIgnoreCase(serviceType)) {
            basicServicesQuery += " or service_type = 'B' or service_type = 'C' ";
        }
        basicServicesQuery += " )";
        Application.rs = Application.stmt.executeQuery(basicServicesQuery);
        List<Integer> basicServicesIDs = new ArrayList<>();
        while(Application.rs.next()) {
            int basicServiceID = Application.rs.getInt("basic_service_id");
            basicServicesIDs.add(basicServiceID);
        }
        if(basicServicesIDs.size() == 0) {
            System.out.println("Basic services not found");
            return;
        }

        // Based on list of basic services - find time required
        float totalTime = 0.0F;
        String basicServiceDetailsQuery = "select * from basic_services where basic_service_id in (";
        for(int i = 0; i < basicServicesIDs.size(); i++) {
            basicServiceDetailsQuery += "" + basicServicesIDs.get(i);
            if(i < basicServicesIDs.size()-1) {
                basicServiceDetailsQuery += ",";
            }
        }
        basicServiceDetailsQuery += ")";
        Application.rs = Application.stmt.executeQuery(basicServiceDetailsQuery);
        while(Application.rs.next()) {
            float time = Application.rs.getFloat("time_hours");
            totalTime += time;
        }

        // Based on list of basic service - find parts required
        Map<Integer, Integer> parts = new HashMap<>();
        for(int basicServiceID : basicServicesIDs) {
            String partsQuery = "select * from basic_services_parts where basic_service_id = " + basicServiceID + " and UPPER(car_make) = UPPER('" + carMake + "') and UPPER(car_model) = UPPER('" + carModel + "')";
            Application.rs = Application.stmt.executeQuery(partsQuery);
            while(Application.rs.next()) {
                int partID = Application.rs.getInt("part_id");
                int qty = Application.rs.getInt("quantity");
                parts.put(partID, qty);
            }
        }

        // Based on list of basic service - find labor charge
        float laborCharge = findLaborCharges(serviceType, licensePlate, basicServiceDetailsQuery);

        Map<Date, Map<String[], Integer>> datesResult = findServiceDates(totalTime, mechanicName);

        Map<String, Map<Integer, Integer>> scParts = getSCParts(parts);
        int partsFound = 0;
        for(String sc : scParts.keySet()) {
            Map<Integer, Integer> partsMap = scParts.get(sc);
            partsFound += partsMap.size();
        }
        scheduleMaintenance2(datesResult, licensePlate, currentMileage, mechanicName, totalTime, serviceType, laborCharge, scParts);
        if(partsFound != parts.size()) {
            System.out.println("Insufficient parts. Please try again after " + dateFormat.format(lastDay).toUpperCase() + ".");
        } else if(datesResult.size() == 2) {
            scheduleMaintenance2(datesResult, licensePlate, currentMileage, mechanicName, totalTime, serviceType, laborCharge, scParts);
        }

/*
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
*/
    }

    // Customer: Schedule Maintenance (Page 2)
    private void scheduleMaintenance2(Map<Date, Map<String[], Integer>> datesResult, String licensePlate, int currentMileage, String mechanicName, float totalTime, String serviceType, float laborCharge, Map<String, Map<Integer, Integer>> scParts) throws Exception {
        System.out.println("\nSCHEDULE MAINTENANCE (Page 2):");
        System.out.println("Service dates: ");
        Set<Date> dates = datesResult.keySet();
        Map<Integer, Date> dateMap = new HashMap<>();
        int i = 1;
        for(Date date : dates) {
            System.out.println("\t" + i + ". " + dateFormat.format(date));
            dateMap.put(i, date);
            i++;
        }
        if(mechanicName != null && !mechanicName.trim().isEmpty()) {
            System.out.println("Mechanic name: " + mechanicName);
        }

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
                    createMaintenanceService(datesResult, dateMap.get(dateNum), licensePlate, totalTime, serviceType, laborCharge, scParts);
                    scheduleService();
                } else {
                    System.out.println("Invalid date chosen. Try again.");
                    scheduleMaintenance2(datesResult, licensePlate, currentMileage, mechanicName, totalTime, serviceType, laborCharge, scParts);
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

    // Returns : <Date foundDate, <[String startTime, String endTime], int mechanicID>>
    private Map<Date, Map<String[], Integer>> findServiceDates(float timeReqd, String mechanicName) throws Exception {

        String scID = findCustomerServiceCenter();

        Map<Date, Map<String[], Integer>> result = new HashMap<>();

        Date today = new Date(); // Today's date
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        for(int i = 1; i <= 5; i++) { // Check for next 5 business days
            cal.add(Calendar.DATE, 1);
            // If weekend - change to Monday
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                cal.add(Calendar.DATE, 2);
            } else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                cal.add(Calendar.DATE, 1);
            }
            Date day = cal.getTime();
            lastDay = day;

            // For this service center - find all service IDs that have booked slots on this date
            String serviceIDQuery = "select SERVICE_ID from SERVICERELN inner join APPOINTMENT on SERVICERELN.CUSTOMER_ID = APPOINTMENT.CUSTOMER_ID where SC_ID = '" + scID + "'";
            String slotsQuery = "select * from TIMESLOT where SERVICE_ID in (" + serviceIDQuery + ") and SERVICE_DATE = '" + dateFormat.format(day).toUpperCase() + "'";
            Application.rs = Application.stmt.executeQuery(slotsQuery);

            if(checkMaintenanceLimit(slotsQuery, timeReqd)) {
                continue; // Skip this day
            }

            List<Integer> mechanics = new ArrayList<>();

            if(mechanicName == null || mechanicName.trim().isEmpty()) {
                // If mechanic not specified, take all mechanics at service center
                String mechQuery = "select MECHANIC_ID from MECHANICAT where SC_ID = '" + scID + "'";
                Application.rs = Application.stmt.executeQuery(mechQuery);
                while(Application.rs.next()) {
                    int mechanicID = Application.rs.getInt("MECHANIC_ID");
                    mechanics.add(mechanicID);
                }
            } else {
                // If mechanic specified, consider only that mechanic
                String mechQuery = "select MECHANIC_ID from MECHANIC inner join EMPLOYEE on MECHANIC.EMP_ID = EMPLOYEE.EMP_ID and UPPER(EMP_NAME) = UPPER('" + mechanicName + "')";
                Application.rs = Application.stmt.executeQuery(mechQuery);
                while(Application.rs.next()) {
                    int mechanicID = Application.rs.getInt("MECHANIC_ID");
                    mechanics.add(mechanicID);
                    break;
                }
            }

            Map<Integer, String> timeSlotLookup = initTimeSlotsMap();
            int slotsNeeded = Math.round(timeReqd/0.5F);

            for(int mechanicID : mechanics) {
                float mechanicWorkedHours = 0.0F;

                // Time slots for each mechanic
                Set<Integer> timeIndices = new TreeSet<>();
                for(int index = 1; index <= 22; index++) timeIndices.add(index);

                // Get time slots of this service center on this day by this mechanic
                String mechanicSlotsQuery = slotsQuery + " and MECHANIC_ID = " + mechanicID;
                Application.rs = Application.stmt.executeQuery(mechanicSlotsQuery);

                while(Application.rs.next()) {
                    String start = Application.rs.getString("SERVICE_TIME");
                    String end = Application.rs.getString("END_TIME");
                    int startIndex = getTimeIndex(start, timeSlotLookup);
                    int endIndex = getTimeIndex(end, timeSlotLookup);
                    for(int remove = startIndex; remove < endIndex; remove++) {
                        timeIndices.remove(remove);
                        mechanicWorkedHours += 0.5F;
                    }
                }
                // Mechanic can only work 11 hours a day
                if(mechanicWorkedHours + timeReqd > 11) {
                    continue;
                }

                // Checking if required number of continuous slots are available
                int timeSlotStart = -1, foundSlots = 0;
                for(int from = 1; from <= 22; from++) {
                    if(timeIndices.contains(from)) {
                        if(timeSlotStart == -1) timeSlotStart = from;
                        foundSlots++;
                    } else {
                        timeSlotStart = -1;
                        foundSlots = 0;
                    }

                    if(foundSlots == slotsNeeded) break;
                }
                if(foundSlots == slotsNeeded) {
                    // TODO: check for parts
                    Map<String[], Integer> innerResultMap = new HashMap<>();
                    String[] resultTimes = new String[2];
                    resultTimes[0] = timeSlotLookup.get(timeSlotStart);
                    resultTimes[1] = timeSlotLookup.get(timeSlotStart+slotsNeeded);
                    innerResultMap.put(resultTimes, mechanicID);
                    result.put(day, innerResultMap);
                    if(result.size() == 2) return result;
                    break;
                }
            }
        }
        return result;
    }

    private Map<String, Map<Integer, Integer>> getSCParts(Map<Integer, Integer> parts) throws Exception {

        Map<String, Map<Integer, Integer>> scParts = new HashMap<>();
        // < ServiceCenterID , <PartID, Qty> >

        for(int partID : parts.keySet()) {
            int qty = parts.get(partID);

            // Check in own SC
            boolean foundInSC = false;
            String scID = findCustomerServiceCenter();
            String query = "select * from INVENTORY where PART_ID = " + partID + " and SC_ID = '" + scID + "' and CURRENT_QTY >= " + qty;
            Application.rs = Application.stmt.executeQuery(query);
            while(Application.rs.next()) {
                int currentQty = Application.rs.getInt("CURRENT_QTY");
                int newQty = currentQty - qty;
                int minThreshold = Application.rs.getInt("MIN_THRESHOLD");
                if(newQty >= minThreshold) {
                    foundInSC = true;
                    Map<Integer, Integer> partsMap = scParts.getOrDefault(scID, new HashMap<>());
                    partsMap.put(partID, qty);
                    scParts.put(scID, partsMap);
                    break;
                }
            }

            // Check other SC
            if(!foundInSC) {
                query = "select * from INVENTORY where PART_ID = " + partID + " and CURRENT_QTY >= " + qty;
                Application.rs = Application.stmt.executeQuery(query);
                while(Application.rs.next()) {
                    int currentQty = Application.rs.getInt("CURRENT_QTY");
                    int newQty = currentQty - qty;
                    int minThreshold = Application.rs.getInt("MIN_THRESHOLD");
                    String sc = Application.rs.getString("SC_ID");
                    if(newQty >= minThreshold) {
                        Map<Integer, Integer> partsMap = scParts.getOrDefault(scID, new HashMap<>());
                        partsMap.put(partID, qty);
                        scParts.put(sc, partsMap);
                        break;
                    }
                }
            }
        }

        for(String sc : scParts.keySet()) {

            Map<Integer, Integer> p = scParts.get(sc);
            for(int pid : p.keySet()) {

            }
        }

        return scParts;
    }

/*    // query db & check if an order exists that satisfies parts requirements
    // Return the date when order will be fulfilled
    // If no such order exists - return null
    private Date getExistingOrderForPartsDate() throws Exception {
        Date orderDate = null;

        return orderDate;
    }

    // insert into db new order for parts required
    // Return the date when the order will be fulfilled
    private Date placeOrder() throws Exception {

        return null;
    }*/

    // (For Customer: Schedule Maintenance (Page 2))
    private void createMaintenanceService(Map<Date, Map<String[], Integer>> datesResult, Date chosenDate, String licensePlate, float totalTime, String serviceType, float laborCharge, Map<String, Map<Integer, Integer>> scParts) throws Exception {

        // Get last service ID
        String lastServiceQuery = "select MAX(SERVICE_ID) from SERVICES";
        Application.rs = Application.stmt.executeQuery(lastServiceQuery);
        int serviceID = 0;
        while(Application.rs.next()) {
            serviceID = Application.rs.getInt("MAX(SERVICE_ID)");
            break;
        }
        ++serviceID;

        // Insert : SERVICES
        String insertService = "insert into SERVICES(SERVICE_ID, LABOR_CHARGE, ESTIMATED_HOURS) values(" + serviceID + ", " + laborCharge + ", " + totalTime + ")";
        Application.stmt.executeUpdate(insertService);

        // Insert : SERVICERELN
        String insertServiceReln = "insert into SERVICERELN(LICENSE_NO, CUSTOMER_ID, SERVICE_ID) values('" + licensePlate +"', " + this.userID + ", " + serviceID + ")";
        Application.stmt.executeUpdate(insertServiceReln);

        // Get last maintenance ID
        String lastMaintQuery = "select MAX(MAINTENANCE_ID) from MAINTENANCE";
        Application.rs = Application.stmt.executeQuery(lastMaintQuery);
        int maintenanceID = 0;
        while(Application.rs.next()) {
            maintenanceID = Application.rs.getInt("MAX(MAINTENANCE_ID)");
            break;
        }
        ++maintenanceID;

        // Insert : MAINTENANCE
        String insertMaintenance = "insert into MAINTENANCE(MAINTENANCE_ID, MAINTENANCE_TYPE, SERVICE_ID) values(" + maintenanceID + ", '" + serviceType + "', " + serviceID + ")";
        Application.stmt.executeUpdate(insertMaintenance);


        // Get last appointment ID
        String lastApp = "select MAX(APPOINTMENT_ID) from APPOINTMENT";
        Application.rs = Application.stmt.executeQuery(lastApp);
        int appID = 0;
        while(Application.rs.next()) {
            appID = Application.rs.getInt("MAX(APPOINTMENT_ID)");
            break;
        }
        ++appID;

        // Insert : APPOINTMENT
        String insertAppointment = "insert into APPOINTMENT(APPOINTMENT_ID, SERVICE_TYPE, CUSTOMER_ID, SC_ID) values(" + appID + ", '" + serviceType + "', " + this.userID + ", '" + findCustomerServiceCenter() + "')";
        Application.stmt.executeUpdate(insertAppointment);

        // Insert : TIMESLOT
        Map<String[], Integer> resultMap = datesResult.get(chosenDate);
        String[] timings = new String[2];
        for(String[] keys : resultMap.keySet()) {
            timings = keys;
            break;
        }
        String startTime = timings[0];
        String endTime = timings[1];
        int mechanicID = resultMap.get(timings);
        String insertTimeslot = "insert into TIMESLOT(SERVICE_ID, SERVICE_DATE, SERVICE_TIME, MECHANIC_ID, END_DATE, END_TIME) values(" + serviceID + ", '" + dateFormat.format(chosenDate).toUpperCase() + "', '" + startTime + "', " + mechanicID + ", '" + dateFormat.format(chosenDate).toUpperCase() + "', '" + endTime + "')";
        Application.stmt.executeUpdate(insertTimeslot);

        System.out.println("Maintenance service scheduled successfully.");
        System.out.println("Date: " + dateFormat.format(chosenDate).toUpperCase());
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("Mechanic ID: " + mechanicID);
        System.out.println("Service Type: " + serviceType);

        // Decrementing parts in inventory
        for(String sc : scParts.keySet()) {
            Map<Integer, Integer> partsMap = scParts.get(sc);
            for(int partID : partsMap.keySet()) {
                if(partID == 7) continue;
                int qty = partsMap.get(partID);
                String update = "update INVENTORY set CURRENT_QTY = (CURRENT_QTY - " + qty + ") where SC_ID = '" + sc + "' and PART_ID = " + partID;
                Application.stmt.executeUpdate(update);
            }
        }
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
            displayDiagnosticReport(option, licensePlate);
            List<Date> dates = findRepairDates(licensePlate, currentMileage, mechanicName);
            if(dates.size() == 2) {
                scheduleRepair2(option, dates, licensePlate, currentMileage, mechanicName);
            } else {
                // If parts are not sufficient
/*                if(!checkPartsSufficient()) {
                    // Check if an order exists that fulfills this requirement; If it doesn't - place the order
                    Date orderFulfilledDate = getExistingOrderForPartsDate();
                    if(orderFulfilledDate == null) {
                        orderFulfilledDate = placeOrder();
                    }
                    System.out.println("Please try again after " + orderFulfilledDate.toString());
                    scheduleService();
                }*/
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
        displayDiagnosticReport(problem, licensePlate);
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

    // (For Customer: Schedule Repair (Page 1))
    private void displayDiagnosticReport(int problem, String licensePlate) throws Exception {
    	System.out.println("\nDIAGNOSTIC REPORT:");
		String query = "select * from REPAIR_LOOKUP where repair_lookup_id = " + problem;
		Application.rs = Application.stmt.executeQuery(query);
		while(Application.rs.next()) {
			System.out.println("Repair name: " + Application.rs.getString("name"));
			System.out.println("Diagnostic: " + Application.rs.getString("diagnostic"));
			System.out.println("Diagnostic fee: " + Application.rs.getFloat("diagnostic_fee"));
		}

		// Finding car make & model for license plate
		String carQuery = "select CAR_MAKE, CAR_MODEL from CAR where LICENSE_NO = '" + licensePlate + "'";
		Application.rs = Application.stmt.executeQuery(carQuery);
		String carMake = "", carModel = "";
		while(Application.rs.next()) {
			carMake = Application.rs.getString("CAR_MAKE");
			carModel = Application.rs.getString("CAR_MODEL");
		}

		// Display parts required
		System.out.println("Parts required: ");
		String partsQuery = "select PARTS.PART_NAME from PARTS inner join BASIC_SERVICES_PARTS on PARTS.PART_ID = BASIC_SERVICES_PARTS.PART_ID where BASIC_SERVICES_PARTS.CAR_MAKE = '" + carMake + "' and BASIC_SERVICES_PARTS.CAR_MODEL = '" + carModel + "' and BASIC_SERVICE_ID in (select REPAIR_SERVICES_LOOKUP.BASIC_SERVICE_ID from REPAIR_SERVICES_LOOKUP where REPAIR_SERVICES_LOOKUP.REPAIR_LOOKUP_ID = " + problem + ")";
		Application.rs = Application.stmt.executeQuery(partsQuery);
		while(Application.rs.next()) {
			String partName = Application.rs.getString("PART_NAME");
			System.out.println("\t" + partName);
		}
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
    }

    static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Throwable whatever) {
			}
		}
	}

    // Initialize 30 minute time slots from 8 AM to 7 PM
    private Set<String> initTimeSlots() throws Exception {
        Set<String> timeSlots = new LinkedHashSet<>();
        timeSlots.add("8am");
        timeSlots.add("8.30am");
        timeSlots.add("9am");
        timeSlots.add("9.30am");
        timeSlots.add("10am");
        timeSlots.add("10.30am");
        timeSlots.add("11am");
        timeSlots.add("11.30am");
        timeSlots.add("12pm");
        timeSlots.add("12.30pm");
        timeSlots.add("1pm");
        timeSlots.add("1.30pm");
        timeSlots.add("2pm");
        timeSlots.add("2.30pm");
        timeSlots.add("3pm");
        timeSlots.add("3.30pm");
        timeSlots.add("4pm");
        timeSlots.add("4.30pm");
        timeSlots.add("5pm");
        timeSlots.add("5.30pm");
        timeSlots.add("6pm");
        timeSlots.add("6.30pm");
        return timeSlots;
    }

    private Map<Integer, String> initTimeSlotsMap() throws Exception {
        Map<Integer, String> timeSlotsMap = new TreeMap<>();
        timeSlotsMap.put(1, "8am");
        timeSlotsMap.put(2, "8.30am");
        timeSlotsMap.put(3, "9am");
        timeSlotsMap.put(4, "9.30am");
        timeSlotsMap.put(5, "10am");
        timeSlotsMap.put(6, "10.30am");
        timeSlotsMap.put(7, "11am");
        timeSlotsMap.put(8, "11.30am");
        timeSlotsMap.put(9, "12pm");
        timeSlotsMap.put(10, "12.30pm");
        timeSlotsMap.put(11, "1pm");
        timeSlotsMap.put(12, "1.30pm");
        timeSlotsMap.put(13, "2pm");
        timeSlotsMap.put(14, "2.30pm");
        timeSlotsMap.put(15, "3pm");
        timeSlotsMap.put(16, "3.30pm");
        timeSlotsMap.put(17, "4pm");
        timeSlotsMap.put(18, "4.30pm");
        timeSlotsMap.put(19, "5pm");
        timeSlotsMap.put(20, "5.30pm");
        timeSlotsMap.put(21, "6pm");
        timeSlotsMap.put(22, "6.30pm");
        return timeSlotsMap;
    }

    // For this day - Check if maintenance services are not more than 50% of the day
    private boolean checkMaintenanceLimit(String slotsQuery, float timeReqd) throws Exception {
        boolean limitReached = false;
        float totalHours = 11.0F;
        Set<String> timeSlots = initTimeSlots();

        // Get list of all maintenance service IDs - so that we only count maintenance IDs and not repairs to find %
        String maintenanceQuery = "select MAINTENANCE_ID from MAINTENANCE";
        Application.rs = Application.stmt.executeQuery(maintenanceQuery);
        List<Integer> maintenanceIDs = new ArrayList<>();
        while(Application.rs.next()) {
            int maintID = Application.rs.getInt("MAINTENANCE_ID");
            maintenanceIDs.add(maintID);
        }

        Application.rs = Application.stmt.executeQuery(slotsQuery);
        float maintHours = 0.0F;
        while(Application.rs.next()) {
            String start = Application.rs.getString("SERVICE_TIME");
            String end = Application.rs.getString("END_TIME");
            int serviceID = Application.rs.getInt("SERVICE_ID");
            Iterator iter = timeSlots.iterator();
            boolean startRemoving = false;
            while(iter.hasNext()) {
                String curTime = (String) iter.next();
                if(start.equalsIgnoreCase(curTime)) {
                    startRemoving = true;
                    iter.remove();
                    if(maintenanceIDs.contains(serviceID)) maintHours += 0.5F;
                } else if(end.equalsIgnoreCase(curTime)) {
                    break;
                } else if(startRemoving) {
                    iter.remove();
                    if(maintenanceIDs.contains(serviceID)) maintHours += 0.5F;
                }
            }
        }

        // Only half the day can be maintenance
        if(maintHours + timeReqd > totalHours/2) {
            limitReached = true;
        }

        return limitReached;
    }

    private String findCustomerServiceCenter() throws Exception {
        String scID = "";
        Application.rs = Application.stmt.executeQuery("select SC_ID from CUSTOMER where CUSTOMER_ID = " + Integer.parseInt(this.userID));
        while(Application.rs.next()) {
            scID = Application.rs.getString("SC_ID");
        }
        return scID;
    }

    private int getTimeIndex(String value, Map<Integer, String> map) throws Exception {
        int index = -1;

        for(Map.Entry entry: map.entrySet()){
            if(value.equals(entry.getValue())){
                index = (Integer) entry.getKey();
            }
        }

        return index;
    }

    private float findLaborCharges(String serviceType, String licensePlate, String basicServiceDetailsQuery) throws Exception {
        float laborCharge = 0.0F;
        // Check if this type of service is provided for first time. If yes - laborCharge = 0; Else - calculate
        String prevServQuery = "select MAINTENANCE_TYPE from SERVICERELN inner join MAINTENANCE on SERVICERELN.SERVICE_ID = MAINTENANCE.SERVICE_ID where CUSTOMER_ID = " + this.userID + " and LICENSE_NO = '" + licensePlate + "' and MAINTENANCE_TYPE = '" + serviceType + "'";
        Application.rs = Application.stmt.executeQuery(prevServQuery);
        boolean firstTime = true;
        while(Application.rs.next()) {
            firstTime = false;
            break;
        }
        if(firstTime) {
            laborCharge = 0.0F;
        } else {
            Application.rs = Application.stmt.executeQuery(basicServiceDetailsQuery);
            while(Application.rs.next()) {
                String rate = Application.rs.getString("RATE");
                float time = Application.rs.getFloat("TIME_HOURS");
                if("low".equalsIgnoreCase(rate)) {
                    laborCharge += (time * 50);
                } else if("high".equalsIgnoreCase(rate)) {
                    laborCharge += (time * 65);
                }
            }
        }
        return laborCharge;
    }

}
