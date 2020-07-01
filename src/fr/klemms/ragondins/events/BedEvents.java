package fr.klemms.ragondins.events;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.klemms.ragondins.Ragondins;

public class BedEvents implements Listener {
	
	@EventHandler
	public void onBedEntered(PlayerBedEnterEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.SURVIVAL || event.getPlayer().getGameMode() == GameMode.ADVENTURE)
			Ragondins.playersInBed.add(event.getPlayer());
	}

	@EventHandler
	public void onBedLeft(PlayerBedLeaveEvent event) {
		Ragondins.playersInBed.remove(event.getPlayer());
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Ragondins.playersInBed.remove(event.getPlayer());
	}
}
