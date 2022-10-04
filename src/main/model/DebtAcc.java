package model;

public class DebtAcc {
    int value;
    String name;
    double interest;

    public DebtAcc(String name, int value, double interest) {
        this.value = value;
        this.name = name;
        this.interest = interest;
    }

    //MODIFIES: this
    //EFFECTS: Computes the addition to the debt at the end of the period.
    public int computeDebtInterest() {
        return 0; // TODO
    }

    //REQUIRES: payment > 0, payment < value (value of debt)
    //MODIFIES: this
    //EFFECTS: Subtracts payment from the debt and returns true if this pays the debt fully
    public boolean payDebt(int payment) {
        // TODO
        return false;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public double getInterest() {
        return interest;
    }
}
