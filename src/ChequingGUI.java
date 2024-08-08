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
 * Represents the GUI for the Chequing Account.
 * Provides functionality for viewing account balance, depositing money, and transferring money to savings.
 *
 * @author Jimmy Roy
 * @version August 2024
 */
public class ChequingGUI {
    /** Root group for the scene */
    private Group root5;

    /** Scene for the chequing account GUI */
    private static Scene scene5;

    /** TextField for entering the deposit amount */
    public TextField depositMoneyField;

    /** Button for submitting the deposit */
    public Button button;

    /** TextField for entering the deposit amount to savings */
    public TextField depositMoneyToSavingsField;

    /** Button for submitting the deposit to savings */
    public Button buttonSavings;

    /** String to hold the deposit amount */
    private static String strDepositAmount;

    /** String representing the chequing account */
    public String chequing;

    /** Text to display the chequing account balance */
    private Text chequingValue;

    /**
     * Constructs the ChequingGUI object.
     *
     * @param primaryStage the primary stage for this application
     * @param main the main application object
     */
    public ChequingGUI(Stage primaryStage, Main main) {
        // Initialize root group and scene
        root5 = new Group();
        scene5 = new Scene(root5, Main.MAX_WIDTH, Main.MAX_HEIGHT);
        scene5.setFill(Color.web(Main.PRIMARY_COLOUR));

        // Retrieve the first name of the user from the database
        String firstName = JDBC.getFirstName(LoginGUI.getUsernameStr()); // first name is Jimmy
        String message = "Hello " + firstName + ", Welcome to Your Chequing Account";

        // Create and configure greeting text
        Text greet = new Text(0, 50, message);
        greet.setFont(Font.font(Main.BIG_HEADER_FONT, FontWeight.BOLD, 35));
        Main.centerText(greet, scene5);

        // Create and configure current balance label
        Text currentBalance = new Text(0, 130, "YOUR CURRENT BALANCE");
        currentBalance.setFont(Font.font(Main.HEADER_FONT, FontWeight.NORMAL, 25));
        Main.centerText(currentBalance, scene5);

        // Retrieve and display the chequing account balance
        chequingValue = new Text(0, 250, "$" + JDBC.getChequingValue(LoginGUI.getUsernameStr()));
        chequingValue.setFont(Font.font(Main.HEADER_FONT, FontWeight.BOLD, 42));
        Main.centerText(chequingValue, scene5);

        // Display the number of transactions left for the month
        Text montlyTransactionsAvaliable = new Text(0, 350, "You have " + JDBC.getTransactionsLeft(LoginGUI.getUsernameStr()) + " transactions left for this month");
        montlyTransactionsAvaliable.setFont(Font.font(Main.HEADER_FONT, FontWeight.NORMAL, 25));
        Main.centerText(montlyTransactionsAvaliable, scene5);

        // Initialize and configure the deposit money text field
        depositMoneyField = new TextField();
        depositMoneyField.setLayoutX(430);
        depositMoneyField.setLayoutY(430);
        depositMoneyField.setPromptText("Amount in _.__");
        depositMoneyField.setFont(Font.font(Main.TEXT_FONT));

        // Create and configure deposit money label
        Text depositMoneyText = new Text(260, 450, "Deposit Money");
        depositMoneyText.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 20));

        // Initialize and configure the submit button for depositing money
        button = new Button("Submit");
        button.setLayoutX(615);
        button.setLayoutY(430);

        // Create and configure the error message text for deposit format
        Text formatError = new Text(680, 447, "");
        formatError.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15));
        formatError.setFill(Color.RED);

        // Set action for the submit button for depositing money
        button.setOnAction(event -> {
            formatError.setText("");
            strDepositAmount = depositMoneyField.getText();
            if (Main.validateString(strDepositAmount)) {
                ChequingAccount.depositChequing(LoginGUI.getUsernameStr(), strDepositAmount);
                updateChequing();
            } else {
                formatError.setText("Invalid amount format. Use two decimal places");
            }
        });

        // Initialize and configure the deposit money to savings text field
        depositMoneyToSavingsField = new TextField();
        depositMoneyToSavingsField.setLayoutX(430);
        depositMoneyToSavingsField.setLayoutY(470);
        depositMoneyToSavingsField.setPromptText("Amount in _.__");
        depositMoneyToSavingsField.setFont(Font.font(Main.TEXT_FONT));

        // Create and configure deposit money to savings label
        Text depositMoneyToSavings = new Text(85, 490, "Deposit Money to Savings Account");
        depositMoneyToSavings.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 20));

        // Create and configure the error message text for savings deposit format
        Text formatError2 = new Text(680, 487, "");
        formatError2.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 15));
        formatError2.setFill(Color.RED);

        // Initialize and configure the submit button for depositing money to savings
        buttonSavings = new Button("Submit");
        buttonSavings.setLayoutX(615);
        buttonSavings.setLayoutY(470);

        // Set action for the submit button for depositing money to savings
        buttonSavings.setOnAction(event -> {
            formatError2.setText("");
            strDepositAmount = depositMoneyToSavingsField.getText();
            if (Main.validateString(strDepositAmount)) {
                ChequingAccount.withdrawChequing(LoginGUI.getUsernameStr(), strDepositAmount);
                updateChequing();
            } else {
                formatError2.setText("Invalid amount format. Use two decimal places");
            }
        });

        // Load the home icon image
        Image home = new Image("images/homeicon.png");

        // Initialize and configure the button to return to the dashboard
        Button returnToDashboard = new Button();
        returnToDashboard.setGraphic(new ImageView(home));
        returnToDashboard.setLayoutX(925);
        returnToDashboard.setLayoutY(27);

        // Set action for the button to return to the dashboard
        returnToDashboard.setOnAction(event -> {
            DashboardGUI.updateDashboard();
            primaryStage.setScene(DashboardGUI.getScene3());
            DashboardGUI.resetSelections();
        });

        // Add all elements to the root group
        root5.getChildren().addAll(chequingValue, greet, currentBalance, button, montlyTransactionsAvaliable, depositMoneyText, depositMoneyField, returnToDashboard, depositMoneyToSavingsField, depositMoneyToSavings, buttonSavings, formatError, formatError2);
    }

    /**
     * Updates the chequing account balance displayed in the GUI.
     */
    private void updateChequing() {
        String chequingValueStr = JDBC.getChequingValue(LoginGUI.getUsernameStr());
        // Convert to double and format to two decimal places
        double chequingValueDouble = Double.parseDouble(chequingValueStr);
        chequingValueStr = Main.twoDecimal(chequingValueDouble);
        chequingValue.setText("$" + chequingValueStr);
    }

    /**
     * Gets the current deposit amount as a string.
     *
     * @return the current deposit amount
     */
    public static String getStrDepositAmount() {
        return strDepositAmount;
    }

    /**
     * Gets the scene for the chequing account GUI.
     *
     * @return the scene for the chequing account GUI
     */
    public static Scene getScene5() {
        return scene5;
    }
}
