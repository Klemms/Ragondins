package com.clementababou.ragondins.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Smoker;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.clementababou.ragondins.ActionBar;
import com.clementababou.ragondins.ParticleThread;
import com.clementababou.ragondins.Ragondins;
import com.clementababou.ragondins.Utils;

public class InventoryEvents implements Listener {

	@EventHandler
	public void onInvClick(InventoryClickEvent event) {
		if (event.getClick() == ClickType.SHIFT_RIGHT && event.getClickedInventory().getItem(event.getSlot()) != null) {
			Material mat = event.getClickedInventory().getItem(event.getSlot()).getType();
			List<Block> blocks = Utils.getBlocksInRadius(event.getWhoClicked().getWorld(), event.getWhoClicked().getLocation(), 20);
			List<Location> found = new ArrayList<Location>();
			
			event.setCancelled(true);
			
			for (Block block : blocks) {
				switch (block.getType()) {
					case CHEST:
						Chest chest = (Chest) block.getState();
						if (Utils.isItemInInventory(chest.getBlockInventory(), mat))
							found.add(chest.getLocation());
						break;
					case BARREL:
						Barrel barrel = (Barrel) block.getState();
						if (Utils.isItemInInventory(barrel.getInventory(), mat))
							found.add(barrel.getLocation());
						break;
					case FURNACE:
						Furnace furnace = (Furnace) block.getState();
						if (Utils.isItemInInventory(furnace.getInventory(), mat))
							found.add(furnace.getLocation());
						break;
					case BLAST_FURNACE:
						BlastFurnace bfurnace = (BlastFurnace) block.getState();
						if (Utils.isItemInInventory(bfurnace.getInventory(), mat))
							found.add(bfurnace.getLocation());
						break;
					case SMOKER:
						Smoker smoker = (Smoker) block.getState();
						if (Utils.isItemInInventory(smoker.getInventory(), mat))
							found.add(smoker.getLocation());
						break;
					case DISPENSER:
						Dispenser dispenser = (Dispenser) block.getState();
						if (Utils.isItemInInventory(dispenser.getInventory(), mat))
							found.add(dispenser.getLocation());
						break;
					case DROPPER:
						Dropper dropper = (Dropper) block.getState();
						if (Utils.isItemInInventory(dropper.getInventory(), mat))
							found.add(dropper.getLocation());
						break;
					case HOPPER:
						Hopper hopper = (Hopper) block.getState();
						if (Utils.isItemInInventory(hopper.getInventory(), mat))
							found.add(hopper.getLocation());
						break;
				}
			}
			
			for (Location loc : found) {
				Bukkit.getScheduler().runTaskLaterAsynchronously(Ragondins.pl, new ParticleThread(10 * 4, loc, (Player) event.getWhoClicked()), 5);
				Location slimeloc = new Location(loc.getWorld(), loc.getX() + 0.5D, loc.getY() + 0.25D, loc.getZ() + 0.5D);
				Slime slime = loc.getWorld().spawn(slimeloc, Slime.class);
				slime.setSize(1);
				slime.setAI(false);
				slime.setSilent(true);
				slime.setInvisible(true);
				slime.setGlowing(true);
				slime.setCollidable(false);
				slime.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200000, 200, true, false));
				slime.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200000, 200, true, false));
				Bukkit.getScheduler().runTaskLater(Ragondins.pl, new Runnable() {

					@Override
					public void run() {
						slime.remove();
					}
					
				}, 5 * 20);
			}
			ActionBar.sendActionBar((Player) event.getWhoClicked(), found.size() + " conteneurs trouvé avec cet item", 4 * 20);
			if (found.size() > 0)
				event.getWhoClicked().closeInventory();
			((Player) event.getWhoClicked()).updateInventory();
		}
	}
}
