package me.matl114.logitech.Utils;

public enum Settings {
    USE_SEC_EXP(),
    USE_TICK_EXP(),
    //顺序搜索
    SEQUNTIAL(),
    //逆序搜索
    REVERSE(),
    //抓取模式 消耗物品
    GRAB(),
    //推送模式 输出物品
    PUSH(),
    //初始化阶段
    INIT(),
    //运行阶段
    RUN(),
    PREFIX(),
    SUFFIX(),
    OUTPUT(),
    INPUT(),
    METHOD(),
    FIELD();

    private static int cnt=0;
    private static int get(){
        return cnt++;
    }

}
