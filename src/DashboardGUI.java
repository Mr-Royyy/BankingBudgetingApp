import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Represents the dashboard GUI for the banking application.
 * Provides functionality for viewing account balances, total income, expenses, and savings.
 * Allows navigation to different account and budget sections.
 *
 * @author Jimmy Roy
 * @version August 2024
 */
public class DashboardGUI {
    /** Root group for the dashboard scene */
    private Group root3;

    /** Scene for the dashboard GUI */
    private static Scene scene3;

    /** ComboBox for selecting accounts (Chequing or Savings) */
    private static ComboBox<String> accounts;

    /** ComboBox for selecting budget options (Monthly Income, Monthly Expense, Monthly Savings) */
    private static ComboBox<String> budget;

    /** Text for displaying the chequing account balance */
    private static Text chequingBalance;

    /** Text for displaying the savings account balance */
    private static Text savingsAccountBalance;

    /** Text for displaying the total income */
    private static Text totalIncome;

    /** Text for displaying the total expenses */
    private static Text totalExpenses;

    /** Text for displaying the total savings */
    private static Text totalSavings;

    /** String for storing the monthly income */
    private static String monthlyIncome;

    /** String for storing the chequing account balance */
    private static String chequingValue;

    /** String for storing the savings account balance */
    private static String savingsValue;

    /** String for storing the monthly expenses */
    private static String monthlyExpense;

    /** String for storing the monthly savings */
    private static String monthlySavings;

    /**
     * Constructs the dashboardGUI object.
     *
     * @param primaryStage the primary stage for this application
     * @param main the main application object
     */
    public DashboardGUI(Stage primaryStage, Main main) {
        // Initialize root group and scene
        root3 = new Group();
        scene3 = new Scene(root3, Main.MAX_WIDTH, Main.MAX_HEIGHT);
        scene3.setFill(Color.web(Main.PRIMARY_COLOUR));

        // Create and configure the header rectangle
        Rectangle rect = new Rectangle(0, 0, 1000, 75);
        rect.setFill(Color.web(Main.SECONDARY_COLOUR));

        // Create and configure the header text
        Text text = new Text("Simplified Banking and Budgeting");
        text.setFont(Font.font(Main.BIG_HEADER_FONT, FontWeight.BOLD, 42));
        text.setFill(Color.BLACK);
        text.setLayoutX(20);
        text.setLayoutY(50);

        // Initialize and configure the accounts ComboBox
        accounts = new ComboBox<>();
        accounts.getItems().addAll("Chequing", "Savings");
        accounts.setLayoutX(720);
        accounts.setLayoutY(25);
        accounts.setPromptText("Accounts");

        // Initialize and configure the budget ComboBox
        budget = new ComboBox<>();
        budget.getItems().addAll("Monthly Income", "Monthly Expense", "Monthly Savings");
        budget.setLayoutX(850);
        budget.setLayoutY(25);
        budget.setPromptText("Budget");

        // Retrieve data for the logged-in user
        String username = LoginGUI.getUsernameStr();
        //System.out.println("Username for data retrieval: " + username);

        monthlyIncome = JDBC.getMonthlyIncome(username);
        chequingValue = JDBC.getChequingValue(username);
        savingsValue = JDBC.getSavingsValue(username);
        monthlyExpense = JDBC.getMonthlyExpense(username);
        monthlySavings = JDBC.getMonthlySavings(username);

        String name = JDBC.getFirstName(LoginGUI.getUsernameStr());

        // Create and configure the greeting text
        Text greet = new Text(0, 125, "Hello, " + name + ". Welcome Back");
        greet.setFont(Font.font(Main.BIG_HEADER_FONT, FontWeight.BOLD, 30));
        Main.centerText(greet, scene3);

        // Create and configure the important information text
        Text importantInfo = new Text(25, 150, "Important Information");
        importantInfo.setFont(Font.font(Main.HEADER_FONT, FontWeight.NORMAL, 25));

        // Create and configure the chequing balance text
        chequingBalance = new Text(25, 180, "Chequing Account Balance: $" + chequingValue);
        chequingBalance.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 20));

        // Create and configure the savings balance text
        savingsAccountBalance = new Text(25, 220, "Savings Account Balance: $" + savingsValue);
        savingsAccountBalance.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 20));

        // Create and configure the total income text
        totalIncome = new Text(690, 180, "Total Income: $" + monthlyIncome);
        totalIncome.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 20));

        // Create and configure the total expenses text
        totalExpenses = new Text(690, 220, "Total Expenses: $" + monthlyExpense);
        totalExpenses.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 20));

        // Create and configure the total savings text
        totalSavings = new Text(690, 260, "Total Savings: $" + monthlySavings);
        totalSavings.setFont(Font.font(Main.TEXT_FONT, FontWeight.NORMAL, 20));

        // Add all elements to the root group
        root3.getChildren().addAll(rect, text, accounts, budget, greet, chequingBalance, savingsAccountBalance, totalExpenses, totalIncome, totalSavings);

        // Set actions for budget ComboBox selections
        budget.setOnAction(event -> {
            String selectedOption = budget.getSelectionModel().getSelectedItem();
            if ("Monthly Income".equals(selectedOption)) {
                MonthlyIncomeGUI monthlyIncomeGUI = new MonthlyIncomeGUI(primaryStage, main);
                primaryStage.setScene(MonthlyIncomeGUI.getScene6());
            }
            if ("Monthly Expense".equals(selectedOption)) {
                MonthlyExpenseGUI monthlyExpenseGUI = new MonthlyExpenseGUI(primaryStage, main);
                primaryStage.setScene(MonthlyExpenseGUI.getScene7());
            }
            if ("Monthly Savings".equals(selectedOption)) {
                MonthlySavingsGUI monthlySavingsGUI = new MonthlySavingsGUI(primaryStage, main);
                primaryStage.setScene(MonthlySavingsGUI.getScene8());
            }
        });

        // Set actions for accounts ComboBox selections
        accounts.setOnAction(event -> {
            String selectedOption = accounts.getSelectionModel().getSelectedItem();
            if ("Chequing".equals(selectedOption)) {
                ChequingGUI chequingGUI = new ChequingGUI(primaryStage, main);
                primaryStage.setScene(ChequingGUI.getScene5());
            }
            if ("Savings".equals(selectedOption)) {
                SavingsGUI savingsGUI = new SavingsGUI(primaryStage, main);
                primaryStage.setScene(SavingsGUI.getScene4());
            }
        });
    }

    /**
     * Resets the selections in the accounts and budget ComboBoxes.
     */
    public static void resetSelections() {
        accounts.getSelectionModel().clearSelection();
        budget.getSelectionModel().clearSelection();
    }

    /**
     * Updates the dashboard with the latest data from the database.
     * Retrieves the latest values for the account balances, income, expenses, and savings.
     */
    public static void updateDashboard() {
        String username = LoginGUI.getUsernameStr();
        monthlyIncome = JDBC.getMonthlyIncome(username);
        chequingValue = JDBC.getChequingValue(username);
        savingsValue = JDBC.getSavingsValue(username);
        monthlyExpense = JDBC.getMonthlyExpense(username);
        monthlySavings = JDBC.getMonthlySavings(username);
        chequingBalance.setText("Chequing Account Balance: $" + chequingValue);
        savingsAccountBalance.setText("Savings Account Balance: $" + savingsValue);
        totalIncome.setText("Total Income: $" + monthlyIncome);
        totalExpenses.setText("Total Expenses: $" + monthlyExpense);
        totalSavings.setText("Total Savings: $" + monthlySavings);
    }

    /**
     * Gets the scene for the dashboard GUI.
     *
     * @return the scene for the dashboard GUI
     */
    public static Scene getScene3() {
        return scene3;
    }
}
