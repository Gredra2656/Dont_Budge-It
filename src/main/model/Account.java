package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.math.BigDecimal;

public class Account {
    BigDecimal balance; // Balance in cents
    BigDecimal savingsPercentGoal; // Savings goal as percentage
    List<Source> sources; // Catalogue of sources
    SavingsAcc savings; // A tracker for long term savings
    ArrayList<DebtAcc> debts; // A list of debts for the user to fill.
    ArrayList<String> receipts = new ArrayList<>(); // A list of Receipts stored as strings.

    //EFFECTS: Creates a fresh account with no values.
    public Account() {
        this.balance = BigDecimal.valueOf(0);
        this.savingsPercentGoal = BigDecimal.valueOf(0);
        this.sources = new ArrayList<>();
        this.savings = new SavingsAcc(BigDecimal.valueOf(0),BigDecimal.valueOf(0));
        this.debts = new ArrayList<>();
    }

    //REQUIRES: val != 0, name IS NOT duplicate
    //MODIFIES: this
    //EFFECTS: Adds an income/expense to the list of sources
    public void addSource(String name, BigDecimal val) {
        Source s = new Source(name, val);
        this.sources.add(s);
    }

    //MODIFIES: this
    //EFFECTS: Removes the specified source from the source list, if not found return false, else true
    public boolean removeSource(String sourceName) {
        List<Source> sources = this.getSources();
        for (int i = 0; i < this.sources.size(); i++) {
            Source s = sources.get(i);
            if (Objects.equals(s.name, sourceName)) {
                sources.remove(i);
                return true;
            }
        }
        return false;
    }

    //REQUIRES: that lst is a list of all sources for an account.
    //EFFECTS: Return a value, positive or negative, that represents inbound
    //         or outbound funds from balance for the month.
    public BigDecimal calculateSurplus() {
        List<Source> sources = getSources();
        BigDecimal s = BigDecimal.valueOf(0);
        for (Source source : sources) {
            s = s.add(source.getValue());
        }
        return s;
    }

    //REQUIRES: that lst is a list of income sources
    //EFFECTS: Return the sum of all income sources
    public BigDecimal calculateIncome() {
        List<Source> sources = getSources();
        BigDecimal income = BigDecimal.valueOf(0);

        for (Source source : sources) {

            if (source.getValue().compareTo(BigDecimal.ZERO) > 0) {
                income = income.add(source.getValue());
            }
        }
        return income;
    }

    //REQUIRES: that lst is a list of expense sources
    //EFFECTS: Return the sum of all expenses.
    public BigDecimal calculateExpenses() {
        List<Source> sources = getSources();
        BigDecimal expenses = BigDecimal.valueOf(0);

        for (Source source : sources) {

            if (source.getValue().compareTo(BigDecimal.ZERO) < 0) {
                expenses = expenses.subtract(source.getValue());
            }
        }
        expenses = expenses.multiply(BigDecimal.valueOf(-1));
        return expenses;
    }

    //MODIFIES: this
    //EFFECTS: Returns a receipt of incoming and outgoing expenses that month, with updated balance and values
    public String returnReceipt() {
        BigDecimal income = calculateIncome();
        BigDecimal expense = calculateExpenses();
        BigDecimal surplus = calculateSurplus();

        return "Income: " + income + "\nExpenses: " + expense + "\n" + "------" + "\nTotal: " + surplus
                + "\nRecommended Savings: " + savingsPercentGoal.multiply(calculateSurplus());
    }

    //REQUIRES: val > 0
    //MODIFIES: this
    //EFFECTS: Adds val to balance
    public void addBalance(BigDecimal val) {
        this.balance = this.balance.add(val);
    }

    //REQUIRES: val > 0, val <= balance
    //MODIFIES: this
    //EFFECTS: Subtracts val from balance
    public void withdrawBalance(BigDecimal val) {
        this.balance = this.balance.subtract(val);
    }

    //MODIFIES: this
    //EFFECTS: Updates balance to val
    public void updateBalance(BigDecimal val) {
        this.balance = val;
    }

    //MODIFIES: this
    //EFFECTS: Updates debt, savings, and balance, records a receipt, and recommends a savings deposit for that month
    public void computeNextPeriod() {
        this.balance = this.balance.add(calculateSurplus());
        this.savings.calculateInterest();
        for (DebtAcc debts : debts) {
            debts.calculateInterest();
        }
        this.receipts.add(returnReceipt());
        System.out.println("We recommend saving: " + savingsPercentGoal.multiply(calculateSurplus()));
    }

    //REQUIRES: amt > 0
    //MODIFIES: this
    //EFFECTS: Adds a debt to the list of debts in the account
    public void addDebt(String name, BigDecimal amt, BigDecimal interest) {
        DebtAcc debt = new DebtAcc(name, amt, interest);
        this.debts.add(debt);
    }

    //MODIFIES: this
    //EFFECTS: Removes a debt with the given name from your account. If name found, true, else false.
    public boolean removeDebt(String name) {
        for (int i = 0; i < this.debts.size(); i++) {
            DebtAcc d = debts.get(i);
            if (d.name.equals(name)) {
                debts.remove(i);
                return true;
            }
        }
        return false;
    }

    //REQUIRES: amt > 0
    //MODIFIES: this
    //EFFECTS: Moves cash from account balance to savings.
    public void depositSavings(BigDecimal amt) {
        SavingsAcc savings = this.savings;
        this.balance = balance.subtract(amt);
        savings.addValue(amt);
    }

    //REQUIRES: amt > 0, amt <= savings.balance
    //MODIFIES: this
    //EFFECTS: Moves cash from savings to account balance
    public void withdrawSavings(BigDecimal amt) {
        SavingsAcc savings = this.savings;
        this.balance = balance.add(amt);
        savings.subValue(amt);
    }

    //EFFECTS: Returns savings balance
    public BigDecimal getSavingsBal() {
        return this.savings.getBal();
    }

    //REQUIRES: That a savings goal has been set
    //EFFECTS: Returns an integer based on your surplus that month and your savings goal
    public BigDecimal suggestSavings(BigDecimal surplus) {
        return surplus.multiply(this.savingsPercentGoal);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getSavingsPercentGoal() {
        return savingsPercentGoal;
    }

    public void setSavingsPercentGoal(BigDecimal savingsPercentGoal) {
        this.savingsPercentGoal = savingsPercentGoal;
    }

    public List<Source> getSources() {
        return sources;
    }

    public ArrayList<String> getReceipts() {
        return receipts;
    }

    public ArrayList<DebtAcc> getDebts() {
        return debts;
    }

    public SavingsAcc getSavings() {
        return savings;
    }
}
