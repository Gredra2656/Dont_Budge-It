package model;

import java.math.BigDecimal;

public class DebtAcc implements HasInterest {
    BigDecimal value;
    String name;
    BigDecimal interest;

    public DebtAcc(String name, BigDecimal value, BigDecimal interest) {
        this.value = value;
        this.name = name;
        this.interest = interest;
    }

    //EFFECTS: Calculates addition to debt -- SEE INTERFACE
    @Override
    public void calculateInterest() {
        this.value *= interest + 1;
    }

    //EFFECTS: Adds value to the debt -- SEE INTERFACE
    @Override
    public void addValue(BigDecimal payment) {
        this.value += payment;
    }

    //EFFECTS: Pays debt by amount payment -- SEE INTERFACE
    @Override
    public boolean subValue(BigDecimal payment) {
        this.value -= payment;
        if (this.value == 0) {
            return true;
        }
        return false;
    }

    public BigDecimal getValue() {
        return value;
    }
}
