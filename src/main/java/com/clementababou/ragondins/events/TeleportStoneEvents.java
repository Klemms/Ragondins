package com.clementababou.ragondins.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import com.clementababou.ragondins.ChatContent;
import com.clementababou.ragondins.Ragondins;
import com.clementababou.ragondins.TeleportStone;

public class TeleportStoneEvents implements Listener {

	@EventHandler
	public void cancelChorusFruit(PlayerTeleportEvent event) {
		if (event.getCause() == TeleportCause.CHORUS_FRUIT && event.getPlayer().hasMetadata("teleport_stone")) {
			event.setCancelled(true);
			event.getPlayer().removeMetadata("teleport_stone", Ragondins.pl);
		}
	}

	@EventHandler
	public void onTeleportStoneUse(PlayerItemConsumeEvent event) {
		if (event.getItem().isSimilar(TeleportStone.getTeleportStoneStack())) {
			if (TeleportStone.playerHomes.containsKey(event.getPlayer().getUniqueId())) {
				Location homeLoc = TeleportStone.playerHomes.get(event.getPlayer().getUniqueId());

				Bukkit.getScheduler().runTaskLater(Ragondins.pl, () -> {
					event.getPlayer().playSound(homeLoc, Sound.BLOCK_PORTAL_TRAVEL, 0.15F, 2F);
				}, 5);
				for(Player pl : Bukkit.getOnlinePlayers()) {
					pl.spawnParticle(Particle.TOTEM, homeLoc, 600, 1D, 2D, 1D);
				}
				
				event.getPlayer().setMetadata("teleport_stone", new FixedMetadataValue(Ragondins.pl, true));
				event.getPlayer().sendMessage(ChatContent.GRAY + ChatContent.ITALIC + "Vous avez été téléporté à votre home");
				event.getPlayer().teleport(homeLoc);
			} else {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatContent.GRAY + ChatContent.ITALIC + "Vous n'avez pas de home");
			}
		}
	}
	
	public boolean isTeleportStone(ItemStack is) {
		if (is.getType() == Material.CHORUS_FRUIT && is.hasItemMeta()) {
			ItemMeta im = is.getItemMeta();
			
			if (im.hasDisplayName() && im.getDisplayName().contains("Pierre de Téléportation")) {
				if (im.hasLore() && im.getLore().size() >= 1) {
					if (im.getLore().get(0).equalsIgnoreCase("Vous téléporte à votre home")) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
}

