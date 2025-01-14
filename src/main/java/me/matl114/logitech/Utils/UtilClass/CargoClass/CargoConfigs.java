package me.matl114.logitech.Utils.UtilClass.CargoClass;

public enum CargoConfigs {
    /**
     * 是否强对称
     */
    IS_SYMM(0),
    /**
     * 是否仅空
     */
    IS_NULL(1),
    /**
     * 是否首位阻断 即只运输一次就停止
     */
    IS_LAZY(2),
    /**
     * 是否使用黑名单,若否则使用白名单
     */
    IS_WHITELST(3),
    /**
     * 是否从源头的输入槽输入，默认否 为输出槽
     */
    REVERSE(6),
    FROM_INPUT_FIRST(4),
    /**
     * 是否从目标的输出槽输入，默认否 为输入槽
     */
    TO_OUTPUT_FIRST(5),
    FROM_REVERSED(30),
    TO_REVERSED(29),
    /**
     * 是否反向传输,即从设置的目标向源头传输
     */

    /**
     *传输数量 ，最大2^16
     */
    TRANSLIMIT(7,16),
    /**
     * end bit
     */
    VALID(31)
    ;



    ;
    int bit;
    int bitPos;
    int blank;
    int valueTemplate;
    int codeTemplate;
    CargoConfigs(int bit,int len){
        this.bit = bit;
        this.bitPos = 1<<this.bit;
        this.valueTemplate = (1<<len) -1;
        this.codeTemplate = this.valueTemplate<<this.bit;
        this.blank = ~(this.codeTemplate);
    }
    CargoConfigs(int bit){
        this(bit,1);
    }
    public int getConfigInt(int code){
        return (code>>this.bit)&this.valueTemplate;
    }
    public boolean getConfig(int code){
        return (code&this.codeTemplate)!=0;
    }
    public int setConfig(int code,boolean value){
        int valueInt=value?this.bitPos:0;
        code=code&this.blank;
        code=code|valueInt;
        return code;

    }
    public int setConfig(int code,int value){
        value=(value&this.valueTemplate)<<this.bit;
        code=code&this.blank;
        code =code|value;
        return code;
    }
    public static int setConfigBit(int code ,int value,int bit){
        for(CargoConfigs c:CargoConfigs.values()){
            if(c.bit==bit){
                return c.setConfig(code,value);
            }
        }
        return 0;
    }
    public static int setAllConfig(boolean symm,boolean isnull,boolean islazy,boolean blklst,boolean fromInputFirst,boolean toOutputFirst,boolean fromReverse,boolean toReverse,boolean reverse,int limit){
        int code=0;
        code= IS_SYMM.setConfig(code,symm);
        code= IS_NULL.setConfig(code,isnull);
        code= IS_LAZY.setConfig(code,islazy);
        code= IS_WHITELST.setConfig(code,blklst);
        code= FROM_INPUT_FIRST.setConfig(code,fromInputFirst);
        code= TO_OUTPUT_FIRST.setConfig(code,toOutputFirst);
        code= FROM_REVERSED.setConfig(code,fromReverse);
        code= TO_REVERSED.setConfig(code,toReverse);
        code= REVERSE.setConfig(code,reverse);
        code=TRANSLIMIT.setConfig(code,limit);
        return code;
    }
    public static int getDefaultConfig(){
        return 8192;
    }
}