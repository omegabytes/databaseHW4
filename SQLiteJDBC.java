import java.sql.*;
import java.util.Scanner;

public class SQLiteJDBC {
    public static void main(String[] args) {
        //create menu
        int selection;
        int customerID;
        int trackID;
        String artistName;

        Scanner input = new Scanner(System.in);

        System.out.println("Please select from the following choices: ");
        System.out.println("------------------------------------------");
        System.out.println("1) Get albums by artist");
        System.out.println("2) Get customer purchase history");
        System.out.println("3) Update a track price");
        System.out.println("4) Quit");

        selection = input.nextInt();

        switch (selection) {
            case 1: //album by artist
                System.out.println("Get album by artist");
                //todo: obtain album titles based on artist name
                //prompt user for artist name
                System.out.println("Please enter artist's name: ");
                artistName = input.next();

                //search db for album titles by this artist
                //output album title and album ID
                //indicate empty output with message
                //multiple artists displayed as headers with artist name and ID
                break;
            case 2: //purchase history
                System.out.println("Get customer purchase history");
                //todo: obtain purchase history for a customer
                //prompt user for customer ID
                System.out.println("Please enter the customer ID: ");
                customerID = input.nextInt();

                //search tables for customer purchase history
                //output track name, album name, quantity, and invoice date
                //indicated empty output with message
                break;
            case 3: //update price
                System.out.println("Update a track price");
                //todo: update track price
                //prompt user for track ID
                System.out.println("Please enter the track ID: ");
                trackID = input.nextInt();

                //display current unit price for track
                //prompt user for new price
                //update the record
                //display the new record
                break;
            case 4: //exit
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default: //bad input
                System.out.println("Unknown selection. Please enter an integer between 1 and 4");
        }

        //database operations
        Connection c = null;
        Statement stmt = null;
        try {
            //opens the db connection
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db"); //todo: change this to chinook.db
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");


            stmt = c.createStatement();
//            //creates the table
//            String sql = "CREATE TABLE COMPANY " +
//                         "(ID INT PRIMARY KEY       NOT NULL, " +
//                         " NAME           TEXT      NOT NULL, " +
//                         " AGE            INT       NOT NULL, " +
//                         " ADDRESS        CHAR(50), " +
//                         " SALARY         REAL)";

            //inserts records into table
            String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                         "VALUES (1, 'Paul', 32, 'California', 20000.00);";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                    "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                    "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                    "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
}

//todo: OPTIONAL obtain tracks of an album title
//todo: OPTIONAL update track price - batch
