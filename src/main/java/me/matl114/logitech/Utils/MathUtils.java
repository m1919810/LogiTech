package me.matl114.logitech.Utils;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class MathUtils {
    protected static final boolean isCompacted=((Supplier<Boolean>)(()->{
         Boolean obj=   (Boolean)ReflectUtils.invokeGetRecursively("草",Settings.METHOD,"coder");
         if(obj==null) {
             return true;
         }
         return obj;
    })).get();
    static final int HI_BYTE_SHIFT=((Supplier<Integer>)(()->{
        Integer obj=   (Integer) ReflectUtils.invokeGetRecursively("草",Settings.FIELD,"HI_BYTE_SHIFT");
        if(obj==null) return 0;
        return obj;
    })).get();
    static final int LO_BYTE_SHIFT=((Supplier<Integer>)(()->{
        Integer obj=   (Integer) ReflectUtils.invokeGetRecursively("草",Settings.FIELD,"LO_BYTE_SHIFT");
        if(obj==null) return 8;
        return obj;
    })).get();

    static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    static final int[] mask ;
    static final int[] POW;
    static final String zeroString=toBinaryCode(0);
    static {
        mask=new int[33];
        POW=new int[33];
        for(int i=0; i<32; i++) {
            POW[i]=(1<<i);
            mask[i]=(1<<i) -1;
        }
        mask[32]=-1;
    }
    public static int getBitPos(int k){
        return POW[k];
    }
    /**
     * int型存储规则 采用小端存储
     */
    public static boolean getBits(int code,int bit){
        return ((code>>bit) &1)!=0;
    }
    public static boolean getBit(String codeStr,int bit){
        return codeStr.charAt(bit)=='1';
    }
    static void putChar(byte[] val, int index, int c) {
        assert index >= 0 && index < val.length >> 1 : "Trusted caller missed bounds check";

        index <<= 1;
        val[index++] = (byte)(c >> HI_BYTE_SHIFT);
        val[index] = (byte)(c >> LO_BYTE_SHIFT);
    }
    private static void formatUnsignedInt(int val, int shift, byte[] buf, int len) {
        int charPos = 0;
        int radix = 1 << shift;
        int mask = radix - 1;
        do {
            buf[charPos] = (byte)digits[val & mask];
            val >>>= shift;
            ++charPos;

        } while(charPos <len);

    }

    private static void formatUnsignedIntUTF16(int val, int shift, byte[] buf, int len) {
        int charPos = 0;
        int radix = 1 << shift;
        int mask = radix - 1;
        do {
            putChar(buf, charPos, digits[val & mask]);
            val >>>= shift;
            ++charPos;
        } while(charPos <len);
    }
    public static String toBinaryCode(int num){
        StringBuilder sb= new StringBuilder();
        for (int i=0;i<32;++i){
            sb.append((num&1)==0?'0':'1');
            num=num>>1;
        }
        return sb.toString();
    }

    /**
     * make sure these codes
     * @param a
     * @param b
     * @return
     */
    public static String andStr(String a,String b){
        if(a.length()!=32||b.length()!=32){
            return zeroString;
        }else {
            StringBuilder sb= new StringBuilder();
            for(int i=0;i<32;++i){
                sb.append((a.charAt(i)==digits[1]&&b.charAt(i)==digits[1])?'1':'0');
            }
            return sb.toString();
        }
    }
    public static String orStr(String a,String b){
        if(a.length()!=32||b.length()!=32){
            return zeroString;
        }else {
            StringBuilder sb= new StringBuilder();
            for(int i=0;i<32;++i){
                sb.append((a.charAt(i)==digits[1]||b.charAt(i)==digits[1])?'1':'0');
            }
            return sb.toString();
        }
    }
    public static String xorStr(String a,String b){
        if(a.length()!=32||b.length()!=32){
            return zeroString;
        }else {
            StringBuilder sb= new StringBuilder();
            for(int i=0;i<32;++i){
                sb.append((a.charAt(i)==b.charAt(i))?'0':'1');
            }
            return sb.toString();
        }
    }
    public static String notStr(String a){
        if(a.length()!=32){
            return zeroString;
        }else {
            StringBuilder sb= new StringBuilder();
            for(int i=0;i<32;++i){
                sb.append((a.charAt(i)==digits[1])?'0':'1');
            }
            return sb.toString();
        }
    }
    public static String leftShiftStr(String a){
        if(a.length()!=32){
            return zeroString;
        }else {
            StringBuilder sb= new StringBuilder(a);
            sb.insert(0,digits[0]);
            return sb.substring(0,32);
        }
    }

    /**
     * 算数右移
     * @param a
     * @return
     */
    public static String rightShiftStr(String a){
        if(a.length()!=32){
            return zeroString;
        }else {
            StringBuilder sb= new StringBuilder(a);
            //sb.deleteCharAt(0);
            sb.append(a.charAt(a.length()-1));
            return sb.substring(1,a.length()+1);
        }
    }
    public static String toBinaryCodeForce(int num){
        int chars = 32;
        byte[] buf;
        if(isCompacted){
            buf = new byte[chars];
            formatUnsignedInt(num, 1, buf, chars);
            return new String(buf, (byte)0);
        }
        else {
            buf = new byte[chars * 2];
            formatUnsignedIntUTF16(num, 1, buf, chars);
            return new String(buf, (byte)1);
        }

    }
    public static int fromBinaryCode(String code){
        /**
         * we assume that  code len 32
         */
        return 0;
    }
    public static int maskToN(int code ,int n){

        int maskN =mask[n];
        return code&maskN;
    }
    public static int getBit(int code,int pos){
        return (code&POW[pos])==0?0:1;
    }
    public static int bitCount(int code,int to){
        code=maskToN(code,to);
        int tmp = code - ((code >>1) &033333333333) - ((code >>2) &011111111111);
        return ((tmp + (tmp >>3)) &030707070707) %63;
    }
    public static int fromLong(@Nonnull Long a){
        if(a>Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else return a.intValue();
    }
}
