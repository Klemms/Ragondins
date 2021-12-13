package com.clementababou.ragondins.events;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.clementababou.ragondins.ChatContent;
import com.clementababou.ragondins.Ragondins;
import com.clementababou.ragondins.Utils;

import net.md_5.bungee.api.ChatColor;

public class WorldEvents implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		//event.setJoinMessage(ChatContent.DARK_GRAY + Utils.getCurrentTime() + " " + ChatContent.ITALIC + Utils.getColoredPlayerName(event.getPlayer(), ChatContent.YELLOW, ChatContent.ITALIC) + ChatContent.YELLOW + " s'est connecté");
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		//event.setQuitMessage(ChatContent.DARK_GRAY + Utils.getCurrentTime() + " " + Utils.getColoredPlayerName(event.getPlayer(), ChatContent.YELLOW, ChatContent.ITALIC) + ChatContent.YELLOW + " s'est déconnecté");
	}

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		if (Utils.isLocationInTheEnd(event.getPlayer().getWorld(), event.getPlayer().getLocation())) {
			BossBar bar = Bukkit.createBossBar(
					ChatColor.BLUE + "En cas de chute dans le vide vous réapparaitrez dans le monde normal",
					BarColor.BLUE, BarStyle.SOLID);
			
			bar.setProgress(1D);
			bar.addPlayer(event.getPlayer());
			bar.setVisible(true);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Ragondins.pl, () -> bar.setVisible(false), 100L);
			
			event.getPlayer().getLocation().getWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, true);
		}
	}

	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.ANCIENT_DEBRIS) {
			if (Utils.isLocationInTheNether(event.getPlayer().getWorld(), event.getPlayer().getLocation())) {
				Bukkit.broadcastMessage(Utils.getPlayerTeamColor(event.getPlayer(), ChatContent.GRAY) + ChatContent.ITALIC + event.getPlayer().getDisplayName() + ChatContent.GRAY + " a trouvé un Ancient Debris");
			}
		}
	}
}
