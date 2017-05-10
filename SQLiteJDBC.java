import java.sql.*;
import java.util.Scanner;

public class SQLiteJDBC {
    public static void main(String[] args) {
        int selection;
        int customerID;
        int trackID;

        String artistName;
        String sql;

        Connection c = null;
        Statement stmt = null;
        Scanner input = new Scanner(System.in);


        //menu
        System.out.println("Please select from the following choices: ");
        System.out.println("------------------------------------------");
        System.out.println("1) Get albums by artist");
        System.out.println("2) Get customer purchase history");
        System.out.println("3) Update a track price");
        System.out.println("4) Quit\n");
        System.out.print("Enter your choice: ");

        selection = input.nextInt();
        input.nextLine(); //nextInt leaves a trailing end of line token, this takes care of it

        try {
            //opens the db connection
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:chinook.db");
            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");

            stmt = c.createStatement();

        switch (selection) {
            case 1: //album by artist
                System.out.println("Get album by artist");
                //prompt user for artist name
                System.out.print("Please enter artist's name: ");
                artistName = input.nextLine();
                System.out.println(artistName);

                //search db for album titles by this artist
                sql = "SELECT al.Title, al.AlbumId " +
                        "FROM ALBUM al " +
                        "WHERE al.ArtistId IN " +
                            "(SELECT a.ArtistId " +
                            "FROM Artist a " +
                            "WHERE al.ArtistId = a.ArtistId " +
                            "AND a.Name = '" + artistName + "');";

                ResultSet rs = stmt.executeQuery(sql);

                //indicate empty output with message
                if (!rs.next()) {
                    System.out.println("Could not find any albums by that artist");
                }
                //output album title and album ID
                while (rs.next()) {
                    String albumTitle = rs.getString("Title");
                    int    albumId    = rs.getInt("AlbumId");

                    System.out.println("Album Title: " + albumTitle + ", Album ID: " + albumId);
                }
                //multiple artists displayed as headers with artist name and ID

                //close and cleanup database
                rs.close();
                stmt.close();
                c.close();
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
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            //opens the db connection
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:chinook.db");
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//
//            stmt = c.createStatement();

//            //inserts records into table
//            String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
//                         "VALUES (1, 'Paul', 32, 'California', 20000.00);";
//            stmt.executeUpdate(sql);
//
//            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
//                    "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
//            stmt.executeUpdate(sql);
//
//            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
//                    "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
//            stmt.executeUpdate(sql);
//
//            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
//                    "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
//            stmt.executeUpdate(sql);
//
//            stmt.close();
//            c.commit();
//            c.close();

//            //select operation
//            ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");
//            while (rs.next()) {
//                int id          = rs.getInt("id");
//                String name     = rs.getString("name");
//                int age         = rs.getInt("age");
//                String address  = rs.getString("address");
//                float salary    = rs.getFloat("salary");
//
//                System.out.println( "ID = " + id );
//                System.out.println( "NAME = " + name );
//                System.out.println( "AGE = " + age );
//                System.out.println( "ADDRESS = " + address );
//                System.out.println( "SALARY = " + salary + "\n");
//            }
//            rs.close();
//            stmt.close();
//            c.close();

//            //update operation
//            String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
//            stmt.executeUpdate(sql);
//            c.commit();
//
//            ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;");
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String  name = rs.getString("name");
//                int age  = rs.getInt("age");
//                String  address = rs.getString("address");
//                float salary = rs.getFloat("salary");
//                System.out.println( "ID = " + id );
//                System.out.println( "NAME = " + name );
//                System.out.println( "AGE = " + age );
//                System.out.println( "ADDRESS = " + address );
//                System.out.println( "SALARY = " + salary );
//                System.out.println();
//            }
//            rs.close();
//            stmt.close();
//            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}

//todo: OPTIONAL obtain tracks of an album title
//todo: OPTIONAL update track price - batch
