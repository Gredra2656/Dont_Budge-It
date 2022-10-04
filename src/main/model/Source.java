package model;

public class Source {
    String name;
    int value;

    public Source(String name, int val) {
        this.name = name;
        this.value = val;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
