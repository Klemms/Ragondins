package com.clementababou.ragondins.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.clementababou.ragondins.ChatContent;
import com.clementababou.ragondins.Utils;

import net.md_5.bungee.api.ChatColor;

public class ChatEvent implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (!event.getMessage().startsWith("/")) {
			String playerName = Utils.getColoredPlayerName(event.getPlayer(), ChatContent.WHITE);
			event.setCancelled(true);
			
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.sendMessage(ChatContent.DARK_GRAY + Utils.getCurrentTime() + " " + ChatContent.GRAY + "[" + playerName + ChatColor.GRAY + "] " + ChatContent.WHITE + event.getMessage());
			}
		}
	}
}
