/* Brandon Sun
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

// gui implementation
// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;

/* class GameRentalGUI {
    private GameRental esql;
    private JFrame frame;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextArea outputArea;
    private String authorisedUser;

    public GameRentalGUI(GameRental esql) {
        this.esql = esql;
    }

    public void createUI() {
        frame = new JFrame("Game Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));
        loginPanel.add(new JLabel("Login:"));
        loginField = new JTextField();
        loginPanel.add(loginField);
        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);
        JButton loginButton = new JButton("Log In");
        loginPanel.add(loginButton);
        frame.add(loginPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        loginButton.addActionListener(e -> handleLogin());

        frame.setVisible(true);
    }

    private void handleLogin() {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());

        try {
            if (logIn(login, password)) {
                authorisedUser = login;
                showMainMenu();
            } else {
                showMessage("Invalid login credentials!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean logIn(String login, String password) throws SQLException {
        String query = String.format("SELECT * FROM Users WHERE login = '%s' AND password = '%s'", login, password);
        int userNum = esql.executeQuery(query);
        return userNum > 0;
    }

    private void showMainMenu() {
        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(5, 1));

        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(e -> handleViewProfile());
        mainMenu.add(viewProfileButton);

        if (isManager(authorisedUser)) {
            JButton viewOtherProfileButton = new JButton("View Other User's Profile");
            viewOtherProfileButton.addActionListener(e -> handleViewOtherProfile());
            mainMenu.add(viewOtherProfileButton);
        }

        frame.getContentPane().removeAll();
        frame.getContentPane().add(mainMenu, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void handleViewProfile() {
        try {
            String query = String.format("SELECT login, password, role, favGames, phoneNum, numOverDueGames FROM Users WHERE login = '%s'", authorisedUser);
            List<List<String>> result = esql.executeQueryAndReturnResult(query);
            showResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleViewOtherProfile() {
        String login = JOptionPane.showInputDialog(frame, "Enter the login of the user to view:");
        if (login != null && !login.trim().isEmpty()) {
            try {
                String query = String.format("SELECT login, password, role, favGames, phoneNum, numOverDueGames FROM Users WHERE login = '%s'", login);
                List<List<String>> result = esql.executeQueryAndReturnResult(query);
                showResult(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isManager(String login) {
        try {
            String query = String.format("SELECT role FROM Users WHERE login = '%s'", login);
            List<List<String>> result = esql.executeQueryAndReturnResult(query);
            if (!result.isEmpty()) {
                String role = result.get(0).get(0);
                return role.trim().equalsIgnoreCase("manager");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showResult(List<List<String>> result) {
        outputArea.setText("");
        for (List<String> row : result) {
            for (String col : row) {
                outputArea.append(col + "\t");
            }
            outputArea.append("\n");
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }
}
*/

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class GameRental {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of GameRental store
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public GameRental(String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end GameRental

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQueryAndPrintResult (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
		 if(outputHeader){
			for(int i = 1; i <= numCol; i++){
			System.out.print(rsmd.getColumnName(i) + "\t");
			}
			System.out.println();
			outputHeader = false;
		 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close();
      return rowCount;
   }//end executeQuery

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and returns the results as
    * a list of records. Each record in turn is a list of attribute values
    *
    * @param query the input query string
    * @return the query result as a list of records
    * @throws java.sql.SQLException when failed to execute the query
    */
   public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and saves the data returned by the query.
      boolean outputHeader = false;
      List<List<String>> result  = new ArrayList<List<String>>();
      while (rs.next()){
        List<String> record = new ArrayList<String>();
		for (int i=1; i<=numCol; ++i)
			record.add(rs.getString (i));
        result.add(record);
      }//end while
      stmt.close ();
      return result;
   }//end executeQueryAndReturnResult

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and returns the number of results
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
       // creates a statement object
       Statement stmt = this._connection.createStatement ();

       // issues the query instruction
       ResultSet rs = stmt.executeQuery (query);

       int rowCount = 0;

       // iterates through the result set and count nuber of results.
       while (rs.next()){
          rowCount++;
       }//end while
       stmt.close ();
       return rowCount;
   }

   /**
    * Method to fetch the last value from sequence. This
    * method issues the query to the DBMS and returns the current
    * value of sequence used for autogenerated keys
    *
    * @param sequence name of the DB sequence
    * @return current value of a sequence
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int getCurrSeqVal(String sequence) throws SQLException {
	Statement stmt = this._connection.createStatement ();

	ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
	if (rs.next())
		return rs.getInt(1);
	return -1;
   }

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            GameRental.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if

      Greeting();
      GameRental esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the GameRental object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new GameRental (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
            System.out.println("\n\n*******************************************************");
            System.out.println("                       MAIN MENU                        ");
            System.out.println("*******************************************************");
            System.out.println("1. Create user");
            System.out.println("2. Log in");
            System.out.println("9. < EXIT");
            System.out.println("*******************************************************\n");
            String authorisedUser = null;
            switch (readChoice()){
               case 1: CreateUser(esql); break;
               case 2: authorisedUser = LogIn(esql); break;
               case 9: keepon = false; break;
               default : System.out.println("Unrecognized choice!\n"); break;
            }//end switch
            if (authorisedUser != null) {
              boolean usermenu = true;
              while(usermenu) {
               System.out.println("\n\n*******************************************************");
               System.out.println("                       MAIN MENU                        ");
               System.out.println("*******************************************************");
               System.out.println("1. View Profile");
               System.out.println("2. Update Profile");
               System.out.println("3. View Catalog");
               System.out.println("4. Place Rental Order");
               System.out.println("5. View Full Rental Order History");
               System.out.println("6. View Past 5 Rental Orders");
               System.out.println("7. View Rental Order Information");
               System.out.println("8. View Tracking Information");

                //the following functionalities basically used by employees & managers
                System.out.println("9. Update Tracking Information");

                //the following functionalities basically used by managers
                System.out.println("10. Update Catalog");
                System.out.println("11. Update User");

                System.out.println(".........................");
                System.out.println("20. Log out");
                System.out.println("*******************************************************\n");
                switch (readChoice()){
                   case 1: viewProfile(esql, authorisedUser); break;
                   case 2: updateProfile(esql, authorisedUser); break;
                   case 3: viewCatalog(esql); break;
                   case 4: placeOrder(esql, authorisedUser); break;
                   case 5: viewAllOrders(esql, authorisedUser); break;
                   case 6: viewRecentOrders(esql, authorisedUser); break;
                   case 7: viewOrderInfo(esql); break;
                   case 8: viewTrackingInfo(esql); break;
                   case 9: updateTrackingInfo(esql, authorisedUser); break;
                   case 10: updateCatalog(esql, authorisedUser); break;
                   case 11: updateUser(esql, authorisedUser); break;



                   case 20: usermenu = false; break;
                   default : System.out.println("Unrecognized choice!"); break;
                }
              }
            }
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main

   /* public static void main(String[] args) {
      if (args.length != 3) {
         System.err.println("Usage: " +
            "java [-classpath <classpath>] " + GameRental.class.getName() + " <dbname> <port> <user>");
         return;
      }

      Greeting();
      final GameRental[] esql = new GameRental[1];  // Use a single-element array
      try {
         Class.forName("org.postgresql.Driver").newInstance();
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql[0] = new GameRental(dbname, dbport, user, "");

         if (GraphicsEnvironment.isHeadless()) {
            System.out.println("No graphical environment detected. Running in headless mode.");
            System.exit(1);
         } else {
            SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                  new GameRentalGUI(esql[0]).createUI();
               }
            });
         }
      } catch (Exception e) {
         System.err.println(e.getMessage());
      } finally {
         try {
            if (esql[0] != null) {
               System.out.print("Disconnecting from database...");
               esql[0].cleanup();
               System.out.println("Done\n\nBye !");
            }
         } catch (Exception e) {
               // ignored.
         }
      }
   }
   */

   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting


   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         } catch (Exception e) {
            System.out.println("Your input is invalid!\n");
            continue;
         }//end try
      } while (true);
      return input;
   }//end readChoice

   /*
    * Creates a new user
    **/
   // utilizes indexes on login and roles for faster searches
   public static void CreateUser(GameRental esql) {
      try {
         String login;
         boolean userExists = true;
         
         // do-while loop that checks to see if the user input is unique and not a user that already exists
         do {
            // read in user input
            System.out.print("\tEnter user login: ");
            login = in.readLine();
            
            // sql query to check if user already exists
            String checkUserQuery = String.format("SELECT * FROM Users WHERE login = '%s'", login);
            int userCount = esql.executeQuery(checkUserQuery);
            
            // if the user already exists makes the user reinput
            if (userCount > 0) {
               System.out.println("User login already exists. Please enter a different login.");
            } 
            else {
               userExists = false;
            }
         } while (userExists);
         
         // read in user input after checking if user already exists
         System.out.print("\tEnter user password: ");
         String password = in.readLine();
         System.out.print("\tEnter user role: ");
         String role = in.readLine();
         System.out.print("\tEnter user phone number: ");
         String phoneNum = in.readLine();
         
         // sql query that inputs the new user into the users table
         String query = String.format("INSERT INTO Users (login, password, role, phoneNum) VALUES ('%s','%s','%s','%s')", login, password, role, phoneNum);
         esql.executeUpdate(query);
         System.out.println("User successfully created!");
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }
   }//end CreateUser


   /*
    * Check log in credentials for an existing user
    * @return User login or null is the user does not exist
    **/
   // utilizes indexes on login for faster user searches
   public static String LogIn(GameRental esql) {
      try {
         // read in user input
         System.out.print("\tEnter user login: ");
         String login = in.readLine();
         System.out.print("\tEnter user password: ");
         String password = in.readLine();

         // sql query to check if user input is valid
         String query = String.format("SELECT * FROM Users WHERE login = '%s' AND password = '%s'", login, password);
         int userNum = esql.executeQuery(query);
         // if user input is valid outputs success
         if (userNum > 0) {
            System.out.println("Login successful!\n");
            return login;
         } 
         // if user input invalid output error
         else {
            System.out.println("Invalid login credentials!\n");
            return null;
         }
      } catch (Exception e) {
         System.err.println(e.getMessage());
         return null;
      }
   }//end

// Rest of the functions definition go in here

   // utilizes indexes on login for faster user searches
   public static void viewProfile(GameRental esql, String authorisedUser) {
      try {
         // read in user input
         System.out.print("\tEnter user login: ");
         String login = in.readLine();

         if (!login.equals(authorisedUser) && !isManager(esql, authorisedUser)) {
            System.out.println("You are not authorized to view another user's profile!");
            return;
         }

         // sql query to check if user input is valid and retrieve information from input
         String query = String.format("SELECT * FROM Users WHERE login = '%s'", login);
         List<List<String>> result = esql.executeQueryAndReturnResult(query);

         if (result.isEmpty()) {
            System.out.println("User not found!");
            return;
         }

         System.out.println("\n========== User Profile ==========");
         List<String> userProfile = result.get(0);
         System.out.printf("Login: %s\n", userProfile.get(0));
         System.out.printf("Password: %s\n", userProfile.get(1));
         System.out.printf("Role: %s\n", userProfile.get(2));
         System.out.printf("Favorite Games: %s\n", userProfile.get(3));
         System.out.printf("Phone Number: %s\n", userProfile.get(4));
         System.out.printf("Number of Overdue Games: %s\n", userProfile.get(5));
         System.out.println("==================================\n");

      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   // utilizes indexes on login for faster user searches
   public static void updateProfile(GameRental esql, String authorisedUser) {
      try {
         // read in user input
         System.out.print("\tEnter user login: ");
         String login = in.readLine();

         // checks to see if user is trying to access a login different than their own
         if (!login.equals(authorisedUser) && !isManager(esql, authorisedUser)) {
            System.out.println("You are not authorized to update another user's profile!");
            return;
         }

         // sql query that takes data already in the table and stores it in a list
         String query = String.format("SELECT password, phoneNum FROM Users WHERE login = '%s'", login);
         List<List<String>> userDetails = esql.executeQueryAndReturnResult(query);

         // checks to see if user entered a valid user
         if (userDetails.isEmpty()) {
            System.out.println("User not found!");
            return;
         }

         String currentPassword = userDetails.get(0).get(0);
         String currentPhoneNum = userDetails.get(0).get(1);

         // takes in user input
         System.out.print("\tEnter new password (leave blank to keep current): ");
         String newPassword = in.readLine();
         // if left blank the data in the table doesn't get updated
         if (newPassword.isEmpty()) {
            newPassword = currentPassword;
         }

         System.out.print("\tEnter new phone number (leave blank to keep current): ");
         String newPhoneNum = in.readLine();
         // if left blank the data in the table doesn't get updated
         if (newPhoneNum.isEmpty()) {
            newPhoneNum = currentPhoneNum;
         }

         // sql query updates the current data with new data from user inputs
         String updateQuery = String.format("UPDATE Users SET password = '%s', phoneNum = '%s' WHERE login = '%s'", newPassword, newPhoneNum, login);
         esql.executeUpdate(updateQuery);
         System.out.println("Profile updated successfully!");
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   // // utilizes indexes on genre and price for faster filterting
   public static void viewCatalog(GameRental esql) {
      try {
         // read in user input
         System.out.print("\tEnter genre (leave blank for all): ");
         // new function that makes the query non case sensitive
         String genre = in.readLine().trim();
         if (!genre.isEmpty()) {
            genre = genre.substring(0, 1).toUpperCase() + genre.substring(1).toLowerCase();
         }

         // read in user input
         System.out.print("\tEnter maximum price (leave blank for no limit): ");
         String maxPriceInput = in.readLine().trim();
         // sql query to access catalog
         String query = "SELECT * FROM Catalog";

         boolean hasGenre = !genre.isEmpty();
         boolean hasMaxPrice = !maxPriceInput.isEmpty();

         // if there is user specification add where clause to sql query
         if (hasGenre || hasMaxPrice) {
            query += " WHERE";
            if (hasGenre) {
               query += String.format(" genre = '%s'", genre);
               if (hasMaxPrice) {
                  query += " AND";
               }
            }
            if (hasMaxPrice) {
               query += String.format(" price <= %s", maxPriceInput);
            }
         }

         query += " ORDER BY price";
         esql.executeQueryAndPrintResult(query);
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   // utilizes indexes on rentalorderID and gameID for faster joins and searches
   public static void placeOrder(GameRental esql, String authorisedUser) {
      try {
         // read in user input
         System.out.print("\tEnter number of games: ");
         int numOfGames = Integer.parseInt(in.readLine());

         // creates rental order id
         double totalPrice = 0.0;
         String rentalOrderID = "gamerentalorder" + System.currentTimeMillis();
         String orderTimestamp = "current_timestamp";
         String dueDate = "current_timestamp + interval '7 days'";

         // sql query to insert rental order id into rentalorder table
         String rentalOrderQuery = String.format("INSERT INTO RentalOrder (rentalOrderID, login, noOfGames, totalPrice, orderTimestamp, dueDate) VALUES ('%s', '%s', %d, 0, %s, %s)", rentalOrderID, authorisedUser, numOfGames, orderTimestamp, dueDate);
         esql.executeUpdate(rentalOrderQuery);

         for (int i = 0; i < numOfGames; i++) {
            // read in user input
            System.out.print("\tEnter game ID: ");
            String gameID = in.readLine();
            System.out.print("\tEnter units ordered: ");
            int unitsOrdered = Integer.parseInt(in.readLine());

            //sql query to retrieve game price
            String gameQuery = String.format("SELECT price FROM Catalog WHERE gameID = '%s'", gameID);
            List<List<String>> gameResult = esql.executeQueryAndReturnResult(gameQuery);
            double gamePrice = Double.parseDouble(gameResult.get(0).get(0));

            totalPrice += gamePrice * unitsOrdered;

            // sql query to insert into gamesinorder table
            String gamesInOrderQuery = String.format("INSERT INTO GamesInOrder (rentalOrderID, gameID, unitsOrdered) VALUES ('%s', '%s', %d)", rentalOrderID, gameID, unitsOrdered);
            esql.executeUpdate(gamesInOrderQuery);
         }

         // sql query to update the total price in the rental order
         String updateRentalOrderQuery = String.format("UPDATE RentalOrder SET totalPrice = %f WHERE rentalOrderID = '%s'", totalPrice, rentalOrderID);
         esql.executeUpdate(updateRentalOrderQuery);

         System.out.println("Order placed successfully!");
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   // utilizes indexes on login for faster user searches
   public static void viewAllOrders(GameRental esql, String authorisedUser) {
      try {
         // read in user input
         System.out.print("\tEnter user login: ");
         String login = in.readLine();

         // checks if user is trying to access another user's order information
         if (!login.equals(authorisedUser) && !isManager(esql, authorisedUser)) {
            System.out.println("You are not authorized to view another user's orders!");
            return;
         }

         // sql query to retrieve all rental orders of a user from user input in rentalorder table
         String query = String.format("SELECT * FROM RentalOrder WHERE login = '%s'", login);
         List<List<String>> result = esql.executeQueryAndReturnResult(query);

         if (result.isEmpty()) {
            System.out.println("No rental orders found!");
            return;
         }

         System.out.println("\n========== All Rental Orders ==========");
         for (int i = 0; i < result.size(); i++) {
            List<String> order = result.get(i);
            System.out.printf("Order ID: %s\n", order.get(0));
            System.out.printf("Login: %s\n", order.get(1));
            System.out.printf("Number of Games: %s\n", order.get(2));
            System.out.printf("Total Price: %s\n", order.get(3));
            System.out.printf("Order Timestamp: %s\n", order.get(4));
            System.out.printf("Due Date: %s\n", order.get(5));
            System.out.println("----------------------------------");
         }
         System.out.println("======================================\n");

      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   // utilizes indexes on login and orderTimestamp for faster user and time searches
   public static void viewRecentOrders(GameRental esql, String authorisedUser) {
      try {
         // read in user input
         System.out.print("\tEnter user login: ");
         String login = in.readLine();

         if (!login.equals(authorisedUser) && !isManager(esql, authorisedUser)) {
            System.out.println("You are not authorized to view another user's recent orders!");
            return;
         }

         //sql query that retrieves only the first 5 orders of a user from user input ordered by time
         String query = String.format("SELECT * FROM RentalOrder WHERE login = '%s' ORDER BY orderTimestamp DESC LIMIT 5", login);
         List<List<String>> result = esql.executeQueryAndReturnResult(query);

         if (result.isEmpty()) {
            System.out.println("No recent rental orders found!");
            return;
         }

         System.out.println("\n========== Recent 5 Rental Orders ==========");
         for (int i = 0; i < result.size(); i++) {
            List<String> order = result.get(i);
            System.out.printf("Order ID: %s\n", order.get(0));
            System.out.printf("Login: %s\n", order.get(1));
            System.out.printf("Number of Games: %s\n", order.get(2));
            System.out.printf("Total Price: %s\n", order.get(3));
            System.out.printf("Order Timestamp: %s\n", order.get(4));
            System.out.printf("Due Date: %s\n", order.get(5));
            System.out.println("----------------------------------");
         }
         System.out.println("======================================\n");

      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   // utilizes indexes on rentalOrderID for faster user searches
   public static void viewOrderInfo(GameRental esql) {
      try {
         // read in user input
         System.out.print("\tEnter rental order ID: ");
         String rentalOrderID = in.readLine();

         // sql query to retrieve order deals of user inputted order
         String query = String.format("SELECT * FROM RentalOrder WHERE rentalOrderID = '%s'", rentalOrderID);
         List<List<String>> orderResult = esql.executeQueryAndReturnResult(query);

         if (orderResult.isEmpty()) {
            System.out.println("Order not found!");
            return;
         }

         List<String> orderInfo = orderResult.get(0);
         System.out.println("\n========== Rental Order Information ==========");
         System.out.printf("Order ID: %s\n", orderInfo.get(0));
         System.out.printf("Login: %s\n", orderInfo.get(1));
         System.out.printf("Number of Games: %s\n", orderInfo.get(2));
         System.out.printf("Total Price: %s\n", orderInfo.get(3));
         System.out.printf("Order Timestamp: %s\n", orderInfo.get(4));
         System.out.printf("Due Date: %s\n", orderInfo.get(5));
         System.out.println("==============================================");

         // sql query to retrieve games from order
         String gamesQuery = String.format("SELECT * FROM GamesInOrder WHERE rentalOrderID = '%s'", rentalOrderID);
         List<List<String>> gamesResult = esql.executeQueryAndReturnResult(gamesQuery);

         // esql.executeQueryAndPrintResult(gamesQuery);

         System.out.println("\n========== Games in Order ==========");
         for (int i = 0; i < gamesResult.size(); i++) {
            List<String> gameInfo = gamesResult.get(i);
            System.out.printf("Rental Order ID: %s\n", gameInfo.get(0));
            System.out.printf("Game ID: %s\n", gameInfo.get(1));
            System.out.printf("Units Ordered: %s\n", gameInfo.get(2));
            System.out.println("----------------------------------");
         }
         System.out.println("====================================\n");

      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   // utilizes indexes on rentalOrderID for faster user searches
   public static void viewTrackingInfo(GameRental esql) {
       try {
         // read in user input
         System.out.print("\tEnter rental order ID: ");
         String rentalOrderID = in.readLine();

         // sql query to retrieve tracking info from user input
         String query = String.format("SELECT * FROM TrackingInfo WHERE rentalOrderID = '%s'", rentalOrderID);
         // esql.executeQueryAndPrintResult(query);

         List<List<String>> trackingResult = esql.executeQueryAndReturnResult(query);

         if (trackingResult.isEmpty()) {
            System.out.println("No tracking information found for this order.");
            return;
         }

          System.out.println("\n========== Tracking Information ==========");
         for (int i = 0; i < trackingResult.size(); i++) {
            List<String> trackingInfo = trackingResult.get(i);
            System.out.printf("Tracking ID: %s\n", trackingInfo.get(0));
            System.out.printf("Rental Order ID: %s\n", trackingInfo.get(1));
            System.out.printf("Status: %s\n", trackingInfo.get(2));
            System.out.printf("Current Location: %s\n", trackingInfo.get(3));
            System.out.printf("Courier Name: %s\n", trackingInfo.get(4));
            System.out.printf("Last Update Date: %s\n", trackingInfo.get(5));
            System.out.printf("Additional Comments: %s\n", trackingInfo.get(6));
            System.out.println("----------------------------------");
         }
         System.out.println("==========================================\n");

      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   // utilizes indexes on trackingID for faster user searches
   public static void updateTrackingInfo(GameRental esql, String authorisedUser) {
      try {
         // checks if user is an employee or manager before allowing them to update tracking info
         if (!isEmployeeOrManager(esql, authorisedUser)) {
            System.out.println("You are not authorized to update tracking information!");
            return;
         }

         // read in user input
         System.out.print("\tEnter tracking ID: ");
         String trackingID = in.readLine();

         String query = String.format("SELECT status, currentLocation, courierName, additionalComments FROM TrackingInfo WHERE trackingID = '%s'", trackingID);
         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         if (result.isEmpty()) {
            System.out.println("Tracking ID not found!");
            return;
         }

         String currentStatus = result.get(0).get(0);
         String currentLocation = result.get(0).get(1);
         String currentCourierName = result.get(0).get(2);
         String currentAdditionalComments = result.get(0).get(3);

         System.out.print("\tEnter new status (leave blank to keep current): ");
         String newStatus = in.readLine();
         if (newStatus.isEmpty()) {
            newStatus = currentStatus;
         }

         System.out.print("\tEnter new current location (leave blank to keep current): ");
         String newCurrentLocation = in.readLine();
         if (newCurrentLocation.isEmpty()) {
            newCurrentLocation = currentLocation;
         }

         System.out.print("\tEnter new courier name (leave blank to keep current): ");
         String newCourierName = in.readLine();
         if (newCourierName.isEmpty()) {
            newCourierName = currentCourierName;
         }

         System.out.print("\tEnter new additional comments (leave blank to keep current): ");
         String newAdditionalComments = in.readLine();
         if (newAdditionalComments.isEmpty()) {
            newAdditionalComments = currentAdditionalComments;
         }

         // sql query to update trackinginfo based on user inputs
         query = String.format("UPDATE TrackingInfo SET status = '%s', currentLocation = '%s', courierName = '%s', additionalComments = '%s', lastUpdateDate = current_timestamp WHERE trackingID = '%s'", newStatus, newCurrentLocation, newCourierName, newAdditionalComments, trackingID);
         esql.executeUpdate(query);
         System.out.println("Tracking information updated successfully!");
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   // utilizes indexes on gameID for faster user searches
   public static void updateCatalog(GameRental esql, String authorisedUser) {
      try {
         // checks if user is a manager before allowing catalog to be updated
         if (!isManager(esql, authorisedUser)) {
            System.out.println("You are not authorized to update the catalog!");
            return;
         }

         // read in user input
         System.out.print("\tEnter game ID: ");
         String gameID = in.readLine();

         String query = String.format("SELECT gameName, genre, price, description, imageURL FROM Catalog WHERE gameID = '%s'", gameID);
         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         if (result.isEmpty()) {
            System.out.println("Game not found!");
            return;
         }

         // sets new variables for data currently still in the databse
         String currentGameName = result.get(0).get(0);
         String currentGenre = result.get(0).get(1);
         String currentPrice = result.get(0).get(2);
         String currentDescription = result.get(0).get(3);
         String currentImageURL = result.get(0).get(4);

         // prompts the user to enter new information or leave blank if they wish to keep the current information   
         System.out.print("Enter new game name (leave blank to keep current): ");
         String newGameName = in.readLine();
         if (newGameName.isEmpty()) {
               newGameName = currentGameName;
         }

         System.out.print("Enter new genre (leave blank to keep current): ");
         String newGenre = in.readLine();
         if (newGenre.isEmpty()) {
            newGenre = currentGenre;
         }

         System.out.print("Enter new price (leave blank to keep current): ");
         String newPrice = in.readLine();
         if (newPrice.isEmpty()) {
            newPrice = currentPrice;
         }

         System.out.print("Enter new description (leave blank to keep current): ");
         String newDescription = in.readLine();
         if (newDescription.isEmpty()) {
            newDescription = currentDescription;
         }

         System.out.print("Enter new image URL (leave blank to keep current): ");
         String newImageURL = in.readLine();
         if (newImageURL.isEmpty()) {
            newImageURL = currentImageURL;
         }

         // sql query to update catalog based on user inputs
         query = String.format("UPDATE Catalog SET gameName = '%s', genre = '%s', price = %s, description = '%s', imageURL = '%s' WHERE gameID = '%s'", newGameName, newGenre, newPrice, newDescription, newImageURL, gameID);
         esql.executeUpdate(query);
         System.out.println("Catalog updated successfully!");
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   // utilizes indexes on login for faster user searches
   public static void updateUser(GameRental esql, String authorisedUser) {
      try {
         // checks if user is a managaer before allowing other users to be updated
         if (!isManager(esql, authorisedUser)) {
            System.out.println("You are not authorized to update another user's profile!");
            return;
         }

         // read in user input
         System.out.print("\tEnter user login: ");
         String login = in.readLine();

         // sql query to get the login from the database
         String query = String.format("SELECT role, phoneNum, numOverDueGames FROM Users WHERE login = '%s'", login);
         List<List<String>> result = esql.executeQueryAndReturnResult(query);

         // check to see if the login entered is valid and exists in the databse
         if (result.isEmpty()) {
            System.out.println("User not found!");
            return;
         }

         // sets new variables for data currently still in the databse
         String currentRole = result.get(0).get(0);
         String currentPhoneNum = result.get(0).get(1);
         String currentNumOverDueGames = result.get(0).get(2);
            
         // prompts the user to enter new information or leave blank if they wish to keep the current information
         System.out.print("Enter new role (leave blank to keep current): ");
         String newRole = in.readLine();
         if (newRole.isEmpty()) {
            newRole = currentRole;
         }

         System.out.print("Enter new phone number (leave blank to keep current): ");
         String newPhoneNum = in.readLine();
         if (newPhoneNum.isEmpty()) {
            newPhoneNum = currentPhoneNum;
         }

         System.out.print("Enter new number of overdue games (leave blank to keep current): ");
         String newNumOverDueGames = in.readLine();
         if (newNumOverDueGames.isEmpty()) {
            newNumOverDueGames = currentNumOverDueGames;
         }

         // sql query to update user information based on user inputs
         query = String.format("UPDATE Users SET role = '%s', phoneNum = '%s', numOverDueGames = %d WHERE login = '%s'", newRole, newPhoneNum, newNumOverDueGames, login);
         esql.executeUpdate(query);
         System.out.println("User information updated successfully!");
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   // helper functions

   public static boolean isManager(GameRental esql, String login) throws SQLException {
      // sql query grabs the user's information from the database
      String query = String.format("SELECT role FROM Users WHERE login = '%s'", login);
      List<List<String>> result = esql.executeQueryAndReturnResult(query);

      // check to see if the user's role is filled and returns true if they are a manager
      if (!result.isEmpty()) {
         String role = result.get(0).get(0);
         return role.trim().equalsIgnoreCase("manager");
      }

      return false;
   }  

   public static boolean isEmployeeOrManager(GameRental esql, String login) throws SQLException {
      // sql query grabs the user's information from the database
      String query = String.format("SELECT role FROM Users WHERE login = '%s'", login);
      List<List<String>> result = esql.executeQueryAndReturnResult(query);

      // check to see if the user's role is filled and returns true if they are a manager or employee
      if (!result.isEmpty()) {
         String role = result.get(0).get(0);
         return role.trim().equalsIgnoreCase("manager") || role.trim().equalsIgnoreCase("employee");
      }

      return false;
   }

}//end GameRental

