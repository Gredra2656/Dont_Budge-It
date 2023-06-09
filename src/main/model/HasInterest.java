package model;

import java.math.BigDecimal;

/**
 * An interface representation used for classes that handle an interest on an account balance.
 */
public interface HasInterest {

    //REQUIRES: payment > 0, payment < value (value of HasInterest)
    //MODIFIES: this
    //EFFECTS: Subtracts payment from the value and returns true if this removes the value fully
    boolean subValue(BigDecimal payment);

    //MODIFIES: this
    //EFFECTS: Computes the addition to the value based on interest at the end of the period.
    void calculateInterest();

    //REQUIRES: payment > 0, payment < value (value of HasInterest)
    //MODIFIES: this
    //EFFECTS: Adds payment to the value
    void addValue(BigDecimal payment);

    //REQUIRES: 0 < interest < 1
    //MODIFIES: this
    //EFFECTS: Sets the interest to the given value
    void setInterest(BigDecimal interest);
}
