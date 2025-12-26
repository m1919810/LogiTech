package me.matl114.logitech;

public class TestStack {
    int amount;
    int max;

    public TestStack(int amount) {
        this.amount = amount;
        this.max = 64;
    }

    public int getAmount() {
        return amount;
    }

    public int getMaxAmount() {
        return max;
    }
}
