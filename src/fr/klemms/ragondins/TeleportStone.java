package fr.klemms.ragondins;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class TeleportStone {
	
	private static HashMap<UUID, Location> playerHomes;

	public static void setupHomes() {
		playerHomes = new HashMap<UUID, Location>();
		
		Path filePath = Ragondins.pl.getDataFolder().toPath().resolve("playerhomes.yml");

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
