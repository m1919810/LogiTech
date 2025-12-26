package me.matl114.logitech;

public abstract class TestCounter implements Cloneable {
    private TestStack it;
    private int amount;
    private boolean dirty;
    private final int shit = 1919;

    public TestCounter(TestStack it) {
        this.it = it;
        this.amount = it.getAmount();
        this.dirty = false;
    }

    public TestCounter clone() {
        TestCounter it = null;
        try {
            it = (TestCounter) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return it;
    }

    public boolean equals(Object o) {
        return false;
    }

    public void init(TestStack it) {
        Tests.log("TestCounter init");
        this.it = it;
        this.amount = it.getAmount();
        this.dirty = false;
    }
}
