package me.matl114.logitech;


import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Algorithms.DynamicArray;
import me.matl114.logitech.Utils.Algorithms.SimpleLinkList;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.MathUtils;
import me.matl114.logitech.Utils.UtilClass.CargoClass.CargoConfigs;
import me.matl114.logitech.Utils.UtilClass.TestItemStack;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
   // @Test
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
        log("bc test time 2"+(b-a)+" ns");
        log(new StringBuilder().append("holy ").append("shit ").toString());
        log("αοθωπαςο".getClass().getName());
        a=System.nanoTime();
        for(int i=0;i<100_000;++i){
            MathUtils.bitCount(114514,23);
        }
        b=System.nanoTime();
        log("bc test time 3  "+(b-a)+" ns");
        log(MathUtils.toBinaryCode(114514));
        for(int i=0;i<=32;++i){
            log(MathUtils.toBinaryCode(MathUtils.maskToN(2147483647,i)));
        }
        log(MathUtils.bitCount(114514,23)+"");
        log(MathUtils.toBinaryCode(-114514));
        log(MathUtils.bitCount(-114514,23)+"");
        log("[TEST MathUtils] TEST SUCCESS");
    }
    //@Test
    public void test_Regex(){
        String s="world,-133,29,14";
        Pattern reg=Pattern.compile("(.*?),(.*?),(.*?),(.*?)");
        Matcher m=reg.matcher(s);
        if(m.find()){
            log(m.group(1));
        }
        String t;
        int a;
        int b;
        int c;
        long start=System.nanoTime();
        for(int i=0;i<100_0000;++i){
            m=reg.matcher(s);
            m.matches();
            t=m.group(1);
            a=Integer.parseInt(m.group(2));
            b=Integer.parseInt(m.group(3));
            c=Integer.parseInt(m.group(4));
        }
        long end=System.nanoTime();
        log("[TEST Regex] first test"+(end-start)+" ns");
        start=System.nanoTime();
        String[] st;
        for(int i=0;i<100_0000;++i){
            st= s.split(",");
            assert st.length==4;
            t=st[0];
            a=Integer.parseInt(st[1]);
            b=Integer.parseInt(st[2]);
            c=Integer.parseInt(st[3]);
        }
        end=System.nanoTime();
        log("[TEST Regex] first test"+(end-start)+" ns");
        st= s.split(",");
        assert st.length==4;
        t=st[0];
        a=Integer.parseInt(st[1]);
        b=Integer.parseInt(st[2]);
        c=Integer.parseInt(st[3]);
        log("print "+t+" "+a+" "+b+" "+c);
    }
    //@Test
    public void test_Transport(){

        long start=System.nanoTime();
        for(int s=0;s<32;++s){
            int[] a=new int[54];
            //int[] b=new int[54];

            for (int i=0;i<54;++i){
                a[i]=-1;
            }
            for(int i=1;i<54;++i){
                for(int j=0;j<54;++j){
                    if(a[j]==-1){
                        //b[i]=i+1;
                        continue;
                    }
                }
            }
        }
        long end=System.nanoTime();
        log("[TEST Transport] first test"+(end-start)+" ns");
        start=System.nanoTime();
        for(int s=0;s<32;++s){
            LinkedHashMap<Integer,Integer> a=new LinkedHashMap<>();
            for (int i=0;i<54;++i){
                a.put(i,-1);
            }
            for(int i=1;i<54;++i){
                Iterator<Map.Entry<Integer, Integer>> it=a.entrySet().iterator();
                while(it.hasNext()){

                    Map.Entry<Integer, Integer> entry=it.next();
                    if(entry.getValue()==-1){
                        it.remove();
                    }
                }
                if(a.isEmpty())break;
            }
        }
        end=System.nanoTime();
        log("[TEST Transport] second test"+(end-start)+" ns");
        start=System.nanoTime();
        for(int s=0;s<32;++s){
            List<Integer> a=new ArrayList<Integer> (55);
            for (int i=0;i<54;++i){
                a.add(-1);
            }
            SimpleLinkList<Integer> slt=new SimpleLinkList<>(a);
            for(int i=1;i<54;++i){
                int prev=-1;
                int next;
                int data;
                while(slt.hasNext(prev)){

                    next=slt.getNext(prev);
                    data=slt.get(next);
                    if(data==-1){
                      //  log("checkout "+prev+" "+next);
                        slt.deleteNext(prev);
                    }else{
                        prev=next;
                    }
                }
            }
        }
        end=System.nanoTime();
        log("[TEST Transport] third test"+(end-start)+" ns");
        start=System.nanoTime();
        for(int s=0;s<32;++s){
            int[] a=new int[54];
            for (int i=0;i<54;++i){
                a[i]=-1;
            }
            loop:
            for(int j=0;j<54;++j){
                for(int i=1;i<54;++i){
                    if(a[j]==-1){
                        continue loop;
                    }
                }
            }
        }
        end=System.nanoTime();
        log("[TEST Transport] final test"+(end-start)+" ns");
        int[] b=new int[54];

        start=System.nanoTime();
        for(int s=0;s<32;++s){
            int[] a=new int[54];
            List<Integer> data1=new ArrayList<>(54);
            List<Integer> data2=new ArrayList<>(54);
            //int[] b=new int[54];

            for (int i=0;i<54;++i){
                a[i]=-1;

                if(a[i]>0){
                    data1.add(i);
                }else {
                    data2.add(i);
                }
            }
            int len1=data1.size();
            int len2=data2.size();
            for(int i=1;i<len1;++i){
                for(int j=0;j<len2;++j){
                    if(data2.get(j)==-1){
                        b[i]=i+1;
                        continue;
                    }
                }
            }
        }
        end=System.nanoTime();
        log("[TEST Transport] fourth test"+(end-start)+" ns");
        //测试样例1
        TestStack[] inv1=new TestStack[54];
        TestStack[] inv2=new TestStack[54];
        for(int i=0;i<54;++i){
            inv1[i]=new TestStack(64);
            inv2[i]=new TestStack(64);
        }
        start=System.nanoTime();
        for(int tr=0;tr<32;++tr){
            int[] toRecord=new int[54];
//            List<TestStack> data1=new ArrayList<>(54);
//            List<TestStack> data2=new ArrayList<>(54);
            TestStack[] data1=new TestStack[54];
            int len1=0;
            TestStack as;
            for(int j=0;j<54;++j){
                as=inv2[j];
                if(as.getAmount()!=as.getMaxAmount()){
                    data1[len1]=as;
                    ++len1;
               }
            }
            TestStack as1;
            TestStack as2;
            for (int i=0;i<54;++i){
                as1=inv1[i];
                if(as1.getAmount()==0)continue;
                int s1=as1.getAmount();
                for(int j=0;j<len1;++j){
                    toRecord[i]=-1;
                    as2=data1[j];
                    //if(as2.getAmount()==as2.getMaxAmount()){continue;}
                    int s2=as2.getAmount();
                    s1-=s2;
                    if(s1<=0){
                        break;
                    }
                }
            }
        }
        end=System.nanoTime();
        log("[TEST Transport] simulate test 1"+(end-start)+" ns");
    }
    @Test
    public void test_Transport2(){
        //测试样例1
        TestStack[] inv1=new TestStack[54];
        TestStack[] inv2=new TestStack[54];
        for(int i=0;i<54;++i){
            inv1[i]=new TestStack(64);
            inv2[i]=new TestStack(64);
        }
        long start=System.nanoTime();
        for(int tr=0;tr<320;++tr){
//            List<TestStack> data1=new ArrayList<>(54);
//            List<TestStack> data2=new ArrayList<>(54);
//            TestStack[] data1=new TestStack[54];
//            TestStack[] data2=new TestStack[54];
            int[] data1=new int[54];
//            int[] data0=new int[54];
            int len1=0;
            TestStack as;
            for(int j=0;j<54;++j){
                as=inv2[j];
                if(as.getAmount()!=as.getMaxAmount()){
                    data1[len1]=j;
                    ++len1;
                }
            }
            int[] toRecord=new int[len1];
            TestStack as1;
            TestStack as2;
            boolean isFirst=true;
            int s1;
            for (int i=0;i<54;++i){
                as1=inv1[i];
                if(as1.getAmount()==0)continue;
                //data0[len1]=as1;
                s1=as1.getAmount();
                for(int j=0;j<len1;++j){

                    toRecord[i]=-1;
                    as2=inv2[data1[j]];
                    if(isFirst){
                        if(as2.getAmount()==as2.getMaxAmount()){
                            continue;
                        }
                    }
                    //if(as2.getAmount()==as2.getMaxAmount()){continue;}
                    int s2=as2.getAmount();
                    s1-=s2;
                    if(s1<=0){
                        break;
                    }
                }
            }
        }
        long end=System.nanoTime();
        log("[TEST Transport] simulate test 1"+(end-start)+" ns");
    }
}
