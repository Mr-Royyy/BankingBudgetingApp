/**
 * Represents a chequing account for a user, extending the User class.
 * Provides methods for depositing and withdrawing from the chequing account.
 *
 * @author Jimmy Roy
 * @version August 2024
 */
public class ChequingAccount extends User {

    /** Balance of the chequing account */
    private static double accountBalance;

    /** Indicates whether the account has overdraft protection */
    private static boolean overdraftProtection;

    /**
     * Constructs a new ChequingAccount object.
     *
     * @param firstName the first name of the account holder
     * @param lastName the last name of the account holder
     * @param ID the ID of the account holder
     * @param email the email address of the account holder
     * @param phoneNumber the phone number of the account holder
     * @param accountBalance the initial balance of the chequing account
     * @param overdraftProtection whether the account has overdraft protection
     */
    public ChequingAccount(String firstName, String lastName, String ID, String email, String phoneNumber, double accountBalance, boolean overdraftProtection) {
        super(firstName, lastName, ID, email, phoneNumber);
        ChequingAccount.accountBalance = accountBalance;
        ChequingAccount.overdraftProtection = overdraftProtection;
    }

    /**
     * Deposits a specified amount into the chequing account.
     *
     * @param ID the ID of the account holder
     * @param amount the amount to deposit
     */
    public static void depositChequing(String ID, String amount) {
        double doubleAmount = Double.parseDouble(amount);
        accountBalance += doubleAmount;
        JDBC.depositChequing(ID, doubleAmount);
    }

    /**
     * Withdraws a specified amount from the chequing account and deposits it into the savings account.
     * Only allows withdrawal if the chequing account has sufficient funds.
     *
     * @param ID the ID of the account holder
     * @param amount the amount to withdraw
     */
    public static void withdrawChequing(String ID, String amount) {
        double doubleAmount = Double.parseDouble(amount);
        if (!JDBC.getChequingValue(LoginGUI.getUsernameStr()).equals("0.00")) {
            double checquingValue = Double.parseDouble(JDBC.getChequingValue(LoginGUI.getUsernameStr()));
            if (checquingValue - doubleAmount >= 0) {
                JDBC.withdrawChequing(LoginGUI.getUsernameStr(), doubleAmount); // Deducts money from chequing account
                JDBC.depositSavings(LoginGUI.getUsernameStr(), doubleAmount); // Adds money to savings account
                JDBC.transaction(LoginGUI.getUsernameStr());
            }
        }
    }

    /**
     * Gets the current balance of the chequing account.
     *
     * @return the current account balance
     */
    public static double getAccountBalance() {
        return accountBalance;
    }

    /**
     * Sets the balance of the chequing account.
     *
     * @param accountBalance the new account balance
     */
    public static void setAccountBalance(double accountBalance) {
        ChequingAccount.accountBalance = accountBalance;
    }

    /**
     * Checks if the account has overdraft protection.
     *
     * @return true if the account has overdraft protection, false otherwise
     */
    public boolean isOverdraftProtection() {
        return overdraftProtection;
    }

    /**
     * Sets the overdraft protection status of the account.
     *
     * @param overdraftProtection the new overdraft protection status
     */
    public void setOverdraftProtection(boolean overdraftProtection) {
        ChequingAccount.overdraftProtection = overdraftProtection;
    }
}
