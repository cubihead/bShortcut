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
            List<String> bsp1 = new LinkedList<String>();
            List<String> bsp2 = new LinkedList<String>();
            
            conf.setProperty("shortcuts.commands./cleanstone", "/item 1 64");            
            bsp1.add("My name is &player");
            bsp1.add("Iam &1 years old");
            conf.setProperty("shortcuts.commands./myName", bsp1);            
            conf.setProperty("shortcuts.commands./breload", "/bShortcut reload");
            conf.setProperty("shortcuts.commands./t", "/time &args");
            
            bsp2.add("/give &1 &2 &3");
            bsp2.add("/m &1 there you got &3 of &2 from me.");
            conf.setProperty("shortcuts.commands./g", bsp2);
            conf.save();
        }
        
    }    
    
	static void load() {	    
    	conf.load();
    	shortcuts.clear();
        shortcuts = conf.getKeys("shortcuts.commands");
    }
	
	static void reload() {
		load();
	}
	
	
	static boolean handleShortcuts(Player player, String pre, String message) {
        if(shortcuts.contains(pre)) {
            String performMessage;
            List<String> perform = new LinkedList<String>();
            perform = conf.getStringList("shortcuts.commands." + pre, null);
            if(perform != null && perform.size() > 1) {
                for(int i = 0; i < perform.size(); i++) {
                    performMessage = perform.get(i);
                    performMessage = handleVariables(player, performMessage, message);
                    player.chat(performMessage);
                }
                return true;
            }
            else if(perform != null && perform.size() == 0) {
                performMessage = conf.getString("shortcuts.commands." + pre, null);
                performMessage = handleVariables(player, performMessage, message);
                player.chat(performMessage);
                return true;
            }
        }
        return false;
	}
	
	static String handleVariables(Player player, String performMessage, String message) {
	    String[] args = null;
	    message = message.replaceFirst(" ", "");
	    args = message.split(" ");
	    for(int k = 0; k < args.length; k++) {
	        performMessage = performMessage.replaceAll("&" + (k + 1), args[k]);
	    }
	    performMessage = performMessage.replaceAll("&player", player.getName());
	    performMessage = performMessage.replaceAll("&args", message);
	    return performMessage;
    }
    
	
	// not yet used, allow other plugins to setup default shortcuts
    static String setupDefaultShortcuts(String command, String shortcut, String alternativeShortcut) {
        String test;
        if(conf != null) {
            test = conf.getString("shortcuts.commands." + shortcut, "");
            if(test != null && test != "") {
                test = conf.getString("shortcuts.commands." + alternativeShortcut, "");
                if( test != null && test != "") {
                    conf.setProperty("shortcuts.commands." + alternativeShortcut, command);
                    conf.save();
                    return alternativeShortcut;
                }
            }
            else {
                conf.setProperty("shortcuts.commands." + shortcut, command);
                conf.save();
                return shortcut;
            }
        }
        return null;
    }
}
