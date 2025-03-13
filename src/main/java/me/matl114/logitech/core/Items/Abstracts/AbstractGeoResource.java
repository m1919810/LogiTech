package me.matl114.logitech.core.Items.Abstracts;

import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.matl114.logitech.core.Registries.AddHandlers;
import me.matl114.logitech.core.AddSlimefunItems;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.Debug;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;

public class AbstractGeoResource extends SlimefunItem implements GEOResource {
    protected final int MAXDEVIATION;
    protected final String NAME;
    protected final NamespacedKey NSKEY;
    protected final HashMap<Biome,Integer> BIOME_MAP;
    protected final HashMap<World.Environment,HashMap<Biome,Integer>> CUSTOMWORLD_MAP;
    public List<ItemStack> displayedMemory;
    public AbstractGeoResource(ItemGroup itemGroup, SlimefunItemStack item,ItemStack[] recipe,    int maxDeviation,HashMap<Biome,Integer> biomeMap,HashMap<World.Environment,HashMap<Biome,Integer>>CustomWorld) {
        super(itemGroup, item,RecipeType.GEO_MINER,recipe);
        this.MAXDEVIATION=maxDeviation;
        this.NAME= item.getDisplayName();
        this.NSKEY=AddUtils.getNameKey(item.getItemId());
        this.BIOME_MAP=biomeMap!=null?biomeMap:new HashMap<>();
        this.CUSTOMWORLD_MAP=CustomWorld!=null?CustomWorld:new HashMap<>();
    }
    public AbstractGeoResource(ItemGroup itemGroup, SlimefunItemStack item, ItemStack[] recipe,  int maxDeviation,HashMap<Biome,Integer> biomeMap) {
        this(itemGroup,item,recipe,maxDeviation,biomeMap,null);
    }
    public int getDefaultSupply(@Nonnull World.Environment var1, @Nonnull Biome var2){
        if(var1 != World.Environment.CUSTOM){
            if(BIOME_MAP.containsKey(var2)){
                return BIOME_MAP.get(var2);
            }else if (CUSTOMWORLD_MAP.containsKey(var1)){
                Integer a=CUSTOMWORLD_MAP.get(var1).get(var2);
                if(a != null){
                    return a;
                }
            }
            return 0;
        }else {
            var a=CUSTOMWORLD_MAP.get(var1);//.get(var2);
            if(a != null){
                return a.getOrDefault(var2, 0);
            }
            return 0;
        }
    }

    public int getMaxDeviation(){
        return MAXDEVIATION;
    }
    public AbstractGeoResource setDisplayRecipes(List<ItemStack> displayRecipes) {
        this.displayedMemory = displayRecipes;
        return this;
    }
    @Nonnull
    public String getName(){
        return NAME;
    }
    @Nonnull
    public ItemStack getItem(){
        return super.getItem().clone();
    }
    public boolean isObtainableFromGEOMiner(){
        return true;
    }
    public NamespacedKey getKey(){
        return NSKEY;
    }
    public SlimefunItem registerGeo(){
        if(AddSlimefunItems.INSTANCE!=null){
            register(AddSlimefunItems.INSTANCE);
        }else{
            Debug.logger("找不到附属实例!  注册信息: "+this.toString());
        }
        register();
        return this;
    }
    public  void preRegister(){
        super.preRegister();
        addItemHandler(AddHandlers.stopAttackHandler);
        addItemHandler(AddHandlers.stopPlacementHandler);
    }
}
