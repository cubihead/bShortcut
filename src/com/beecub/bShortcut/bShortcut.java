package com.beecub.bShortcut;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import java.util.logging.Logger;


public class bShortcut extends JavaPlugin {
	private final bShortcutPlayerListener playerListener = new bShortcutPlayerListener(this);
	public static Logger log = Logger.getLogger("Minecraft");
	public static PluginDescriptionFile pdfFile;
	public static Configuration conf;

	@SuppressWarnings("static-access")
	public void onEnable() {

		pdfFile = this.getDescription();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Event.Priority.Lowest, this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info( "[" +  pdfFile.getName() + "]" + " version " + pdfFile.getVersion() + " is enabled!" );
		
		bConfigManager bConfigManager = new bConfigManager(this);
		bConfigManager.load();
		conf = bConfigManager.conf;
		
	}
	public void onDisable() {
		log.info("[" + pdfFile.getName() + "]" + " version " + pdfFile.getVersion() + " disabled!");
	}
}
