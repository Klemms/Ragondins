package fr.klemms.ragondins.events;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.klemms.ragondins.ChatContent;
import fr.klemms.ragondins.Ragondins;
import fr.klemms.ragondins.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class JoinEvent implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
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
	
//	@EventHandler
//	public void onPlayerJoin(PlayerJoinEvent event) {
//		boolean keepInventory = Utils.isLocationInTheEnd(event.getPlayer().getWorld(), event.getPlayer().getLocation()) || event.getPlayer().getLocation().getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY);
//		BossBar bar = Bukkit.createBossBar(
//				keepInventory ? "§aStuff gardé à la mort dans ce monde" : "§cStuff droppé à la mort dans ce monde",
//				keepInventory ? BarColor.GREEN : BarColor.RED, BarStyle.SOLID);
//		bar.setProgress(1.0D);
//		bar.addPlayer(event.getPlayer());
//		bar.setVisible(true);
//		Bukkit.getScheduler().scheduleSyncDelayedTask(Ragondins.pl, () -> bar.setVisible(false), 100L);
//		event.getPlayer().getLocation().getWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, true);
//	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		/*if (event.getEntity().getGameMode() == GameMode.CREATIVE || event.getEntity().getGameMode() == GameMode.SPECTATOR) {
			event.setKeepInventory(true);
		} else if (Utils.isLocationInTheEnd(event.getEntity().getWorld(), event.getEntity().getLocation())) {
			event.setKeepInventory(true);
		} else {*/
			TextComponent deathText = new TextComponent(
					"[Server] Vous êtes mort, passez votre souris ici si vous souhaitez voir les coordonnées de votre cadavre");
			deathText.setColor(ChatColor.LIGHT_PURPLE);
			deathText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
					(new ComponentBuilder("X : ")).color(ChatColor.AQUA).italic(true)
							.append(String.valueOf(event.getEntity().getLocation().getBlockX()))
							.color(ChatColor.LIGHT_PURPLE).append(" Y : ").color(ChatColor.AQUA).italic(true)
							.append(String.valueOf(event.getEntity().getLocation().getBlockY()))
							.color(ChatColor.LIGHT_PURPLE).append(" Z : ").color(ChatColor.AQUA).italic(true)
							.append(String.valueOf(event.getEntity().getLocation().getBlockZ()))
							.color(ChatColor.LIGHT_PURPLE).create()));
			event.getEntity().spigot().sendMessage(deathText);
		//}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (event.getPlayer().getLocation().getY() <= -50D) {
			if (Utils.isLocationInTheEnd(event.getPlayer().getWorld(), event.getPlayer().getLocation())) {
				Location oldLoc = event.getPlayer().getLocation();
				Location newLocation = new Location(Bukkit.getWorld("world"), oldLoc.getX() * 8, 500D, oldLoc.getZ() * 8, oldLoc.getYaw(), oldLoc.getPitch());
				
				event.getPlayer().teleport(newLocation);
				event.getPlayer().sendMessage(ChatContent.GRAY + ChatContent.ITALIC + "Vous êtes tombé de l'End");
			}
		}
	}
}
