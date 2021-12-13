package com.clementababou.ragondins.events;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.clementababou.ragondins.ActionBar;
import com.clementababou.ragondins.ChatContent;
import com.clementababou.ragondins.Ragondins;
import com.clementababou.ragondins.Utils;

public class ItemPickupEvent implements Listener {
	
	@EventHandler
	public void onPlayerPickupItem(EntityPickupItemEvent event) {
		if (event.getEntityType() == EntityType.PLAYER && !event.isCancelled()) {
			Player player = (Player) event.getEntity();
			ItemStack item = event.getItem().getItemStack();
			
			int totalItemCount = 0;
			byte b;
			int i;
			
			ItemStack[] arrayOfItemStack;
			for (i = (arrayOfItemStack = player.getInventory().getContents()).length, b = 0; b < i;) {
				ItemStack is = arrayOfItemStack[b];
				if (is != null && is.isSimilar(item))
					totalItemCount += is.getAmount();
				b++;
			}
			
			totalItemCount += item.getAmount();
			
			int stacks = totalItemCount / item.getMaxStackSize();
			int remainingItems = totalItemCount % item.getMaxStackSize();
			
			ActionBar.sendActionBar(player,
					ChatContent.GRAY + Ragondins.getItemName(item) + ChatContent.ITALIC + " +" + item.getAmount() + ChatContent.RESET + ChatContent.GRAY + " | "
							+ totalItemCount + " item" + ((totalItemCount > 1) ? "s" : "")
							+ ((stacks > 0 && item.getMaxStackSize() > 1)
									? (" | " + stacks + "s" + ((remainingItems > 0) ? (" + " + remainingItems) : ""))
									: ""));
		}
	}

	@EventHandler
	public void onPlayerPickupEnderPearl(EntityPickupItemEvent event) {
		ItemStack item = event.getItem().getItemStack();
		LivingEntity entity = event.getEntity();
		
		if (item == null)
			return;
		
		if (event.getEntityType() == EntityType.PLAYER && item.getType() == Material.ENDER_PEARL && Utils.isLocationInTheEnd(entity.getWorld(), entity.getLocation())) {
			Player player = (Player) event.getEntity();
			if (!player.isSneaking())
				event.setCancelled(true);
		}
	}
}
