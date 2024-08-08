import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class represents the graphical user interface for managing monthly income.
 * It allows users to input, submit, and view their monthly income.
 *
 * @author Jimmy Roy
 * @version August 2024
 */
public class MonthlyIncomeGUI {
    private Group root6; // The root node for the scene graph
    private static Scene scene6; // The scene for the monthly income screen
    public TextField monthlyIncomeField; // Text field for entering the monthly income
    public Button button; // Button to submit the income
    private Text monthlyIncomeText; // Text node for displaying the monthly income
    private Text totalIncome; // Text node for displaying total income
    public static String firstName; // Stores the user's first name

    /**
     * Constructor for MonthlyIncomeGUI.
     * Initializes and sets up the monthly income screen.
     *
     * @param primaryStage The primary stage for the application
     * @param main An instance of the Main class, used for accessing constants and methods
     */
    public MonthlyIncomeGUI(Stage primaryStage, Main main) {
        root6 = new Group(); // Creates a new Group to hold all GUI components
        scene6 = new Scene(root6, Main.MAX_WIDTH, Main.MAX_HEIGHT); // Creates a new Scene with specified dimensions
        scene6.setFill(Color.web(Main.PRIMARY_COLOUR)); // Sets the background color of the scene

        // Retrieves the user's first name from the database
        firstName = JDBC.getFirstName(LoginGUI.getUsernameStr());

        // Creates a greeting message
        Text greet = new Text(0, 50, "Hello " + firstName + ", welcome to your monthly income");
        greet.setFont(Font.font(Main.BIG_HEADER_FONT, FontWeight.BOLD, 35)); // Sets the font, weight, and size of the text
        Main.centerText(greet, scene6); // Centers the text horizontally

        // Creates a label for displaying the total income
        Text currentBalance = new Text (0, 130, "YOUR TOTAL INCOME");
        currentBalance.setFont(Font.font(Main.HEADER_FONT, FontWeight.NORMAL, 25)); // Sets the font, weight, and size
        Main.centerText(currentBalance, scene6); // Centers the text horizontally

        // Creates a text node for displaying the monthly income
        monthlyIncomeText = new Text(0, 250, "$" + JDBC.getMonthlyIncome(LoginGUI.getUsernameStr()));
        monthlyIncomeText.setFont(Font.font(Main.HEADER_FONT, FontWeight.BOLD, 42)); // Sets the font, weight, and size
        Main.centerText(monthlyIncomeText, scene6); // Centers the text horizontally

        // Creates a label for depositing money
        Text depositMoneyText = new Text(260, 400, "Deposit Income");
        depositMoneyText.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 20)); // Sets the font and size

        // Creates a text field for entering the income amount
        monthlyIncomeField = new TextField();
        monthlyIncomeField.setLayoutX(430); // Sets the x-coordinate for positioning
        monthlyIncomeField.setLayoutY(380); // Sets the y-coordinate for positioning
        monthlyIncomeField.setPromptText("Amount in _.__"); // Sets the placeholder text
        monthlyIncomeField.setFont(Font.font(Main.TEXT_FONT)); // Sets the font

        // Creates a text node for displaying format errors
        Text formatError = new Text(680, 397, "");
        formatError.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15)); // Sets the font and size
        formatError.setFill(Color.RED); // Sets the text color to red

        // Creates a submit button
        button = new Button("Submit");
        button.setLayoutX(615); // Sets the x-coordinate for positioning
        button.setLayoutY(380); // Sets the y-coordinate for positioning

        // Sets the action for the submit button
        button.setOnAction(event -> {
            String strAmount = monthlyIncomeField.getText(); // Retrieves the income amount input
            if (Main.validateString(strAmount)) { // Validates the income amount format
                double doubleAmount = Double.parseDouble(strAmount); // Converts the amount to a double
                JDBC.depositMonthlyIncome(LoginGUI.getUsernameStr(), doubleAmount); // Deposits the income amount
                updateIncome(); // Updates the displayed income
                MonthlySavingsGUI.updateMonthlySavingsValue(LoginGUI.getUsernameStr()); // Updates the monthly savings
            } else {
                formatError.setText("Invalid amount format. Use two decimal places"); // Displays an error message for invalid format
            }
        });

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
        root6.getChildren().addAll(greet, currentBalance, returnToDashboard, monthlyIncomeText, depositMoneyText, monthlyIncomeField, button, formatError);
    }

    /**
     * Updates the text node displaying the monthly income.
     */
    private void updateIncome() {
        // Updates the text node with the current monthly income
        monthlyIncomeText.setText("$" + JDBC.getMonthlyIncome(LoginGUI.getUsernameStr()));
    }

    /**
     * Returns the scene for the monthly income GUI.
     *
     * @return The scene for the monthly income GUI
     */
    public static Scene getScene6() {
        return scene6;
    }
}
