/**
 * This class represents a savings account that extends the User class.
 * It provides methods to interact with a savings account, including retrieving savings value and withdrawing funds.
 *
 * @author Jimmy Roy
 * @version August 2024
 */
public class SavingsAccount extends User {

    /**
     * Constructor for the SavingsAccount class.
     * Initializes a new savings account with the provided user details.
     *
     * @param firstName The first name of the user
     * @param lastName The last name of the user
     * @param ID The user ID
     * @param email The user's email address
     * @param phoneNumber The user's phone number
     */
    public SavingsAccount(String firstName, String lastName, String ID, String email, String phoneNumber) {
        super(firstName, lastName, ID, email, phoneNumber); // Calls the constructor of the superclass User
    }

    /**
     * Retrieves the current savings value for a given user ID.
     *
     * @param ID The user ID
     * @return The current savings value as a String
     */
    public static String getSavingsValue(String ID) {
        return JDBC.getSavingsValue(LoginGUI.getUsernameStr()); // Gets the savings value from the JDBC class for the current user
    }

    /**
     * Withdraws a specified amount from the savings account.
     * Performs a series of checks and updates the account balances accordingly.
     *
     * @param ID The user ID
     * @param amount The amount to withdraw as a String
     */
    public static void withdrawSavings(String ID, String amount) {
        double doubleAmount = Double.parseDouble(amount); // Converts the amount from String to double

        // Checks if the current savings value is not zero
        if (!JDBC.getSavingsValue(LoginGUI.getUsernameStr()).equals("0.00")) {
            double savingsValue = Double.parseDouble(JDBC.getSavingsValue(LoginGUI.getUsernameStr())); // Gets and converts the current savings value

            // Checks if there are sufficient funds to withdraw the specified amount
            if (savingsValue - doubleAmount >= 0) {
                JDBC.transaction(LoginGUI.getUsernameStr()); // Logs the transaction for the current user
                JDBC.withdrawSavings(LoginGUI.getUsernameStr(), doubleAmount); // Withdraws the specified amount from savings
                ChequingAccount.depositChequing(LoginGUI.getUsernameStr(), SavingsGUI.getStrWithdrawAmount()); // Deposits the withdrawn amount into the chequing account
            }
        }
    }
}
