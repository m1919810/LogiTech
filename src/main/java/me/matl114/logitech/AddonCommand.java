package me.matl114.logitech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.commands.subcommands.SlimefunSubCommands;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.UtilClass.CommandClass.ExportAddons;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class AddonCommand implements CommandExecutor, Listener {
    private boolean registered = false;
    private final MyAddon plugin;
    public AddonCommand(MyAddon plugin) {
        this.plugin=plugin;
    }
    public void register() {
        Validate.isTrue(!registered, "Slimefun's subcommands have already been registered!");
        registered = true;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("logitech").setExecutor(this);
    }
    public boolean onCommand( CommandSender var1,  Command var2, String var3,  String[] var4){
        if(var1 instanceof Player player){

            if(var4[0].equalsIgnoreCase("export")){
                if(var1.hasPermission("logitech.command.op")){
                    if(var4.length>1){
                        String addonName = var4[1];
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
        }

        return true;
    }
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/help logitech")) {
            //sendHelp(e.getPlayer());
            e.setCancelled(true);
        }
    }
}
