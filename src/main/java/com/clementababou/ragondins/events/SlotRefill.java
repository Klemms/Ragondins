package com.clementababou.ragondins.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.clementababou.ragondins.ActionBar;
import com.clementababou.ragondins.ChatContent;
import com.clementababou.ragondins.Ragondins;

public class SlotRefill implements Listener {
	@EventHandler
	public void onBlockPlaced(BlockPlaceEvent event) {
		if (event.getHand() == EquipmentSlot.HAND && Ragondins.isSimilarMaterial(event.getBlock().getType(), event.getItemInHand().getType())) {
			if (event.getPlayer().getInventory().getItem(event.getPlayer().getInventory().getHeldItemSlot()).getAmount() - 1 == 0) {
				int slot = findDifferent(event.getPlayer(), event.getBlock().getType(), event.getPlayer().getInventory().getHeldItemSlot());
				if (slot >= 0) {
					event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), event.getPlayer().getInventory().getItem(slot));
					event.getPlayer().getInventory().setItem(slot, null);
					event.getPlayer().updateInventory();
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.1F, 1.8F);
					ActionBar.sendActionBar(event.getPlayer(), ChatContent.GRAY + ChatContent.ITALIC + Ragondins.getItemName(event.getPlayer().getInventory().getItemInMainHand()) + " replacé depuis l'inventaire");
				}
			}
		} else if (event.getHand() == EquipmentSlot.OFF_HAND && Ragondins.isSimilarMaterial(event.getBlock().getType(), event.getItemInHand().getType()) && event.getPlayer().getInventory().getItemInOffHand().getAmount() - 1 == 0) {
			int slot = findDifferent(event.getPlayer(), event.getBlock().getType(), -1);
			if (slot >= 0) {
				event.getPlayer().getInventory().setItemInOffHand(event.getPlayer().getInventory().getItem(slot));
				event.getPlayer().getInventory().setItem(slot, null);
				event.getPlayer().updateInventory();
				event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.1F, 1.8F);
				ActionBar.sendActionBar(event.getPlayer(), ChatContent.GRAY + ChatContent.ITALIC + Ragondins.getItemName(event.getPlayer().getInventory().getItemInOffHand()) + " replacé depuis l'inventaire");
			}
		}
	}

	public static int findDifferent(Player player, Material type, int slot) {
		for (int i = 0; i < (player.getInventory().getStorageContents()).length; i++) {
			if (i != slot && player.getInventory().getStorageContents()[i] != null && Ragondins.isSimilarMaterial(type, player.getInventory().getStorageContents()[i].getType()))
				return i;
		}
		return -1;
	}
}
