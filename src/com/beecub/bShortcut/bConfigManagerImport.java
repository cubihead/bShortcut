package com.beecub.bShortcut;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.util.config.Configuration;

public class bConfigManagerImport {
    
    protected static bShortcut bShortcut;
    protected static Configuration conf;
    protected File confFile;
    static List<String> shortcuts = new LinkedList<String>();
    
    @SuppressWarnings("static-access")
    public bConfigManagerImport(bShortcut bShortcut) {
        this.bShortcut = bShortcut;

        File f = new File(bShortcut.getDataFolder(), "imports.yml");
        conf = null;
        
        if (f.exists())
        {
            conf = new Configuration(f);
            conf.load();            
        }
        else {
            this.confFile = new File(bShortcut.getDataFolder(), "imports.yml");
            this.conf = new Configuration(confFile);
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
    
    List<String> getShortcuts() {
        load();
        return shortcuts;
    }
}
