package com.beecub.bShortcut;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

public class bConfigManager {
	
	protected bShortcut bShortcut;
    protected static Configuration conf;
    protected File confFile;
    static List<String> shortcuts = new LinkedList<String>();    
	
	@SuppressWarnings("static-access")
	public bConfigManager(bShortcut bShortcut) {
    	this.bShortcut = bShortcut;

    	File f = new File(bShortcut.getDataFolder(), "config.yml");
    	conf = null;

        if (f.exists())
        {
        	conf = new Configuration(f);
        	conf.load();
        	
        }
        else {
        	this.confFile = new File(bShortcut.getDataFolder(), "config.yml");
            this.conf = new Configuration(confFile);
            shortcuts.add("/i");
            shortcuts.add("/give");
            conf.setProperty("shortcuts.shortcuts", shortcuts);
            conf.save();
        }
        
    }    
    
	static void load() {	    
    	conf.load();
    	shortcuts.clear();
        shortcuts = conf.getStringList("shortcuts.shortcuts", shortcuts);
    }
	
	static void reload() {
		load();
	}
	
   static boolean handleShortcuts(Player player, String pre, String message) {
       String preNew = "";
       if(shortcuts.contains(pre)) {
           preNew = conf.getString("shortcuts.shortcuts." + pre, null);
           if(preNew != null || preNew != "") {
               player.performCommand(preNew + message);
               return true;
           }
       }       
    return false;
    }
}
