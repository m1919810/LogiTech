package me.matl114.logitech;

import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import me.matl114.logitech.Utils.AddUtils;
import me.matl114.logitech.Utils.Debug;

import java.util.*;

public class Language {
    public static final String LANGUAGE_NAMESPACE="zh_CN";
    public static final HashMap<String ,Object> LANGUAGE=new HashMap<>(){{
    }};
    public static final HashMap<String,String> PLACEHOLDERS=new HashMap<>();
    public static HashMap<String ,Object> getLanguage() {
        return LANGUAGE;
    }
    public static String get(String key){
        String s=(String)getLanguage().get(key);
        return s!=null?s:key;
    }
    public static List<String> getList(String key){
        List<String > ls= (List<String>) getLanguage().get(key);
        return ls!=null?ls:new ArrayList<>();
    }
    public static String getPlaceHolder(String key){
        return PLACEHOLDERS.get("%%%s%%".formatted(key));
    }
    public static Object replacePlaceHolder(Object val){
        if(val instanceof String s){
            for(Map.Entry<String,String> entry:PLACEHOLDERS.entrySet()){
                s=s.replace(entry.getKey(),entry.getValue());
            }
            return s;
        }else if(val instanceof List<?> lst){
            return lst.stream().map(Language::replacePlaceHolder).toList();
        }else return val;
    }
    public static boolean loadConfig(Config cfg){
        Set<String> placeHolders=cfg.getKeys("placeholder");
        for(String placeHolder : placeHolders){
            PLACEHOLDERS.put("%%%s%%".formatted(placeHolder),cfg.getString("placeholder."+placeHolder));
        }
        Set<String> keys=cfg.getKeys(LANGUAGE_NAMESPACE);
        if(keys.isEmpty()) return false;
        for (String ns : keys) {
            loadRecursively(cfg,ns);
        }
        Iterator<Map.Entry<String, Object>> it=LANGUAGE.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Object> entry=it.next();
            entry.setValue(replacePlaceHolder(entry.getValue()));
        }
        Debug.logger("语言配置加载完毕");
        return true;
    }
    public static void loadRecursively(Config config,String path){
        Set<String> keys=config.getKeys(String.join( ".", LANGUAGE_NAMESPACE,path));
        for (String key : keys) {
            String nextPath=path+"."+key;
            if(key.endsWith("lan")){
                LANGUAGE.put(path,config.getValue(String.join(".", LANGUAGE_NAMESPACE,nextPath)));
            }else if(key.endsWith("key")||key.endsWith("enable")){

            }else if(key.endsWith("Lore")||key.endsWith("Name")){
                LANGUAGE.put(AddUtils.concat(path,".",key),config.getValue(String.join(".", LANGUAGE_NAMESPACE,path,key)));
            }
            else {
                loadRecursively(config,nextPath);
            }
        }
    }
}
