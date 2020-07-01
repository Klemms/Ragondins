package fr.klemms.ragondins.events;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import fr.klemms.ragondins.ChatContent;
import fr.klemms.ragondins.Ragondins;
import fr.klemms.ragondins.Utils;

public class WorldEvents implements Listener {

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		boolean keepInventory = Utils.isLocationInTheEnd(event.getPlayer().getWorld(), event.getPlayer().getLocation()) || event.getPlayer().getLocation().getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY);
		BossBar bar = Bukkit.createBossBar(
				keepInventory ? ChatContent.GREEN + "Stuff gardé à la mort dans ce monde" : ChatContent.RED + "Stuff droppé à la mort dans ce monde",
				keepInventory ? BarColor.GREEN : BarColor.RED,
				BarStyle.SOLID);
		
		bar.setProgress(1D);
		bar.addPlayer(event.getPlayer());
		bar.setVisible(true);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Ragondins.pl, () -> {
			bar.setVisible(false);
		}, 5 * 20);
		
		event.getPlayer().getLocation().getWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, true);
	}
}
