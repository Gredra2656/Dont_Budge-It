package model;

import java.math.BigDecimal;

public class SavingsAcc implements HasInterest {
    BigDecimal bal;
    BigDecimal interest;

    //REQUIRES: initial >= 0
    //EFFECTS: Creates savings account with given initial balance and interest
    public SavingsAcc(BigDecimal initial, BigDecimal interest) {
        this.bal = initial;
        this.interest = interest;
    }

    //EFFECTS: Withdraws from savings -- SEE INTERFACE
    @Override
    public boolean subValue(BigDecimal payment) {
        bal = bal.subtract(payment);
        return bal.equals(BigDecimal.valueOf(0));
    }

    //EFFECTS: Calculates interest on your savings -- SEE INTERFACE
    @Override
    public void calculateInterest() {
        bal = bal.multiply(interest.add(BigDecimal.valueOf(1)));
    }

    //EFFECTS: Deposits payment to savings -- SEE INTERFACE
    @Override
    public void addValue(BigDecimal payment) {
        bal = bal.add(payment);
    }

    public BigDecimal getBal() {
        return bal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    @Override
    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }
}
