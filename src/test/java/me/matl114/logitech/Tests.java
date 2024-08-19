package me.matl114.logitech;


import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.MathUtils;
import me.matl114.logitech.Utils.UtilClass.CargoClass.CargoConfigs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Tests {
    public static void log(String message) {
        System.out.println("Test: "+message);
    }


    //@Test
    public void test_init(){
        TestStack[] testStacks = new TestStack[]{
                new TestStack(1),
                new TestStack(2),
                new TestStack(3),
        };
        long a =System.nanoTime();
        TestCounter is=null;
        for(int i=0;i<3;++i){
            is=new TestCounter (testStacks[i]);
        }
        long b =System.nanoTime();

        log("time cost "+(b-a)+" ns");
        long c =System.nanoTime();
        TestCounter is2=null;
        for(int i=0;i<3;++i){
            is2=is.clone();
            is2.amount=testStacks[i].getAmount();
            is2.dirty=false;
            is2.it=testStacks[i];
        }
        long d =System.nanoTime();

        log("time cost "+(d-c)+" ns");
        log("check amount "+is2.amount);
        log("check if equal "+((is2==is)?1:0));
        TestConsumer s=new TestConsumer(testStacks[1]);
        TestConsumer s2=s.clone();
        log("check amounnt"+s2.amount);
        s.amount=10;
        log("check if equal "+s2.amount);
        TestSlotPusher itp=new TestSlotPusher(testStacks[1],3);
        TestSlotPusher itp2=itp.clone();
        itp2.init(testStacks[2],5);
        log(itp2.amount+" "+itp2.maxCnt+" "+itp2.slot);
        //ItemStack
    }
    @Test
    public void test_configCode(){
       boolean symm=false;
       boolean isnull=true;
       boolean lazy=false;
       boolean bklst=true;
       boolean fromInput=true;
       boolean toOutput=false;
       boolean reverse=false;
       int trans=647;
       int configCode= CargoConfigs.setAllConfig(symm,isnull,lazy,bklst,fromInput,toOutput,reverse,trans);

       log("[TEST CONFIG CODE] "+configCode);
       assert CargoConfigs.IS_SYMM.getConfig(configCode)==symm;
       assert CargoConfigs.IS_NULL.getConfig(configCode)==isnull;
       assert CargoConfigs.IS_LAZY.getConfig(configCode)==lazy;
       assert CargoConfigs.TO_OUTPUT.getConfig(configCode)==toOutput;
       assert CargoConfigs.REVERSE.getConfig(configCode)==reverse;
       assert CargoConfigs.IS_WHITELST.getConfig(configCode)==bklst;
       assert CargoConfigs.FROM_INPUT.getConfig(configCode)==fromInput;
       assert CargoConfigs.TRANSLIMIT.getConfigInt(configCode)==trans;
       log("[TEST CONFIG CODE] TEST SUCCESS");
        ArrayList<Integer> a=new ArrayList<>(){{
            for(int i=0;i<16;++i){
                add(i);
            }
        }};
        log(a.size()+"");
        int size=a.size();
        a.subList(size-8,size).clear();
        log(a.size()+"");
    }
    @Test
    public void test_MathUtils(){
        log("[TEST MathUtils] TEST START");
        //StringBuilder builder=new StringBuilder();
        assert "01001110000000000000000000000000".equals( MathUtils.toBinaryCode(114));
        assert "11111111111111111111111111111111".equals( MathUtils.toBinaryCode(-1));
        assert "11111111111111111111111111111110".equals( MathUtils.toBinaryCode(2147483647));
        assert "01110001111111111111111111111111".equals( MathUtils.toBinaryCode(-114));
        assert MathUtils.getBit("01001110000000000000000000000000",4);
        for(int i=0;i<10;++i){
            int a= ThreadLocalRandom.current().nextInt(217956969);
            int b= ThreadLocalRandom.current().nextInt(217956969);
            assert MathUtils.andStr(MathUtils.toBinaryCode(a),MathUtils.toBinaryCode(b)).equals(MathUtils.toBinaryCode(a&b));
            assert MathUtils.orStr(MathUtils.toBinaryCode(a),MathUtils.toBinaryCode(b)).equals(MathUtils.toBinaryCode(a|b));
            assert MathUtils.xorStr(MathUtils.toBinaryCode(a),MathUtils.toBinaryCode(b)).equals(MathUtils.toBinaryCode(a^b));
            assert MathUtils.notStr(MathUtils.toBinaryCode(a)).equals(MathUtils.toBinaryCode(~a));
            assert MathUtils.leftShiftStr(MathUtils.toBinaryCode(a)).equals(MathUtils.toBinaryCode(a<<1));
            assert MathUtils.rightShiftStr(MathUtils.toBinaryCode(a)).equals(MathUtils.toBinaryCode(a>>1));
            assert MathUtils.rightShiftStr(MathUtils.toBinaryCode(-a)).equals(MathUtils.toBinaryCode((-a)>>1));
        }
        //char s="abuoiono".charAt(8);
        long a=System.nanoTime();
        MathUtils.rightShiftStr("11111111111111111111111111111110");
        MathUtils.leftShiftStr("11111111111111111111111111111110");
        long b=System.nanoTime();
        log("bc test time "+(b-a)+" ns");
        log(new StringBuilder().append("holy ").append("shit ").toString());
        log("αοθωπαςο".getClass().getName());
        log("[TEST MathUtils] TEST SUCCESS");
    }

}
