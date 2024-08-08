package me.matl114.logitech.SlimefunItem.Machines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemState;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;

import io.github.thebusybiscuit.slimefun4.core.attributes.NotHopperable;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;

import me.matl114.logitech.SlimefunItem.CustomSlimefunItem;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Settings;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract  class AbstractMachine extends CustomSlimefunItem implements Ticking, MenuBlock,  EnergyNetComponent, NotHopperable , DataCache {
    //我们的目标是 最广的需求 最好的性能 最大的答辩(bushi
    public  List<MachineRecipe> machineRecipes ;
    private final String id;
    /**
     * energy stuff
     */
    public final  int energybuffer;
    public final int energyConsumption;
    public final EnergyNetComponentType energyNetComponent;
    private Function<BlockMenu,Boolean> handleProcess;
    private Consumer<BlockMenu> handlerProcessCost;
    /**
     * constructor of abstractMachines will keep Collections of MachineRecipes,will register energyNetwork params,
     * will set up menu by overriding constructMenu method
     * @param category
     * @param item
     * @param recipeType
     * @param recipe
     * @param energybuffer
     * @param energyConsumption
     */
    public AbstractMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                           int energybuffer, int energyConsumption){
        super(category,item , recipeType, recipe);
        this.energybuffer = energybuffer;
        this.energyConsumption = energyConsumption>0?energyConsumption:-energyConsumption;
        this.energyNetComponent=(energybuffer==0)?(EnergyNetComponentType.NONE) :
                ((energyConsumption>0)?(EnergyNetComponentType.CONSUMER):(energyConsumption==0?EnergyNetComponentType.CAPACITOR: EnergyNetComponentType.GENERATOR));
        this.id = item.getItemId();
        switch (this.energyNetComponent){
            case GENERATOR:
                this.handleProcess = (inv) -> {
                    if(this.getCapacity()>this.getCharge(inv.getLocation())+this.energyConsumption){
                        return true;
                    }else return false;
                };
                this.handlerProcessCost = (inv) -> {
                    return ;
                };
                break;
            case CONSUMER:
                this.handleProcess =  (inv) -> {
                    if(this.energyConsumption<this.getCharge(inv.getLocation())){
                        return true;
                    }else return false;
                };
                this.handlerProcessCost = (inv) -> {
                    this.removeCharge(inv.getLocation(), this.energyConsumption);
                };
                break;
            case CAPACITOR:
            case NONE:
            case CONNECTOR:
            default:
                this.handleProcess = (inv) -> {
                    return true;
                };
                this.handlerProcessCost = (inv) -> {
                    return ;
                };
                break;

        }


    }

    /**
     * automatically add information lore to sfitemstack
     * @param stack
     * @return
     */
    public void addInfo(ItemStack stack){
        if(this.energyConsumption!=0)
            stack.setItemMeta(AddUtils.machineInfoAdd(stack,this.energybuffer,this.energyConsumption).getItemMeta());
    }

    /**
     * construct your menu here.called in constructor
     * @param preset
     */
    public abstract void constructMenu(BlockMenuPreset preset);

    /**
     * cargo and IO
     * @return
     */
    public abstract int[] getInputSlots();

    /**
     * cargo and IO
     * @return
     */
    public abstract int[] getOutputSlots();

    public abstract List<MachineRecipe> getMachineRecipes();

    /**
     * differently get machinerecipes via location and inv
     * @param b
     * @param inv
     * @return
     */
    public List<MachineRecipe> getMachineRecipes(Block b,BlockMenu inv){
        return getMachineRecipes();
    }
    /**
     * handle all conditions and make response like electricity, and so on,
     * if condition pass, run process()
     * @param b
     * @param menu
     * @return
     */
    public boolean conditionHandle(Block b,BlockMenu menu){
        return this.handleProcess.apply(menu);
    }

    /**
     * do real tick process like findrecipe or pushitem
     * @param b
     * @param preset
     */
    public abstract void process(Block b,BlockMenu preset);

    /**
     * make cost for process
     * @param b
     * @param menu
     */
    protected void processorCost(Block b,BlockMenu menu) {
        this.handlerProcessCost.accept(menu);
    }

    /**
     * get energycomponenttype
     * @return
     */
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return this.energyNetComponent;
    }

    /**
     * get capacity
     * @return
     */
    public final int getCapacity(){
        return this.energybuffer;
    }

    /**
     * container name,default item name
     * @return
     */
    public final String getInventoryTitle() {
        return this.getItemName();
    }

    /**
     * update menu when we have sth change or we have a new Instance
     * @param blockMenu
     * @param block
     */
    public void updateMenu(BlockMenu blockMenu, Block block, Settings mod){

    }

    /**
     * create new Menu INSTANCE at blockMenu,default None
     * @param blockMenu
     * @param block
     */
    public void newMenuInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block){
        updateMenu(blockMenu,block,Settings.INIT);
    }
    /**
     * fetch List of recipes tobee show
     * @return
     */
    public List<MachineRecipe> provideDisplayRecipe(){
        return getMachineRecipes();
    }
    public void tick(Block b, BlockMenu menu, int ticker) {
        if(conditionHandle(b,menu)){
            process(b,menu);
        }}
    public void enable() {
        super.enable();
        this.registerDefaultRecipes();
    }

    public void disable() {
        super.disable();
        //this.getMachineRecipes().clear();
    }
    public void postRegister() {
        super.postRegister();
        if (this.getState() == ItemState.ENABLED) {
            this.registerDefaultRecipes();
        }

    }
    public void preRegister(){
        super.preRegister();
        //
        registerTick(this);
        //为menublock提供 需要
        registerBlockMenu(this);
    }
    protected void registerDefaultRecipes() {
    }


}
