package model;

import org.json.JSONObject;

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

    //EFFECTS: Parses a source to a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("value", value);
        return json;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }
}
