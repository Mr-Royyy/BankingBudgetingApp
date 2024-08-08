import java.sql.*;

/**
 * JDBC class handles database operations for user registration, transactions, and account updates.
 *
 * @author Jimmy Roy
 * @date August 2024
 */
public class JDBC {
    // Database connection URL
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/login_schema";
    // Database username
    public static final String USERNAME = "root";
    // Database password
    public static final String PASSWORD = "sqlDatabasePassword"; // sqlDatabasePassword
    // Table name in the database
    public static final String TABLE_NAME = "USERS";

    /**
     * Registers a new user in the database.
     *
     * @param ID User ID
     * @param password User password
     * @param name User first name
     * @param lastname User last name
     * @param email User email
     * @param phone User phone number
     * @return true if registration is successful, false otherwise
     */
    public static boolean register(String ID, String password, String name, String lastname, String email, String phone) {
        try {
            // Check if the user already exists
            if (!checkUser(ID)) {
                // Establish database connection
                Connection connection = DriverManager.getConnection(
                        URL,
                        USERNAME,
                        PASSWORD
                );
                // Prepare SQL statement for inserting new user
                PreparedStatement insertUser = connection.prepareStatement(
                        "INSERT INTO " + TABLE_NAME + "(ID, password, name, lastname, email, phone)" + "VALUES(?,?,?,?,?,?)"
                );
                // Set values for the prepared statement
                insertUser.setString(1, ID);
                insertUser.setString(2, password);
                insertUser.setString(3, name);
                insertUser.setString(4, lastname);
                insertUser.setString(5, email);
                insertUser.setString(6, phone);
                // Execute the statement
                insertUser.executeUpdate();
                return true;
            }
        }
        // Error management
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Reduces monthly transactions by 1 for the given user ID.
     *
     * @param ID User ID
     */
    public static void transaction(String ID) {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        try {
            // Establish database connection
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
            // Prepare SQL statement for updating transaction count
            updateStatement = connection.prepareStatement(
                    "UPDATE " + TABLE_NAME + " SET motrans = motrans - 1 WHERE ID = ?"
            );
            // Set user ID in the statement
            updateStatement.setString(1, ID);
            // Execute the statement and get affected rows
            int affectedRows = updateStatement.executeUpdate();
            //System.out.println(affectedRows);
        }
        // Error management
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deposits a specified amount into the chequing account of the given user ID.
     *
     * @param ID User ID
     * @param amount Amount to deposit
     */
    public static void depositChequing(String ID, double amount) {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        try {
            // Establish database connection
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
            // Get current chequing value
            String chequingValue = JDBC.getChequingValue(LoginGUI.getUsernameStr());
            // Get new chequing value after deposit
            double doubleChequingValue = Double.parseDouble(chequingValue);
            double newChequingValue = (doubleChequingValue+amount);
            String strNewChequingValue = String.valueOf(newChequingValue);
            // Determine the query based on current chequing value
            if (chequingValue.equals("0.00")) {
                updateStatement = connection.prepareStatement(
                        "UPDATE " + TABLE_NAME + " SET chequing = "+strNewChequingValue+" WHERE ID = ?"
                );
            } else {
                updateStatement = connection.prepareStatement(
                        "UPDATE " + TABLE_NAME + " SET chequing = "+strNewChequingValue+" WHERE ID = ?"
                );
            }
            // Set chequing value in the statement
            updateStatement.setString(1, ID);
            // Execute the statement and get affected rows
            int affectedRows = updateStatement.executeUpdate();
            //System.out.println(affectedRows);
        }
        // Error management
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Withdraws a specified amount from the chequing account of the given user ID.
     *
     * @param ID User ID
     * @param amount Amount to withdraw
     */
    public static void withdrawChequing(String ID, double amount) {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        try {
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
            // Get current chequing value
            String chequingValue = JDBC.getChequingValue(LoginGUI.getUsernameStr());
            // Get new chequing value after deposit
            double doubleChequingValue = Double.parseDouble(chequingValue);
            double newChequingValue = (doubleChequingValue-amount);
            //System.out.println(newChequingValue);
            double newRoundedChequingValue = Double.parseDouble(Main.twoDecimal(newChequingValue));
            //System.out.println(newRoundedChequingValue);
            String strNewChequingValue = String.valueOf(newRoundedChequingValue);
            updateStatement = connection.prepareStatement(
                    "UPDATE " + TABLE_NAME + " SET chequing = "+strNewChequingValue+" WHERE ID = ?"
            );
            // Set chequing value in the statement
            updateStatement.setString(1, ID);
            int affectedRows = updateStatement.executeUpdate();
            //System.out.println(affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deposits a specified amount into the savings account of the given user ID.
     *
     * @param ID User ID
     * @param amount Amount to deposit
     */
    public static void depositSavings(String ID, double amount) {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        try {
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
            // Get current savings value after deposit
            String savingsValue = JDBC.getSavingsValue(LoginGUI.getUsernameStr());
            // Get new savings value after deposit
            double doubleSavingsValue = Double.parseDouble(savingsValue);
            double newSavingsValue = (doubleSavingsValue+amount);
            String strNewSavingsValue = String.valueOf(newSavingsValue);
            //System.out.println("NEW SAVINGS VALUE: "+strNewSavingsValue);
            // Determine the query based on current chequing value
            if (savingsValue.equals("0.00")) {
                updateStatement = connection.prepareStatement(
                        "UPDATE " + TABLE_NAME + " SET savings = "+strNewSavingsValue+" WHERE ID = ?"
                );
            } else {
                updateStatement = connection.prepareStatement(
                        "UPDATE " + TABLE_NAME + " SET savings = "+strNewSavingsValue+" WHERE ID = ?"
                );
            }
            updateStatement.setString(1, ID);
            int affectedRows = updateStatement.executeUpdate();
            //System.out.println(affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Withdraws a specified amount from the savings account of the given user ID.
     *
     * @param ID User ID
     * @param amount Amount to withdraw
     */
    public static void withdrawSavings(String ID, double amount) {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        try {
            // Establish database connection
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
            // Get current savings value
            String savingsValue = JDBC.getSavingsValue(LoginGUI.getUsernameStr());
            // Get new savings value after deposit
            double doubleSavingsValue = Double.parseDouble(savingsValue);
            double newSavingsValue = (doubleSavingsValue-amount);
            double newRoundedSavingsValue = Double.parseDouble(Main.twoDecimal(newSavingsValue));
            String strNewSavingsValue = String.valueOf(newRoundedSavingsValue);
            updateStatement = connection.prepareStatement(
                    "UPDATE " + TABLE_NAME + " SET savings = "+strNewSavingsValue+" WHERE ID = ?"
            );
            // Prepare SQL statement for updating savings value
            updateStatement.setString(1, ID);
            // Execute the statement and get affected rows
            int affectedRows = updateStatement.executeUpdate();
            //System.out.println(affectedRows);
        }
        // Error management
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deposits a specified amount as monthly income into the chequing account of the given user ID.
     *
     * @param ID User ID
     * @param amount Amount to deposit
     */
    public static void depositMonthlyIncome(String ID, double amount) {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        try {
            // Establish database connection
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
            // Get current monthly income value
            String incomeValue = JDBC.getMonthlyIncome(LoginGUI.getUsernameStr());
            // Determine the query based on current income value
            if (incomeValue.equals("0.00")) {
                updateStatement = connection.prepareStatement(
                        "UPDATE " + TABLE_NAME + " SET income = " + amount + " WHERE ID = ?"
                );
            } else {
                updateStatement = connection.prepareStatement(
                        "UPDATE " + TABLE_NAME + " SET income = income + " + amount + " WHERE ID = ?"
                );
            }
            // Set user ID in the statement
            updateStatement.setString(1, ID);
            // Execute the statement and get affected rows
            int affectedRows = updateStatement.executeUpdate();
            //System.out.println(affectedRows);
        }
        // Error management
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deposits a specified amount as monthly expenses into the chequing account of the given user ID.
     *
     * @param ID User ID
     * @param amount Amount to deposit
     */
    public static void depositMonthlyExpense(String ID, double amount) {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        // Round the amount to two decimal places
        try {
            // Establish database connection
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
            // Get current monthly expense value
            String expenseValue = JDBC.getMonthlyExpense(LoginGUI.getUsernameStr());
            double doubleExpenseValue = Double.parseDouble(expenseValue);
            double newExpenseValue = (doubleExpenseValue+amount);
            double newRoundedExpenseValue = Double.parseDouble(Main.twoDecimal(newExpenseValue));
            String strNewExpenseValue = String.valueOf(newRoundedExpenseValue);
            // Determine the query based on current expense value
            if (expenseValue.equals("0.00")) {
                updateStatement = connection.prepareStatement(
                        "UPDATE " + TABLE_NAME + " SET expense = " + strNewExpenseValue + " WHERE ID = ?"
                );
            } else {
                updateStatement = connection.prepareStatement(
                        "UPDATE " + TABLE_NAME + " SET expense = expense + " + strNewExpenseValue + " WHERE ID = ?"
                );
            }
            // Set user ID in the statement
            updateStatement.setString(1, ID);
            // Execute the statement and get affected rows
            int affectedRows = updateStatement.executeUpdate();
            //System.out.println(affectedRows);
        }
        // Error management
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param ID User ID
     * @param expenseType Type of expense
     * @param amount Amount to deposit
     */
    public static void updateMonthlyExpense(String ID, String expenseType, double amount) {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        try {
            // Establish database connection
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
            // Get current monthly expense in each type's value and parse it into a double
            double currentAmount = Double.parseDouble(JDBC.getMonthlyExpenseTypeAmount(LoginGUI.getUsernameStr(),expenseType));
            // Total new monthly expense amount
            double newAmount = currentAmount + amount;
            double newRoundedAmount = Double.parseDouble(Main.twoDecimal(newAmount));
            String strNewAmount = String.valueOf(newRoundedAmount);
            // Update MySQL database
            updateStatement = connection.prepareStatement(
                    "UPDATE " + TABLE_NAME + " SET "+expenseType +" = "+ strNewAmount + " WHERE ID = ?"
            );
            updateStatement.setString(1, ID);
            int affectedRows = updateStatement.executeUpdate();
            //System.out.println(affectedRows);
        }
        // Error management
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateMonthlySavings(String ID){
        Connection connection = null;
        PreparedStatement updateStatement = null;
        try{
            // Get monthly income and expense and total savings
            double doubleIncome = Double.parseDouble(JDBC.getMonthlyIncome(LoginGUI.getUsernameStr()));
            double doubleExpense = Double.parseDouble(JDBC.getMonthlyExpense(LoginGUI.getUsernameStr()));
            double roundedAmount = doubleIncome-doubleExpense;
            roundedAmount = Double.parseDouble(Main.twoDecimal(roundedAmount));
            // Establish database connection
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
            // Update MySQL database
            updateStatement = connection.prepareStatement(
                        "UPDATE " + TABLE_NAME + " SET mosavings = " + roundedAmount + " WHERE ID = ?"
                );

            assert updateStatement != null;
            updateStatement.setString(1,ID);
            int affectedRows = updateStatement.executeUpdate();
            //System.out.println(affectedRows);
        }
        // Error management
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * Checks if a user exists in the database based on the given ID.
     *
     * @param ID the user ID to check
     * @return true if the user exists, false otherwise
     */
    public static boolean checkUser(String ID) {
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );

            // Preparing SQL query to check if the user exists
            PreparedStatement checkUserExists = connection.prepareStatement(
                    "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?"
            );
            checkUserExists.setString(1, ID);

            // Executing the query
            ResultSet resultSet = checkUserExists.executeQuery();

            // If the result set is empty, the user does not exist
            if (!resultSet.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Validates the user login credentials.
     *
     * @param ID the user ID to check
     * @param password the user password to check
     * @return true if the credentials are valid, false otherwise
     */
    public static boolean validateLogin(String ID, String password) {
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );

            // Preparing SQL query to validate user credentials
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT * FROM " + TABLE_NAME + " WHERE ID = ? AND PASSWORD = ?"
            );
            validateUser.setString(1, ID);
            validateUser.setString(2, password);

            // Executing the query
            ResultSet resultSet = validateUser.executeQuery();

            // If the result set is empty, the credentials are invalid
            if (!resultSet.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Retrieves the first name of the user based on the given ID.
     *
     * @param ID the user ID
     * @return the first name of the user, or null if not found
     */
    public static String getFirstName(String ID) {
        String firstName = null;
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );

            // Preparing SQL query to get the first name
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT name FROM " + TABLE_NAME + " WHERE ID = ?"
            );
            validateUser.setString(1, ID);

            // Executing the query
            ResultSet resultSet = validateUser.executeQuery();

            // Getting the first name from the result set
            if (resultSet.next()) {
                firstName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            e.printStackTrace();
        }
        return firstName;
    }

    /**
     * Retrieves the savings value of the user based on the given ID.
     *
     * @param ID the user ID
     * @return the savings value of the user, or "0.00" if not found
     */
    public static String getSavingsValue(String ID) {
        String savings = null;
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );

            // Preparing SQL query to get the savings value
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT savings FROM " + TABLE_NAME + " WHERE ID = ?"
            );
            validateUser.setString(1, ID);

            // Executing the query
            ResultSet resultSet = validateUser.executeQuery();

            // Getting the savings value from the result set
            if (resultSet.next()) {
                savings = resultSet.getString("savings");
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            e.printStackTrace();
        }
        // If savings value is null, return "0.00"
        if (savings == null) {
            savings = "0.00";
        }
        return savings;
    }

    /**
     * Retrieves the chequing value of the user based on the given ID.
     *
     * @param ID the user ID
     * @return the chequing value of the user, or "0.00" if not found
     */
    public static String getChequingValue(String ID) {
        String chequing = null;
        try {
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT chequing FROM " + TABLE_NAME + " WHERE ID = ?"
            );
            validateUser.setString(1, ID);
            ResultSet resultSet = validateUser.executeQuery();
            if (resultSet.next()) {
                chequing = resultSet.getString("chequing");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (chequing == null) {
            chequing = "0.00";
        }
        return chequing;
    }

    /**
     * Retrieves the number of transactions left for the user based on the given ID.
     *
     * @param ID the user ID
     * @return the number of transactions left for the user, or null if not found
     */
    public static String getTransactionsLeft(String ID) {
        String transactionsLeft = null;
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );

            // Preparing SQL query to get the transactions left
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT motrans FROM " + TABLE_NAME + " WHERE ID = ?"
            );
            validateUser.setString(1, ID);

            // Executing the query
            ResultSet resultSet = validateUser.executeQuery();

            // Getting the transactions left from the result set
            if (resultSet.next()) {
                transactionsLeft = resultSet.getString("motrans");
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            e.printStackTrace();
        }
        return transactionsLeft;
    }

    /**
     * Retrieves the monthly income of the user based on the given ID.
     *
     * @param ID the user ID
     * @return the monthly income of the user, or "0.00" if not found
     */
    public static String getMonthlyIncome(String ID) {
        String income = null;
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );

            // Preparing SQL query to get the monthly income
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT income FROM " + TABLE_NAME + " WHERE ID = ?"
            );
            validateUser.setString(1, ID);

            // Executing the query
            ResultSet resultSet = validateUser.executeQuery();

            // Getting the monthly income from the result set
            if (resultSet.next()) {
                income = resultSet.getString("income");
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            e.printStackTrace();
        }
        // If income value is null, return "0.00"
        if (income == null) {
            income = "0.00";
        }
        return income;
    }

    /**
     * Retrieves the monthly expense of the user based on the given ID.
     *
     * @param ID the user ID
     * @return the monthly expense of the user, or "0.00" if not found
     */
    public static String getMonthlyExpense(String ID) {
        String expense = null;
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );

            // Preparing SQL query to get the monthly expense
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT expense FROM " + TABLE_NAME + " WHERE ID = ?"
            );
            validateUser.setString(1, ID);

            // Executing the query
            ResultSet resultSet = validateUser.executeQuery();

            // Getting the monthly expense from the result set
            if (resultSet.next()) {
                expense = resultSet.getString("expense");
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            e.printStackTrace();
        }
        // If expense value is null, return "0.00"
        if (expense == null) {
            expense = "0.00";
        }
        return expense;
    }

    /**
     * Retrieves the amount for a specific expense type for the user based on the given ID.
     *
     * @param ID the user ID
     * @param expenseType the type of expense to retrieve
     * @return the amount for the specified expense type, or "0.00" if not found
     */
    public static String getMonthlyExpenseTypeAmount(String ID, String expenseType) {
        String expenseTypeAmount = null;
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );

            // Preparing SQL query to get the amount for the specified expense type
            String query = "SELECT " + expenseType + " FROM " + TABLE_NAME + " WHERE ID = ?";
            PreparedStatement validateUser = connection.prepareStatement(query);
            validateUser.setString(1, ID);

            // Executing the query
            ResultSet resultSet = validateUser.executeQuery();

            // Getting the amount for the specified expense type from the result set
            if (resultSet.next()) {
                expenseTypeAmount = resultSet.getString(expenseType);
            }

            // Closing resources
            resultSet.close();
            validateUser.close();
            connection.close();
        } catch (SQLException e) {
            // Handling SQL exceptions
            e.printStackTrace();
        }
        // If amount for the specified expense type is null, return "0.00"
        if (expenseTypeAmount == null) {
            expenseTypeAmount = "0.00";
        }
        return expenseTypeAmount;
    }

    /**
     * Retrieves the monthly savings of the user based on the given ID.
     *
     * @param ID the user ID
     * @return the monthly savings of the user, or "0.00" if not found
     */
    public static String getMonthlySavings(String ID) {
        String savings = null;
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );

            // Preparing SQL query to get the monthly savings
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT mosavings FROM " + TABLE_NAME + " WHERE ID = ?"
            );
            validateUser.setString(1, ID);

            // Executing the query
            ResultSet resultSet = validateUser.executeQuery();

            // Getting the monthly savings from the result set
            if (resultSet.next()) {
                savings = resultSet.getString("mosavings");
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            e.printStackTrace();
        }
        // If savings value is null, return "0.00"
        if (savings == null) {
            savings = "0.00";
        }
        return savings;
    }

    /**
     * Retrieves the last name of the user based on the given ID.
     *
     * @param ID the user ID
     * @return the last name of the user, or null if not found
     */
    public static String getLastName(String ID) {
        String lastName = null;
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );

            // Preparing SQL query to get the last name
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT lastname FROM " + TABLE_NAME + " WHERE ID = ?"
            );
            validateUser.setString(1, ID);

            // Executing the query
            ResultSet resultSet = validateUser.executeQuery();

            // Getting the last name from the result set
            if (resultSet.next()) {
                lastName = resultSet.getString("lastname");
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            e.printStackTrace();
        }
        return lastName;
    }

    /**
     * Retrieves the email of the user based on the given ID.
     *
     * @param ID the user ID
     * @return the email of the user, or null if not found
     */
    public static String geteMail(String ID) {
        String eMail = null;
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );

            // Preparing SQL query to get the email
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT email FROM " + TABLE_NAME + " WHERE ID = ?"
            );
            validateUser.setString(1, ID);

            // Executing the query
            ResultSet resultSet = validateUser.executeQuery();

            // Getting the email from the result set
            if (resultSet.next()) {
                eMail = resultSet.getString("email");
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            e.printStackTrace();
        }
        return eMail;
    }

    /**
     * Retrieves the phone number of the user based on the given ID.
     *
     * @param ID the user ID
     * @return the phone number of the user, or null if not found
     */
    public static String getPhoneNumber(String ID) {
        String phoneNumber = null;
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD
            );

            // Preparing SQL query to get the phone number
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT phone FROM " + TABLE_NAME + " WHERE ID = ?"
            );
            validateUser.setString(1, ID);

            // Executing the query
            ResultSet resultSet = validateUser.executeQuery();

            // Getting the phone number from the result set
            if (resultSet.next()) {
                phoneNumber = resultSet.getString("phone");
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            e.printStackTrace();
        }
        return phoneNumber;
    }
}