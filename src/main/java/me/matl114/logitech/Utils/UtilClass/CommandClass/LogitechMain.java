package me.matl114.logitech.Utils.UtilClass.CommandClass;

import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.CraftUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.WorldUtils;
import me.matl114.matlib.Utils.Command.CommandGroup.AbstractMainCommand;
import me.matl114.matlib.Utils.Command.CommandGroup.SubCommand;
import me.matl114.matlib.Utils.Command.Params.SimpleCommandArgs;
import me.matl114.matlib.core.EnvironmentManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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
    private SubCommand testCommand=new SubCommand("runTest",new SimpleCommandArgs("test"),"/logitech export 内部开发指令"){
        public boolean onCommand(CommandSender var1, Command var2, String var3, String[] var4) {
            AddUtils.sendMessage(var1,"No Permission!");
            if(var1.hasPermission("logitech.command.op")){
                runTest(parseInput(var4).getFirstValue().nextArg());
            }
            return true;
        }
    }
            .setTabCompletor("test",List::of)
            .register(this);
    private void runTest(String value){
        Method[] fields= this.getClass().getDeclaredMethods();
        Arrays.stream(fields).forEach(me->{
            if(me.getName().endsWith(value)&&me.getParameterTypes().length==0){
                Debug.logger("find ",me.getName());
                try{
                    me.invoke(this);
                }catch(Throwable e){
                    Debug.logger(e);
                }
                Debug.logger("test success");
            }
        });

    }
    private World world= Bukkit.getWorld("world");
    private void test1(){
        Block b=world.getBlockAt(0,0,0);
        Block b2=world.getBlockAt(0,1,0);
        WorldUtils.copyBlockState(b.getState(),b2);
    }
    private void test2(){
        Debug.logger(CraftUtils.COMPLEX_MATERIALS);
        Debug.logger(WorldUtils.LOOTTABLES_TYPES);
        Debug.logger(EnvironmentManager.getManager().getVersioned());
    }
}
