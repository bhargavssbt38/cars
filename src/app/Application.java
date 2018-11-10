package app;

import pages.DisplayPage;
import users.User;
import java.sql.*;


/*
EXAMPLES:

stmt.executeUpdate("CREATE TABLE COFFEES10 " + "(COF_NAME VARCHAR(32), SUP_ID INTEGER, " + "PRICE FLOAT, SALES INTEGER, TOTAL INTEGER)");

stmt.executeUpdate("INSERT INTO COFFEES1 " + "VALUES ('Colombian', 101, 7.99, 0, 0)");

// GET DATA
        rs = stmt.executeQuery("SELECT COF_NAME, PRICE FROM COFFEES1");
		while (rs.next()) {
		    String s = rs.getString("COF_NAME");
		    float n = rs.getFloat("PRICE");
		    System.out.println(s + "   " + n);
		}

 */

public class Application {

    static final String jdbcURL = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";
    public static Connection conn = null;
    public static Statement stmt = null;
    public static ResultSet rs = null;

    public static User currentUser = null; // User ID of current logged in user

    public static void main(String[] args) {
        startApplication();
    }

    private static void startApplication() {

        System.out.println("Welcome to CARS!");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String user = "aravich7";
            String passwd = "12345";
            conn = DriverManager.getConnection(jdbcURL, user, passwd);
            stmt = conn.createStatement();
            DisplayPage.homePage();
        } catch(Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        } finally {
            close(rs);
            close(stmt);
            close(conn);
        }
    }

    static void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); } catch(Throwable whatever) {}
        }
    }

    static void close(Statement st) {
        if(st != null) {
            try { st.close(); } catch(Throwable whatever) {}
        }
    }

    static void close(ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } catch(Throwable whatever) {}
        }
    }

}
