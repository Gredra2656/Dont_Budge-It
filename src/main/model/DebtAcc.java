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
        this.value = value.multiply(interest.add(BigDecimal.valueOf(1)));
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

    public BigDecimal getValue() {
        return value;
    }
}
