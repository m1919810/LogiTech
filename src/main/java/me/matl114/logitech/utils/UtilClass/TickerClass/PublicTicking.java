package me.matl114.logitech.utils.UtilClass.TickerClass;

public interface PublicTicking extends Ticking {
    //    //one type one ticker
    //    //all machines under one object use same ticks
    //
    //    int getPublicTick();
    //    void updatePublicTick();
    //    /**
    //     * call this method in preRegister
    //     * @param item
    //     */
    //    default void registerTick(SlimefunItem item){
    //        item.addItemHandler(
    //                new BlockTicker() {
    //                    public boolean isSynchronized() {
    //                        return PublicTicking.this.isSync();
    //                    }
    //
    //                    @ParametersAreNonnullByDefault
    //                    public void tick(Block b, SlimefunItem item, SlimefunBlockData data) {
    //                        BlockMenu menu = data.getBlockMenu();
    //                        if (menu != null) {
    //                            PublicTicking.this.tick(b, menu,data,getPublicTick());
    //                        }
    //
    //                    }
    //                    public void uniqueTick() {
    //                        PublicTicking.this.updatePublicTick();
    //                    }
    //                }
    //
    //
    //        );
    //    }
    //    default boolean isSync(){
    //        return  false;
    //    }
    //
    //    /**
    //     * imple blockTicking in this method
    //     * @param b
    //     * @param menu
    //     * @param tickCount
    //     */
}
