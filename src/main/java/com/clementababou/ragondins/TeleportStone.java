package com.clementababou.ragondins;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeleportStone {
	
	public static HashMap<UUID, Location> playerHomes;
	public static Path filePath;
	
	public static ItemStack getTeleportStoneStack() {
		ItemStack is = new ItemStack(Material.CHORUS_FRUIT, 1);
		ItemMeta im = is.getItemMeta();
		
		im.setDisplayName(ChatContent.AQUA + "Pierre de Téléportation");
		im.setLore(Arrays.asList(ChatContent.GRAY + ChatContent.ITALIC + "Vous téléporte à votre home"));
		im.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		is.setItemMeta(im);
		
		return is;
	}

	public static void setupHomes() {
		playerHomes = new HashMap<UUID, Location>();
		
		filePath = Ragondins.pl.getDataFolder().toPath().resolve("playerhomes.yml");

		if (Files.exists(filePath)) {
			loadHomes(filePath);
		}
		writeHomes(filePath);
	}
	
	public static void loadHomes(Path path) {
		YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(path.toFile());

		if (ymlFile.contains("Homes")) {
			Set<String> homes = ymlFile.getConfigurationSection("Homes").getKeys(false);
			
			for(String home : homes) {
				Location loc = new Location(
						Bukkit.getWorld(ymlFile.getString("Homes." + home + ".World")),
						ymlFile.getDouble("Homes." + home + ".X"),
						ymlFile.getDouble("Homes." + home + ".Y"),
						ymlFile.getDouble("Homes." + home + ".Z"),
						(float) ymlFile.getDouble("Homes." + home + ".Yaw"),
						(float) ymlFile.getDouble("Homes." + home + ".Pitch"));
				
				playerHomes.put(UUID.fromString(home), loc);
			}
		}
	}
	
	public static void writeHomes(Path path) {
		YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(path.toFile());
		
		for (UUID home : playerHomes.keySet()) {
			ymlFile.set("Homes." + home.toString() + ".X", playerHomes.get(home).getX());
			ymlFile.set("Homes." + home.toString() + ".Y", playerHomes.get(home).getY());
			ymlFile.set("Homes." + home.toString() + ".Z", playerHomes.get(home).getZ());
			ymlFile.set("Homes." + home.toString() + ".Yaw", playerHomes.get(home).getYaw());
			ymlFile.set("Homes." + home.toString() + ".Pitch", playerHomes.get(home).getPitch());
			ymlFile.set("Homes." + home.toString() + ".World", playerHomes.get(home).getWorld().getName());
		}
		
		try {
			ymlFile.save(path.toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
