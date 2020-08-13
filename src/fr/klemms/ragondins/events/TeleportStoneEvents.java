package fr.klemms.ragondins.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeleportStoneEvents implements Listener {

	@EventHandler
	public void onTeleportStoneUse(PlayerItemConsumeEvent event) {
		if (isTeleportStone(event.getItem())) {
			
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

