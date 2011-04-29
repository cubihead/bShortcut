package com.beecub.bShortcut;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerListener;


public class bShortcutPlayerListener extends PlayerListener {
	@SuppressWarnings("unused")
	private final bShortcut plugin;

	public bShortcutPlayerListener(bShortcut instance) {
		plugin = instance;
	}
	
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) {
            return;
        }
        String message = event.getMessage();
        Player player = event.getPlayer();
        
        String pre;
        int i = message.indexOf(' ');
        if(i < 0) { i = message.length(); }
        
        pre = (String) message.subSequence(0, i);
        message = (String) message.subSequence(i, message.length());
        
        if(bConfigManager.handleShortcuts(player, pre, message)) {
            event.setCancelled(true);
        }
	}
}