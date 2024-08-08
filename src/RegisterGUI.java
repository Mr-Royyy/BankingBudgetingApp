import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * This class represents the graphical user interface for user registration.
 * It allows users to input their details, validate them, and submit the registration form.
 *
 * @author Jimmy Roy
 * @version August 2024
 */
public class RegisterGUI {
    private Group root2; // The root node for the scene graph
    private static Scene scene2; // The scene for the registration screen
    public TextField IDField; // TextField for user ID
    public TextField passwordField; // TextField for password
    public TextField nameField; // TextField for first name
    public TextField lastNameField; // TextField for last name
    public TextField eMailField; // TextField for email
    public TextField phoneField; // TextField for phone number
    public Button button; // Button to submit the registration form
    private static String usernameStr; // Stores the username
    private static String passwordStr; // Stores the password
    public static String nameStr; // Stores the first name
    public static String lastNameStr; // Stores the last name
    public static String eMailStr; // Stores the email address
    public static String phoneStr; // Stores the phone number

    /**
     * Constructor for registerGUI.
     * Initializes and sets up the registration screen.
     *
     * @param primaryStage The primary stage for the application
     * @param main An instance of the Main class, used for accessing constants and methods
     */
    public RegisterGUI(Stage primaryStage, Main main) {
        root2 = new Group(); // Creates a new Group to hold all GUI components
        scene2 = new Scene(root2, Main.MAX_WIDTH, Main.MAX_HEIGHT); // Creates a new Scene with specified dimensions
        scene2.setFill(Color.web(Main.PRIMARY_COLOUR)); // Sets the background color of the scene

        Rectangle rect = new Rectangle(500, 0, 500, 700); // Creates a rectangle for the background
        rect.setFill(Color.web(Main.SECONDARY_COLOUR)); // Sets the fill color of the rectangle

        Text text = new Text(630, 200, "Create Account!"); // Creates a text node for the title
        text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 36)); // Sets the font, weight, and size of the title text

        IDField = new TextField(); // Creates a TextField for the user ID
        IDField.setLayoutX(675); // Sets the x-coordinate for positioning
        IDField.setLayoutY(250); // Sets the y-coordinate for positioning

        Text usernameText = new Text(570, 266, "Username"); // Creates a text node for the username label
        usernameText.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15)); // Sets the font, weight, and size of the username label

        passwordField = new TextField(); // Creates a TextField for the password
        passwordField.setLayoutX(675); // Sets the x-coordinate for positioning
        passwordField.setLayoutY(300); // Sets the y-coordinate for positioning

        Text passwordText = new Text(570, 316, "Password"); // Creates a text node for the password label
        passwordText.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15)); // Sets the font, weight, and size of the password label

        nameField = new TextField(); // Creates a TextField for the first name
        nameField.setLayoutX(675); // Sets the x-coordinate for positioning
        nameField.setLayoutY(350); // Sets the y-coordinate for positioning

        Text nameText = new Text(570, 366, "First Name"); // Creates a text node for the first name label
        nameText.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15)); // Sets the font, weight, and size of the first name label

        lastNameField = new TextField(); // Creates a TextField for the last name
        lastNameField.setLayoutX(675); // Sets the x-coordinate for positioning
        lastNameField.setLayoutY(400); // Sets the y-coordinate for positioning

        Text lastNameText = new Text(570, 416, "Last Name"); // Creates a text node for the last name label
        lastNameText.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15)); // Sets the font, weight, and size of the last name label

        eMailField = new TextField(); // Creates a TextField for the email address
        eMailField.setLayoutX(675); // Sets the x-coordinate for positioning
        eMailField.setLayoutY(450); // Sets the y-coordinate for positioning

        Text eMailText = new Text(570, 466, "eMail"); // Creates a text node for the email label
        eMailText.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15)); // Sets the font, weight, and size of the email label

        Text formatError = new Text(830, 466, ""); // Creates a text node for displaying email format errors
        formatError.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15)); // Sets the font, weight, and size of the email error text
        formatError.setFill(Color.RED); // Sets the text color to red for errors

        phoneField = new TextField(); // Creates a TextField for the phone number
        phoneField.setLayoutX(675); // Sets the x-coordinate for positioning
        phoneField.setLayoutY(500); // Sets the y-coordinate for positioning

        Text numberText = new Text(570, 516, "Phone Number"); // Creates a text node for the phone number label
        numberText.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15)); // Sets the font, weight, and size of the phone number label

        Text formatError2 = new Text(830, 516, ""); // Creates a text node for displaying phone number format errors
        formatError2.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15)); // Sets the font, weight, and size of the phone number error text
        formatError2.setFill(Color.RED); // Sets the text color to red for errors

        button = new Button("Submit"); // Creates a button for submitting the registration form
        button.setLayoutX(725); // Sets the x-coordinate for positioning
        button.setLayoutY(550); // Sets the y-coordinate for positioning
        // Sets the action for the submit button
        button.setOnAction(event -> {
            // Retrieves the input values from the text fields
            usernameStr = IDField.getText();
            passwordStr = passwordField.getText();
            nameStr = nameField.getText();
            lastNameStr = lastNameField.getText();
            eMailStr = eMailField.getText();
            phoneStr = phoneField.getText();

            // Prints the values to the console for debugging
            //System.out.println("Username: " + IDField.getText());
            //System.out.println("Password: " + passwordField.getText());

            // Checks if the username already exists
            if (!JDBC.checkUser(usernameStr)) {
                // Validates the email and phone number
                if (validateEmail(eMailStr) && validatePhoneNumber(phoneStr)) {
                    // Registers the user in the database
                    if (JDBC.register(usernameStr, passwordStr, nameStr, lastNameStr, eMailStr, phoneStr)) {
                        // Checks if the username and password fields are not empty
                        if (!IDField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                            // Switches to the login scene
                            primaryStage.setScene(LoginGUI.getScene());
                        }
                    }
                } else {
                    // Displays error messages if validation fails
                    if (!validateEmail(eMailStr)) {
                        formatError.setText("Invalid eMail address");
                    }
                    if (!validatePhoneNumber(phoneStr)) {
                        formatError2.setText("Invalid number");
                    }
                }
            }
        });

        // Loads an image for the return button
        Image home = new Image("images/homeicon.png"); // Use the correct path for the image
        Button returnToLogin = new Button(); // Creates a button for returning to the login screen
        returnToLogin.setGraphic(new ImageView(home)); // Sets the button graphic to the home image
        returnToLogin.setLayoutX(925); // Sets the x-coordinate for positioning
        returnToLogin.setLayoutY(27); // Sets the y-coordinate for positioning

        // Sets the action for the return button
        returnToLogin.setOnAction(event -> {
            // Switches to the login scene
            primaryStage.setScene(LoginGUI.getScene());
        });

        // Adds all GUI components to the root node
        root2.getChildren().addAll(rect, text, usernameText, passwordText, nameText, lastNameText, eMailText, numberText, IDField, passwordField, nameField, lastNameField, eMailField, phoneField, button, returnToLogin, formatError, formatError2);
    }

    /**
     * Returns the username string.
     *
     * @return The username string
     */
    public static String getUsernameStr() {
        return usernameStr;
    }

    /**
     * Sets the username string.
     *
     * @param usernameStr The username string to set
     */
    public void setUsernameStr(String usernameStr) {
        this.usernameStr = usernameStr;
    }

    /**
     * Returns the password string.
     *
     * @return The password string
     */
    public static String getPasswordStr() {
        return passwordStr;
    }

    /**
     * Sets the password string.
     *
     * @param passwordStr The password string to set
     */
    public void setPasswordStr(String passwordStr) {
        this.passwordStr = passwordStr;
    }

    /**
     * Returns the scene for the registration GUI.
     *
     * @return The scene for the registration GUI
     */
    public static Scene getScene2() {
        return scene2;
    }

    /**
     * Validates the email address format using a regular expression.
     *
     * @param email The email address to validate
     * @return True if the email format is valid, false otherwise
     */
    private boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"; // Regular expression for email validation
        Pattern pattern = Pattern.compile(emailRegex); // Compiles the regular expression into a pattern
        Matcher matcher = pattern.matcher(email); // Creates a matcher for the given email
        return matcher.matches(); // Returns whether the email matches the pattern
    }

    /**
     * Validates the phone number format using a regular expression.
     *
     * @param phoneNumber The phone number to validate
     * @return True if the phone number format is valid, false otherwise
     */
    private boolean validatePhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\d{10}$"; // Regular expression for phone number validation
        Pattern pattern = Pattern.compile(phoneRegex); // Compiles the regular expression into a pattern
        Matcher matcher = pattern.matcher(phoneNumber); // Creates a matcher for the given phone number
        return matcher.matches(); // Returns whether the phone number matches the pattern
    }
}
