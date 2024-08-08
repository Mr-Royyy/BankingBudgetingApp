import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class represents the graphical user interface for managing monthly savings.
 * It allows users to view their total savings, get feedback on their savings habits,
 * and navigate back to the dashboard.
 *
 * @author Jimmy Roy
 * @version August 2024
 */
public class MonthlySavingsGUI {
    private Group root8; // The root node for the scene graph
    private static Scene scene8; // The scene for the monthly savings screen
    private Text commentSavingsText; // Text node for displaying comments on savings
    private Text totalSavedText; // Text node for displaying total savings
    private static double doubleMonthlyIncome; // Stores the user's monthly income
    private static double doubleMonthlyExpense; // Stores the user's monthly expenses
    private static String strTotalSavings; // Stores the total savings as a string
    private static double doubleTotalSavings; // Stores the total savings as a double

    /**
     * Constructor for MonthlySavingsGUI.
     * Initializes and sets up the monthly savings screen.
     *
     * @param primaryStage The primary stage for the application
     * @param main An instance of the Main class, used for accessing constants and methods
     */
    public MonthlySavingsGUI(Stage primaryStage, Main main) {
        root8 = new Group(); // Creates a new Group to hold all GUI components
        scene8 = new Scene(root8, Main.MAX_WIDTH, Main.MAX_HEIGHT); // Creates a new Scene with specified dimensions
        scene8.setFill(Color.web(Main.PRIMARY_COLOUR)); // Sets the background color of the scene

        // Retrieves the user's first name from the database
        String firstName = JDBC.getFirstName(LoginGUI.getUsernameStr());

        // Creates a greeting message
        Text greet = new Text(0, 50, "Hello " + firstName + ", welcome to your monthly savings");
        greet.setFont(Font.font(Main.BIG_HEADER_FONT, FontWeight.BOLD, 35)); // Sets the font, weight, and size of the text
        Main.centerText(greet, scene8); // Centers the text horizontally

        // Creates a label for displaying total savings
        Text currentBalance = new Text(0, 130, "YOUR TOTAL SAVINGS");
        currentBalance.setFont(Font.font(Main.HEADER_FONT, FontWeight.NORMAL, 25)); // Sets the font, weight, and size
        Main.centerText(currentBalance, scene8); // Centers the text horizontally

        // Updates the savings values from the database
        updateMonthlySavingsValue(LoginGUI.getUsernameStr());

        // Creates a text node for displaying the total savings
        totalSavedText = new Text(0, 250, "$" + strTotalSavings);
        totalSavedText.setFont(Font.font(Main.HEADER_FONT, FontWeight.BOLD, 42)); // Sets the font, weight, and size
        Main.centerText(totalSavedText, scene8); // Centers the text horizontally

        // Creates a text node for displaying comments on the savings
        commentSavingsText = new Text(0, 400, evaluateSavings(doubleMonthlyIncome, doubleMonthlyExpense, doubleTotalSavings));
        commentSavingsText.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 20)); // Sets the font and size
        Main.centerText(commentSavingsText, scene8); // Centers the text horizontally

        // Loads an image for the home button
        Image home = new Image("images/homeicon.png");
        Button returnToDashboard = new Button(); // Creates a button for returning to the dashboard
        returnToDashboard.setGraphic(new ImageView(home)); // Sets the button graphic to the home image
        returnToDashboard.setLayoutX(925); // Sets the x-coordinate for positioning
        returnToDashboard.setLayoutY(27); // Sets the y-coordinate for positioning

        // Sets the action for the return button
        returnToDashboard.setOnAction(event -> {
            DashboardGUI.updateDashboard(); // Updates the dashboard
            primaryStage.setScene(DashboardGUI.getScene3()); // Switches to the dashboard scene
            DashboardGUI.resetSelections(); // Resets selections in the dashboard
        });

        // Adds all GUI components to the root node
        root8.getChildren().addAll(greet, currentBalance, totalSavedText, commentSavingsText, returnToDashboard);
    }

    /**
     * Evaluates the user's savings and provides feedback based on their savings percentage.
     *
     * @param doubleMonthlyIncome The user's monthly income
     * @param doubleMonthlyExpense The user's monthly expenses
     * @param totalSavings The user's total savings
     * @return A message providing feedback on the user's savings
     */
    public static String evaluateSavings(double doubleMonthlyIncome, double doubleMonthlyExpense, double totalSavings) {
        String message;
        double savingsPercentage = (totalSavings / doubleMonthlyIncome) * 100; // Calculates the savings percentage

        // Determines the appropriate feedback message based on the savings percentage and expenses
        if (doubleMonthlyExpense > doubleMonthlyIncome) {
            message = "Warning: Your expenses exceed your income. This is concerning and should be addressed immediately.";
        } else if (savingsPercentage < 20) {
            message = "Caution: You are saving less than the recommended 20% of your income. \n\tConsider adjusting your expenses or finding ways to save more.";
        } else if (savingsPercentage >= 20 && savingsPercentage <= 50) {
            message = "Good job: You are saving a healthy amount of your income. Keep up the good work!";
        } else {
            message = "Great job: You are saving a lot of your income. You can spend a bit more on fun activities or investments.";
        }
        return message;
    }

    /**
     * Updates the monthly savings values from the database.
     *
     * @param ID The user's ID for fetching data from the database
     */
    public static void updateMonthlySavingsValue(String ID) {
        // Updates the monthly savings in the database
        JDBC.updateMonthlySavings(LoginGUI.getUsernameStr());
        doubleMonthlyIncome = Double.parseDouble(JDBC.getMonthlyIncome(LoginGUI.getUsernameStr())); // Retrieves the monthly income
        doubleMonthlyExpense = Double.parseDouble(JDBC.getMonthlyExpense(LoginGUI.getUsernameStr())); // Retrieves the monthly expenses
        strTotalSavings = JDBC.getMonthlySavings(LoginGUI.getUsernameStr()); // Retrieves the total savings as a string
        doubleTotalSavings = Double.parseDouble(strTotalSavings); // Converts the total savings to a double
    }

    /**
     * Returns the scene for the monthly savings GUI.
     *
     * @return The scene for the monthly savings GUI
     */
    public static Scene getScene8() {
        return scene8;
    }
}
