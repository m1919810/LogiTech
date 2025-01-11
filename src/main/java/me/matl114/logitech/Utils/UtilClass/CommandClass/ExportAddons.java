package me.matl114.logitech.Utils.UtilClass.CommandClass;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import me.matl114.logitech.ConfigLoader;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.core.Registries.RecipeSupporter;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Consumer;

public class ExportAddons {
    public static void export(String addonName,boolean doExportIfAbsent,boolean doExportWhenEmpty, Consumer<String> outputStream){
        Plugin pl= Bukkit.getPluginManager().getPlugin(addonName);
        if(pl instanceof SlimefunAddon sfaddon){
            outputStream.accept("检测到相关的附属! %s".formatted(sfaddon.toString()));
            outputStream.accept("准备导出该附属相关机器信息");
            HashSet<SlimefunItem> addonItems=new LinkedHashSet<>();
            HashSet<SlimefunItem> addonItemsWithoutRecipe=new LinkedHashSet<>();
            for(SlimefunItem sfitem: Slimefun.getRegistry().getAllSlimefunItems()){
                if(sfitem.getAddon()==sfaddon){
                    addonItems.add(sfitem);
                    if(!RecipeSupporter.MACHINE_RECIPELIST.containsKey(sfitem)){
                        addonItemsWithoutRecipe.add(sfitem);
                    }
                }
            }
            outputStream.accept("读取到该附属的 %d 项sf物品".formatted(addonItems.size()));
            outputStream.accept("检测到该附属的 %d 项拥有标准配方表的物品".formatted(addonItems.size()-addonItemsWithoutRecipe.size()));
            outputStream.accept("其余物品将导出展示用配方列表");
            Config outputConfig= ConfigLoader.loadExternalConfig("exports/"+addonName);
            outputConfig.setValue(addonName+".enable",true);
            if(doExportIfAbsent){
                outputStream.accept("提示:暂未支持转换随机配方!");
            }
            for(SlimefunItem sfitem: addonItems){
                String root=addonName+"."+sfitem.getId();
                String recipeRoot=root+".recipe";
                boolean isEmpty=true;
                if(addonItemsWithoutRecipe.contains(sfitem)){
                    if(sfitem instanceof RecipeDisplayItem rdi){
                        outputConfig.setValue(root+".source","displayed");
                        List<ItemStack> stacks=rdi.getDisplayRecipes();
                        int len=stacks.size();
                        int index=0;
                        for(int i=0;2*i<len;i+=1){
                            ItemStack a1=stacks.get(2*i);
                            ItemStack a2=null;
                            if(2*i+1<len){
                                a2=stacks.get(2*i+1);
                            }
                            if(a1!=null)
                                outputConfig.setValue(recipeRoot+".%d.input.1".formatted(index),AddUtils.getItemId(a1));
                            if(a2!=null)
                                outputConfig.setValue(recipeRoot+".%d.output.1".formatted(index),AddUtils.getItemId(a2));
                            if(a1!=null||a2!=null){
                                isEmpty=false;
                                outputConfig.setValue(recipeRoot+".%d.tick".formatted(index),0);
                                index+=1;
                            }
                        }
                    }else {
                        if(doExportWhenEmpty){
                            outputConfig.setValue(root+".source","empty");
                        }
                    }
                }else {
                    if(doExportIfAbsent){
                        List<MachineRecipe> recipes=RecipeSupporter.MACHINE_RECIPELIST.get(sfitem);
                        outputConfig.setValue(root+".source","recipe");
                        int index=0;
                        for(MachineRecipe recipe: recipes){
                            ItemStack[] inputs=recipe.getInput();
                            ItemStack[] outputs=recipe.getOutput();
                            for(int i=0;i<inputs.length;i+=2){
                                outputConfig.setValue(recipeRoot+".%d.input.%d".formatted(index,i),AddUtils.getItemId(inputs[i]));
                            }
                            for(int j=0;j<outputs.length;j+=2){
                                outputConfig.setValue(recipeRoot+".%d.output.%d".formatted(index,j),AddUtils.getItemId(outputs[j]));
                            }
                            isEmpty=false;
                            outputConfig.setValue(recipeRoot+".%d.tick".formatted(index),recipe.getTicks());
                        }
                    }
                }
                if(!isEmpty||doExportWhenEmpty){
                    outputConfig.setValue(root+".mode","w");
                    outputConfig.setValue(root+".type","machine");
                    int energy=RecipeSupporter.tryGetMachineEnergy(sfitem);
                    if(energy>=0)
                        outputConfig.setValue(root+".energy",energy);
                }
            }
            outputConfig.save();
            outputStream.accept("配方配置已导出至 %s".formatted(outputConfig.getFile().getPath()));
        }else {
            Debug.logger("this is not a SlimefunAddon");
            outputStream.accept("这不是一个有效的粘液附属名称! %s".formatted(addonName));
        }
    }
}
