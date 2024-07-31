package me.matl114.logitech.Utils;

import com.ytdd9527.networks.expansion.core.item.machine.manual.ExpansionWorkbench;
import io.github.mooy1.infinityexpansion.items.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.items.machines.VoidHarvester;
import io.github.mooy1.infinityexpansion.items.mobdata.MobDataInfuser;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumWorkbench;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.altar.AltarRecipe;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.Composter;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.Crucible;
import me.matl114.logitech.Dependency;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class RecipeSupporter {
    public static void init(){
        Debug.logger("配方供应器开始注册配方......");
    }
    public static final ArrayList<MachineRecipe> GOLDPAN_RECIPE = new ArrayList<>(){{
        add(MachineRecipeUtils.stackFrom(-1,
                new ItemStack[] {new ItemStack(Material.GRAVEL)},
                new ItemStack[] {
                        AddUtils.randItemStackFactory(
                                new LinkedHashMap<>(){{
                                    put("FLINT",40);
                                    put("CLAY_BALL",20);
                                    put("SIFTED_ORE",35);
                                    put("IRON_NUGGET",5);
                                }}
                        )
                }
        ));
        add(MachineRecipeUtils.stackFrom(-1,
                new ItemStack[] {
                        AddUtils.equalItemStackFactory(new ArrayList<>(){{
                            add("SOUL_SAND");
                            add("SOUL_SOIL");
                        }})
                },
                new ItemStack[] {
                        AddUtils.randItemStackFactory(
                                new LinkedHashMap<>(){{
                                    put("QUARTZ",50);
                                    put("GOLD_NUGGET",25);
                                    put("NETHER_WART",10);
                                    put("BLAZE_POWDER",8);
                                    put("GLOWSTONE_DUST",5);
                                    put("GHAST_TEAR",2);
                                }}
                        )
                }
        ));
    }};
    public static final ArrayList<MachineRecipe> ORE_WASHER_RECIPE=new ArrayList<>(){{
        add(MachineRecipeUtils.stackFrom(-1,
                new ItemStack[]{SlimefunItems.SIFTED_ORE},
                new ItemStack[] {
                        AddUtils.randItemStackFactory(
                                new LinkedHashMap<>(){{
                                    put("IRON_DUST",1);
                                    put("GOLD_DUST",1);
                                    put("COPPER_DUST",1);
                                    put("TIN_DUST",1);
                                    put("ZINC_DUST",1);
                                    put("ALUMINUM_DUST",1);
                                    put("SILVER_DUST",1);
                                    put("MAGNESIUM_DUST",1);
                                    put("LEAD_DUST",1);
                                }}
                        ),
                        SlimefunItems.STONE_CHUNK
                }
                ));
        add(MachineRecipeUtils.stackFrom(-1,
                new ItemStack[]{SlimefunItems.PULVERIZED_ORE},
                new ItemStack[] {SlimefunItems.PURE_ORE_CLUSTER}
        ));
        add(MachineRecipeUtils.stackFrom(-1,
                new ItemStack[]{new ItemStack(Material.SAND,2)},
                new ItemStack[] {SlimefunItems.SALT}
                ));
    }};
    /**
     * stored Unshaped machine recipe pieces
     */
    public static final HashMap<RecipeType, List<MachineRecipe>> PROVIDED_UNSHAPED_RECIPES = new LinkedHashMap<>(){{
        put(RecipeType.GOLD_PAN,GOLDPAN_RECIPE);
        put(RecipeType.ORE_WASHER,ORE_WASHER_RECIPE);

    }};
    //总统计表
    public static final HashSet<RecipeType> RECIPE_TYPES = new LinkedHashSet<>();
    //废弃的表
    public static final HashSet<RecipeType> SUPPORTED_UNSHAPED_RECIPETYPE =new LinkedHashSet<>()    {{
        add(RecipeType.ENHANCED_CRAFTING_TABLE);
        add(RecipeType.MAGIC_WORKBENCH);
        add(RecipeType.ANCIENT_ALTAR);
        add(RecipeType.ARMOR_FORGE);
        add(RecipeType.COMPRESSOR);
        add(RecipeType.GRIND_STONE);
        add(RecipeType.ORE_CRUSHER);
        add(RecipeType.SMELTERY);
        if(Dependency.hasInfiniteExpansion){
            add(InfinityWorkbench.TYPE);
            add(MobDataInfuser.TYPE);
        }
        if(Dependency.hasNetwork){
            add(NetworkQuantumWorkbench.TYPE);
        }
        if(Dependency.hasNetworkExpansion){
            add(ExpansionWorkbench.TYPE);
        }
    }};
    //禁用某些读取 并换上自己的 比如某些傻逼多方块机器
    public static final HashSet<RecipeType> RECIPETYPE_BLACKLISTED = new HashSet<>(){{
        add(RecipeType.GOLD_PAN);
        add(RecipeType.ORE_WASHER);
        if(Dependency.hasInfiniteExpansion){
            add(VoidHarvester.TYPE);
        }

    }};
    //一般来说不能让玩家合成的
    public static final HashSet<RecipeType> RECIPETYPE_ILLEGAL=new HashSet<>(){{
        if(Dependency.hasInfiniteExpansion){
            add(VoidHarvester.TYPE);
        }
        add(RecipeType.NULL);
        add(RecipeType.MOB_DROP);
        add(RecipeType.BARTER_DROP);
        add(RecipeType.INTERACT);
        add(RecipeType.GEO_MINER);
    }};
    //禁用某些读取 并换上自己的 比如某些傻逼多方块机器
    public static final HashSet<MultiBlockMachine> MULTIBLOCK_BLACKLISTED = new HashSet<>(){{
    }};
    //检测全部为MultiBlockMachine的机器 并抓取配方
    public static final HashMap<MultiBlockMachine,List<MachineRecipe>> MULTIBLOCK_RECIPES = new LinkedHashMap<>();
    //检测存在TYPE成员的SlimefunItem，，或者也可以手动添加
    public static final HashMap<SlimefunItem,RecipeType> CUSTOM_RECIPETYPES = new LinkedHashMap<>(){{
    }};
    /**
     * stored shaped machine recipe pieces
     */
    public static final HashMap<RecipeType,List<MachineRecipe>> PROVIDED_SHAPED_RECIPES = new LinkedHashMap<>();
    //废弃的表
    public static final HashSet<RecipeType> SUPPORTED_SHAPED_RECIPETYPE= new HashSet<>(){{
        add(RecipeType.MULTIBLOCK);
    }};
    //注册为可识别icon的
    public static final HashMap<RecipeType,ItemStack> RECIPETYPE_ICON=new LinkedHashMap<>();
    public static final HashMap<SlimefunItem ,List<MachineRecipe>> MACHINE_RECIPELIST=new LinkedHashMap<>();
    public static List<MachineRecipe> getStackedRecipes(RecipeType type) {
        if(SUPPORTED_UNSHAPED_RECIPETYPE.contains(type)) {
            if(PROVIDED_UNSHAPED_RECIPES.get(type) == null|| PROVIDED_UNSHAPED_RECIPES.get(type).size() == 0) {
                initUnshapedRecipes(type);
            }
            return PROVIDED_UNSHAPED_RECIPES.get(type);
        }
        return  null;
    }
    static{
        long a=System.nanoTime();
        Debug.logger("配方供应器开始进行数据采集,预计耗时2秒");
        Debug.logger("期间tps可能会下降,请不要在供应器工作时进入服务器");
        Debug.logger("期间的报错信息均可忽略");
        MULTIBLOCK_BLACKLISTED.add((MultiBlockMachine) SlimefunItem.getById("ORE_WASHER"));
        MULTIBLOCK_RECIPES.put((MultiBlockMachine) SlimefunItem.getById("ORE_WASHER"),ORE_WASHER_RECIPE);
        MULTIBLOCK_BLACKLISTED.add((MultiBlockMachine) SlimefunItem.getById("AUTOMATED_PANNING_MACHINE"));
        MULTIBLOCK_RECIPES.put((MultiBlockMachine) SlimefunItem.getById("AUTOMATED_PANNING_MACHINE"),GOLDPAN_RECIPE);
        Debug.logger("读取全部配方中...");
        for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
            RecipeType recipeType = item.getRecipeType();
           // Debug.logger("RECIPE INFO :  "+item.toString()+"  "+item.getRecipe().length);
            if(!RECIPETYPE_BLACKLISTED.contains(recipeType)){
                if(RECIPE_TYPES.contains(recipeType)) {
                    PROVIDED_SHAPED_RECIPES.get(recipeType).add(MachineRecipeUtils.shapeFromRecipe(item));
                    PROVIDED_UNSHAPED_RECIPES.get(recipeType).add(MachineRecipeUtils.stackFromRecipe(item)) ;
                }else{
                    RECIPE_TYPES.add(recipeType);
                    PROVIDED_SHAPED_RECIPES.put(recipeType,new ArrayList<>(){{
                        add(MachineRecipeUtils.shapeFromRecipe(item));
                    }});
                    PROVIDED_UNSHAPED_RECIPES.put(recipeType,new ArrayList<>(){{
                        add(MachineRecipeUtils.stackFromRecipe(item));
                    }});
                }
            }
            //解析多方块机器
            if(recipeType==RecipeType.MULTIBLOCK) {

                if(item instanceof MultiBlockMachine ) {
                    if(!MULTIBLOCK_BLACKLISTED.contains(item)){
                    List<ItemStack[]>  recipeInputs= RecipeType.getRecipeInputList((MultiBlockMachine) item);
                    List<MachineRecipe> recipes=new ArrayList<>();
                    for(ItemStack[] recipeInput: recipeInputs) {
                        recipes.add(MachineRecipeUtils.stackFrom(0,recipeInput,new ItemStack[] {RecipeType.getRecipeOutputList((MultiBlockMachine) item,recipeInput)}));
                    }
                    MULTIBLOCK_RECIPES.put((MultiBlockMachine) item,recipes);
                    }
                }
            }
            //解析指定类型特殊机器配方类型
            try{
                Class<?> clazz=item.getClass();
                boolean hasRecipeType=true;
                try{
                    Field _hasType=clazz.getDeclaredField("TYPE");
                    _hasType.setAccessible(true);
                    Object _Type=_hasType.get(item);
                    if(_Type instanceof RecipeType&&!RECIPETYPE_BLACKLISTED.contains((RecipeType) _Type)){
                        CUSTOM_RECIPETYPES.put(item,(RecipeType) _Type);
                        //Debug.logger("detect certain field TYPE: "+item);
                    }
                }catch (Throwable e){
                    hasRecipeType=false;
                }
            }catch (Throwable e){
                Debug.logger("generate an exception while registering recipes! :"+e.getMessage());
            }
        }
        Debug.logger("初始化配方类型显示界面...");
        //初始化对应物品
        for (RecipeType type:RECIPE_TYPES){
            try{
            ItemStack it=type.toItem();
            String ns=type.getKey().getNamespace().toUpperCase();
            String id=type.getKey().getKey().toUpperCase();
            if(it!=null){
                SlimefunItemStack sfit=new SlimefunItemStack(id+"_ICON",AddUtils.addLore(it,"","&7配方类型:"+ns+":"+id));
                RECIPETYPE_ICON.put(type, sfit  );
            }else{
                RECIPETYPE_ILLEGAL.add(type);
            }
            }catch (Throwable e){
                RECIPETYPE_ILLEGAL.add(type);
            }
        }
        Debug.logger("初始化机器类型显示界面...");
        //解析含有recipe的机器类型
        for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
            try{
                List<MachineRecipe> recipes=new ArrayList<>();
                if (item instanceof AContainer){
                    recipes=((AContainer)item).getMachineRecipes();

                }else{
                    Class<?> clazz=item.getClass();
                    switch (1){
                        case 1:
                            Object machineRecipes=null;
                            String methodName=null;
                            String infinityMachineBlockRecipe="io.github.mooy1.infinityexpansion.infinitylib.machines.MachineBlockRecipe";
                            try{
                                if(methodName==null){
                                    try{
                                        Method getMR=clazz.getDeclaredMethod("getMachineRecipes");
                                        getMR.setAccessible(true);
                                        machineRecipes=getMR.invoke(item);
                                        methodName="getMachineRecipes() method";
                                    }catch (Throwable e){
                                        machineRecipes=null;
                                    }
                                }
                                if(methodName==null){
                                    try{
                                        Field getMR=clazz.getDeclaredField("recipes");
                                        getMR.setAccessible(true);
                                        machineRecipes=getMR.get(item);
                                        methodName="recipes field";
                                    }catch (Throwable e1){
                                        machineRecipes=null;
                                    }
                                }
                                if(machineRecipes!=null) {
                                    if (machineRecipes instanceof List) {
                                        for (Object machineRecipe : (List) machineRecipes) {
                                            //压缩配方,用于提供给机器
                                            if (machineRecipe instanceof MachineRecipe) {
                                                recipes.add((MachineRecipe) machineRecipe);
                                            } else if (machineRecipe instanceof ItemStack) {//some of sf's shit machine
                                                if (item instanceof Composter) {
                                                    int len = ((List<?>) machineRecipes).size();
                                                    for (int i = 0; i < len; i += 2) {
                                                        recipes.add(MachineRecipeUtils.stackFrom(4, new ItemStack[]{(ItemStack) ((List<?>) machineRecipes).get(i)}, new ItemStack[]{(ItemStack) ((List<?>) machineRecipes).get(i + 1)}));
                                                    }
                                                    break;
                                                } else if (item instanceof Crucible) {
                                                    break;
                                                } else break;
                                            } else if (machineRecipe instanceof AltarRecipe) {
                                                List<ItemStack> inp = ((AltarRecipe) machineRecipe).getInput();
                                                inp.add(4, ((AltarRecipe) machineRecipe).getCatalyst());
                                                MachineRecipe rp = new MachineRecipe(9, inp.toArray(new ItemStack[inp.size()]), new ItemStack[]{((AltarRecipe) machineRecipe).getOutput()});
                                            } else if (machineRecipe.getClass().getName().equals(infinityMachineBlockRecipe)){
                                                break;
                                            }
                                            else{
                                                throw new ClassCastException("wrong "+methodName+ " return type , " +
                                                    "List of " + machineRecipe.getClass().getName());
                                            }
                                        }
                                        break;
                                    }
                                }
                            }catch (ClassCastException e){
                                Debug.logger("an Error occurs while invoking "+item.toString()+" : "+e.getMessage());

                            }
                            catch (Throwable e){
                                e.getMessage();
                            }
                        default:

                    }

                }
                if(!recipes.isEmpty()){
                    List<MachineRecipe> result=new ArrayList<>();
                    for(MachineRecipe machineRecipe : recipes) {
                        result.add(MachineRecipeUtils.stackFromMachine(machineRecipe));
                    }
                    MACHINE_RECIPELIST.put(item,result);
                }

            }catch (Throwable e){
                Debug.logger("generate unexpected exception while registering recipes! :"+e.getMessage());
                e.printStackTrace();
            }
        }
        Debug.logger("配方供应器工作完成, 耗时 "+(System.nanoTime()-a)+ " 纳秒");

    }

    private static void initUnshapedRecipes(RecipeType type) {
        List<MachineRecipe> recipes_test = PROVIDED_UNSHAPED_RECIPES.get(type);
        if(recipes_test == null|| recipes_test.size() == 0|| SUPPORTED_UNSHAPED_RECIPETYPE.contains(type)) {
                PROVIDED_UNSHAPED_RECIPES.put(type,new ArrayList<>());
            List<MachineRecipe> recipes = PROVIDED_UNSHAPED_RECIPES.get(type);
            for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
                RecipeType recipeType = item.getRecipeType();
                if (recipeType==type ) {

                    recipes.add(MachineRecipeUtils.stackFromRecipe(item)) ;
                }
            }

        }
    }
    //not finished yet
    public static void initMachinieRecipeWithRecipeType(List<MachineRecipe> machineRecipes,RecipeType... craftType){
        if(machineRecipes.isEmpty()||craftType!=null) {
            if(craftType.length<=0){
                return ;
            }
            else if(craftType.length==1){
                machineRecipes = RecipeSupporter.getStackedRecipes(craftType[0]);

            }else{
                machineRecipes=new ArrayList<>();
                for(RecipeType rt : craftType){
                    if(rt!=null)
                        machineRecipes.addAll(RecipeSupporter.getStackedRecipes(rt));
                }
            }
            if(machineRecipes==null) {
                machineRecipes = new ArrayList<>();
            }
        }

    }
    public static ItemStack getRecipeTypeIcon(RecipeType type){
        ItemStack icon=type.toItem();
        if(icon==null)return new ItemStack(Material.AIR);
        return icon;
    }

    public static void invokeMachineRecipes(){

    }
}
