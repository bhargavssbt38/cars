package users;

import app.Application;
import pages.DisplayPage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Receptionist extends Employee {

    static Scanner scanner = new Scanner(System.in);
    static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static Date lastDay = new Date();
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
     System.out.println(" Enter the Mechanic Name, It is optional(y/n)");
     String c =scanner.next();
     String mechanicname ="";
     if(c.equals("y"))
     {
    	 System.out.println("Enter mechanic name");
    	 mechanicname=scanner.next();
     }
    	 
     if(validateservicerequest(email,licenseplate,currentmileage,mechanicname)) {
    	 System.out.println("\n Menu");
    	 System.out.println("1. Schedule Maintenance");
    	 System.out.println("2. Schedule Repair");
    	 System.out.println("3. Go Back");
    	 int option=scanner.nextInt();
    	 switch(option) {
    	 case 1: scheduleMaintenance(email,licenseplate,currentmileage,mechanicname); break;
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
    private void scheduleMaintenance(String email,String licenseplate,int currentmileage, String mechanicname) throws Exception{
    	System.out.println("\n Menu");
    	System.out.println("1. Find Service Date");
    	System.out.println("2. Go Back");
    	int option=scanner.nextInt();
    	switch(option)
    	{ case 1: //Write a query to find two earliest available service dates and go to the receptionist schedule maintenance page 2
    		scheduleMaintenanceProcess(email,licenseplate, currentmileage, mechanicname);
            break;
    		    // If service date cannot be found due to insufficient parts then place order and show a message to the user asking him to try again after a specific date.
    		 //Uncomment the below line based on the result of the above query.     
    		//scheduleService(); break;
    		    		  
    	 case 2:  
    		 	scheduleService();
    	 		break;
    	 default: 
    		 System.out.println("Invalid Option");
    		 System.exit(0);
    	}
    	
    	
    }
    
    private Map<Date, Map<String[], Integer>> findServiceDates(float timeReqd, String mechanicName) throws Exception {
    	return findServiceDates(timeReqd, mechanicName, true);
	}

    private void scheduleMaintenance2(Map<Date, Map<String[], Integer>> datesResult, String licensePlate, int currentMileage, String mechanicName, float totalTime, String serviceType, float laborCharge, Map<String, Map<Integer, Integer>> scParts, String email) throws Exception {
        
    	int customer_id = 0;
    	Application.rs = Application.stmt.executeQuery("Select customer_id from customer where email = '"+email+"'");
    	while(Application.rs.next())
    	{
    		//System.out.println("Executed 7");
    		customer_id = Application.rs.getInt("customer_id");
        	
    	}
    		
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
                    createMaintenanceService(datesResult, dateMap.get(dateNum), licensePlate, totalTime, serviceType, laborCharge, scParts,customer_id);
                    landingPage();
                } else {
                    System.out.println("Invalid date chosen. Try again.");
                    scheduleMaintenance2(datesResult, licensePlate, currentMileage, mechanicName, totalTime, serviceType, laborCharge, scParts,email);
                }
                break;
            case 2:
                scheduleMaintenance(email,licensePlate, currentMileage, mechanicName);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }


    
    private void createMaintenanceService(Map<Date, Map<String[], Integer>> datesResult, Date chosenDate, String licensePlate, float totalTime, String serviceType, float laborCharge, Map<String, Map<Integer, Integer>> scParts, int customer_id) throws Exception {

        // Get last service ID
        String lastServiceQuery = "select MAX(SERVICE_ID) from SERVICES";
        //System.out.println(lastServiceQuery);
        Application.rs = Application.stmt.executeQuery(lastServiceQuery);
        int serviceID = 0;
        while(Application.rs.next()) {
        	//System.out.println("Service ID");
            serviceID = Application.rs.getInt("MAX(SERVICE_ID)");
            break;
        }
        ++serviceID;

        // Insert : SERVICES
        String insertService = "insert into SERVICES(SERVICE_ID, LABOR_CHARGE, ESTIMATED_HOURS) values(" + serviceID + ", " + laborCharge + ", " + totalTime + ")";
        //System.out.println(insertService);
        Application.stmt.executeUpdate(insertService);

        // Insert : SERVICERELN
        String insertServiceReln = "insert into SERVICERELN(LICENSE_NO, CUSTOMER_ID, SERVICE_ID) values('" + licensePlate +"', " + customer_id + ", " + serviceID + ")";
        //System.out.println(insertServiceReln);
        Application.stmt.executeUpdate(insertServiceReln);

        // Get last maintenance ID
        String lastMaintQuery = "select MAX(MAINTENANCE_ID) from MAINTENANCE";
        //System.out.println(lastMaintQuery);
        Application.rs = Application.stmt.executeQuery(lastMaintQuery);
        int maintenanceID = 0;
        while(Application.rs.next()) {
            maintenanceID = Application.rs.getInt("MAX(MAINTENANCE_ID)");
            break;
        }
        ++maintenanceID;

        // Insert : MAINTENANCE
        String insertMaintenance = "insert into MAINTENANCE(MAINTENANCE_ID, MAINTENANCE_TYPE, SERVICE_ID) values(" + maintenanceID + ", '" + serviceType + "', " + serviceID + ")";
        //System.out.println(insertMaintenance);
        Application.stmt.executeUpdate(insertMaintenance);


        // Get last appointment ID
        String lastApp = "select MAX(APPOINTMENT_ID) from APPOINTMENT";
        //System.out.println(lastApp);
        Application.rs = Application.stmt.executeQuery(lastApp);
        int appID = 0;
        while(Application.rs.next()) {
            appID = Application.rs.getInt("MAX(APPOINTMENT_ID)");
            break;
        }
        ++appID;

        // Insert : APPOINTMENT
        String insertAppointment = "insert into APPOINTMENT(APPOINTMENT_ID, SERVICE_TYPE, CUSTOMER_ID, SC_ID) values(" + appID + ", '" + serviceType + "', " + customer_id + ", '" + getScId() + "')";
        //System.out.println(insertAppointment);
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
        String insertTimeslot = "insert into TIMESLOT(SERVICE_ID, SERVICE_DATE, SERVICE_TIME, MECHANIC_ID, END_DATE, END_TIME) values(" + serviceID + ", TO_DATE('" + dateFormat.format(chosenDate).toUpperCase() + "','YYYY-MM-DD'), '" + startTime + "', " + mechanicID + ", TO_DATE('" + dateFormat.format(chosenDate).toUpperCase() + "','YYYY-MM-DD'), '" + endTime + "')";
        //System.out.println(insertTimeslot);
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
                //System.out.println(update);
                Application.stmt.executeUpdate(update);
            }
        }
    }

    
    
    private void scheduleMaintenanceProcess(String email,String licensePlate, int currentMileage, String mechanicName) throws Exception {
        // Find type of service & list of basic services based on car details & mileage
        // Find last recorded mileage
        int lastMileage = 0, carYear = 0,customerID  = 0;
        String carMake = "", carModel = "", lastServiceType = "";
        Application.rs = Application.stmt.executeQuery("select customer_id from customer where email = '"+email+"'");
        while(Application.rs.next())
        	customerID = Application.rs.getInt("customer_id");
        System.out.println(customerID);
        String carQuery = "select * from car where customer_id = " + customerID + " and license_no = '" + licensePlate + "'";
        Application.rs = Application.stmt.executeQuery(carQuery);
        while(Application.rs.next()) {
        	//System.out.println("Executed");
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
        	//System.out.println("Executed 2");
            
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
            scheduleService();
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
        	//System.out.println("Executed 3");
            
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
        	//System.out.println("Executed 4");
            
            float time = Application.rs.getFloat("time_hours");
            totalTime += time;
        }

        // Based on list of basic service - find parts required
        Map<Integer, Integer> parts = new HashMap<>();
        for(int basicServiceID : basicServicesIDs) {
            String partsQuery = "select * from basic_services_parts where basic_service_id = " + basicServiceID + " and UPPER(car_make) = UPPER('" + carMake + "') and UPPER(car_model) = UPPER('" + carModel + "')";
            //System.out.println(partsQuery);
            Application.rs = Application.stmt.executeQuery(partsQuery);
            while(Application.rs.next()) {
            	//System.out.println("Executed 5");
                
                int partID = Application.rs.getInt("part_id");
                int qty = Application.rs.getInt("quantity");
                parts.put(partID, qty);
            }
        }

        // Based on list of basic service - find labor charge
        float laborCharge = findLaborCharges(serviceType, licensePlate, basicServiceDetailsQuery,customerID);

        Map<Date, Map<String[], Integer>> datesResult = findServiceDates(totalTime, mechanicName);

        Map<String, Map<Integer, Integer>> scParts = getSCParts(parts);
        int partsFound = 0;
        for(String sc : scParts.keySet()) {
        	//System.out.println("Executed 6");
            
            Map<Integer, Integer> partsMap = scParts.get(sc);
            partsFound += partsMap.size();
        }
        //scheduleMaintenance2(datesResult, licensePlate, currentMileage, mechanicName, totalTime, serviceType, laborCharge, scParts);
        if(partsFound != parts.size()) {
            System.out.println("Insufficient parts. Please try again after " + dateFormat.format(lastDay).toUpperCase() + ".");
        } else if(datesResult.size() == 2) {
            scheduleMaintenance2(datesResult, licensePlate, currentMileage, mechanicName, totalTime, serviceType, laborCharge, scParts,email);
        }
    }
    
    
    
    private Map<String, Map<Integer, Integer>> getSCParts(Map<Integer, Integer> parts) throws Exception {

        Map<String, Map<Integer, Integer>> scParts = new HashMap<>();
        // < ServiceCenterID , <PartID, Qty> >

        for(int partID : parts.keySet()) {
            int qty = parts.get(partID);

            // Check in own SC
            boolean foundInSC = false;
            String scID = getScId();
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

    
    
    private float findLaborCharges(String serviceType, String licensePlate, String basicServiceDetailsQuery,int customer_id) throws Exception {
        float laborCharge = 0.0F;
		boolean firstTime = true;
        if(serviceType != null && !serviceType.isEmpty()) {
        	//System.out.println("Executed 9");
			// Check if this type of service is provided for first time. If yes - laborCharge = 0; Else - calculate
			String prevServQuery = "select MAINTENANCE_TYPE from SERVICERELN inner join MAINTENANCE on SERVICERELN.SERVICE_ID = MAINTENANCE.SERVICE_ID where CUSTOMER_ID = " + customer_id + " and LICENSE_NO = '" + licensePlate + "' and MAINTENANCE_TYPE = '" + serviceType + "'";
			Application.rs = Application.stmt.executeQuery(prevServQuery);

			while(Application.rs.next()) {
				//System.out.println("Executed 10");
				
				firstTime = false;
				break;
			}
		}

        if(firstTime) {
            laborCharge = 0.0F;
        } else {
            Application.rs = Application.stmt.executeQuery(basicServiceDetailsQuery);
            while(Application.rs.next()) {
            	//System.out.println("Executed 11");
    			
                String rate = Application.rs.getString("RATE");
                float time = Application.rs.getFloat("TIME_HOURS");
                if("low".equalsIgnoreCase(rate)) {
                    laborCharge += (time * 50);
                } else if("high".equalsIgnoreCase(rate)) {
                    laborCharge += (time * 65);
                }
            }
        }
        //System.out.println(laborCharge);
		
        return laborCharge;
    }
    
    
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

    

    
    private boolean checkMaintenanceLimit(String slotsQuery, float timeReqd) throws Exception {
        boolean limitReached = false;
        float totalHours = 11.0F;
        Set<String> timeSlots = initTimeSlots();

        // Get list of all maintenance service IDs - so that we only count maintenance IDs and not repairs to find %
        String maintenanceQuery = "select MAINTENANCE_ID from MAINTENANCE";
        //System.out.println(maintenanceQuery);
        Application.rs = Application.stmt.executeQuery(maintenanceQuery);
        List<Integer> maintenanceIDs = new ArrayList<>();
        while(Application.rs.next()) {
        	//System.out.println("Executed 14");
			
            int maintID = Application.rs.getInt("MAINTENANCE_ID");
            maintenanceIDs.add(maintID);
        }

        Application.rs = Application.stmt.executeQuery(slotsQuery);
        //System.out.println(slotsQuery);
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

      private int getTimeIndex(String value, Map<Integer, String> map) throws Exception {
        int index = -1;

        for(Map.Entry entry: map.entrySet()){
            if(value.equals(entry.getValue())){
                index = (Integer) entry.getKey();
            }
        }

        return index;
    }

    
    
    
    
    private Map<Date, Map<String[], Integer>> findServiceDates(float timeReqd, String mechanicName, boolean isMaintenace) throws Exception {

        String scID = getScId();

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
            String slotsQuery = "select * from TIMESLOT where SERVICE_ID in (" + serviceIDQuery + ") and SERVICE_DATE = TO_DATE('" + dateFormat.format(day).toUpperCase() + "','YYYY-MM-DD')";
            //System.out.println(serviceIDQuery);
            //System.out.println(slotsQuery);
            
            Application.rs = Application.stmt.executeQuery(slotsQuery);
            
            if(isMaintenace) {
				if(checkMaintenanceLimit(slotsQuery, timeReqd)) {
					continue; // Skip this day
				}
			}


            List<Integer> mechanics = new ArrayList<>();

            if(mechanicName == null || mechanicName.trim().isEmpty()) {
                // If mechanic not specified, take all mechanics at service center
                String mechQuery = "select MECHANIC_ID from MECHANICAT where SC_ID = '" + scID + "'";
                Application.rs = Application.stmt.executeQuery(mechQuery);
                while(Application.rs.next()) {
                	//System.out.println("Executed 12");
        			
                    int mechanicID = Application.rs.getInt("MECHANIC_ID");
                    mechanics.add(mechanicID);
                }
            } else {
                // If mechanic specified, consider only that mechanic
                String mechQuery = "select MECHANIC_ID from MECHANIC inner join EMPLOYEE on MECHANIC.EMP_ID = EMPLOYEE.EMP_ID and UPPER(EMP_NAME) = UPPER('" + mechanicName + "')";
                Application.rs = Application.stmt.executeQuery(mechQuery);
                while(Application.rs.next()) {
                	//System.out.println("Executed 12");
        			
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
                	//System.out.println("Executed 13");
        			
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



    
    
    
    
    //TODO: Write a query to display the two identified service dates and mechanic name 
    //Receptionist: Schedule Repair
    //TODO: Find two repair dates that are available - write a query for the same.
    private void schedulerepair(String email,String licenseplate,int currentmileage,String mechanicname) throws Exception{
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
			scheduleRepairProcess(option, licenseplate, currentmileage, mechanicname,email);
 //else {
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
           // }
        } else if(option == 8) {
            scheduleService();
        } else {
            System.out.println("Invalid option");
            schedulerepair(email,licenseplate, currentmileage, mechanicname);
        }
   	}
    	
    private void displayDiagnosticReport(int problem, String licensePlate,int customer_id) throws Exception {
    	System.out.println("\nDIAGNOSTIC REPORT:");
    	//Application.rs = Application.stmt.executeQuery("Select customer_id from customer where email = '"+email+"'");
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

    // (For Customer: Schedule Repair (Page 2))
    private void createRepairService(Map<Date, Map<String[], Integer>> datesResult, Date chosenDate, String licensePlate, float laborCharge, float totalTime, Map<String, Map<Integer, Integer>> scParts, int customer_id) throws Exception {

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
		String insertServiceReln = "insert into SERVICERELN(LICENSE_NO, CUSTOMER_ID, SERVICE_ID) values('" + licensePlate +"', " + customer_id + ", " + serviceID + ")";
		Application.stmt.executeUpdate(insertServiceReln);

		//TODO: populate repair table

/*
		// Get last repair ID
		String lastMaintQuery = "select MAX(MAINTENANCE_ID) from MAINTENANCE";
		Application.rs = Application.stmt.executeQuery(lastMaintQuery);
		int maintenanceID = 0;
		while(Application.rs.next()) {
			maintenanceID = Application.rs.getInt("MAX(MAINTENANCE_ID)");
			break;
		}
		++maintenanceID;

		// Insert : REPAIR
		String insertMaintenance = "insert into MAINTENANCE(MAINTENANCE_ID, MAINTENANCE_TYPE, SERVICE_ID) values(" + maintenanceID + ", '" + serviceType + "', " + serviceID + ")";
		Application.stmt.executeUpdate(insertMaintenance);
*/

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
		String insertAppointment = "insert into APPOINTMENT(APPOINTMENT_ID, SERVICE_TYPE, CUSTOMER_ID, SC_ID) values(" + appID + ", '', " + customer_id + ", '" + getScId() + "')";
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
		String insertTimeslot = "insert into TIMESLOT(SERVICE_ID, SERVICE_DATE, SERVICE_TIME, MECHANIC_ID, END_DATE, END_TIME) values(" + serviceID + ", TO_DATE('" + dateFormat.format(chosenDate).toUpperCase() + "','YYYY-MM-DD'), '" + startTime + "', " + mechanicID + ", TO_DATE('" + dateFormat.format(chosenDate).toUpperCase() + "','YYYY-MM-DD'), '" + endTime + "')";
		Application.stmt.executeUpdate(insertTimeslot);

		System.out.println("Repair service scheduled successfully.");
		System.out.println("Date: " + dateFormat.format(chosenDate).toUpperCase());
		System.out.println("Start Time: " + startTime);
		System.out.println("End Time: " + endTime);
		System.out.println("Mechanic ID: " + mechanicID);

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


    
	private void scheduleRepairProcess(int problem, String licensePlate, int currentMileage, String mechanicName, String email) throws Exception {

		
		int customer_id = 0;
		Application.rs = Application.stmt.executeQuery("SELECT customer_id from customer where email = '"+email+"'");
		while(Application.rs.next())
			customer_id = Application.rs.getInt("customer_id");
		
		displayDiagnosticReport(problem, licensePlate,customer_id);
		
		// Find list of basic services for this problem
		String basicServicesQuery = "select REPAIR_SERVICES_LOOKUP.BASIC_SERVICE_ID from REPAIR_SERVICES_LOOKUP where REPAIR_SERVICES_LOOKUP.REPAIR_LOOKUP_ID = " + problem;

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

		// Based on list of basic service - find labor charge
		float laborCharge = findLaborCharges(null, licensePlate, basicServiceDetailsQuery,customer_id); // TODO: modify

		// Finding car make & model for license plate
		String carQuery = "select CAR_MAKE, CAR_MODEL from CAR where LICENSE_NO = '" + licensePlate + "'";
		Application.rs = Application.stmt.executeQuery(carQuery);
		System.out.println(carQuery);
		String carMake = "", carModel = "";
		while(Application.rs.next()) {
			carMake = Application.rs.getString("CAR_MAKE");
			carModel = Application.rs.getString("CAR_MODEL");
		}

		// Based on list of basic service - find parts required
		Map<Integer, Integer> parts = new HashMap<>();
		for(int basicServiceID : basicServicesIDs) {
			String partsQuery = "select * from basic_services_parts where basic_service_id = " + basicServiceID + " and UPPER(car_make) = UPPER('" + carMake + "') and UPPER(car_model) = UPPER('" + carModel + "')";
			System.out.println(partsQuery);
			Application.rs = Application.stmt.executeQuery(partsQuery);
			while(Application.rs.next()) {
				int partID = Application.rs.getInt("part_id");
				int qty = Application.rs.getInt("quantity");
				parts.put(partID, qty);
			}
		}

		Map<Date, Map<String[], Integer>> datesResult = findServiceDates(totalTime, mechanicName, false);

		Map<String, Map<Integer, Integer>> scParts = getSCParts(parts);
		int partsFound = 0;
		for(String sc : scParts.keySet()) {
			Map<Integer, Integer> partsMap = scParts.get(sc);
			partsFound += partsMap.size();
		}
		//scheduleMaintenance2(datesResult, licensePlate, currentMileage, mechanicName, totalTime, serviceType, laborCharge, scParts);
		if(partsFound != parts.size()) {
			System.out.println("Insufficient parts. Please try again after " + dateFormat.format(lastDay).toUpperCase() + ".");
		} else if(datesResult.size() == 2) {
			scheduleRepair2(problem, datesResult, licensePlate, currentMileage, mechanicName, laborCharge, totalTime, scParts,email);
			//scheduleMaintenance2(datesResult, licensePlate, currentMileage, mechanicName, totalTime, serviceType, laborCharge, scParts);
		}
	}

 
    
    // Receptionist: Schedule Repair
    //TODO: Write a query for diagonsitic report and available dates along with mechanic name
    //TODO: Write query to book a repair on the choosen date
    private void scheduleRepair2(int problem, Map<Date, Map<String[], Integer>> datesResult, String licensePlate, int currentMileage, String mechanicName, float laborCharge, float totalTime, Map<String, Map<Integer, Integer>> scParts,String email) throws Exception {
    	
    	int customer_id = 0;
    	Application.rs = Application.stmt.executeQuery("Select customer_id from customer where email = '"+email+"'");
    	while(Application.rs.next())
    		customer_id = Application.rs.getInt("customer_id");
    	displayDiagnosticReport(problem, licensePlate,customer_id);
        System.out.println("\nSCHEDULE REPAIR (Page 2):");
        displayDiagnosticReport(problem, licensePlate,customer_id);
        System.out.println("Repair dates: ");
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
        System.out.println("\t1. Repair on Date");
        System.out.println("\t2. Go Back");
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        switch(option) {
            case 1:
                System.out.println("Choose one of the service dates listed above (enter 1 or 2): ");
                int dateNum = scanner.nextInt();
                if(dateNum == 1 || dateNum == 2) {
                    //createRepairService(datesResult, dateMap.get(dateNum), licensePlate, currentMileage, mechanicName);
					createRepairService(datesResult, dateMap.get(dateNum), licensePlate, laborCharge, totalTime, scParts,customer_id);
                    scheduleService();
                } else {
                    System.out.println("Invalid date chosen. Try again.");
                    scheduleRepair2(problem, datesResult, licensePlate, currentMileage, mechanicName, laborCharge, totalTime, scParts,email);
                }
                break;
            case 2:
                schedulerepair(email,licensePlate, currentMileage, mechanicName);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    //Receptionist: Reschedule Service
    //Query: Write a query to retrieving details of the customer(license plate, service id, service date, service type, service details
    private void rescheduleService() throws Exception {
     System.out.println("Enter customer email address");
     String mail=scanner.next();
     System.out.println("Customer Details");
     displayUpcomingServices(mail);
     System.out.println("Menu");
     System.out.println("1. Pick a service");
     System.out.println("2. Go back");
     int option = scanner.nextInt();
     switch(option) {
     case 1: System.out.println("Choose one of the Service IDs to reschedule the service");
             //String sid=scanner.next();
             int serviceID = scanner.nextInt();
             findRescheduleDate(serviceID,mail);
             break;
     
     case 2: landingPage();break;
     default: System.out.println("Invalid option");System.exit(0);
     }
    
    }
    
    
    private void findRescheduleDate(int serviceID,String email) throws Exception {
        List<Date> dates = new ArrayList<>();
        System.out.println("Choose whether reschedule is for maintenance or for repair(1/2)");
        int choice=scanner.nextInt();
        switch(choice)
        {
        case 1: Application.rs=Application.stmt.executeQuery("select license_no from servicereln where service_id="+serviceID);
                String plate=null;
                while(Application.rs.next())
                {
                	plate=Application.rs.getString("license_no");
                }
                Application.rs=Application.stmt.executeQuery("select  last_recorded_mileage from car where license_no='"+plate+"'");
                int mileage=0;
                while(Application.rs.next())
                { 
                	mileage=Application.rs.getInt("last_recorded_mileage");
                }
                String mname=null;
                Application.rs=Application.stmt.executeQuery("select employee.emp_name from employee,mechanic,timeslot where timeslot.service_id="+serviceID+" AND timeslot.mechanic_id=mechanic.mechanic_id AND mechanic.emp_id=employee.emp_id");
                while(Application.rs.next())
                {
                  mname=Application.rs.getString("emp_name");
                }
                System.out.println("Service ID: " +serviceID);
                System.out.println("License Plate: "+plate);
                System.out.println("Current Mileage: "+mileage);
                System.out.println("Mechanic Name:"+mname); 
                scheduleMaintenanceProcess(email,plate,mileage,mname);break;
        case 2: Application.rs=Application.stmt.executeQuery("select license_no from servicereln where service_id="+serviceID);
        String rplate=null;
        while(Application.rs.next())
        {
        	rplate=Application.rs.getString("license_no");
        }
        Application.rs=Application.stmt.executeQuery("select  last_recorded_mileage from car where license_no='"+rplate+"'");
        int rmileage=0;
        while(Application.rs.next())
        { rmileage=Application.rs.getInt("last_recorded_mileage");
        }
        String rmname=null;
        Application.rs=Application.stmt.executeQuery("select employee.emp_name from employee,mechanic,timeslot where timeslot.service_id="+serviceID+" AND timeslot.mechanic_id=mechanic.mechanic_id AND mechanic.emp_id=employee.emp_id");
        while(Application.rs.next())
        {
          rmname=Application.rs.getString("emp_name");
        }
        System.out.println("Service ID: " +serviceID);
        System.out.println("License Plate: "+rplate);
        System.out.println("Current Mileage: "+rmileage);
        System.out.println("Mechanic Name:"+rmname); 
        scheduleMaintenanceProcess(email,rplate,rmileage,rmname);break; 
        
        }
        
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
                    landingPage();
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

    
    private void displayUpcomingServices(String email) throws Exception {
    	int customer_id = 0;
    	Application.rs = Application.stmt.executeQuery("select customer_id from customer where email = '"+email+"'");
    	while(Application.rs.next())
    		customer_id = Application.rs.getInt("customer_id");
    	long millis=System.currentTimeMillis();  
    	java.sql.Date date=new java.sql.Date(millis);  
    	System.out.println("Current Date"+date); 
    	//System.out.println("The current date is"+dateFormat.format(currentdate));
    	Application.rs=Application.stmt.executeQuery("select license_no, service_id from servicereln where customer_id='"+customer_id+"'");
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
    	//int flag=0,flag1=0;
    	System.out.println("Service ID size: "+upcoming_services.size());
    	for (int b=0;b<upcoming_services.size();b++)
    	{  
    	  Application.rs=Application.stmt.executeQuery("select license_no from servicereln where service_id="+sid.get(b));
    	  while(Application.rs.next())
    	  {String x=Application.rs.getString("license_no");
    	    lplate.add(x);
       	  }
    	  Application.rs=Application.stmt.executeQuery("select estimated_hours from services where service_id="+sid.get(b));
    	  while(Application.rs.next())
    	  {int y=Application.rs.getInt("estimated_hours");
    		  ehours.add(y);
    	  }
    	  String Query="select problem from repair where service_id="+sid.get(b);
    	  System.out.println(Query);
       /*Application.rs=Application.stmt.executeQuery(Query);
   
          while(Application.rs.next())
    		  { String c= Application.rs.getString("problem");
    		  System.out.println("Problem"+c); 
    		  problem.add(c); 
    		  }*/
    	  
    	  
    	  //Application.rs=Application.stmt.executeQuery("select maintenance_type from maintenance where service_id="+sid.get(b));
    	 
    	 /*if(Application.rs.next())
    	 {	  
    	   while(Application.rs.next())
    	  { String d= Application.rs.getString("maintenance_type");
              
    	     { mtype.add(d);}
    	  }}  else
    	  {
    		{ mtype.add("No Maintenance");
    		
    		}
    	  }*/
    	  
    }
    	


    		
    	System.out.println("Upcoming Services for this customer" +customer_id);
    	
  	  for(int j=0;j<upcoming_services.size();j++)
  	  {System.out.print("Date: "+upcoming_services.get(j));
  	   System.out.print("\t Service ID: " +sid.get(j));
  	   System.out.print("\t License Plate: "+lplate.get(j));
  	   System.out.print("\t Estimated hours for this service: "+ehours.get(j));
  	   //System.out.print("\t Repair: "+problem.get(j));
  	   //System.out.print("\t Maintenance Type: "+mtype.get(j));
  	   System.out.println();
  	  }
    	  
    }
    
    

    //Receptionist: Reschedule Service
    //TODO: Write a query to identify 2 available dates based on the service id  and display them.
    //TODO: Write a query to reschedule the service date based on the choice of the customer.
    
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
    	 //System.out.println("Customer ID");
    	 customer_id = Application.rs.getString("customer_id");
     }
     rr = sql.executeQuery("select service_id from services");
     while (rr.next()) {

         //String id=Application.currentUser.userID;

         String serviceID = rr.getString("service_id");
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
         {
        	 startdate=Application.rs.getDate("service_date");
         }

         int lookup_id=0;

         String make=car_make.toLowerCase();
         String model=car_model.toLowerCase();
         Application.rs=Application.stmt.executeQuery("select lookup_id from service_type_lookup where car_make='"+make+"' AND car_model='"+model+"'");
         System.out.println("The car make"+(make));
         System.out.println("The car model"+(model));


         while(Application.rs.next())
         {
        	// System.out.println("Lookup ID");
        	
             lookup_id=Application.rs.getInt("lookup_id");

         }


         Application.rs=Application.stmt.executeQuery("select maintenance_type from maintenance where service_id="+serviceID);
         String type="";
         while(Application.rs.next())
         {
        	 //System.out.println("Maintenance type");
        	 type=Application.rs.getString("maintenance_type");

         }
         List<Integer>bid=new ArrayList();


         Application.rs=Application.stmt.executeQuery("select basic_service_id from service_type_services where service_type='"+type+"'AND lookup_id="+lookup_id);
         int basicid=0;
         while(Application.rs.next())
         {
        	 //System.out.println("Basic service");
        	 basicid=Application.rs.getInt("basic_service_id");
             bid.add(basicid);

         }


         List<Integer>part=new ArrayList();
         List<Integer>quantity=new ArrayList();

         for(int i=0;i<bid.size();i++)
         { 
        	 Application.rs=Application.stmt.executeQuery("select part_id,quantity from basic_services_parts where basic_service_id="+bid.get(i)+"AND car_make='"+car_make+"' AND car_model='"+car_model+"'");
             int parts=0,count=0;
             while(Application.rs.next())
             {
            	 //System.out.println("part id");
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
             {
            	 String temp;
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
             {
            	 temp1+=temp1*hours.get(b);
                 servicecost.add(temp+temp1);
             }
             totalcost+=servicecost.get(b);
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
             // System.out.println("The Customer ID is:" +id);
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
             //System.out.println("The Customer ID is:" +id);
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
        boolean valid = true;
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
    	boolean valid=true;
    	//licenseplate = "IRM-1212";
    	boolean emailValid = validateemail(email);
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
