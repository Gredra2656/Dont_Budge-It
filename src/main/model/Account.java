package model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    int balance; // Balance in cents
    double savingsPercentGoal; // Savings goal as percentage
    List sources; // Catalogue of sources
    SavingsAcc savings; // A tracker for long term savings
    ArrayList debts; // A list of debts for the user to fill.
    ArrayList receipts; // A list of Receipts stored as strings.

    //EFFECTS: Creates a fresh account with no values, and default period.
    public Account() {
        this.balance = 0;
        this.savingsPercentGoal = 0;
        this.sources = new ArrayList();
        this.savings = new SavingsAcc(0,.01);
        this.debts = new ArrayList<DebtAcc>();
        this.receipts = new ArrayList<String>();
    }

    //REQUIRES: val != 0, name IS NOT duplicate
    //MODIFIES: this
    //EFFECTS: Adds an income/expense to the list of sources
    public void addSource(String name, int val) {
        // TODO
    }

    //MODIFIES: this
    //EFFECTS: Removes the specified source from the source list, if not found return false
    public boolean removeSource(String sourceName) {
        // TODO
        return false;
    }

    //REQUIRES: that lst is a list of all sources for an account.
    //EFFECTS: Return a value, positive or negative, that represents inbound
    //         or outbound funds from balance for the month.
    public int calculateSurplus() {
        return 0; // TODO
    }

    //REQUIRES: that lst is a list of income sources
    //EFFECTS: Return the sum of all income sources
    public int calculateIncome() {
        return 0; // TODO
    }

    //REQUIRES: that lst is a list of expense sources
    //EFFECTS: Return the sum of all expenses.
    public int calculateExpenses() {
        return -1; // TODO
    }

    //MODIFIES: this
    //EFFECTS: Returns a receipt of incoming and outgoing expenses that month, with updated balance and values
    public String returnReceipt() {
        return ""; // TODO
    }

    // FORMATTING FOR RETURN RECEIPT // TODO
    // MAYBE MONTH HERE??
    // "Income: " + income
    // "Expenses: " + expenses
    // "------"
    // "Total: " + total

    //REQUIRES: val > 0
    //MODIFIES: this
    //EFFECTS: Adds val to balance
    public void addBalance(int val) {
        this.balance += val;
    }

    //REQUIRES: val > 0, val <= balance
    //MODIFIES: this
    //EFFECTS: Subtracts val from balance
    public void withdrawBalance(int val) {
        this.balance -= val;
    }

    //MODIFIES: this
    //EFFECTS: Updates debt, savings, and balance, and records a receipt
    public void computeNextPeriod() {
        // TODO
    }

    //REQUIRES: amt > 0
    //MODIFIES: this
    //EFFECTS: Adds a debt to the list of debts in the account
    public void addDebt(int amt) {
        // TODO
    }

    //REQUIRES: 0 <= prd <= 2
    //MODIFIES: this
    //EFFECTS: Changes the period to Weekly, monthly, or yearly based on prd
    public void changePeriod(int prd) {
        // TODO
    }

    //REQUIRES: amt > 0
    //MODIFIES: this
    //EFFECTS: Deposits an amount to the SavingsAcc attached to the Account object
    public void depositSavings(int amt) {
        // TODO
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public double getSavingsPercentGoal() {
        return savingsPercentGoal;
    }

    public void setSavingsPercentGoal(double savingsPercentGoal) {
        this.savingsPercentGoal = savingsPercentGoal;
    }

    public List getSources() {
        return sources;
    }

    public void setSources(List sources) {
        this.sources = sources;
    }

    public ArrayList getReceipts() {
        return receipts;
    }

    public ArrayList getDebts() {
        return debts;
    }

    public SavingsAcc getSavings() {
        return savings;
    }
}
