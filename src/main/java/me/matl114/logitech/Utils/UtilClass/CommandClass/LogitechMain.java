package me.matl114.logitech.Utils.UtilClass.CommandClass;

import me.matl114.matlib.Utils.Command.CommandGroup.AbstractMainCommand;
import me.matl114.matlib.Utils.Command.CommandGroup.SubCommand;
import me.matl114.matlib.Utils.Command.Params.SimpleCommandArgs;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LogitechMain extends AbstractMainCommand {
    public LogitechMain() {
        super();
    }
    private String multiBlockVideo="https://www.bilibili.com/video/BV1utqfYJEU5/?share_source=copy_web&vd_source=e9e2f8944c3907e62b2927cdca14a26c";
    private SubCommand mainCommand=new SubCommand("logitech",new SimpleCommandArgs("_operator"),"...")
            .setTabCompletor("_operator",()-> getSubCommands().stream().map(SubCommand::getName).toList());
    @Override
    public String permissionRequired() {
        return null;
    }
    private SubCommand helpCommand=new SubCommand("help",new SimpleCommandArgs(),"/logitech export 显示指令列表"){
        @Override
        public boolean onCommand(CommandSender var1, Command var2, String var3, String[] var4) {
            showHelpCommand(var1);
            return true;
        }
    }.register(this);
    private SubCommand exportAddonCommand=new SubCommand("export",new SimpleCommandArgs(),"/logitech export 内部开发指令")
            .setCommandExecutor(new AddonCommand(null))
            .register(this);
    private SubCommand multiBlockCommand=new SubCommand("multiblock",new SimpleCommandArgs(),"&a/logitech multiblock 获取本附属多方块结构搭建教程"){
        @Override
        public boolean onCommand(CommandSender var1, Command var2, String var3, String[] var4) {
            if(var1 instanceof Player p){
                final TextComponent link = new TextComponent("单击此处打开附属多方块结构搭建教程");
                link.setColor(ChatColor.YELLOW);
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, multiBlockVideo));
                p.spigot().sendMessage(link);
            }
            return true;
        }
    }.register(this);
}
