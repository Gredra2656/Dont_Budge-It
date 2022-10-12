package model;

import java.math.BigDecimal;


public class Source {
    String name;
    BigDecimal value;

    //MODIFIES: this
    //EFFECTS: Creates a source
    public Source(String name, BigDecimal val) {
        this.name = name;
        this.value = val;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }
}
