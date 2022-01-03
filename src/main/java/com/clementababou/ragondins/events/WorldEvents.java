package com.clementababou.ragondins.events;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.block.Beehive;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.clementababou.ragondins.ActionBar;
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
	public void onLeafDecay(LeavesDecayEvent event) {
		if (event.getBlock().getType() == Material.DARK_OAK_LEAVES) {
			// 20% chance to drop an extra sapling
			if (ThreadLocalRandom.current().nextInt(10) <= 1) {
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.DARK_OAK_SAPLING, 1));
			}
		}
		
		if (event.getBlock().getType() == Material.JUNGLE_LEAVES) {
			// 10% chance to drop an extra sapling
			if (ThreadLocalRandom.current().nextInt(10) == 0) {
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.JUNGLE_SAPLING, 1));
			}	
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().isSneaking() && event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Beehive) {
			event.setCancelled(true);
			
			Beehive beehive = (Beehive) event.getClickedBlock().getState();
			String str = "Il y a " + beehive.getEntityCount() + "/" + beehive.getMaxEntities() + " abeilles dans cette ruche. ";
			
			if (event.getClickedBlock().getBlockData() instanceof org.bukkit.block.data.type.Beehive) {
				org.bukkit.block.data.type.Beehive beehiveData = (org.bukkit.block.data.type.Beehive) event.getClickedBlock().getBlockData();
				
				str += "Niveau de miel " + beehiveData.getHoneyLevel() + "/" + beehiveData.getMaximumHoneyLevel() + ". ";
			}
			
			if (beehive.isSedated())
				str += "Ruche sédatée.";
			
			ActionBar.sendActionBar(event.getPlayer(), str, 3 * 20);
		}
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
