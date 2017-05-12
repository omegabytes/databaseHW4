import java.sql.*;
import java.util.*;

public class SQLiteJDBC {
    public static void main(String[] args) {
        int selection = 0;
        int customerID;
        int trackID;
        double price;

        String artistName;
        String sql;

        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner input = new Scanner(System.in);

        while (selection != 4) {

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

                        //search db for album titles by this artist
                        sql = "SELECT al.Title, al.AlbumId " +
                                "FROM ALBUM al " +
                                "WHERE al.ArtistId IN " +
                                "(SELECT a.ArtistId " +
                                "FROM Artist a " +
                                "WHERE al.ArtistId = a.ArtistId " +
                                "AND a.Name = '" + artistName + "');";

                        rs = stmt.executeQuery(sql);

                        //indicate empty output with message
                        if (!rs.next()) {
                            System.out.println("Could not find any albums by that artist");
                        }
                        //output album title and album ID
                        while (rs.next()) {
                            String albumTitle = rs.getString("Title");
                            int albumId = rs.getInt("AlbumId");

                            System.out.println(String.format("%-15s| %s", "Album ID: " + albumId,
                                    "Album Title: " + albumTitle));
                        }
                        //multiple artists displayed as headers with artist name and ID

                        //close and cleanup database
                        System.out.println();

                        rs.close();
                        stmt.close();
                        c.close();
                        break;
                    case 2: //purchase history
                        System.out.println("Get customer purchase history");
                        //todo: obtain purchase history for a customer
                        //prompt user for customer ID
                        System.out.print("Please enter the customer ID: ");
                        customerID = input.nextInt();

                        //search tables for customer purchase history

                        sql = "SELECT t.Name, al.Title, il.Quantity, i.InvoiceDate " +
                                "FROM Track t, Album al, InvoiceLine il, Invoice i " +
                                "WHERE t.AlbumId = al.AlbumId " +
                                "AND t.TrackId = il.TrackId " +
                                "AND il.InvoiceId = i.InvoiceId " +
                                "AND i.CustomerId = '" + customerID + "'";

                        rs = stmt.executeQuery(sql);

                        //indicate empty output with message
                        if (!rs.next()) {
                            System.out.println("Could not find a customer by that ID, or the customer has no purchases.\n");
                        }

                        //output track name, album name, quantity, and invoice date
                        while (rs.next()) {
                            String trackName = rs.getString("Name");
                            String albumName = rs.getString("Title");
                            int quantity = rs.getInt("Quantity");
                            String date = rs.getString("InvoiceDate");

                            System.out.println(String.format("%-40s| %s", "Track: " + trackName,
                                    "Album: " + albumName));
                            System.out.println(String.format("%-40s| %s", "Quantity: " + quantity,
                                    "Date: " + date));
                            System.out.println("--------------------------------------------------------------------------");
                        }
                        System.out.println();

                        rs.close();
                        stmt.close();
                        c.close();
                        break;
                    case 3: //update price
                        System.out.println("Update a track price");
                        //todo: update track price
                        //prompt user for track ID
                        System.out.print("Please enter the track ID: ");
                        trackID = input.nextInt();
                        input.nextLine(); //nextInt leaves a trailing end of line token, this takes care of it


                        //display current unit price for track
                        sql = " SELECT DISTINCT il.UnitPrice " +
                                "FROM InvoiceLine il " +
                                "WHERE il.TrackId = '" + trackID + "';";

                        rs = stmt.executeQuery(sql);

                        //indicate empty output with message
                        if (!rs.next()) {
                            System.out.println("Could not find a track by that ID");
                        } else {
                            //prompt user for new price
                            price = rs.getDouble("UnitPrice");
                            System.out.print("Current Price: $" + price + " New Price: $");

                            price = input.nextDouble();

                            //update the record
                            sql = "UPDATE InvoiceLine " +
                                    "SET UnitPRice = " + price +
                                    " WHERE TrackId = '" + trackID + "'";
                            stmt.executeUpdate(sql);
                            c.commit();

                            //display the new record
                            rs = stmt.executeQuery("SELECT * FROM InvoiceLine WHERE TrackId = '3456';");
                            System.out.println("Updated price: $" + rs.getDouble("UnitPrice") + "\n");
                        }

                        rs.close();
                        stmt.close();
                        c.close();
                        break;
                    case 4: //exit
                        System.out.println("Goodbye!");
                        System.exit(0);
                        break;
                    default: //bad input
                        System.out.println("Unknown selection. Please enter an integer between 1 and 4");
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
    }
}

//todo: OPTIONAL obtain tracks of an album title
//todo: OPTIONAL update track price - batch
