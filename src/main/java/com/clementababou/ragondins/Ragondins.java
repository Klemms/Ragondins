package com.clementababou.ragondins;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.clementababou.ragondins.events.InventoryEvents;
import com.clementababou.ragondins.events.ItemPickupEvent;
import com.clementababou.ragondins.events.JoinEvent;
import com.clementababou.ragondins.events.SlotRefill;
import com.clementababou.ragondins.events.TeleportStoneEvents;
import com.clementababou.ragondins.events.WorldEdit;
import com.clementababou.ragondins.events.WorldEvents;

public class Ragondins extends JavaPlugin {
	
	public static Ragondins pl;

	public static HashSet<Player> playersInBed;
	public static HashMap<Player, Location> worldEdit_first;
	public static HashMap<Player, Location> worldEdit_second;
	
	public static NamespacedKey teleportStoneKey;

	public void onEnable() {
		pl = this;
		teleportStoneKey = new NamespacedKey(Ragondins.pl, "teleport_stone");
		
		addCustomRecipes();
		
		getServer().getPluginManager().registerEvents(new ItemPickupEvent(), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new SlotRefill(), this);
		getServer().getPluginManager().registerEvents(new WorldEdit(), this);
		getServer().getPluginManager().registerEvents(new WorldEvents(), this);
		getServer().getPluginManager().registerEvents(new TeleportStoneEvents(), this);
		getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
		
		playersInBed = new HashSet<>();
		worldEdit_first = new HashMap<>();
		worldEdit_second = new HashMap<>();
		TeleportStone.setupHomes();
		
		getCommand("sethome").setExecutor(new CommandSetHome());
		
		World world = Bukkit.getWorld("world");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			if (world.getTime() > 12000L && playersInBed.size() > 0) {
				if (getPlayersInSurvival() == playersInBed.size()) {
					world.setTime(world.getTime() + (60 + 20 * getPlayersInSurvival()));
				} else {
					world.setTime(world.getTime() + (20 + 10 * getPlayersInSurvival()));
				}
			} else {
				playersInBed.clear();
			}
		}, 10L, 5L);
	}

	public void onDisable() {
		Bukkit.removeRecipe(teleportStoneKey);
	}
	
	public static void addCustomRecipes() {
		if (Bukkit.getRecipe(teleportStoneKey) != null)
			return;
		
		ShapedRecipe teleportStoneRecipe = new ShapedRecipe(teleportStoneKey, TeleportStone.getTeleportStoneStack());
		
		teleportStoneRecipe.shape("FFF", "FEF", "FFF");
		teleportStoneRecipe.setIngredient('F', Material.CHORUS_FRUIT);
		teleportStoneRecipe.setIngredient('E', Material.ENDER_EYE);
		
		Bukkit.addRecipe(teleportStoneRecipe);
	}

	public static String getItemName(ItemStack itemStack) {
		if (itemStack.hasItemMeta()) {
			ItemMeta im = itemStack.getItemMeta();
			if (im.hasDisplayName())
				return im.getDisplayName();
			if (im.hasLocalizedName())
				return im.getLocalizedName();
			return WordUtils.capitalizeFully(itemStack.getType().toString().replace('_', ' '));
		}
		return WordUtils.capitalizeFully(itemStack.getType().toString().replace('_', ' '));
	}

	public static boolean isSimilarMaterial(Material mat1, Material mat2) {
		if ((mat1 == Material.TORCH || mat1 == Material.WALL_TORCH)
				&& (mat2 == Material.TORCH || mat2 == Material.WALL_TORCH))
			return true;
		if ((mat1 == Material.SOUL_TORCH || mat1 == Material.SOUL_WALL_TORCH)
				&& (mat2 == Material.SOUL_TORCH || mat2 == Material.SOUL_WALL_TORCH))
			return true;
		if ((mat1 == Material.REDSTONE_TORCH || mat1 == Material.REDSTONE_WALL_TORCH)
				&& (mat2 == Material.REDSTONE_TORCH || mat2 == Material.REDSTONE_WALL_TORCH))
			return true;
		if ((mat1 == Material.POTATO || mat1 == Material.POTATOES)
				&& (mat2 == Material.POTATO || mat2 == Material.POTATOES))
			return true;
		if (mat1 == Material.WHEAT && mat2 == Material.WHEAT_SEEDS)
			return true;
		if ((mat1 == Material.CARROT || mat1 == Material.CARROTS)
				&& (mat2 == Material.CARROT || mat2 == Material.CARROTS))
			return true;
		if ((mat1 == Material.BEETROOT_SEEDS || mat1 == Material.BEETROOTS)
				&& (mat2 == Material.BEETROOT_SEEDS || mat2 == Material.BEETROOTS))
			return true;
		return (mat1 == mat2);
	}

	public static int getPlayersInSurvival() {
		int players = 0;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
				players++;
		}
		return players;
	}
}
