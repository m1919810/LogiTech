package me.matl114.logitech.Utils;

import com.google.common.base.Preconditions;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.chat.ChatInput;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.matl114.logitech.Language;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.SlimefunItem.AddItem;
import me.matl114.logitech.Utils.UtilClass.ItemClass.*;
import me.matl114.logitech.Utils.UtilClass.FunctionalClass.LoreDecorator;
import me.matl114.logitech.Utils.UtilClass.FunctionalClass.StringDecorator;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddUtils {
    public static final String ADDON_NAME="逻辑工艺";
    public static final String ADDON_ID="LOGITECH";
    public static final SlimefunAddon ADDON_INSTANCE=MyAddon.getInstance();
    public static boolean USE_IDDECORATOR=true;
    private static final double SF_TPS = 20.0 / (double) Slimefun.getTickerTask().getTickRate();
    private static final DecimalFormat FORMAT = new DecimalFormat("###,###,###,###,###,###.#");
    private static Random random=new Random();
    public static String formatDouble(double s){
        return FORMAT.format(s);
    }
    public static String idDecorator(String b){
        if(USE_IDDECORATOR){
            return ADDON_ID+"_"+b;
        }
        else return b;
    }
    public static NamespacedKey getNameKey(String str) {
        return new NamespacedKey(MyAddon.getInstance(),str);
    }
    public static String desc(String str) {
        return "&7" + str;
    }
    public static String addonTag(String str) {
        return "&3"+ADDON_NAME+" " + str;
    }
    public static final String[] COLOR_MAP=new String[]{"&0","&1","&2","&3","&4","&5","&6","&7","&8","&9","&a","&b","&c","&d","&e","&f"};
    public static String resolveRGB(int rgb){
        if(rgb>16777216){
            rgb=16777216;
        }
        else if (rgb<0){
            rgb=0;
        }
        String prefix="";
        for(int i=0;i<6;i++){
            int r=rgb%16;
            rgb=rgb/16;
            prefix=COLOR_MAP[r]+prefix;
        }
        prefix="&x"+prefix;
        return prefix;
    }

    public static String resolveRGB(String rgb) throws IllegalArgumentException {
        if(rgb.length()!=6){
            throw new IllegalArgumentException("Invalid RGB String");
        }
        String prefix="&x";
        for (int i=0;i<6;i++){
            prefix=prefix+"&"+rgb.substring(i,i+1);
        }
        return prefix;
    }
    public static int rgb2int(String rgb) throws IllegalArgumentException{
        if(rgb.length()!=6){
            throw new IllegalArgumentException("Invalid RGB String");
        }
        int value=0;
        for (int i=0;i<6;i++){
            char c=rgb.charAt(i);
            if(Character.isDigit(c)){
                value=value*16+(c-'0');
            }
            else if(c>='a'&&c<='f'){
                value=value*16+(c-'a'+10);
            }
            else if(c>='A'&&c<='F'){
                value=value*16+(c-'A'+10);
            }
            else throw new IllegalArgumentException("Invalid RGB String");
        }
        return value;
    }
    public static final int START_CODE=rgb2int("eb33eb");
            //15409899;
    public static final int END_CODE=rgb2int("970097");
    public static String color(String str){
        return resolveRGB(START_CODE)+str;
    }
    public static String colorful(String str) {
        int len=str.length()-1;
        if(len<=0){
            return resolveRGB(START_CODE)+str;
        }
        else{
            int start=START_CODE;
            int end=END_CODE;
            int[] rgbs=new int[9];
            for(int i=0;i<3;++i){
                rgbs[i]=start%256;
                rgbs[i+3]=end%256;
                rgbs[i+6]=rgbs[i+3]-rgbs[i];
                start=start/256;
                end=end/256;
            }
            String str2="";
            for(int i=0;i<=len;i++){
                str2=str2+resolveRGB(START_CODE+65536*((rgbs[8]*i)/len)+256*((rgbs[7]*i)/len)+((rgbs[6]*i)/len))+str.substring(i,i+1);
            }
            return str2;

        }
    }
    public static final ItemStack[] NULL_RECIPE=new ItemStack[]{null,null,null,null,null,null,null,null,null} ;
    public static final ItemMeta NULL_META=null;
    public enum Theme{
        NONE(
                ((a)->{return a;}),
                ((a)->{return a;})
        ),
        DEFAULT(
                ((a)->{return a;}),
                ((List<String> a)->{{
                    List<String> finallist=new ArrayList<>() ;
                    for(String i:a){
                        finallist.add(desc(i));
                    }
                    return finallist;
                }
                })
        ),
        ITEM1(
                AddUtils::color,
                ((List<String> a)->{{
                        List<String> finallist=new ArrayList<>() ;
                        for(String i:a){
                            finallist.add(desc(i));
                        }
                        //减少开销
//                        finallist.add("");
//                        finallist.add(addonTag(Language.get("Theme.ITEM1.Name")));
                        return finallist;
                }
                })
        ),
        ITEM2(
                AddUtils::colorful,
                ((List<String> a)->{{
                    List<String> finallist=new ArrayList<>() ;
                    for(String i:a){
                        finallist.add(desc(i));
                    }
                    //减少开销
                        finallist.add("");
                        finallist.add(addonTag(Language.get("Theme.ITEM1.Name")));
                    return finallist;
                }
                })
        ),
        MACHINE1(
                AddUtils::colorful,
                ((List<String> a)->{{
                    List<String> finallist=new ArrayList<>() ;
                    for(String i:a){
                        finallist.add(desc(i));
                    }
                    finallist.add("");
                    finallist.add(addonTag(Language.get("Theme.MACHINE1.Name")));
                    return finallist;
                }
                })
        ),
        MACHINE2(
                AddUtils::colorful,
                ((List<String> a)->{{
                    List<String> finallist=new ArrayList<>() ;
                    for(String i:a){
                        finallist.add(desc(i));
                    }
                    finallist.add("");
                    finallist.add(addonTag(Language.get("Theme.MACHINE2.Name")));
                    return finallist;
                }
                })
        ),
        MULTIBLOCK1(
                AddUtils::colorful,
                ((List<String> a)->{{
                    List<String> finallist=new ArrayList<>() ;
                    for(String i:a){
                        finallist.add(desc(i));
                    }
                    finallist.add("");
                    finallist.add(addonTag(Language.get("Theme.MULTIBLOCK1.Name")));
                    return finallist;
                }
                })
        ),
        MULTIBLOCK2(
                AddUtils::colorful,
                ((List<String> a)->{{
                    List<String> finallist=new ArrayList<>() ;
                    for(String i:a){
                        finallist.add(desc(i));
                    }
                    finallist.add("");
                    finallist.add(addonTag(Language.get("Theme.MULTIBLOCK2.Name")));
                    return finallist;
                }
                })
        ),
        MANUAL1(
                AddUtils::colorful,
                ((List<String> a)->{{
                    List<String> finallist=new ArrayList<>() ;
                    for(String i:a){
                        finallist.add(desc(i));
                    }
                    finallist.add("");
                    finallist.add(addonTag(Language.get("Theme.MANUAL1.Name")));
                    return finallist;
                }
                })
        ),
        ADVANCED1(
                AddUtils::colorful,
                ((List<String> a)->{{
                    List<String> finallist=new ArrayList<>() ;
                    for(String i:a){
                        finallist.add(desc(i));
                    }
                    finallist.add("");
                    finallist.add(addonTag(Language.get("Theme.ADVANCED1.Name")));
                    return finallist;
                }
                })
        ),
        CARGO1(
                AddUtils::colorful,
                ((List<String> a)->{{
                    List<String> finallist=new ArrayList<>() ;
                    for(String i:a){
                        finallist.add(desc(i));
                    }
                    finallist.add("");
                    finallist.add(addonTag(Language.get("Theme.CARGO1.Name")));
                    return finallist;
                }
                })
        ),
        BENCH1(
                AddUtils::colorful,
                ((List<String> a)->{{
                    List<String> finallist=new ArrayList<>() ;
                    for(String i:a){
                        finallist.add(desc(i));
                    }
                    finallist.add("");
                    finallist.add(addonTag(Language.get("Theme.BENCH1.Name")));
                    return finallist;
                }
                })
        ),
        CATEGORY(
                ((a)->{return a;}),
                ((List<String> a)->{{
            List<String> finallist=new ArrayList<>() ;
            //finallist.add("");
            for(String i:a){
                finallist.add(desc(i));
            }
            finallist.add("");
            finallist.add(Language.get("Theme.CATEGORY.Name"));
            return finallist;
        }
        })
        ),
        CATEGORY2(
                ((a)->{return a;}),
                ((List<String> a)->{{
            List<String> finallist=new ArrayList<>() ;
            //finallist.add("");
            for(String i:a){
                finallist.add(desc(i));
            }
            finallist.add("");
            finallist.add(Language.get("Theme.CATEGORY2.Name"));
            return finallist;
        }
        })),
        INFO1(
                ((a)->{return a;}),
                ((List<String> a)->{{
                    List<String> finallist=new ArrayList<>() ;
                    for(String i:a){
                        finallist.add(desc(i));
                    }
                    finallist.add("");
                    finallist.add(Language.get("Theme.INFO1.Name"));
                    return finallist;
                }
                })
        ),
        MENU1(
                ((a)->{return a;}),
                ((List<String> a)->{{
            List<String> finallist=new ArrayList<>() ;
            for(String i:a){
                finallist.add(desc(i));
            }
            finallist.add("");
            finallist.add(Language.get("Theme.MENU1.Name"));
            return finallist;
        }
        })
                )
        ;
        private final StringDecorator NAME_DEC;
        private final LoreDecorator  LORE_DEC;
        Theme(StringDecorator nameDec, LoreDecorator loreDec){
            NAME_DEC=nameDec;
            LORE_DEC=loreDec;
        }

    }
    public static  SlimefunItemStack themed(String id, Material itemStack, String name, String... lore){
        return themed(id,new ItemStack(itemStack),name,lore);
    }
    public static  SlimefunItemStack themed(String id, ItemStack itemStack, String name, String... lore){
        return themed(id, itemStack, name, Arrays.asList(lore));
    }
    public static SlimefunItemStack themed(String id ,Material itemStack ,String name,List<String> lore){
        return themed(id,new ItemStack(itemStack),name,lore);
    }
    public static SlimefunItemStack themed(String id ,ItemStack itemstack ,String name,List<String> lore){
        return themed(id,itemstack,Theme.DEFAULT,name,lore);
    }
    public static  SlimefunItemStack themed(String id, Material itemStack, Theme themeType, String name, String... lore){
        return themed(id,new ItemStack(itemStack),themeType,name,lore);
    }
    public static  SlimefunItemStack themed(String id, ItemStack itemStack, Theme themeType, String name, String... lore){
        return themed(id,itemStack,themeType,name,Arrays.asList(lore));
    }
    public static  SlimefunItemStack themed(String id, Material itemStack, Theme themeType, String name, List<String> lore){
        return themed(id,new ItemStack(itemStack),themeType,name,lore);
    }
    public static  SlimefunItemStack themed(String id, ItemStack itemStack, Theme themeType, String name, List<String> lore){
        List<String> finallist=themeType.LORE_DEC.decorator(lore);
        return new SlimefunItemStack(
                idDecorator(id),
                itemStack,
                themeType.NAME_DEC.decorate(name),
                finallist.toArray(String[]::new)
        );
    }
    public static ItemStack themed(Material material, Theme themeType, String name, String... lore){
        return themed(material,themeType,name,Arrays.asList(lore));
    }
    public static ItemStack themed(Material material, Theme themeType, String name, List<String> lore){
        return themed(new ItemStack(material),themeType,name,lore);
    }
    public static ItemStack themed(ItemStack itemStack, Theme themeType, String name, String... lore){
        return themed(itemStack,themeType,name,Arrays.asList(lore));
    }
    public static ItemStack themed(ItemStack itemStack, Theme themeType, String name, List<String>  lore){
        List<String> finallist=themeType.LORE_DEC.decorator(lore);
        return new CustomItemStack(
                itemStack,
                themeType.NAME_DEC.decorate(name),
                finallist.toArray(String[]::new)
        );
    }
    public static ItemStack themed(Material material, String name, String... lore){
        return themed(material,Theme.DEFAULT,name,lore);
    }
    public static ItemStack themed(ItemStack itemStack, String name, String... lore){
        return themed(itemStack,Theme.DEFAULT,name,lore);
    }
    public static ItemStack resolveItem(Object a){
        if(a==null)return null;
        if(a instanceof ItemStack){
            return  (ItemStack) a;
        }else if(a instanceof SlimefunItem){
            Debug.logger("clone slimefunItem");
            return ((SlimefunItem) a).getItem().clone();
        }else if(a instanceof  Material){
            return  new ItemStack((Material) a);
        }else if(a instanceof String){
            Pattern re=Pattern.compile("^([0-9]*)(.*)$");
            Matcher info= re.matcher((String)a);
            int cnt=-1;
            String id;
            if(info.find()){
                String amount=info.group(1);
                id=info.group(2);
                try{
                    cnt=Integer.parseInt(amount);
                }catch(NumberFormatException e){
                    cnt=-1;
                }
            }
            else{
                id=(String) a;
            }
            try{
            ItemStack b=SlimefunItem.getById(id).getItem();
            if(cnt>0&&cnt!=b.getAmount()){
                b=b.clone();
                b.setAmount(cnt);
            }
            return b;
            }catch (Exception e){
                try{
                    ItemStack b=new ItemStack( Material.getMaterial(id));
                    if(cnt>0&&cnt!=b.getAmount()){
                        b=b.clone();
                        b.setAmount(cnt);
                    }
                    return b;
                }catch (Exception e2){
                    Debug.logger("WARNING: Object %s can not be solved ! Required Addon not installed ! Disabling relavent recipes...".formatted(a));
                    return AddItem.RESOLVE_FAILED;
                }
            }
        } else {
            Debug.logger("WARNING: failed to solve Object "+a.toString());
            return AddItem.RESOLVE_FAILED;
        }

    }
    public static Pair<ItemStack[], ItemStack[]> buildRecipes(Pair<Object[],Object[]> itemStacks){
        return buildRecipes(itemStacks.getFirstValue(),itemStacks.getSecondValue());
    }
    public static Pair<ItemStack[],ItemStack[]> buildRecipes (Object[] input,Object[] output){
        ItemStack[] a;
        ArrayList<ItemStack> a_=new ArrayList<>(){{

                Arrays.stream(input).forEach(
                        (obj)->{
                                ItemStack a__=resolveItem(obj);
                                this.add(a__);
                            }
                );
        }};
        a=a_.toArray(new ItemStack[a_.size()]);
        ItemStack[] b;
        ArrayList<ItemStack> b_=new ArrayList<>(){{

                Arrays.stream(output).forEach(
                        (obj)->{


                                ItemStack b__=resolveItem(obj);
                                this.add(b__);
                            }
                );

        }};
        b=b_.toArray(new ItemStack[b_.size()]);
        return new Pair<>(a,b);
    }
    public static MachineRecipe buildMachineRecipes(int time,Pair<Object[],Object[]> itemStacks){
        Pair<ItemStack[],ItemStack[]> b=buildRecipes(itemStacks);
        return new MachineRecipe(time,b.getFirstValue(),b.getSecondValue());
    }
    public static <T extends Object> LinkedHashMap<Pair<ItemStack[],ItemStack[]>,Integer> buildRecipeMap(LinkedHashMap<T,Integer> rawDataMap){
        if(rawDataMap==null)return new LinkedHashMap<>();
        LinkedHashMap<Pair<ItemStack[],ItemStack[]>,Integer> map = new LinkedHashMap<>();
        rawDataMap.forEach((k,v)->{
            if(k instanceof Object[]){

                map.put(AddUtils.buildRecipes(
                        Arrays.copyOfRange((Object[])k,0,2),Arrays.copyOfRange((Object[])k,2,4)),v);
            }
            else if (k instanceof Pair){
                try{

                Object[] input=(Object[])((Pair)k).getFirstValue();
                if(input==null){
                    input=new Object[]{};
                }
                Object[] output=(Object[])((Pair)k).getSecondValue();
                if(output==null){
                    output=new Object[]{};
                }
                map.put(AddUtils.buildRecipes(input,output),v);
                }catch (Exception a){
                    throw new IllegalArgumentException("illegalArguments in recipe Pair, Pair val must be <T extends Object>T[]");
                }
            }
        });
        return map;
    }
    public static ItemStack addLore(ItemStack item,String... lores){

        ItemStack item2=item.clone();

        ItemMeta meta=item2.getItemMeta();
        List<String> finallist=meta.hasLore() ? meta.getLore() : new ArrayList<>();
        for (String l:lores){
            finallist.add(resolveColor(l));
        }
        meta.setLore(finallist);
        item2.setItemMeta(meta);
        return item2;
    }
    public static ItemStack renameItem(ItemStack item,String name){
        ItemStack item2=item.clone();

        ItemMeta meta=item2.getItemMeta();
        meta.setDisplayName(resolveColor(name));
        item2.setItemMeta(meta);
        return item2;
    }
    public static String resolveColor(String s){
        return translateAlternateColorCodes('&', s);
    }
    public static String translateAlternateColorCodes(char altColorChar,  String textToTranslate) {
        Preconditions.checkArgument(textToTranslate != null, "Cannot translate null text");
        char[] b = textToTranslate.toCharArray();

        for(int i = 0; i < b.length - 1; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = 167;
            }
        }

        return new String(b);
    }
    public static String formatEnergy(int energy) {
        return FORMAT.format((double)energy * SF_TPS);
    }
     public static String energyPerSecond(int energy) {
        return "&8⇨ &e⚡ &7" + formatEnergy(energy) + " J/s";
    }
    public static String energyPerTick(int energy){
        return "&8⇨ &e⚡ &7" + FORMAT.format((double)energy) + " J/t";
    }
    public static String energyPerCraft(int energy){
        return "&8⇨ &e⚡ &7" + FORMAT.format((double)energy ) + " J 每次合成";
    }
    public static  ItemStack workBenchInfoAdd(ItemStack item,int energyBuffer,int energyConsumption){
        return AddUtils.addLore(item, LoreBuilder.powerBuffer(energyBuffer), AddUtils.energyPerCraft(energyConsumption));
    }
    public static  String tickPerGen(int time){
        return "&8⇨ &7速度: &b每 " + Integer.toString(time) + " 粘液刻生成一次";
    }

    public static ItemStack capacitorInfoAdd(ItemStack item,int energyBuffer){
        return AddUtils.addLore(item, LoreBuilder.powerBuffer(energyBuffer));
    }
    public static  ItemStack machineInfoAdd(ItemStack item,int energyBuffer,int energyConsumption){
        return machineInfoAdd(item,energyBuffer,energyConsumption,Settings.USE_SEC_EXP);
    }
    public static ItemStack machineInfoAdd(ItemStack item,int energyBuffer,int energyConsumption,Settings type){
        if(type==Settings.USE_SEC_EXP) {
            return AddUtils.addLore(item, LoreBuilder.powerBuffer(energyBuffer), AddUtils.energyPerSecond(energyConsumption));
        }
        else if(type==Settings.USE_TICK_EXP) {
            return  AddUtils.addLore( item, LoreBuilder.powerBuffer(energyBuffer), AddUtils.energyPerTick(energyConsumption));
        }
        else return null;
    }
    public static SlimefunItemStack smgInfoAdd(ItemStack item,int time){
        return (SlimefunItemStack) AddUtils.addLore( item, tickPerGen(time));
    }
    public static ItemStack advancedMachineShow(ItemStack stack,int limit){
        return AddUtils.addLore(stack,"&7机器合成进程数: %s".formatted(limit));
    }
    public static String getPercentFormat(double b){
        DecimalFormat df = new DecimalFormat("#.##");
        NumberFormat nf = NumberFormat.getPercentInstance();
        return nf.format(Double.parseDouble(df.format(b)));
    }

    /**
     * return int values in [0,length)
     * @param length
     * @return
     */
    public static int random(int length){
        return random.nextInt(length);
    }
    //generate rand in (0,1)
    public static double standardRandom(){
        return random.nextDouble();
    }
    //we supposed that u have checked these shits

    public static ItemStack eqRandItemStackFactory(List<ItemStack> list){
        LinkedHashMap<Object,Integer> map=new LinkedHashMap<>();
        int i=list.size();
        for(int j=0;j<i;j++){
            map.put(list.get(j),1);
        }
        return randItemStackFactory(map);
    }
    public static ItemStack randItemStackFactory(List<ItemStack> st,List<Integer> it){
        LinkedHashMap<Object,Integer> map=new LinkedHashMap<>();
        int len=st.size();
        for(int i=0;i<len;i++){
            map.put(st.get(i),it.get(i));
        }
        return randItemStackFactory(map);
    }
    public static ItemStack randItemStackFactory(LinkedHashMap<Object,Integer> list){
            LinkedHashMap<ItemStack,Integer> c=new LinkedHashMap<>();
            boolean isEqPro=true;
            int last_value=-1;
            for(Map.Entry<Object ,Integer> s:list.entrySet()){
                Object o=s.getKey();
                if(o!=null){
                    ItemStack o_=AddUtils.resolveItem(o);
                    if(o_!=null){
                        c.put(o_,s.getValue());
                    }
                    else return null;
                }else return null;
                if(last_value>0){

                    isEqPro=(last_value==s.getValue());
                }
                assert s.getValue()>0;
                last_value=s.getValue();
            }
            if(isEqPro){
                return (ItemStack) new EqProRandomStack(c);
            }
            return (ItemStack)new RandomItemStack(c);
        }
    public static ItemStack probItemStackFactory(ItemStack it,int prob){
        if(prob>=100)return it;
        prob=prob<0?0:prob;
        return new ProbItemStack(it,((double)prob)/100 );
    }
    public static <T extends Object> ItemStack equalItemStackFactory(List<T> list){
            LinkedHashMap<ItemStack,Integer> c=new LinkedHashMap<>();
            for(Object o:list){
                if(o!=null){
                    ItemStack o_=AddUtils.resolveItem(o);
                    if(o_!=null){
                        c.put(o_,1);
                    }
                    else return null;
                }else return null;
            }

            return (ItemStack)new EquivalItemStack(c);
    }

    /**
     * like /give command
     * @param p
     * @param toGive
     */
    public static void forceGive(Player p, ItemStack toGive,int amount) {
        ItemStack incoming;
        int maxSize=toGive.getMaxStackSize();
        while(amount>0) {
            incoming = toGive.clone();
            int amount2=Math.min(maxSize, amount);
            incoming.setAmount(amount2);
            amount-=amount2;
            Collection<ItemStack> leftover = p.getInventory().addItem(incoming).values();
            for (ItemStack itemStack : leftover) {
                p.getWorld().dropItemNaturally(p.getLocation(), itemStack);
            }
        }
    }

    /**
     * add glowing effect to itemstack
     * no clone in this method
     * @param stack
     */
    public static ItemStack addGlow(ItemStack stack){
        //stack.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        ItemMeta meta=stack.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
        return stack;
    }
    public static ItemStack hideAllFlags(ItemStack stack){
        ItemMeta meta=stack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        stack.setItemMeta(meta);
        return stack;
    }
    /**
     * get a info display item to present in SF machineRecipe display
     * @param title
     * @param name
     * @return
     */
    public static ItemStack getInfoShow(String title,String... name){
        return new DisplayItemStack(new CustomItemStack(Material.BOOK,title,name));
    }

    /**
     * set the specific lore line in stack ,will not clone
     * @param stack
     * @param index
     * @param str
     * @return
     */
    public static ItemStack setLore(ItemStack stack,int index,String str){
        ItemMeta meta=stack.getItemMeta();
        List<String> lore=meta.getLore();
        while(index>=lore.size()){
            lore.add("");
        }
        lore.set(index,resolveColor(str));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
    /**
     * set the total lore line in stack ,will not clone
     * @param stack
     * @param str
     * @return
     */
    public static ItemStack setLore(ItemStack stack,String... str){
        ItemMeta meta=stack.getItemMeta();
        List<String> lore=new ArrayList<>();
        int len=str.length;
        for (int i=0;i<len;++i) {
            lore.add(resolveColor(str[i]));
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public static void sendMessage(Player p,String msg){
        p.sendMessage(ChatColors.color(msg));
    }
    public static ItemStack[] formatInfoRecipe(ItemStack stack,String source){
        return new ItemStack[]{
                null,stack,null,
                null,getInfoShow("&f获取方式","&7在 %s 中获取".formatted(source)),null,
                null,null,null
        };
    }
    public static MachineRecipe formatInfoMachineRecipe(ItemStack[] stack,int tick,String... description){
        return MachineRecipeUtils.From(tick,new ItemStack[]{
                getInfoShow("&f获取方式",description)
        },stack);
    }
    public static @Nonnull Optional<Material> getPlanks(@Nonnull Material log) {
        String materialName = log.name().replace("STRIPPED_", "");
        int endIndex = materialName.lastIndexOf('_');

        if (endIndex > 0) {
            materialName = materialName.substring(0, endIndex) + "_PLANKS";
            return Optional.ofNullable(Material.getMaterial(materialName));
        } else {
            // Fixed #3651 - Do not panic because of one weird wood type.
            return Optional.empty();
        }
    }
    public static void displayCopyString(Player player,String display,String hover,String copy){
        final TextComponent link = new TextComponent(display);
        link.setColor(ChatColor.YELLOW);
        link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hover)));
        link.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,copy));
        player.spigot().sendMessage(link);
    }
    public static void asyncWaitPlayerInput(Player player, Consumer<String> consumer){
        ChatInput.waitForPlayer(
                Slimefun.instance(),
                player,
                msg ->{
                    consumer.accept(msg);
                } );
    }
    public static ItemStack getGeneratorDisplay(boolean working,String type,int charge,int buffer){
        if(working){
            return new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&a发电中",
                    "&7类型:&6 %s".formatted(type),"&7&7电量: &6%s/%sJ".formatted(FORMAT.format((double)charge),FORMAT.format((double)buffer)));
        }else {
            return new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&a未发电",
                    "&7类型:&6 %s".formatted(type),"&7&7电量: &6%s/%sJ".formatted(FORMAT.format((double)charge),FORMAT.format((double)buffer)));
        }
    }
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
    public static void broadCast(String string){
        ADDON_INSTANCE.getJavaPlugin().getServer().broadcastMessage(resolveColor(string));
    }
    public static ItemStack randAmountItemFactory(ItemStack it,int min,int max){
        if(min<max){
            return new RandAmountStack(it,min,max);
        }
        else if(min==max){
            return it;
        }else {
            throw new IllegalArgumentException("randomAmountStack expects min<max, got %d and %d".formatted(min,max));
        }
    }
    public static ItemStack setCount(ItemStack stack,int amount){
        return new CustomItemStack(stack,amount);
    }
    public static String concat(String... strs){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<strs.length;++i){
            sb.append(strs[i]);
        }
        return sb.toString();
    }
    public static void damageGeneric(Damageable e, double f){
        e.setHealth(Math.max( e.getHealth()-f,0.0));
    }
}
