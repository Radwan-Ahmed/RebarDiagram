package com.example.rebardia;

public class MutableDouble {
    private double value;

    public MutableDouble(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void add(double amount) {
        value += amount;
    }

    public void subtract(double amount) {
        value -= amount;
    }
}
