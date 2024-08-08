import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.shape.*;

/**
 * This class represents the graphical user interface for the login screen of the application.
 * It allows users to enter their username and password to log in.
 *
 * @author Jimmy Roy
 * @version August 2024
 */
public class LoginGUI {
    private Group root; // The root node for the scene graph
    private static Scene scene; // The scene for the login screen
    public Button button; // The login button
    public Button button2; // The registration button
    public TextField IDField; // The text field for username input
    public TextField passwordField; // The text field for password input
    private static String usernameStr = null; // Stores the username input
    private static String passwordStr; // Stores the password input

    /**
     * Constructor for loginGUI.
     * Initializes and sets up the login screen.
     *
     * @param primaryStage The primary stage for the application
     * @param main An instance of the Main class, used for accessing constants
     */
    public LoginGUI(Stage primaryStage, Main main){
        root = new Group(); // Creates a new Group to hold all GUI components
        scene = new Scene(root, Main.MAX_WIDTH, Main.MAX_HEIGHT); // Creates a new Scene with specified dimensions
        scene.setFill(Color.web(Main.PRIMARY_COLOUR)); // Sets the background color of the scene

        Rectangle rect = new Rectangle(500, 0, 500, 700); // Creates a rectangle to serve as a background panel
        rect.setFill(Color.web(Main.SECONDARY_COLOUR)); // Sets the fill color of the rectangle

        Text text = new Text (630, 250, "Welcome Back"); // Creates a welcome message text
        text.setFont(Font.font(Main.BIG_HEADER_FONT, FontWeight.BOLD, 36)); // Sets the font, weight, and size of the text
        text.setFill(Color.web(Main.FOURTH_COLOUR)); // Sets the text color

        IDField = new TextField(); // Creates a new text field for username input
        IDField.setLayoutX(675); // Sets the x-coordinate for positioning
        IDField.setLayoutY(300); // Sets the y-coordinate for positioning
        IDField.setPromptText("Username"); // Sets the placeholder text
        IDField.setFont(Font.font(Main.TEXT_FONT)); // Sets the font of the text field

        Label label1 = new Label("Username"); // Creates a label for the username field
        label1.setLayoutX(590); // Sets the x-coordinate for positioning
        label1.setLayoutY(300); // Sets the y-coordinate for positioning
        label1.setFont(Font.font(Main.TEXT_FONT)); // Sets the font of the label

        passwordField = new TextField(); // Creates a new text field for password input
        passwordField.setLayoutX(675); // Sets the x-coordinate for positioning
        passwordField.setLayoutY(350); // Sets the y-coordinate for positioning
        passwordField.setPromptText("Password"); // Sets the placeholder text
        passwordField.setFont(Font.font(Main.TEXT_FONT)); // Sets the font of the text field

        Label label2 = new Label ("Password"); // Creates a label for the password field
        label2.setLayoutX(590); // Sets the x-coordinate for positioning
        label2.setLayoutY(350); // Sets the y-coordinate for positioning
        label2.setFont(Font.font(Main.TEXT_FONT)); // Sets the font of the label

        button = new Button ("Login"); // Creates a login button
        button.setLayoutX(725); // Sets the x-coordinate for positioning
        button.setLayoutY(400); // Sets the y-coordinate for positioning
        button.setFont(Font.font(Main.TEXT_FONT)); // Sets the font of the button

        button2 = new Button ("No Account Yet? Register!"); // Creates a registration button
        button2.setLayoutX(675); // Sets the x-coordinate for positioning
        button2.setLayoutY(430); // Sets the y-coordinate for positioning
        button2.setFont(Font.font(Main.TEXT_FONT)); // Sets the font of the button

        // Adds all GUI components to the root node
        root.getChildren().addAll(rect, text, label1, label2, IDField, passwordField, button, button2);

        // Sets the action for the registration button
        button2.setOnAction(event -> {
            //primaryStage.setScene(registerGUI.getScene2()); // Set scene for registration GUI (commented out)
        });

        // Sets the action for the login button
        button.setOnAction(event -> {
            usernameStr = IDField.getText(); // Retrieves the username input
            passwordStr = passwordField.getText(); // Retrieves the password input

            //System.out.println("Username: " + IDField.getText()); // Prints the username to the console
            //System.out.println("Password: " + passwordField.getText()); // Prints the password to the console

            // Validates the login credentials
            if (JDBC.validateLogin(usernameStr, passwordStr)){
                // Checks if the username and password fields are not empty
                if (!IDField.getText().isEmpty() && !passwordField.getText().isEmpty()){
                    DashboardGUI dashboardGUI = new DashboardGUI(primaryStage, main); // Creates a new dashboardGUI object
                    primaryStage.setScene(dashboardGUI.getScene3()); // Sets the scene to the dashboard GUI
                }
            }
        });
    }

    /**
     * Gets the username string.
     *
     * @return the username string
     */
    public static String getUsernameStr(){
        return usernameStr;
    }

    /**
     * Sets the username string.
     *
     * @param usernameStr the username string to set
     */
    public void setUsernameStr(String usernameStr){
        LoginGUI.usernameStr = usernameStr;
    }

    /**
     * Gets the password string.
     *
     * @return the password string
     */
    public static String getPasswordStr(){
        return passwordStr;
    }

    /**
     * Sets the password string.
     *
     * @param passwordStr the password string to set
     */
    public void setPasswordStr(String passwordStr){
        LoginGUI.passwordStr = passwordStr;
    }

    /**
     * Gets the scene for the login GUI.
     *
     * @return the scene for the login GUI
     */
    public static Scene getScene(){
        return scene;
    }
}
