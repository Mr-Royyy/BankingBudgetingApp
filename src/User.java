import java.util.Random;

/**
 * This class represents a user in the application with personal details.
 * It provides methods to access and modify user attributes.
 *
 * @author Jimmy Roy
 * @version August 2024
 */
public class User {
    private String firstName; // User's first name
    private String lastName; // User's last name
    public String ID; // User's ID
    private Random random = new Random(); // Random number generator (not currently used)
    private String email; // User's email address
    private String phoneNumber; // User's phone number

    /**
     * Constructor for the User class.
     * Initializes the user's personal details.
     *
     * @param firstName User's first name
     * @param lastName User's last name
     * @param ID User's ID
     * @param email User's email address
     * @param phoneNumber User's phone number
     */
    public User(String firstName, String lastName, String ID, String email, String phoneNumber) {
        this.firstName = firstName; // Sets the user's first name
        this.lastName = lastName; // Sets the user's last name
        this.ID = ID; // Sets the user's ID
        this.email = email; // Sets the user's email address
        this.phoneNumber = phoneNumber; // Sets the user's phone number
    }

    /**
     * Retrieves the user's first name.
     *
     * @return The user's first name
     */
    public String getFirstName() {
        return firstName; // Returns the user's first name
    }

    /**
     * Sets the user's first name.
     *
     * @param firstName The new first name for the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName; // Updates the user's first name
    }

    /**
     * Retrieves the user's last name.
     *
     * @return The user's last name
     */
    public String getLastName() {
        return lastName; // Returns the user's last name
    }

    /**
     * Sets the user's last name.
     *
     * @param lastName The new last name for the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName; // Updates the user's last name
    }

    /**
     * Retrieves the user's ID.
     *
     * @return The user's ID
     */
    public String getID() {
        return ID; // Returns the user's ID
    }

    /**
     * Retrieves the user's email address.
     *
     * @return The user's email address
     */
    public String getEmail() {
        return email; // Returns the user's email address
    }

    /**
     * Sets the user's email address.
     *
     * @param email The new email address for the user
     */
    public void setEmail(String email) {
        this.email = email; // Updates the user's email address
    }

    /**
     * Retrieves the user's phone number.
     *
     * @return The user's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber; // Returns the user's phone number
    }

    /**
     * Sets the user's phone number.
     *
     * @param phoneNumber The new phone number for the user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber; // Updates the user's phone number
    }

}
