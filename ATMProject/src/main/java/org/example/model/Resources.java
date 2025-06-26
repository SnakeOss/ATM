package org.example.model;

public class Resources {
    private int ink;
    private int paper;
    private double cash;

    public Resources() {
        // Set default values
        this.ink = 100;
        this.paper = 100;
        this.cash = 1000.0;
    }

    // Constructor to initialize all resources
    public Resources(int ink, int paper, double cash) {
        this.ink = ink;
        this.paper = paper;
        this.cash = cash;
    }

    // Getters and Setters
    public int getInk() {
        return ink;
    }

    public void setInk(int ink) {
        this.ink = ink;
    }

    public int getPaper() {
        return paper;
    }

    public void setPaper(int paper) {
        this.paper = paper;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    // Resource status checks
    public boolean isLowOnResources() {
        return ink <= 20 || paper <= 20 || cash <= 20;
    }

    public boolean isOutOfResources() {
        return ink == 0 || paper == 0 || cash == 0;
    }
}
