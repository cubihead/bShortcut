package com.beecub.bShortcut;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

public class bConfigManager {
	
	protected static bShortcut bShortcut;
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
            conf.setProperty("shortcuts.commands./breload", "/bShortcut reload");
            conf.setProperty("shortcuts.commands./g", "/give");
            conf.setProperty("shortcuts.commands./cleanstone", "/item 1 64");
            conf.save();
        }
        
    }    
    
	static void load() {	    
    	conf.load();
    	shortcuts.clear();
        shortcuts = conf.getStringList("shortcuts.commands", shortcuts);
    }
	
	static void reload() {
		load();
	}
	
   static boolean handleShortcuts(Player player, String pre, String message) {
       String preNew = "";
       preNew = conf.getString("shortcuts.commands." + pre, null);
       if(preNew != null && preNew != "") {
           player.chat(preNew + message);
           return true;
       }
    return false;
    }
}
