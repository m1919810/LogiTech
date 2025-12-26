package me.matl114.logitech;

public class TestSlotPusher extends TestPusher {
    int slot;

    public TestSlotPusher(TestStack stack, int slot) {
        super(stack);
        this.slot = slot;
    }

    public void init(TestStack item, int slot) {
        if (item != null) {
            init(item);
        } else {
            super.init();
        }
        this.slot = slot;
        Tests.log("TestSlotPusher init");
        this.slot = slot;
    }

    public TestSlotPusher clone() {
        return (TestSlotPusher) super.clone();
    }
}
