package fr.klemms.ragondins.events;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import fr.klemms.ragondins.ChatContent;
import fr.klemms.ragondins.Utils;

public class ChatEvents implements Listener {
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		TimeZone tz = TimeZone.getTimeZone("Europe/Paris");
		DateFormat date = new SimpleDateFormat("HH:mm:ss");
		date.setTimeZone(tz);

		if (event.getMessage().contains("<3")) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.spawnParticle(Particle.HEART, event.getPlayer().getLocation(), 50, 1D, 2D, 1D);
			}
		}
		
		if (event.getMessage().toLowerCase().contains("wololo")) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.playSound(pl.getLocation(), Sound.ENTITY_EVOKER_PREPARE_WOLOLO, 2F, 1F);
			}
		}
		
		event.setMessage(event.getMessage().replace("<3", ChatContent.RED + "♥" + ChatContent.RESET));
		
		event.setFormat(ChatContent.DARK_GRAY + date.format(new Date()) + ChatContent.GRAY + " [" + Utils.getPlayerTeamColor(event.getPlayer(), ChatContent.GRAY) + event.getPlayer().getDisplayName() + ChatContent.GRAY + "] " + ChatContent.WHITE + event.getMessage());
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (event.getMessage().toLowerCase().contains(pl.getDisplayName().toLowerCase()) || event.getMessage().toLowerCase().contains(pl.getName().toLowerCase())) {
				pl.playSound(pl.getLocation(), Sound.BLOCK_BELL_RESONATE, 2F, 2F);
			}
		}
		
		if (event.getMessage().toLowerCase().contains("@everyone")) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.playSound(pl.getLocation(), Sound.BLOCK_CONDUIT_DEACTIVATE, 2F, 2F);
			}
		}
		
		if (event.getMessage().toLowerCase().contains("@wizz")) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.playSound(pl.getLocation(), Sound.ENTITY_WITHER_SPAWN, 2F, 2F);
			}
		}
	}
}
