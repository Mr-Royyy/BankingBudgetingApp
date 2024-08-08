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
 * This class represents the graphical user interface for managing savings in the application.
 * It provides functionality to view the current balance, withdraw money, and navigate back to the dashboard.
 *
 * @author Jimmy Roy
 * @version August 2024
 */
public class SavingsGUI {
    private Group root4; // Root node for the scene graph
    private static Scene scene4; // The scene for the SavingsGUI
    public TextField withdrawMoneyField; // Field to input the amount to withdraw
    public Button button; // Button to submit the withdrawal request
    private static String strWithdrawAmount; // Amount to withdraw
    public String saving; // Variable for saving details
    public Text savingsValue;

    /**
     * Constructor for the SavingsGUI class.
     * Initializes the GUI components and sets up the layout for the savings interface.
     *
     * @param primaryStage The primary stage for the application
     * @param main The main application instance
     */
    public SavingsGUI(Stage primaryStage, Main main) {
        root4 = new Group(); // Creates a new Group node
        scene4 = new Scene(root4, Main.MAX_WIDTH, Main.MAX_HEIGHT); // Creates a new Scene with the specified dimensions
        scene4.setFill(Color.web(Main.PRIMARY_COLOUR)); // Sets the background color of the scene

        // Retrieves the current savings value and the user's first name from the database
        String savings = JDBC.getSavingsValue(LoginGUI.getUsernameStr());
        String firstName = JDBC.getFirstName(LoginGUI.getUsernameStr());

        // Creates and configures a Text node for greeting the user
        Text greet = new Text(100, 50, "Hello " + firstName + ", welcome to your savings account");
        greet.setFont(Font.font(Main.BIG_HEADER_FONT, FontWeight.BOLD, 35)); // Sets the font style and size
        Main.centerText(greet, scene4); // Centers the text in the scene

        // Creates and configures a Text node for displaying the current balance label
        Text currentBalance = new Text(400, 150, "YOUR CURRENT BALANCE");
        currentBalance.setFont(Font.font(Main.HEADER_FONT, FontWeight.NORMAL, 25)); // Sets the font style and size
        Main.centerText(currentBalance, scene4); // Centers the text in the scene

        // Creates and configures a Text node for displaying the current savings value
        savingsValue = new Text(400, 250, "$" + savings);
        savingsValue.setFont(Font.font(Main.HEADER_FONT, FontWeight.BOLD, 42)); // Sets the font style and size
        savingsValue.setStroke(Color.BLACK); // Sets the text stroke color
        Main.centerText(savingsValue, scene4); // Centers the text in the scene

        // Creates and configures a Text node for displaying the number of transactions left for the month
        Text montlyTransactionsAvaliable = new Text(350, 350, "You have " + JDBC.getTransactionsLeft(LoginGUI.getUsernameStr()) + " transactions left for this month");
        montlyTransactionsAvaliable.setFont(Font.font(Main.HEADER_FONT, FontWeight.NORMAL, 25)); // Sets the font style and size
        Main.centerText(montlyTransactionsAvaliable, scene4); // Centers the text in the scene

        // Creates and configures a TextField for entering the withdrawal amount
        withdrawMoneyField = new TextField();
        withdrawMoneyField.setLayoutX(430); // Sets the X position of the TextField
        withdrawMoneyField.setLayoutY(430); // Sets the Y position of the TextField
        withdrawMoneyField.setPromptText("Amount in _.__"); // Sets the prompt text for the TextField
        withdrawMoneyField.setFont(Font.font(Main.TEXT_FONT)); // Sets the font for the TextField

        // Creates and configures a Text node for the withdrawal label
        Text withdrawMoneyText = new Text(240, 450, "Withdraw Money");
        withdrawMoneyText.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 20)); // Sets the font style and size

        // Creates and configures a Text node for displaying format error messages
        Text formatError = new Text(680, 447, "");
        formatError.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15)); // Sets the font style and size
        formatError.setFill(Color.RED); // Sets the text color for error messages

        // Creates and configures a Button for submitting the withdrawal request
        button = new Button("Submit");
        button.setLayoutX(615); // Sets the X position of the Button
        button.setLayoutY(430); // Sets the Y position of the Button

        // Defines the action for the Button when clicked
        button.setOnAction(event -> {
            strWithdrawAmount = withdrawMoneyField.getText(); // Gets the withdrawal amount from the TextField
            if (Main.validateString(strWithdrawAmount)) { // Validates the format of the withdrawal amount
                SavingsAccount.withdrawSavings(LoginGUI.getUsernameStr(), strWithdrawAmount); // Withdraws money from savings account
                updateSavings();
            } else {
                formatError.setText("Invalid amount format. Use two decimal places"); // Sets the error message if the format is invalid
            }
        });

        // Creates and configures an Image for the home button
        Image home = new Image("images/homeicon.png");
        Button returnToDashboard = new Button(); // Creates a new Button
        returnToDashboard.setGraphic(new ImageView(home)); // Sets the ImageView for the Button
        returnToDashboard.setLayoutX(925); // Sets the X position of the Button
        returnToDashboard.setLayoutY(27); // Sets the Y position of the Button

        // Defines the action for the Button when clicked
        returnToDashboard.setOnAction(event -> {
            DashboardGUI.updateDashboard(); // Updates the dashboard
            primaryStage.setScene(DashboardGUI.getScene3()); // Sets the scene to the dashboard scene
            DashboardGUI.resetSelections(); // Resets the selections on the dashboard
        });

        // Adds all the nodes to the root group
        root4.getChildren().addAll(savingsValue, greet, currentBalance, montlyTransactionsAvaliable, returnToDashboard, withdrawMoneyText, withdrawMoneyField, button, formatError);
    }
    private void updateSavings() {
        String savingsValueStr = JDBC.getSavingsValue(LoginGUI.getUsernameStr());
        // Convert to double and format to two decimal places
        double savingsValueDouble = Double.parseDouble(savingsValueStr);
        savingsValueStr = Main.twoDecimal(savingsValueDouble);
        savingsValue.setText("$" + savingsValueStr);
    }
    /**
     * Retrieves the current withdrawal amount.
     *
     * @return The withdrawal amount as a String
     */
    public static String getStrWithdrawAmount() {
        return strWithdrawAmount;
    }

    /**
     * Retrieves the scene for the SavingsGUI.
     *
     * @return The scene4
     */
    public static Scene getScene4() {
        return scene4;
    }
}
