import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Main application class for the Banking App.
 * This class sets up the JavaFX application and provides utility methods for formatting and validation.
 *
 * @author Jimmy Roy
 * @version August 2024
 */
public class Main extends Application {

    /** Maximum height for the application window */
    public static final int MAX_HEIGHT = 700;

    /** Maximum width for the application window */
    public static final int MAX_WIDTH = 1000;

    /** Primary color used in the application */
    public static final String PRIMARY_COLOUR = "#D6F9DD";

    /** Secondary color used in the application */
    public static final String SECONDARY_COLOUR = "#99F7AB";

    /** Third color used in the application */
    public static final String THIRD_COLOUR = "#ABDF75";

    /** Fourth color used in the application */
    public static final String FOURTH_COLOUR = "#60695C";

    /** Fifth color used in the application */
    public static final String FIFTH_COLOUR = "#C5D6D8";

    /** Font used for big headers */
    public static final String BIG_HEADER_FONT = "Impact";

    /** Font used for headers */
    public static final String HEADER_FONT = "Verdana";

    /** Font used for text */
    public static final String TEXT_FONT = "Arial";

    /**
     * The main method which serves as the entry point for the Java application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        //System.out.println("Hello world!");
        launch(args);
    }

    /**
     * Formats a double value to two decimal places.
     *
     * @param amount the double value to format
     * @return the formatted string with two decimal places
     */
    public static String twoDecimal(double amount) {
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.HALF_UP); // Ensures correct rounding
        return df.format(amount);
    }

    /**
     * Validates if the given string matches the pattern for a valid amount with two decimal places.
     *
     * @param input the string to validate
     * @return true if the string matches the pattern, false otherwise
     */
    public static boolean validateString(String input) {
        // Define the regex pattern
        String regex = "\\d+\\.\\d{2}";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher to match the input string against the pattern
        Matcher matcher = pattern.matcher(input);

        // Return whether the input string matches the pattern
        return matcher.matches();
    }

    /**
     * Centers the given text horizontally within the specified scene.
     *
     * @param text the Text object to center
     * @param scene the Scene object within which to center the text
     */
    public static void centerText(Text text, Scene scene) {
        // Get the bounds of the text
        Bounds bounds = text.getBoundsInLocal();

        // Calculate the center position horizontally
        double x = (1000 - bounds.getWidth()) / 2;

        // Set the text position
        text.setX(x);
    }

    /**
     * The start method is the main entry point for all JavaFX applications.
     * This method is called after the init method has returned, and after the system is ready for the application to begin running.
     *
     * @param primaryStage the primary stage for this application, onto which the application scene can be set
     */
    @Override
    public void start(Stage primaryStage) {
        LoginGUI loginGUI = new LoginGUI(primaryStage, this);
        RegisterGUI registerGUI = new RegisterGUI(primaryStage, this);
        //dashboardGUI dashboardGUI = new dashboardGUI(primaryStage, this);

        primaryStage.setScene(loginGUI.getScene());
        primaryStage.setTitle("Banking App");
        primaryStage.show();

        loginGUI.button2.setOnAction(event -> primaryStage.setScene(registerGUI.getScene2()));
    }
}
