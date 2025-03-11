package me.matl114.logitech.utils.UtilClass.CommandClass;

import me.matl114.logitech.MyAddon;
import me.matl114.logitech.utils.AddUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

public class AddonCommand implements TabExecutor {
    private boolean registered = false;
    private final MyAddon plugin;
    public AddonCommand(MyAddon plugin) {
        this.plugin=plugin;
    }
    public boolean onCommand( CommandSender var1,  Command var2, String var3,  String[] var4){
        if(var1 instanceof Player player){
            if(var1.hasPermission("logitech.command.op")){
                if(var4.length>0){
                    String addonName = var4[0];
                    boolean doExportIfAbsent=false;
                    boolean doExportWhenEmpty=false;
                    for (String s:var4){
                        if(s.startsWith("-")){
                            if(s.endsWith("a")){
                                doExportIfAbsent=true;
                            }
                            else if(s.endsWith("e")){
                                doExportWhenEmpty=true;
                            }
                        }
                    }
                    Consumer<String> output=(str)-> AddUtils.sendMessage(player,str);
                    ExportAddons.export(addonName,doExportIfAbsent,doExportWhenEmpty,output);
                }else {
                    AddUtils.sendMessage(player,"Please enter an sf Addon to continue!");
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return List.of();
    }
}
