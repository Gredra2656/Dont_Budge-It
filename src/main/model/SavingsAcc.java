package model;

import org.json.JSONObject;
import persistence.Writable;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A representation of the user's savings account, with balance and interest accumulated per month.
 */
public class SavingsAcc implements HasInterest, Writable {
    BigDecimal bal;
    BigDecimal interest;

    //REQUIRES: initial >= 0
    //EFFECTS: Creates savings account with given initial balance and interest
    public SavingsAcc(BigDecimal initial, BigDecimal interest) {
        this.bal = initial;
        this.interest = interest;
    }

    //EFFECTS: Calculates interest on your savings -- SEE INTERFACE
    @Override
    public void calculateInterest() {
        BigDecimal newBal = bal.multiply(interest.add(BigDecimal.valueOf(1))).setScale(2, RoundingMode.CEILING);
        EventLog.getInstance().logEvent(new Event("Calculated interest added to savings: "
                + newBal.subtract(bal)));
        bal = newBal;
    }

    //EFFECTS: Deposits payment to savings -- SEE INTERFACE
    @Override
    public void addValue(BigDecimal payment) {
        EventLog.getInstance().logEvent(new Event(payment + " deposited to savings account"));
        bal = bal.add(payment);
    }

    //EFFECTS: Withdraws from savings -- SEE INTERFACE
    @Override
    public boolean subValue(BigDecimal payment) {
        EventLog.getInstance().logEvent(new Event(payment + " withdrawn from savings account"));
        bal = bal.subtract(payment);
        return bal.equals(BigDecimal.valueOf(0));
    }

    public BigDecimal getBal() {
        return bal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setBal(BigDecimal bal) {
        this.bal = bal;
    }

    @Override
    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    //EFFECTS: Parses a SavingsAcc to a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("balance", bal);
        json.put("interest", interest);
        return json;
    }
}
