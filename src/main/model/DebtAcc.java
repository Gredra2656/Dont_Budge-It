package model;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DebtAcc implements HasInterest {
    BigDecimal value;
    String name;
    BigDecimal interest;

    //MODIFIES: this
    //EFFECTS: Constructs a debt account
    public DebtAcc(String name, BigDecimal value, BigDecimal interest) {
        this.value = value;
        this.name = name;
        this.interest = interest;
    }

    //EFFECTS: Calculates addition to debt -- SEE INTERFACE
    @Override
    public void calculateInterest() {
        this.value = value.multiply(interest.add(BigDecimal.valueOf(1))).setScale(2, RoundingMode.CEILING);
    }

    //EFFECTS: Adds value to the debt -- SEE INTERFACE
    @Override
    public void addValue(BigDecimal payment) {
        this.value = value.add(payment);
    }

    //EFFECTS: Pays debt by amount payment -- SEE INTERFACE
    @Override
    public boolean subValue(BigDecimal payment) {
        this.value = value.subtract(payment);
        return this.value.equals(BigDecimal.valueOf(0));
    }

    @Override
    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getInterest() {
        return this.interest;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("value", value);
        json.put("interest", interest);
        return json;
    }
}
