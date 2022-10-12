package ui;
// Credit: TellerApp -- https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

import model.*;

import java.util.Scanner;
import java.math.*;

public class BudgeItApp {
    private Account userAccount;
    private Scanner input;

    public BudgeItApp() {
        startBudgeIt();
    }

    private void startBudgeIt() {
        Boolean running = true;
        String command = null;
        // TODO
    }

    private void commandLine() {
        // TODO
    }

    //MODIFIES: this
    //EFFECTS: Creates the user's account for entry
    private void init() {
        userAccount = new Account();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //EFFECTS: Display options for input to the user
    private void displayOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> deposit");
        System.out.println("\tw -> withdraw");
        System.out.println("\tt -> transfer");
        System.out.println("\tq -> quit");
    }

    private void updateBalance() {
        System.out.println("Please enter your account balance:");
        BigDecimal amount = input.nextBigDecimal();
    }

    //MODIFIES: this
    //EFFECTS: Adds a source to the userAccount
    private void addSource() {
        String name;
        BigDecimal val;

        System.out.println("Please enter the name of the source you'd like to add: ");
        name = input.next();
        System.out.println("Please enter the value of the source you'd like to add: ");
        val = input.nextBigDecimal();

        userAccount.addSource(name, val);
    }

    private void displaySurplus() {
        System.out.println("Your current surplus is: ");
        System.out.println(userAccount.calculateSurplus());
    }

    private void setSavingsGoal() {
        BigDecimal savingsGoal;

        System.out.println("Please enter your desired savings goal as a percentage (of your income): ");
        savingsGoal = input.nextBigDecimal();
        savingsGoal.movePointLeft(2);

        if (savingsGoal.compareTo(BigDecimal.ONE) > 0) {
            System.out.println("Please make sure your value is not greater than 100%...");
        } else if (savingsGoal.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Please make sure your value is not less than 0%...");
        } else {
            userAccount.setSavingsPercentGoal(savingsGoal);
        }
    }

    private void addDebt() {
        // TODO
    }

    private void addToSavings() {
        // TODO
    }

    private void displayReceipt() {
        // TODO
    }

    private void displayReceipts() {
        // TODO
    }

    private void pinAccess() {
        // TODO
    }

    private void printBalance() {

    }

    private void showSavings() {

    }

    private void showDebts() {

    }
}
