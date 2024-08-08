import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class represents the graphical user interface for managing monthly expenses.
 * It allows users to input, submit, and view their expenses categorized by type.
 *
 * @author Jimmy Roy
 * @version August 2024
 */
public class MonthlyExpenseGUI {
    private Group root7; // The root node for the scene graph
    private static Scene scene7; // The scene for the monthly expense screen
    public TextField expenseAmount; // Text field for entering the expense amount
    public Button submit; // Button to submit the expense
    public static String strExpenceAmount; // Stores the expense amount input as a string
    public static ComboBox<String> expenseType; // Combo box for selecting the expense type
    public static String strSelectedExpenceType; // Stores the selected expense type
    // Text nodes for displaying expenses
    private Text housingText; // Text node for housing expenses
    private Text utilitiesText; // Text node for utilities expenses
    private Text transportationText; // Text node for transportation expenses
    private Text foodText; // Text node for food expenses
    private Text personalText; // Text node for personal expenses
    private Text entertainmentText; // Text node for entertainment expenses
    private Text amortizationText; // Text node for amortization expenses
    private Text miscellaneousText; // Text node for miscellaneous expenses
    private Text currentBalance; // Text node for current balance
    private Text totalExpencesText; // Text node for total expenses

    /**
     * Constructor for MonthlyExpenseGUI.
     * Initializes and sets up the monthly expense screen.
     *
     * @param primaryStage The primary stage for the application
     * @param main An instance of the Main class, used for accessing constants and methods
     */
    public MonthlyExpenseGUI(Stage primaryStage, Main main) {
        root7 = new Group(); // Creates a new Group to hold all GUI components
        scene7 = new Scene(root7, Main.MAX_WIDTH, Main.MAX_HEIGHT); // Creates a new Scene with specified dimensions
        scene7.setFill(Color.web(Main.PRIMARY_COLOUR)); // Sets the background color of the scene

        // Retrieves the user's first name from the database
        String firstName = JDBC.getFirstName(LoginGUI.getUsernameStr());

        // Creates a greeting message
        Text greet = new Text(100, 50, "Hello " + firstName + ", welcome to your monthly expense");
        greet.setFont(Font.font(Main.BIG_HEADER_FONT, FontWeight.BOLD, 35)); // Sets the font, weight, and size of the text
        Main.centerText(greet, scene7); // Centers the text horizontally

        // Creates a label for depositing money
        Text depositMoneyText = new Text(150, 320, "Deposit Money");
        depositMoneyText.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 20)); // Sets the font and size

        // Creates a text field for entering the expense amount
        expenseAmount = new TextField();
        expenseAmount.setLayoutX(80); // Sets the x-coordinate for positioning
        expenseAmount.setLayoutY(360); // Sets the y-coordinate for positioning
        expenseAmount.setPromptText("Amount in _.__"); // Sets the placeholder text

        // Creates a combo box for selecting the expense type
        expenseType = new ComboBox<>();
        expenseType.getItems().addAll("Housing", "Utilities", "Transportation", "Food", "Personal", "Entertainment", "Amortization", "Miscellaneous");
        expenseType.setLayoutX(240); // Sets the x-coordinate for positioning
        expenseType.setLayoutY(360); // Sets the y-coordinate for positioning
        expenseType.setPromptText("Expense Types"); // Sets the placeholder text

        // Creates a text node for displaying format errors
        Text formatError = new Text(80, 405, "");
        formatError.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15)); // Sets the font and size
        formatError.setFill(Color.RED); // Sets the text color to red

        // Creates a submit button
        submit = new Button("Submit");
        submit.setLayoutX(380); // Sets the x-coordinate for positioning
        submit.setLayoutY(360); // Sets the y-coordinate for positioning
        submit.setOnAction(event -> {
            strExpenceAmount = expenseAmount.getText(); // Retrieves the expense amount input
            strSelectedExpenceType = expenseType.getSelectionModel().getSelectedItem(); // Retrieves the selected expense type

            // Validates the expense amount format
            if (Main.validateString(strExpenceAmount)) {
                // Checks if both the amount and type are not null
                if (strExpenceAmount != null && strSelectedExpenceType != null) {
                    double doubleExpenseAmount = Double.parseDouble(strExpenceAmount); // Converts the amount to a double
                    JDBC.updateMonthlyExpense(LoginGUI.getUsernameStr(), strSelectedExpenceType, doubleExpenseAmount); // Updates the monthly expense
                    JDBC.depositMonthlyExpense(LoginGUI.getUsernameStr(), doubleExpenseAmount); // Deposits the expense amount
                    //System.out.println("Amount: " + strExpenceAmount + " | Type: " + strSelectedExpenceType); // Prints the expense details to the console
                    MonthlySavingsGUI.updateMonthlySavingsValue(LoginGUI.getUsernameStr()); // Updates the monthly savings
                    updateExpenses(); // Updates the displayed expenses
                }
            } else {
                formatError.setText("Invalid amount format. Use two decimal places."); // Displays an error message for invalid format
            }
        });

        // Creates a text node for displaying the breakdown of monthly expenses
        Text yourExpencesText = new Text(600, 320, "Your Monthly Expenses Broken Down");
        yourExpencesText.setFont(Font.font(Main.TEXT_FONT, FontWeight.BOLD, 20)); // Sets the font, weight, and size

        // Initializes text nodes for each expense type
        housingText = new Text(600, 350, "Housing: ");
        housingText.setFont(Font.font(Main.TEXT_FONT, 20)); // Sets the font and size

        utilitiesText = new Text(600, 380, "Utilities: ");
        utilitiesText.setFont(Font.font(Main.TEXT_FONT, 20)); // Sets the font and size

        transportationText = new Text(600, 410, "Transportation: ");
        transportationText.setFont(Font.font(Main.TEXT_FONT, 20)); // Sets the font and size

        foodText = new Text(600, 440, "Food: ");
        foodText.setFont(Font.font(Main.TEXT_FONT, 20)); // Sets the font and size

        personalText = new Text(600, 470, "Personal: ");
        personalText.setFont(Font.font(Main.TEXT_FONT, 20)); // Sets the font and size

        entertainmentText = new Text(600, 500, "Entertainment: ");
        entertainmentText.setFont(Font.font(Main.TEXT_FONT, 20)); // Sets the font and size

        amortizationText = new Text(600, 530, "Amortization: ");
        amortizationText.setFont(Font.font(Main.TEXT_FONT, 20)); // Sets the font and size

        miscellaneousText = new Text(600, 560, "Miscellaneous: ");
        miscellaneousText.setFont(Font.font(Main.TEXT_FONT, 20)); // Sets the font and size

        // Creates a text node for displaying the total expenses
        currentBalance = new Text(0, 130, "YOUR TOTAL EXPENSES");
        currentBalance.setFont(Font.font(Main.TEXT_FONT, 25)); // Sets the font and size
        centerText(currentBalance, scene7); // Centers the text horizontally

        // Creates a text node for displaying the total expenses value
        totalExpencesText = new Text(0, 250, "$" + JDBC.getMonthlyExpense(LoginGUI.getUsernameStr()));
        totalExpencesText.setFont(Font.font(Main.HEADER_FONT, FontWeight.BOLD, 42)); // Sets the font, weight, and size
        Main.centerText(totalExpencesText, scene7); // Centers the text horizontally

        updateExpenses(); // Updates the displayed expenses

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
        root7.getChildren().addAll(greet, depositMoneyText, returnToDashboard, expenseAmount, expenseType, submit, yourExpencesText,
                housingText, utilitiesText, transportationText, foodText, personalText,
                entertainmentText, amortizationText, miscellaneousText, totalExpencesText, currentBalance, formatError);
    }

    /**
     * Returns the scene for the monthly expense GUI.
     *
     * @return The scene for the monthly expense GUI
     */
    public static Scene getScene7() {
        return scene7;
    }

    /**
     * Updates the text nodes displaying each type of expense and the total expenses.
     */
    private void updateExpenses() {
        String userId = LoginGUI.getUsernameStr(); // Retrieves the username

        // Updates each text node with the corresponding expense amount
        housingText.setText("Housing: " + JDBC.getMonthlyExpenseTypeAmount(userId, "Housing"));
        utilitiesText.setText("Utilities: " + JDBC.getMonthlyExpenseTypeAmount(userId, "Utilities"));
        transportationText.setText("Transportation: " + JDBC.getMonthlyExpenseTypeAmount(userId, "Transportation"));
        foodText.setText("Food: " + JDBC.getMonthlyExpenseTypeAmount(userId, "Food"));
        personalText.setText("Personal: " + JDBC.getMonthlyExpenseTypeAmount(userId, "Personal"));
        entertainmentText.setText("Entertainment: " + JDBC.getMonthlyExpenseTypeAmount(userId, "Entertainment"));
        amortizationText.setText("Amortization: " + JDBC.getMonthlyExpenseTypeAmount(userId, "Amortization"));
        miscellaneousText.setText("Miscellaneous: " + JDBC.getMonthlyExpenseTypeAmount(userId, "Miscellaneous"));

        currentBalance.setText("YOUR TOTAL EXPENSES"); // Updates the total expenses label
        centerText(currentBalance, scene7); // Centers the total expenses label horizontally

        // Updates the total expenses value
        totalExpencesText.setText("$" + JDBC.getMonthlyExpense(LoginGUI.getUsernameStr()));
        centerText(totalExpencesText, scene7); // Centers the total expenses value horizontally
    }

    /**
     * Calculates the total expenses for all categories.
     *
     * @param ID The user ID
     * @return A string representing the total expenses formatted to two decimal places
     */
    private static String totalExpences(String ID) {
        // Calculates the total expenses by summing up amounts for each category
        double doubleTotal = Double.parseDouble(JDBC.getMonthlyExpenseTypeAmount(ID, "Housing"))
                + Double.parseDouble(JDBC.getMonthlyExpenseTypeAmount(ID, "Utilities"))
                + Double.parseDouble(JDBC.getMonthlyExpenseTypeAmount(ID, "Transportation"))
                + Double.parseDouble(JDBC.getMonthlyExpenseTypeAmount(ID, "Food"))
                + Double.parseDouble(JDBC.getMonthlyExpenseTypeAmount(ID, "Personal"))
                + Double.parseDouble(JDBC.getMonthlyExpenseTypeAmount(ID, "Entertainment"))
                + Double.parseDouble(JDBC.getMonthlyExpenseTypeAmount(ID, "Amortization"))
                + Double.parseDouble(JDBC.getMonthlyExpenseTypeAmount(ID, "Miscellaneous"));

        return Main.twoDecimal(doubleTotal); // Formats the total to two decimal places and returns it
    }

    /**
     * Centers the text horizontally in the scene.
     *
     * @param text The text node to center
     * @param scene The scene in which to center the text
     */
    private void centerText(Text text, Scene scene) {
        // Get the bounds of the text
        Bounds bounds = text.getBoundsInLocal();

        // Calculate the center position horizontally
        double x = (1000 - bounds.getWidth()) / 2;

        // Set the text position
        text.setX(x);
    }
}
