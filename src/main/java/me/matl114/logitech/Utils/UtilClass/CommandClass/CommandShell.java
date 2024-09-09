package me.matl114.logitech.Utils.UtilClass.CommandClass;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.Listeners.Listeners.PlayerQuiteListener;
import me.matl114.logitech.Schedule.Schedules;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;
import me.matl114.logitech.Utils.ReflectUtils;
import me.matl114.logitech.Utils.UtilClass.FunctionalClass.AsyncResultRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class CommandShell {
    public static String PREFIX=AddUtils.color( AddUtils.ADDON_ID+" >>  ");
    public Player user;
    public BukkitRunnable task;
    public static HashMap<UUID,CommandShell> map = new HashMap<UUID,CommandShell>();
    public HashMap<String,Object> variables;
    public List<String> historyLogs=new ArrayList<>();
    public boolean status=true;
    public boolean interact_mod=true;
    static {
        PlayerQuiteListener.addHandler((playerQuitEvent -> {
            map.remove(playerQuitEvent.getPlayer().getUniqueId());
        }));
    }
    public static void setup(Player player){
        UUID uuid=player.getUniqueId();
        if(map.containsKey(uuid)){
            CommandShell shell=  map.get(uuid);
            shell.status=true;
            AddUtils.sendMessage(player,AddUtils.color( "成功进入LogiTech shell 输入help获取帮助 输入exit离开"));
            shell.run();

        }else {
           CommandShell shell=  new CommandShell(player);
           AddUtils.sendMessage(player,AddUtils.color("成功进入LogiTech shell 输入help获取帮助 输入exit离开"));
           shell.run();
        }
    }

    public CommandShell(Player player) {
        this.user = player;
        this.variables=new HashMap<>();
        map.put(this.user.getUniqueId(),this);
    }
    public List<String> outputStream=new ArrayList<>();
    public byte[] lock=new byte[0];
    //TODO RUN
    public void run(){
        AddUtils.asyncWaitPlayerInput(user,this::runCommand);
    }
    //TODO execute
    public void clear(){
        if(!interact_mod)return;
        synchronized(lock){
            outputStream.clear();
        }
    }
    public void send(String message){
        if(!interact_mod)return;
        synchronized(lock){
            outputStream.add(AddUtils.concat(PREFIX,"&f",message));
        }
    }
    public void warn(String message){
        if(!interact_mod)return;
        synchronized(lock){
            outputStream.add(AddUtils.concat(PREFIX,"&e",message));
        }
    }
    public void error(String message){
        if(!interact_mod)return;
        synchronized(lock){
            outputStream.add(AddUtils.concat(PREFIX,"&c",message));
        }
    }
    public void print(){
        if(!interact_mod)return;
        synchronized(lock){
            for(String s:outputStream){
                AddUtils.sendMessage(user,s);
            }
            outputStream.clear();
        }
    }
    public static int MAX_LENGTH_IN_LINE=72;
    public void printObject(Object obj){
        if(!interact_mod)return;
        String str=getObjectStringDisplay(obj);
        if(str==null){
            str="<ERROR:UNKNOWN>";
        }
        synchronized(lock){
            outputStream.add(AddUtils.concat(PREFIX,str));
        }
    }
    public static String getObjectStringDisplay(Object obj){
        if(obj!=null){
            if(obj instanceof Class<?> cls){
                return "&6<path: %s>".formatted(cls.getName());
            }else if(obj.getClass().isArray()){
                try{
                    StringBuilder builder=new StringBuilder("[");
                    int length = java.lang.reflect.Array.getLength(obj);
                    for(int j=0;j<length;j++){
                        Object item = java.lang.reflect.Array.get(obj, j);
                        if(j!=0){
                            builder.append(",");
                            if(builder.length()>MAX_LENGTH_IN_LINE){
                                builder.append("\n");
                            }
                        }
                        builder.append(getObjectStringDisplay(item));

                    }
                    builder.append("]");
                    return builder.toString();
                }catch(Throwable ex){
                    return "<ERROR:ARRAY DISPLAY>";
                }
            }
            else if(obj instanceof Field field){
                return "&6<FIELD, type=%s, Name=%s, Class=%s, static=%s>".formatted(
                        getObjectStringDisplay(field.getType()),field.getName(),field.getDeclaringClass().getName(), Modifier.isStatic(field.getModifiers())?"true":"false");
            }else if(obj instanceof List<?> lst&&lst.isEmpty()){
                return "Empty List";
            }
            else {
                return "&6<class: %s, %s>".formatted(obj.getClass().getName(),obj.toString());
            }
        }
        else
            return "&6<NULL>";
    }

    public static HashMap<String,ShellCommand> commands=new HashMap<>();
    public interface ShellCommand{
        public int cmd(String[] argv,CommandShell shell);
        default ShellCommand register(String cmd){
            commands.put(cmd,this);
            return this;
        }
        String[] getHelp();
    }
    public void runCommand(String command) {
        clear();
        AddUtils.sendMessage(user,command);
        historyLogs.add(command);
        try{
            String[] args = command.split(" ");
            String cmd=args[0];
            String[] argv= Arrays.copyOfRange(args,1,args.length);
            ShellCommand commandShell=commands.get(cmd);
            send("指令正常运行! 返回值 %d".formatted(commandShell.cmd(argv,this)));
        }catch (Throwable e){
            Debug.debug(e);
            clear();
            error("未知的指令,使用help获取更多信息!");
        }
        print();
        if(!status){
            return;
        }
        run();
    }
    public static Pattern validVariable=Pattern.compile("(.*+)\\[[0-9]*+\\]");
    public static boolean checkVariableName(String name){
        return true;
    }
    public static int classNotFound(CommandShell shell,String className){
        shell.warn( AddUtils.concat( "找不到对应的类! ",className));
        return -1;
    }
    public static int classFieldNotFound(CommandShell shell,Class cls){
        shell.warn(AddUtils.concat("类 ",cls.getName()," 不存在该成员"));
        return -1;
    }
    public static int pathNotInit(CommandShell shell){
        shell.warn("暂未设置path! 请使用cd指令设置path");
        return 0;
    }
    public static int variableNotInit(CommandShell shell,String var){
        shell.warn("变量<%s>暂未初始化".formatted(var));
        return -1;
    }
    public static int variableFieldNotFound(CommandShell shell,String var){
        shell.warn(AddUtils.concat("变量 ",var," 不存在该成员"));
        return -1;
    }
    public static int arrayVaribleIndexOutOfBounds(CommandShell shell,String[] var){
        StringBuilder builder=new StringBuilder("尝试访问数组元素 <%s>, 但是下标".formatted(var[0]));
        for(int i=1;i<var.length;i++){
            if(i!=1){
                builder.append(",");
            }
            builder.append("[");
            builder.append(var[i]);
        }
        builder.append(" 并不是有效的下标");
        shell.warn(builder.toString());
        return -1;
    }
    public static int setFieldFailed(CommandShell shell,String var1,Field field,String err){
        shell.error(AddUtils.concat("设置变量<%s>的成员<%s>时出现未知错误!: %s").formatted(var1,field==null?"ERROR:NULL":field.getName(),err));
        return -1;
    }
    public static int typeNotSupported(CommandShell shell,String var1){
        shell.error(AddUtils.concat("不支持的常量类型: ",var1));
        return -1;
    }
    public static Object getVariable(CommandShell shell,String val){
        String[] vals=val.split("\\[");
        Object result=shell.variables.get(vals[0]);
        if(result==null){
            variableNotInit(shell,vals[0]);
            return null;
        }
        try{
            for(int i=1;i<vals.length;i++){
                if(result instanceof List<?> lst){
                    result=lst.get(Integer.parseInt(vals[i].substring(0,vals[i].length()-1)));
                }else {
                    result=java.lang.reflect.Array.get(result,Integer.parseInt(vals[i].substring(0,vals[i].length()-1)));
                }
            }
            return result;
        }catch (Throwable e){
            arrayVaribleIndexOutOfBounds(shell,vals);
            return null;
        }
    }
    public static int setVariable(CommandShell shell,String val,Object var1){
        String[] vals=val.split("\\[");
        if(vals.length>1){
            Object result=shell.variables.get(vals[0]);
            if(result==null){
                return variableNotInit(shell,vals[0]);
            }
            try{
                for(int i=1;i<vals.length-1;i++){
                    if(result instanceof List<?> lst){
                        result=lst.get(Integer.parseInt(vals[i].substring(0,vals[i].length()-1)));
                    }else {
                        result=java.lang.reflect.Array.get(result,Integer.parseInt(vals[i].substring(0,vals[i].length()-1)));
                    }
                }
                if(result instanceof List lst){
                    lst.set(Integer.parseInt(vals[vals.length-1].substring(0,vals[vals.length-1].length()-1)),var1);
                }else {
                    java.lang.reflect.Array.set(result,Integer.parseInt(vals[vals.length-1].substring(0,vals[vals.length-1].length()-1)),var1);
                }
                return 1;
            }catch (Throwable e){
                arrayVaribleIndexOutOfBounds(shell,vals);
                return -1;
            }
        }else {
            shell.variables.put(val,var1);
            return 1;
        }
    }
    public static String getPathVarName(){
        return "____path@#";
    }
    public static ShellCommand help=new ShellCommand(){
        public String[] help=new String[]{
                "help 获得全部指令的说明",
                "help <arg1> 获得单个指令的说明"
        };
        public int cmd(String[] argv,CommandShell shell){
            shell.send("指令说明:");
            if(argv.length==1){
                String arg=argv[0];
                ShellCommand cmd=commands.get(arg);
                String[] help=cmd.getHelp();
                shell.send(AddUtils.concat("指令 ",arg));
                for(String s:help){
                    shell.send(s);
                }
                shell.send("  ");
            }else if(argv.length==0){
                for(String arg:commands.keySet()){
                    ShellCommand cmd=commands.get(arg);
                    String[] help=cmd.getHelp();
                    shell.send(AddUtils.concat("指令 ",arg));
                    for(String s:help){
                        shell.send(s);
                    }
                    shell.send("  ");
                }
            }
            return 0;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("help");
    public static ShellCommand exit=new ShellCommand(){
        public String[] help=new String[]{
                "exit 退出shell并删除记录",
        };
        public int cmd(String[] argv,CommandShell shell){
            shell.status=false;
            boolean success=false;
            Iterator<Map.Entry<UUID,CommandShell>> shells=map.entrySet().iterator();
            while(shells.hasNext()){
                Map.Entry<UUID,CommandShell> entry=shells.next();
                if(entry.getValue()==shell){
                    shells.remove();
                    success=true;
                    break;
                }
            }
            if(success){
                shell.send("成功退出");
            }else {
                shell.send("未知错误! 不正常的退出!");
            }
            return success?1:0;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("exit");
    public static ShellCommand quit=new ShellCommand(){
        public String[] help=new String[]{
                "quit 退出shell不删除记录",
        };
        public int cmd(String[] argv,CommandShell shell){
            shell.status=false;
            shell.send("成功退出");
            return 1;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("quit");
    public static ShellCommand runCommand=new ShellCommand(){
        public String[] help=new String[]{
                "say <argvs> 玩家输出<argvs>,用空格相连"
        };
        public int cmd(String[] argv,CommandShell shell){
            if(Bukkit.isPrimaryThread()){
                shell.user.chat(String.join(" ", argv));
                return 1;
            }else {
                shell.warn("shell并未在主线程中运行,无法使用该指令");
                return 0;
            }
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("say");
    public static ShellCommand changeClass=new ShellCommand(){
        public String[] help=new String[]{
                "setPath <Class> 将当前指令的默认类对象设置为<Class>"
        };
        public int cmd(String[] argv,CommandShell shell){
            Class clazz;
            try{
                clazz=Class.forName(argv[0]);
            }catch (ClassNotFoundException err){
               return   classNotFound(shell,argv[0]);
            }
            return setVariable(shell,getPathVarName(),clazz);

        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("setPath");
    public static ShellCommand getStaticField=new ShellCommand(){
        public String[] help=new String[]{
                "getStatic <arg1> 获取当前默认类的名称为<arg1>的静态成员",
                "getStatic <arg1> <varName> 获取当前默认类的名称为<arg1>的静态成员,并赋值给<varName>变量"
        };
        public int cmd(String[] argv,CommandShell shell){

            Object clazz=getVariable(shell,getPathVarName());
            if(clazz instanceof Class cls){
                Pair<Field,Class> result= ReflectUtils.getDeclaredFieldsRecursively(cls,argv[0]);
                if(result==null){
                    return classFieldNotFound(shell,cls);
                }else {
                    Object res;
                    try{
                        res= result.getFirstValue().get(null);
                    }catch (Throwable err){
                        Debug.debug(err);

                        return classFieldNotFound(shell,cls);
                    }
                    shell.send(AddUtils.concat( "位于Class ",result.getSecondValue().getName()));
                    shell.printObject(res);
                    if(argv.length==2){
                        return setVariable(shell,argv[1],res);
                    }
                    return 1;
                }
            }else {
                return pathNotInit(shell);
            }
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("getStatic");
    public static ShellCommand setStaticField=new ShellCommand(){
        public String[] help=new String[]{
                "setStatic <arg1> 将当前默认类对象的<arg1>静态成员设置为当前默认类对象",
                "setStatic <arg1> <var1> 将当前默认类对象的<arg1>静态成员设置为变量<var1>"
        };
        public int cmd(String[] argv,CommandShell shell){
            Object obj=getVariable(shell,getPathVarName());
            if(obj instanceof Class clazz) {
                Pair<Field,Class> result=ReflectUtils.getDeclaredFieldsRecursively(clazz,argv[0]);
                if(result==null){
                    return classFieldNotFound(shell,clazz);
                }else {
                    Object obj2= argv.length==1?obj:getVariable(shell,argv[1]);
                    if(obj2==null){
                        return variableNotInit(shell,argv[1]);
                    }
                    try{
                        result.getFirstValue().set(null,obj2);
                    }catch (Throwable err){
                        setFieldFailed(shell,argv[0],result.getFirstValue(),err.getMessage());
                        return -1;
                    }
                    shell.send(AddUtils.concat( "位于Class ",result.getSecondValue().getName()," 的成员",result.getFirstValue().getName()));
                    shell.send("设置成功!");
                    return 1;
                }
            }
           else {
                return pathNotInit(shell);
            }
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("setStatic");
    public static ShellCommand get=new ShellCommand(){
        public String[] help=new String[]{
                "get 打印当前选中的类path信息",
                "get <varName> 打印变量<varName>"
        };
        public int cmd(String[] argv,CommandShell shell){
            if(argv.length==0){

                shell.printObject(getVariable(shell,getPathVarName()) );
            }else {
                shell.printObject( getVariable(shell,argv[0]));
            }
            return 1;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("get");
    public static ShellCommand set=new ShellCommand(){
        public String[] help=new String[]{
                "set <var1> 将当前类对象存储至<var1>",
                "set <var1> <var2> 将变量<var2>存储至<var1>"
        };
        public int cmd(String[] argv,CommandShell shell){
            Object obj=getVariable(shell, argv.length==1? getPathVarName():argv[1]);
           return setVariable(shell,argv[0],obj);
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("set");
    public static ShellCommand getField=new ShellCommand(){
        public String[] help=new String[]{
                "getField <var1> <arg1> 获取变量<var1>名称为<arg1>的成员 ",
                "getField <var1> <arg1> <var2> 获取变量<var1>名称为<arg1>的成员,并赋值给var2 ",
        };
        public int cmd(String[] argv,CommandShell shell){
            Object obj=getVariable(shell,argv[0]);
            if(obj==null){
                return variableNotInit(shell,argv[0]);
            }else {
                Class clazz=obj.getClass();
                Pair<Field,Class> result=ReflectUtils.getDeclaredFieldsRecursively(clazz,argv[1]);
                if(result==null){
                    return variableFieldNotFound(shell,argv[0]);
                }else {
                    Object res;
                    try{
                        res= result.getFirstValue().get(obj);
                    }catch (Throwable err){
                        classFieldNotFound(shell,clazz);
                        return -1;
                    }
                    shell.send(AddUtils.concat( "位于Class ",result.getSecondValue().getName()));
                    shell.printObject(res);
                    if(argv.length==3){
                        setVariable(shell,argv[2],res);
                    }
                    return 1;
                }
            }
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("getField");
    public static ShellCommand getAllFields=new ShellCommand(){
        public String[] help=new String[]{
                "getAllFields <var1> 获取变量<var1>的所有成员 ",
                "getAllFields 获取当前设置的Class的所有成员",
        };
        public int cmd(String[] argv,CommandShell shell){
            Class clazz;
           if(argv.length==0){

               Object obj=getVariable(shell,getPathVarName());
               if(obj instanceof Class cls){
                   clazz=cls;
               }else {
                   return pathNotInit(shell);
               }
           }else {
               Object obj= getVariable(shell,argv[0]);
               if(obj==null){
                   return variableFieldNotFound(shell,argv[0]);
               }
               clazz=obj.getClass();
           }
           List<Field> fields=ReflectUtils.getAllDeclaredFieldsRecursively(clazz);
           if(fields.isEmpty()){
               shell.send("Empty List");
           }else {
               for(Field field:fields){
                   shell.printObject(field);
               }
           }
           return 1;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("getAllFields");
    public static ShellCommand setField=new ShellCommand(){
        public String[] help=new String[]{
                "setField <var1> <arg1> <var2> 将变量<var2>赋值给变量<var1>名称为<arg1>的成员 ",
        };
        public int cmd(String[] argv,CommandShell shell){
            assert argv.length==3;
            Object obj=getVariable(shell,argv[0]);
            if(obj==null){
                return variableNotInit(shell,argv[0]);
            }else {
                Class clazz=obj.getClass();
                Pair<Field,Class> result=ReflectUtils.getDeclaredFieldsRecursively(clazz,argv[1]);
                if(result==null){
                    return variableFieldNotFound(shell,argv[0]);
                }else {
                    Object obj2=getVariable(shell,argv[2]);
                    if(obj2==null){
                        return variableNotInit(shell,argv[2]);
                    }
                    try{
                        result.getFirstValue().set(obj,obj2);
                    }catch (Throwable err){
                        setFieldFailed(shell,argv[0],result.getFirstValue(),err.getMessage());
                        return -1;
                    }
                    shell.send(AddUtils.concat( "位于Class ",result.getSecondValue().getName()," 的成员",result.getFirstValue().getName()));
                    shell.send("设置成功!");
                    return 1;
                }
            }
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("setField");
    public static ShellCommand constant=new ShellCommand(){
        public String[] help=new String[]{
                "const <type> <val> <var2> 将字符串<val>转为支持的基类/字符串<type>并将变量<var2>设置为该值",
                "const list <len> <var2> 将变量<var2>设置为创建的长度为<len>的数组"
        };
        public int cmd(String[] argv,CommandShell shell){
            assert argv.length==3;
            String val=argv[1];
            Object obj;
            switch(argv[0]){
                case "int": obj=Integer.valueOf(val); break;
                case "long": obj=Long.valueOf(val); break;
                case "double": obj=Double.valueOf(val); break;
                case "float": obj=Float.valueOf(val); break;
                case "boolean": obj=Boolean.valueOf(val); break;
                case "String": obj=val; break;
                case "byte": obj=Byte.valueOf(val); break;
                case "char": obj=val.charAt(0); break;
                case "list": obj=new Object[Integer.parseInt(val)]; break;
                default: return typeNotSupported(shell,argv[0]);
            }
            setVariable(shell,argv[2],obj);
            return 0;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("const");
    public static ShellCommand getSfItem=new ShellCommand(){
        public String[] help=new String[]{
                "getSfItem <ID> <var1> 获取id为<ID>的sf物品实例,并存储到",
                "getSfitem <ID> 获取id为<ID>的sf物品实例"
        };
        public int cmd(String[] argv,CommandShell shell){
            SlimefunItem item=SlimefunItem.getById(argv[0]);
            shell.printObject(item);
            if(argv.length==2){
                setVariable(shell,argv[1],item);
            }
            return 1;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("getSfItem");
    public static ShellCommand getNearbyPlayer=new ShellCommand(){
        public String[] help=new String[]{
                "getNearbyEntity <int> <var1> 获取当前范围<int>内的全部实体,并存储在<var1>中",
                "getNearbyEntity <int> 获取当前范围<int>内的全部实体"
        };
        public int cmd(String[] argv,CommandShell shell){
            Collection<Entity> result;
            int nearby=Integer.parseInt(argv[0]);
            AsyncResultRunnable<Collection<Entity>> getNearby=new AsyncResultRunnable<Collection<Entity>>() {
                public Collection<Entity> result(){
                    return shell.user.getWorld().getNearbyEntities(shell.user.getLocation(),nearby,nearby,nearby);
                }
            };
            result=getNearby.waitForDone(true);
            Entity[] entities=new Entity[result.size()];
            int index=0;
            for (Entity  e:result){
                entities[index]=e;
                ++index;
            }
            shell.printObject(entities);
            if(argv.length==2){
                setVariable(shell,argv[1],entities);
            }return 1;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("getNearbyEntity");
    public static ShellCommand invoke=new ShellCommand(){
        public String[] help=new String[]{
                "invoke <var1> <var1> 获取id为<ID>的sf物品实例,并存储到",
        };
        public int cmd(String[] argv,CommandShell shell){
            SlimefunItem item=SlimefunItem.getById(argv[0]);
            shell.printObject(item);
            if(argv.length==2){
                setVariable(shell,argv[1],item);
            }
            return 1;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("invoke");
    //TODO more commands
}
