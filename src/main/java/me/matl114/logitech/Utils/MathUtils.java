package me.matl114.logitech.Utils;

public class MathUtils {
    /**
     * int型存储规则 采用小端存储
     */
    public static boolean getBits(int code,int bit){
        return ((code>>bit) &1)!=0;
    }
    public static boolean getActualBit(String codeStr,int bit){
        return codeStr.charAt(bit)=='1';
    }
    public static boolean getBit(String codeStr,int bit){

    }
    public static String toBinaryCode(int num){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<32;++i){
            sb.append(num&1);
            num=num>>1;
        }
        return sb.toString();
    }
}
