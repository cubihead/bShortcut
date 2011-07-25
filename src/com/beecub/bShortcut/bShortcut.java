package com.beecub.bShortcut;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import util.bChat;


public class bShortcut extends JavaPlugin {
	private final bShortcutPlayerListener playerListener = new bShortcutPlayerListener(this);
	public Logger log = Logger.getLogger("Minecraft");
	public static PluginDescriptionFile pdfFile;
	public static Configuration conf;
	static bChat bChat;

	@SuppressWarnings("static-access")
	public void onEnable() {

		pdfFile = this.getDescription();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Event.Priority.Lowest, this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[" +  pdfFile.getName() + "]" + " version " + pdfFile.getVersion() + " is enabled!");
		
		bChat = new bChat(this.getServer());
		bConfigManager bConfigManager = new bConfigManager(this);
		bConfigManager.load();
		conf = bConfigManager.conf;		
	}
	
	public void onDisable() {
		log.info("[" + pdfFile.getName() + "]" + " version " + pdfFile.getVersion() + " disabled!");
	}
	
	@SuppressWarnings("static-access")
    public boolean onCommand(CommandSender sender, Command c, String commandLabel, String[] args) {
        String command = c.getName().toLowerCase();
        if (command.equalsIgnoreCase("bShortcut")) {
            bConfigManager.reload();
            bChat.sendMessageToCommandSender(sender, "&6[" + pdfFile.getName() + "]" + " config reloaded");
            return true;
        }
        return false;
	}
}
