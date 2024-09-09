package me.matl114.logitech;

public class Unittest {
    //为什么要单开一个文件夹？
    //好问题

    //测试1 测试dataload和menu的 顺序
    //测试2 测试dataload时候区块的加载状态
    //测试3 测试chunkloadEvent调用顺序
    //测试结果， 调用menuInstance时候是requestload后
    //使用getBlockDataFromCache会导致区块加载
    //在loc.getChunk()时会加载区块
    //修改DataCache为不加载的反射方法
    public static boolean SFDATA_TEST =false;
}
