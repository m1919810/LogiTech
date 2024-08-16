package me.matl114.logitech.Utils;

import com.ytdd9527.networks.expansion.core.item.machine.manual.ExpansionWorkbench;
import io.github.mooy1.infinityexpansion.items.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.items.machines.VoidHarvester;
import io.github.mooy1.infinityexpansion.items.mobdata.MobDataInfuser;
import io.github.sefiraat.networks.slimefun.network.NetworkQuantumWorkbench;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.altar.AltarRecipe;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.Composter;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.Crucible;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.Dependency;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.SlimefunItem.Machines.AbstractAdvancedProcessor;
import me.matl114.logitech.SlimefunItem.Machines.AbstractMachine;
import me.matl114.logitech.SlimefunItem.Machines.AbstractProcessor;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class RecipeSupporter {
    public static void init(){

    }
    public static final SlimefunAddon PLUGIN= MyAddon.getInstance();
//    public static final RecipeType VANILLA_MERCHANT=new RecipeType(AddUtils.getNameKey("vanilla_merchant"),
//            new CustomItemStack(Material.PLAYER_HEAD,"交易内容","","村民交易项目"));
    private static final ArrayList<MachineRecipe> GOLDPAN_RECIPE = new ArrayList<>(){{
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
    private static final ArrayList<MachineRecipe> ORE_WASHER_RECIPE=new ArrayList<>(){{
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
    private static final ArrayList<MachineRecipe> TABLE_SAW_RECIPE=new ArrayList<>(){{
        for (Material log : Tag.LOGS.getValues()) {
            Optional<Material> planks = AddUtils.getPlanks(log);

            if (planks.isPresent()) {

                add(MachineRecipeUtils.stackFrom(-1,Utils.array(new ItemStack(log)),Utils.array(new ItemStack(planks.get(),8))));
            }
        }

        for (Material plank : Tag.PLANKS.getValues()) {
            add(MachineRecipeUtils.stackFrom(-1,Utils.array(new ItemStack(plank)),Utils.array(new ItemStack(Material.STICK,4))));
        }
    }};

    /**
     * stored shaped machine recipe pieces
     * be careful ,ticks of these MachineRecipe are -1 ! not able to use directly in machine
     */
    public static final HashMap<RecipeType,List<MachineRecipe>> PROVIDED_SHAPED_RECIPES = new LinkedHashMap<>();
    /**
     * stored Unshaped machine recipe pieces
     * be careful ,ticks of these MachineRecipe are -1 ! not able to use directly in machine
     */
    public static final HashMap<RecipeType, List<MachineRecipe>> PROVIDED_UNSHAPED_RECIPES = new LinkedHashMap<>(){{


    }};
    //总统计表
    public static final HashSet<RecipeType> RECIPE_TYPES = new LinkedHashSet<>();
    //是和多方块绑定的配方
    public static final HashMap<RecipeType,MultiBlockMachine> MULTIBLOCK_RECIPETYPES=new LinkedHashMap<>();
    //检测存在TYPE成员的SlimefunItem，，或者也可以手动添加
    public static final HashMap<SlimefunItem,RecipeType> CUSTOM_RECIPETYPES = new LinkedHashMap<>(){{
    }};
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
    //废弃的表
    public static final HashSet<RecipeType> SUPPORTED_SHAPED_RECIPETYPE= new HashSet<>(){{
        add(RecipeType.MULTIBLOCK);
    }};
    //被禁用的配方
    public static final HashSet<RecipeType> BLACKLIST_RECIPETYPES = new HashSet<>(){{
        if(Dependency.hasInfiniteExpansion){
            add(VoidHarvester.TYPE);
        }

    }};
    //一般来说不能让玩家合成的
    public static final HashSet<RecipeType> ILLEGAL_RECIPETYPES =new HashSet<>(){{
        if(Dependency.hasInfiniteExpansion){
            add(VoidHarvester.TYPE);
        }
        add(RecipeType.NULL);
        add(RecipeType.MOB_DROP);
        add(RecipeType.BARTER_DROP);
        add(RecipeType.INTERACT);
        add(RecipeType.GEO_MINER);
    }};


    //被禁用的多方块
    public static final HashSet<MultiBlockMachine> BLACKLIST_MULTIBLOCK = new HashSet<>(){{
    }};
    //检测全部为MultiBlockMachine的机器 并抓取配方
    public static final HashMap<MultiBlockMachine,List<MachineRecipe>> MULTIBLOCK_RECIPES = new LinkedHashMap<>();


    //注册为可识别icon的
    public static final HashMap<RecipeType,ItemStack> RECIPETYPE_ICON=new LinkedHashMap<>();
    //堆叠机器黑名单
    public static final HashSet<SlimefunItem> BLACKLIST_STACKMACHINE=new LinkedHashSet<>();
    //读取机器类黑名单 防止循环读取
    public static final HashSet<Class> BLACKLIST_MACHINECLASS=new HashSet<>(){{
       add(AbstractMachine.class);
    }};
    //读取的全部机器配方
    public static final HashMap<SlimefunItem ,List<MachineRecipe>> MACHINE_RECIPELIST=new LinkedHashMap<>();
    //记录机器类型和机器耗电
    public static final HashMap<SlimefunItem,Integer> STACKMACHINE_LIST=new LinkedHashMap<>();
    public static List<MachineRecipe> getStackedRecipes(RecipeType type) {
        if(SUPPORTED_UNSHAPED_RECIPETYPE.contains(type)) {
            if(PROVIDED_UNSHAPED_RECIPES.get(type) == null|| PROVIDED_UNSHAPED_RECIPES.get(type).size() == 0) {
                initUnshapedRecipes(type);
            }
            return PROVIDED_UNSHAPED_RECIPES.get(type);
        }
        return  null;
    }

    /**
     * invoke recursively
     * @param target
     * @param mod
     * @return
     */
    public static  Object invokeRecursively(Object target,Settings mod,String declared){
        return invokeRecursively(target,target.getClass(),mod,declared);
    }
    public static  Object invokeRecursively(Object target,Class clazz,Settings mod,String decleared){
        try{
            switch (mod){
                case FIELD:
                    Field _hasType=clazz.getDeclaredField(decleared);
                    _hasType.setAccessible(true);
                    return  _hasType.get(target);
                case METHOD:
                    Method _hasMethod=clazz.getDeclaredMethod(decleared);

                    _hasMethod.setAccessible(true);
                    return _hasMethod.invoke(target);
            }
        }catch (Throwable e){
        }
        clazz=clazz.getSuperclass();
        if(clazz==null){
            return null;
        }else {
            return invokeRecursively(target,clazz,mod,decleared);
        }
    }
    public static Object invokeMethodRecursively(Object target,Class clazz,Settings mod,String declared,Object ... args){
        return null;
    }
    static{
        long a=System.nanoTime();
        Debug.logger("配方供应器开始进行数据采集,预计耗时2秒");
        Debug.logger("期间tps可能会下降,请不要在供应器工作时进入服务器");
        Debug.logger("期间的报错信息均可忽略");
        //this should be recipes with problems ,but we eventually replace them after search
//        HashSet<RecipeType> recipeBlackList=new HashSet<>(){{
//            add(RecipeType.GOLD_PAN);
//            add(RecipeType.ORE_WASHER);}
//        };
//
//        HashSet<MultiBlockMachine> multiblockBlackList=new HashSet<>(){{
//            add((MultiBlockMachine) SlimefunItems.ORE_WASHER.getItem());
//            add((MultiBlockMachine) SlimefunItems.AUTOMATED_PANNING_MACHINE.getItem());
//            add((MultiBlockMachine) SlimefunItems.TABLE_SAW.getItem());
//        }
//        };


        Debug.logger("读取全部配方中...");
        RECIPE_TYPES.add(BukkitUtils.VANILLA_CRAFTTABLE);
        PROVIDED_SHAPED_RECIPES.put(BukkitUtils.VANILLA_CRAFTTABLE,new ArrayList<>());
        PROVIDED_UNSHAPED_RECIPES.put(BukkitUtils.VANILLA_CRAFTTABLE,new ArrayList<>());
        RECIPE_TYPES.add(BukkitUtils.VANILLA_FURNACE);
        PROVIDED_UNSHAPED_RECIPES.put(BukkitUtils.VANILLA_FURNACE,new ArrayList<>());
        PROVIDED_SHAPED_RECIPES.put(BukkitUtils.VANILLA_FURNACE,new ArrayList<>());
//        RECIPE_TYPES.add(VANILLA_MERCHANT);
//        PROVIDED_UNSHAPED_RECIPES.put(VANILLA_MERCHANT,new ArrayList<>());
//        PROVIDED_SHAPED_RECIPES.put(VANILLA_MERCHANT,new ArrayList<>());
        Iterator<Recipe> recipeIterator = PLUGIN.getJavaPlugin().getServer().recipeIterator();
        while (recipeIterator.hasNext()) {
            Recipe next = recipeIterator.next();
            if (next instanceof ShapedRecipe) {
                Set<Map.Entry<Character, ItemStack>> entries = ((ShapedRecipe) next).getIngredientMap().entrySet();
                List<ItemStack> input = new ArrayList<>(entries.size());
                for (Map.Entry<Character, ItemStack> entry : entries) {
                    input.add(entry.getValue());
                }
                PROVIDED_SHAPED_RECIPES.get(BukkitUtils.VANILLA_CRAFTTABLE).add(MachineRecipeUtils.shapeFrom(-1,input.toArray(new ItemStack[0]),Utils.array(next.getResult())));
                PROVIDED_UNSHAPED_RECIPES.get(BukkitUtils.VANILLA_CRAFTTABLE).add(MachineRecipeUtils.stackFrom(-1,input.toArray(new ItemStack[0]),Utils.array(next.getResult())));

            } else if (next instanceof ShapelessRecipe) {
                List<ItemStack> input = ((ShapelessRecipe) next).getIngredientList();
                PROVIDED_SHAPED_RECIPES.get(BukkitUtils.VANILLA_CRAFTTABLE).add(MachineRecipeUtils.shapeFrom(-1,input.toArray(new ItemStack[input.size()]),Utils.array(next.getResult())));
                PROVIDED_UNSHAPED_RECIPES.get(BukkitUtils.VANILLA_CRAFTTABLE).add(MachineRecipeUtils.stackFrom(-1,input.toArray(new ItemStack[input.size()]),Utils.array(next.getResult())));
            }else if(next instanceof FurnaceRecipe) {
                ItemStack input = ((FurnaceRecipe) next).getInput();
                PROVIDED_SHAPED_RECIPES.get(BukkitUtils.VANILLA_FURNACE).add(MachineRecipeUtils.shapeFrom(-1,Utils.array(input),Utils.array(next.getResult())));
                PROVIDED_UNSHAPED_RECIPES.get(BukkitUtils.VANILLA_FURNACE).add(MachineRecipeUtils.stackFrom(-1,Utils.array(input),Utils.array(next.getResult())));
            }
//            else if(next instanceof MerchantRecipe){
//                List<ItemStack> input=((MerchantRecipe)next).getIngredients();
//                PROVIDED_SHAPED_RECIPES.get(VANILLA_MERCHANT).add(MachineRecipeUtils.shapeFrom(-1,input.toArray(new ItemStack[input.size()]),Utils.array(next.getResult())));
//                PROVIDED_UNSHAPED_RECIPES.get(VANILLA_MERCHANT).add(MachineRecipeUtils.stackFrom(-1,input.toArray(new ItemStack[input.size()]),Utils.array(next.getResult())));
//            }
        }
        for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
            RecipeType recipeType = item.getRecipeType();
            //过会解析
            if(!RECIPE_TYPES.contains(recipeType)){
                RECIPE_TYPES.add(recipeType);
                PROVIDED_SHAPED_RECIPES.put(recipeType,new ArrayList<>());
                PROVIDED_UNSHAPED_RECIPES.put(recipeType,new ArrayList<>());
            }
        }
        //先解析当前的
        for(RecipeType recipeType :RECIPE_TYPES){
            if(!BLACKLIST_RECIPETYPES.contains(recipeType)) {//not in black list
                SlimefunItem sfitem=recipeType.getMachine();
                if (sfitem instanceof MultiBlockMachine) {
                    List<ItemStack[]>  recipeInputs= RecipeType.getRecipeInputList((MultiBlockMachine) sfitem);
                    List<MachineRecipe> recipes=new ArrayList<>();
                    List<MachineRecipe> shapedrecipes=new ArrayList<>();
                    for(ItemStack[] recipeInput: recipeInputs) {
                        ItemStack recipeOutput=RecipeType.getRecipeOutputList((MultiBlockMachine) sfitem,recipeInput);
                        SlimefunItem resultSfitem=SlimefunItem.getByItem(recipeOutput);
                        if(resultSfitem !=null) {//如果是由物品注册来的
                            recipes.add(MachineRecipeUtils.stackFromRecipe(resultSfitem));
                            shapedrecipes.add(MachineRecipeUtils.shapeFromRecipe(resultSfitem));
                        }else{
                            recipes.add(MachineRecipeUtils.stackFrom(0,recipeInput,new ItemStack[] {RecipeType.getRecipeOutputList((MultiBlockMachine) sfitem,recipeInput)}));
                            shapedrecipes.add(MachineRecipeUtils.shapeFrom(0,recipeInput,new ItemStack[] {RecipeType.getRecipeOutputList((MultiBlockMachine) sfitem,recipeInput)}));
                        }
                    }
                    //记录
                    MULTIBLOCK_RECIPETYPES.put(recipeType,(MultiBlockMachine) sfitem);
                    //将多方块的配方注册为
                    PROVIDED_SHAPED_RECIPES.put(recipeType,shapedrecipes);
                    PROVIDED_UNSHAPED_RECIPES.put(recipeType,recipes);
                    //顺便注册了多方块
                    MULTIBLOCK_RECIPES.put((MultiBlockMachine) sfitem,recipes);
                }
            }
        }
        //非多方块的类型,基本上就是普通物品的配方注册，读取一遍
        for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()){
            RecipeType recipeType = item.getRecipeType();
            if((!BLACKLIST_RECIPETYPES.contains(recipeType))&&(!MULTIBLOCK_RECIPETYPES.containsKey(recipeType))){
                PROVIDED_SHAPED_RECIPES.get(recipeType).add(MachineRecipeUtils.shapeFromRecipe(item));
                PROVIDED_UNSHAPED_RECIPES.get(recipeType).add(MachineRecipeUtils.stackFromRecipe(item)) ;
            }
            //解析多方块机器
            if(recipeType==RecipeType.MULTIBLOCK) {
                if(item instanceof MultiBlockMachine ) {
                    if(!BLACKLIST_MULTIBLOCK.contains(item)&&(!MULTIBLOCK_RECIPES.containsKey(item))){
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
                Object _Type=invokeRecursively(item,Settings.FIELD,"TYPE");
                if(_Type instanceof RecipeType&&!BLACKLIST_RECIPETYPES.contains((RecipeType) _Type)){
                    CUSTOM_RECIPETYPES.put(item,(RecipeType) _Type);
                    //Debug.logger("detect certain field TYPE: "+item);
                }

            }catch (Throwable e){
                //Debug.logger("generate an exception while registering recipes! :"+e.getMessage());
            }
        }
        //强制替换有问题的配方
        PROVIDED_UNSHAPED_RECIPES.put(RecipeType.GOLD_PAN,GOLDPAN_RECIPE);
        PROVIDED_UNSHAPED_RECIPES.put(RecipeType.ORE_WASHER,ORE_WASHER_RECIPE);
        PROVIDED_SHAPED_RECIPES.put(RecipeType.GOLD_PAN,GOLDPAN_RECIPE);
        PROVIDED_SHAPED_RECIPES.put(RecipeType.ORE_WASHER,ORE_WASHER_RECIPE);

        MULTIBLOCK_RECIPES.put((MultiBlockMachine)SlimefunItems.ORE_WASHER.getItem(),ORE_WASHER_RECIPE);
        MULTIBLOCK_RECIPES.put((MultiBlockMachine) SlimefunItems.AUTOMATED_PANNING_MACHINE.getItem(),GOLDPAN_RECIPE);
        MULTIBLOCK_RECIPES.put((MultiBlockMachine) SlimefunItems.TABLE_SAW.getItem(),TABLE_SAW_RECIPE);
        Debug.logger("全部配方读取完成");
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
                ILLEGAL_RECIPETYPES.add(type);
            }
            }catch (Throwable e){
                ILLEGAL_RECIPETYPES.add(type);
            }
        }
        Debug.logger("初始化机器类型显示界面...");
        Debug.logger("警告: 如果你发现有机器没有检测到,说明他们使用了不支持的配方类型");

        //解析含有recipe的机器类型
        for (SlimefunItem item : Slimefun.getRegistry().getEnabledSlimefunItems()) {
            try{
                List<MachineRecipe> recipes=new ArrayList<>();
                if (item instanceof AContainer){
                    recipes=((AContainer)item).getMachineRecipes();
                }
                else{
                    boolean blst=false;
                    //if in class blacklist
                    for (Class classt:BLACKLIST_MACHINECLASS){
                        if(classt.isInstance(item)){
                            blst=true;
                            break;
                        }
                    }
                    if(!blst){
                        Class<?> clazz=item.getClass();
                        switch (1){
                            case 1:
                                Object machineRecipes=null;
                                String methodName=null;
                                String infinityMachineBlockRecipe=".MachineBlockRecipe";
                                String infinitylib="infinitylib";
                                try{
                                    if(methodName==null){
                                        machineRecipes=invokeRecursively(item,Settings.METHOD,"getMachineRecipes");
                                        if(machineRecipes!=null){
                                            methodName="getMachineRecipes() method";
                                        }
                                    }
                                    if(methodName==null){
                                        machineRecipes=invokeRecursively(item,Settings.FIELD,"recipes");
                                        if(machineRecipes!=null){
                                            methodName="getMachineRecipes() method";
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
                                                    recipes.add(rp);
                                                } else if (machineRecipe.getClass().getName().endsWith(infinityMachineBlockRecipe)&&
                                                        machineRecipe.getClass().getName().contains(infinitylib)){
                                                    MachineRecipe ip=resolveInfinityRecipe(machineRecipe,item);
                                                    if(ip!=null){
                                                        recipes.add(ip);
                                                    }
                                                }
                                                else{
                                                    throw new ClassCastException("wrong "+methodName+ " return type , " +
                                                            "List of " + machineRecipe.getClass().getName());
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                                catch (Throwable e){
                                    e.getMessage();
                                }
                            default:

                        }
                    }

                }
                if(!recipes.isEmpty()){
                    List<MachineRecipe> result=new ArrayList<>();
                    MachineRecipe res=null;
                    for(MachineRecipe machineRecipe : recipes) {
                        if(machineRecipe instanceof MGeneratorRecipe){
                            res=machineRecipe;

                        }//else if(machineRecipe instanceof CustomMachineRecipe){
                        //将3mjkx的玩意转一下
                        // continue;
                        //}
                        else {
                            res=MachineRecipeUtils.stackFromMachine(machineRecipe);
                        }

                        result.add(res);

                    }
                    MACHINE_RECIPELIST.put(item,result);
                }

            }catch (Throwable e){
                Debug.logger("generate unexpected exception while registering recipes! :"+e.getMessage());
                e.printStackTrace();
            }
        }
        //invok 进机器配方 测试
//        SlimefunItem compressor= SlimefunItem.getById("ELECTRIC_PRESS");
//        try{
//            Class clazz= AContainer.class;
//            Field getMR=clazz.getDeclaredField("recipes");
//            getMR.setAccessible(true);
//            List<MachineRecipe> machineRecipes=(List<MachineRecipe>) getMR.get(compressor);
//            machineRecipes.addAll(PROVIDED_UNSHAPED_RECIPES.get(RecipeType.COMPRESSOR));
//        }catch (Throwable e){
//            Debug.logger("generate unexpected exception while invoking compressor! :"+e.getMessage());
//            e.printStackTrace();
//        }
        //解析机器用电,并提供给StackMachine
        //到时候还需要解析生成器配方和用电
        for(SlimefunItem item:MACHINE_RECIPELIST.keySet()){
            //黑名单 本附属非processor的machine 和 高级processor
            if(!(item instanceof AbstractMachine&&(!(item instanceof AbstractProcessor)))){
                int energyComsumption=0;
                if(item instanceof AContainer container){
                    energyComsumption=container.getEnergyConsumption();
                }else{
                    Class<?> clazz=item.getClass();
                    Object energy=null;
                    String methodName=null;
                    try{
                        if(methodName==null){
                            energy=invokeRecursively(item,clazz,Settings.METHOD,"getEnergyConsumption");
                            if(energy!=null){
                                methodName="getEnergyConsumption() method";
                            }
                        }
                        if(methodName==null){
                            energy=invokeRecursively(item,Settings.FIELD,"EnergyConsumption");
                            if(energy!=null){
                                methodName="EnergyConsumption field";
                            }
                        }
                        if(methodName!=null){
                            energy=invokeRecursively(item,Settings.FIELD,"energyConsumedPerTick");
                            if(energy!=null){
                                methodName="energyConsumedPerTick field";
                            }
                        }
                        if(methodName!=null){
                            energy=invokeRecursively(item,Settings.FIELD,"energyPerTick");
                            if(energy!=null){
                                methodName="energyPerTick field";
                            }
                        }
                        if (methodName!=null) {
                            if(energy instanceof Integer e)
                                energyComsumption=e;
                        }
                    }catch (Throwable e){

                    }
                }
                STACKMACHINE_LIST.put(item,energyComsumption);
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
