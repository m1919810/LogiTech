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
    //TODO 不知道为啥 我似乎喜欢把东西记录在这里
    //TODO 做一个配置文件导出系统 然后完善
    //TODO 做一个随机附魔机
    //TODO 完成之前的TODO
    //TODO 简化多方块机器uid数据大小，并且兼容之前的数据 随机字符串
    //TODO 测试先移除sfdata再blockbreak
    //TODO 增加增幅槽位限制
    //TODO 堆叠发电机增加增幅率
    //TODO 减少配方价格
    //TODO 增加机器产出
    //
    //完成普通特殊合成台和高级特殊合成肽
    // 增加无尽
}
