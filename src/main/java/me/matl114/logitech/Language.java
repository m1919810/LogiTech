package me.matl114.logitech;

import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import me.matl114.logitech.Utils.Debug;

import java.util.*;

public class Language {
    public static final String LANGUAGE_NAMESPACE="zh_CN";
    public static final HashMap<String ,Object> LANGUAGE=new HashMap<>(){{
    }};
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
    public static boolean loadConfig(Config cfg){
        Set<String> keys=cfg.getKeys(LANGUAGE_NAMESPACE);
        if(keys.isEmpty()) return false;
        for (String ns : keys) {
            Set<String> val=cfg.getKeys(String.join(".", LANGUAGE_NAMESPACE,ns));
            for (String key : val) {
                String namespaceKeyName=String.join(".", ns,key,"Name");
                String namespaceKeyLore=String.join(".", ns,key,"Lore");
                LANGUAGE.put(namespaceKeyName,cfg.getValue(String.join(".", LANGUAGE_NAMESPACE,namespaceKeyName)));
                LANGUAGE.put(namespaceKeyLore,cfg.getValue(String.join(".", LANGUAGE_NAMESPACE,namespaceKeyLore)));
            }
        }
        Debug.logger("语言文件加载完毕");
        return true;
    }
}
