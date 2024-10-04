package me.matl114.logitech;


import me.matl114.logitech.Utils.*;
import me.matl114.logitech.Utils.Algorithms.DynamicArray;
import me.matl114.logitech.Utils.Algorithms.SimpleLinkList;
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
        String s="\"networks:output\":[B;-84B,-19B,0B,5B,115B,114B,0B,26B,111B,114B,103B,46B,98B,117B,107B,107B,105B,116B,46B,117B,116B,105B,108B,46B,105B,111B,46B,87B,114B,97B,112B,112B,101B,114B,-14B,80B,71B,-20B,-15B,18B,111B,5B,2B,0B,1B,76B,0B,3B,109B,97B,112B,116B,0B,15B,76B,106B,97B,118B,97B,47B,117B,116B,105B,108B,47B,77B,97B,112B,59B,120B,112B,115B,114B,0B,53B,99B,111B,109B,46B,103B,111B,111B,103B,108B,101B,46B,99B,111B,109B,109B,111B,110B,46B,99B,111B,108B,108B,101B,99B,116B,46B,73B,109B,109B,117B,116B,97B,98B,108B,101B,77B,97B,112B,36B,83B,101B,114B,105B,97B,108B,105B,122B,101B,100B,70B,111B,114B,109B,0B,0B,0B,0B,0B,0B,0B,0B,2B,0B,2B,76B,0B,4B,107B,101B,121B,115B,116B,0B,18B,76B,106B,97B,118B,97B,47B,108B,97B,110B,103B,47B,79B,98B,106B,101B,99B,116B,59B,76B,0B,6B,118B,97B,108B,117B,101B,115B,113B,0B,126B,0B,4B,120B,112B,117B,114B,0B,19B,91B,76B,106B,97B,118B,97B,46B,108B,97B,110B,103B,46B,79B,98B,106B,101B,99B,116B,59B,-112B,-50B,88B,-97B,16B,115B,41B,108B,2B,0B,0B,120B,112B,0B,0B,0B,4B,116B,0B,2B,61B,61B,116B,0B,1B,118B,116B,0B,4B,116B,121B,112B,101B,116B,0B,4B,109B,101B,116B,97B,117B,113B,0B,126B,0B,6B,0B,0B,0B,4B,116B,0B,30B,111B,114B,103B,46B,98B,117B,107B,107B,105B,116B,46B,105B,110B,118B,101B,110B,116B,111B,114B,121B,46B,73B,116B,101B,109B,83B,116B,97B,99B,107B,115B,114B,0B,17B,106B,97B,118B,97B,46B,108B,97B,110B,103B,46B,73B,110B,116B,101B,103B,101B,114B,18B,-30B,-96B,-92B,-9B,-127B,-121B,56B,2B,0B,1B,73B,0B,5B,118B,97B,108B,117B,101B,120B,114B,0B,16B,106B,97B,118B,97B,46B,108B,97B,110B,103B,46B,78B,117B,109B,98B,101B,114B,-122B,-84B,-107B,29B,11B,-108B,-32B,-117B,2B,0B,0B,120B,112B,0B,0B,13B,-119B,116B,0B,11B,69B,78B,68B,95B,67B,82B,89B,83B,84B,65B,76B,115B,113B,0B,126B,0B,0B,115B,113B,0B,126B,0B,3B,117B,113B,0B,126B,0B,6B,0B,0B,0B,4B,113B,0B,126B,0B,8B,116B,0B,9B,109B,101B,116B,97B,45B,116B,121B,112B,101B,116B,0B,12B,100B,105B,115B,112B,108B,97B,121B,45B,110B,97B,109B,101B,116B,0B,18B,80B,117B,98B,108B,105B,99B,66B,117B,107B,107B,105B,116B,86B,97B,108B,117B,101B,115B,117B,113B,0B,126B,0B,6B,0B,0B,0B,4B,116B,0B,8B,73B,116B,101B,109B,77B,101B,116B,97B,116B,0B,10B,85B,78B,83B,80B,69B,67B,73B,70B,73B,67B,116B,1B,7B,123B,34B,101B,120B,116B,114B,97B,34B,58B,91B,123B,34B,98B,111B,108B,100B,34B,58B,102B,97B,108B,115B,101B,44B,34B,105B,116B,97B,108B,105B,99B,34B,58B,102B,97B,108B,115B,101B,44B,34B,117B,110B,100B,101B,114B,108B,105B,110B,101B,100B,34B,58B,102B,97B,108B,115B,101B,44B,34B,115B,116B,114B,105B,107B,101B,116B,104B,114B,111B,117B,103B,104B,34B,58B,102B,97B,108B,115B,101B,44B,34B,111B,98B,102B,117B,115B,99B,97B,116B,101B,100B,34B,58B,102B,97B,108B,115B,101B,44B,34B,99B,111B,108B,111B,114B,34B,58B,34B,35B,68B,67B,68B,55B,57B,69B,34B,44B,34B,116B,101B,120B,116B,34B,58B,34B,-28B,-68B,-86B,34B,125B,44B,123B,34B,98B,111B,108B,100B,34B,58B,102B,97B,108B,115B,101B,44B,34B,105B,116B,97B,108B,105B,99B,34B,58B,102B,97B,108B,115B,101B,44B,34B,117B,110B,100B,101B,114B,108B,105B,110B,101B,100B,34B,58B,102B,97B,108B,115B,101B,44B,34B,115B,116B,114B,105B,107B,101B,116B,104B,114B,111B,117B,103B,104B,34B,58B,102B,97B,108B,115B,101B,44B,34B,111B,98B,102B,117B,115B,99B,97B,116B,101B,100B,34B,58B,102B,97B,108B,115B,101B,44B,34B,99B,111B,108B,111B,114B,34B,58B,34B,35B,68B,67B,68B,55B,57B,69B,34B,44B,34B,116B,101B,120B,116B,34B,58B,34B,-25B,-119B,-87B,34B,125B,93B,44B,34B,116B,101B,120B,116B,34B,58B,34B,34B,125B,115B,114B,0B,17B,106B,97B,118B,97B,46B,117B,116B,105B,108B,46B,72B,97B,115B,104B,77B,97B,112B,5B,7B,-38B,-63B,-61B,22B,96B,-47B,3B,0B,2B,70B,0B,10B,108B,111B,97B,100B,70B,97B,99B,116B,111B,114B,73B,0B,9B,116B,104B,114B,101B,115B,104B,111B,108B,100B,120B,112B,63B,64B,0B,0B,0B,0B,0B,12B,119B,8B,0B,0B,0B,16B,0B,0B,0B,1B,116B,0B,22B,115B,108B,105B,109B,101B,102B,117B,110B,58B,115B,108B,105B,109B,101B,102B,117B,110B,95B,105B,116B,101B,109B,116B,0B,15B,70B,73B,78B,65B,76B,84B,69B,67B,72B,95B,80B,72B,79B,78B,89B,120B],\"networks:recipe\":[B;-84B,-19B,0B,5B,119B,4B,0B,0B,0B,9B,115B,114B,0B,26B,111B,114B,103B,46B,98B,117B,107B,107B,105B,116B,46B,117B,116B,105B,108B,46B,105B,111B,46B,87B,114B,97B,112B,112B,101B,114B,-14B,80B,71B,-20B,-15B,18B,111B,5B,2B,0B,1B,76B,0B,3B,109B,97B,112B,116B,0B,15B,76B,106B,97B,118B,97B,47B,117B,116B,105B,108B,47B,77B,97B,112B,59B,120B,112B,115B,114B,0B,53B,99B,111B,109B,46B,103B,111B,111B,103B,108B,101B,46B,99B,111B,109B,109B,111B,110B,46B,99B,111B,108B,108B,101B,99B,116B,46B,73B,109B,109B,117B,116B,97B,98B,108B,101B,77B,97B,112B,36B,83B,101B,114B,105B,97B,108B,105B,122B,101B,100B,70B,111B,114B,109B,0B,0B,0B,0B,0B,0B,0B,0B,2B,0B,2B,76B,0B,4B,107B,101B,121B,115B,116B,0B,18B,76B,106B,97B,118B,97B,47B,108B,97B,110B,103B,47B,79B,98B,106B,101B,99B,116B,59B,76B,0B,6B,118B,97B,108B,117B,101B,115B,113B,0B,126B,0B,4B,120B,112B,117B,114B,0B,19B,91B,76B,106B,97B,118B,97B,46B,108B,97B,110B,103B,46B,79B,98B,106B,101B,99B,116B,59B,-112B,-50B,88B,-97B,16B,115B,41B,108B,2B,0B,0B,120B,112B,0B,0B,0B,4B,116B,0B,2B,61B,61B,116B,0B,1B,118B,116B,0B,4B,116B,121B,112B,101B,116B,0B,4B,109B,101B,116B,97B,117B,113B,0B,126B,0B,6B,0B,0B,0B,4B,116B,0B,62B,105B,111B,46B,103B,105B,116B,104B,117B,98B,46B,116B,104B,101B,98B,117B,115B,121B,98B,105B,115B,99B,117B,105B,116B,46B,115B,108B,105B,109B,101B,102B,117B,110B,52B,46B,97B,112B,105B,46B,105B,116B,101B,109B,115B,46B,83B,108B,105B,109B,101B,102B,117B,110B,73B,116B,101B,109B,83B,116B,97B,99B,107B,115B,114B,0B,17B,106B,97B,118B,97B,46B,108B,97B,110B,103B,46B,73B,110B,116B,101B,103B,101B,114B,18B,-30B,-96B,-92B,-9B,-127B,-121B,56B,2B,0B,1B,73B,0B,5B,118B,97B,108B,117B,101B,120B,114B,0B,16B,106B,97B,118B,97B,46B,108B,97B,110B,103B,46B,78B,117B,109B,98B,101B,114B,-122B,-84B,-107B,29B,11B,-108B,-32B,-117B,2B,0B,0B,120B,112B,0B,0B,13B,-119B,116B,0B,16B,80B,82B,73B,83B,77B,65B,82B,73B,78B,69B,95B,83B,72B,65B,82B,68B,115B,113B,0B,126B,0B,0B,115B,113B,0B,126B,0B,3B,117B,113B,0B,126B,0B,6B,0B,0B,0B,4B,113B,0B,126B,0B,8B,116B,0B,9B,109B,101B,116B,97B,45B,116B,121B,112B,101B,116B,0B,12B,100B,105B,115B,112B,108B,97B,121B,45B,110B,97B,109B,101B,116B,0B,18B,80B,117B,98B,108B,105B,99B,66B,117B,107B,107B,105B,116B,86B,97B,108B,117B,101B,115B,117B,113B,0B,126B,0B,6B,0B,0B,0B,4B,116B,0B,8B,73B,116B,101B,109B,77B,101B,116B,97B,116B,0B,10B,85B,78B,83B,80B,69B,67B,73B,70B,73B,67B,116B,1B,5B,123B,34B,101B,120B,116B,114B,97B,34B,58B,91B,123B,34B,98B,111B,108B,100B,34B,58B,102B,97B,108B,115B,101B,44B,34B,105B,116B,97B,108B,105B,99B,34B,58B,102B,97B,108B,115B,101B,44B,34B,117B,110B,100B,101B,114B,108B,105B,110B,101B,100B,34B,58B,102B,97B,108B,115B,101B,44B,34B,115B,116B,114B,105B,107B,101B,116B,104B,114B,111B,117B,103B,104B,34B,58B,102B,97B,108B,115B,101B,44B,34B,111B,98B,102B,117B,115B,99B,97B,116B,101B,100B,34B,58B,102B,97B,108B,115B,101B,44B,34B,99B,111B,108B,111B,114B,34B,58B,34B,35B,69B,52B,67B,53B,69B,66B,34B,44B,34B,116B,101B,120B,116B,34B,58B,34B,-27B,-93B,-77B,34B,125B,44B,123B,34B,98B,111B,108B,100B,34B,58B,102B,97B,108B,115B,101B,44B,34B,105B,116B,97B,108B,105B,99B,34B,58B,102B,97B,108B,115B,101B,44B,34B,117B,110B,100B,101B,114B,108B,105B,110B,101B,100B,34B,58B,102B,97B,108B,115B,101B,44B,34B,115B,116B,114B,105B,107B,101B,116B,104B,114B,111B,117B,103B,104B,34B,58B,102B,97B,108B,115B,101B,44B,34B,111B,98B,102B,117B,115B,99B,97B,116B,101B,100B,34B,58B,102B,97B,108B,115B,101B,44B,34B,99B,111B,108B,111B,114B,34B,58B,34B,35B,69B,52B,67B,53B,69B,66B,34B,44B,34B,116B,101B,120B,116B,34B,58B,34B,32B,34B,125B,93B,44B,34B,116B,101B,120B,116B,34B,58B,34B,34B,125B,115B,114B,0B,17B,106B,97B,118B,97B,46B,117B,116B,105B,108B,46B,72B,97B,115B,104B,77B,97B,112B,5B,7B,-38B,-63B,-61B,22B,96B,-47B,3B,0B,2B,70B,0B,10B,108B,111B,97B,100B,70B,97B,99B,116B,111B,114B,73B,0B,9B,116B,104B,114B,101B,115B,104B,111B,108B,100B,120B,112B,63B,64B,0B,0B,0B,0B,0B,12B,119B,8B,0B,0B,0B,16B,0B,0B,0B,1B,116B,0B,22B,115B,108B,105B,109B,101B,102B,117B,110B,58B,115B,108B,105B,109B,101B,102B,117B,110B,95B,105B,116B,101B,109B,116B,0B,15B,70B,73B,78B,65B,76B,84B,69B,67B,72B,95B,83B,72B,69B,76B,76B,120B,113B,0B,126B,0B,2B,113B,0B,126B,0B,2B,113B,0B,126B,0B,2B,112B,113B,0B,126B,0B,2B,113B,0B,126B,0B,2B,113B,0B,126B,0B,2B,113B,0B,126B,0B,2B]";

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
    @Test
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
        Pattern pat= Pattern.compile("(.*?)\\[([0-9]*)\\]");
        String varName="homePeople[336]";
        Matcher matcher=pat.matcher(varName);
        if(matcher.matches()){
            log("name "+matcher.group(1));
            log("index "+matcher.group(2));
        }else{
            log("match failed");
        }
        String[] list=varName.split("\\[");
        log(String.valueOf( list.length));
        log(list[0]);
        int sb=Integer.parseInt( list[1].substring(0,list[1].length()-1));
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
   // @Test
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
  //  @Test
    public void test_InstanceOf(){
        TestConsumer tc=new TestConsumer(new TestStack(114));
        long start,end;
        start=System.nanoTime();
        int a=1;
        for(int i=0;i<100_000;++i){
            if(tc instanceof TestCounter){
                a=1;
            }
        }
        end=System.nanoTime();
        log((end-start)+" ns");
    }

    public static int weightedRandom(int[] weightSum ){
        int len=weightSum.length;
        int randouble=ThreadLocalRandom.current().nextInt(len);
        //if(len>114){
        int start=0;
        int end=len-1;
        while(end-start>1){
            int mid=(start+end)/2;
            if(weightSum[mid]<randouble){
                start=mid;
            }else if (weightSum[mid]>randouble) {
                end=mid;
            }else return mid;
        }
        return start;
    }
   // @Test
    public void test_Random(){
        long a,s;
        Random r=new Random();

        a=System.nanoTime();
        for(int i=0;i<100_000;++i){
            r.nextInt(400);
        }
        s=System.nanoTime();
        log("rand "+(s-a)+" ns");
        int[] weightSum=new int[]{
                30,60,110,130,170,230,290,330,400
        };
        a=System.nanoTime();
        for(int i=0;i<100_000;++i){
            weightedRandom(weightSum);
        }
        s=System.nanoTime();
        log("rand "+(s-a)+" ns");
    }
   // @Test
    public void test_Reflection(){
        TestConsumer tc=new TestConsumer(new TestStack(114));
        Object obj= ReflectUtils.invokeGetRecursively(tc, Settings.FIELD,"shit");
        if(obj==null){
            log("reflect failed");
        }else
        log("reflect success"+(Integer)(obj) );
    }

}
