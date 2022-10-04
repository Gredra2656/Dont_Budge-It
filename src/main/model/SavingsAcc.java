package model;

public class SavingsAcc {
    int bal;
    int goal;
    double interest;

    //REQUIRES: initial >= 0
    //EFFECTS: Creates savings account with given initial balance and interest
    public SavingsAcc(int initial, double interest) {
        this.bal = initial;
        this.interest = interest;
    }

    //MODIFIES: this
    //EFFECTS: Adds interest onto your savings at the end of the month based on interest
    public int computeInterest() {
        return 0; // TODO
    }

    //REQUIRES: amt > 0
    //MODIFIES: this
    //EFFECTS: adds amt to savings balance
    public void addBal(int amt) {
        // TODO
    }

    //REQUIRES: amt > 0
    //MODIFIES: this
    //EFFECTS: subtracts amt from savings balance. Returns true if possible, false if insufficient funds.
    public boolean withdrawBal(int amt) {
        return false; // TODO
    }

    public int getBal() {
        return bal;
    }

    public void setBal(int bal) {
        this.bal = bal;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
}
