package me.matl114.logitech;

import me.matl114.logitech.Utils.Debug;

public class TestPusher extends TestConsumer{
    public int maxCnt;
    public TestPusher(TestStack stack){
        super(stack);
        this.maxCnt=stack.getMaxAmount();
    }
    public void init(TestStack stack){
        super.init(stack);
        this.maxCnt=stack.getMaxAmount();
        Tests.log("TestPusher init");

    }
    public void init(){
       // super.init();
        Tests.log("TestPusher empty init");
    }
    public TestPusher clone(){
        return (TestPusher)super.clone();
    }
}
