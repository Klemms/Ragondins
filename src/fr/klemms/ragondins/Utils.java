package fr.klemms.ragondins;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;

public class Utils {
	public static Biome getBiomeFromLocation(World world, Location location) {
		return world.getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

	public static boolean isLocationInTheOverworld(World world, Location location) {
		if (!isLocationInTheEnd(world, location) && !isLocationInTheNether(world, location))
			return true;
		return false;
	}

	public static boolean isLocationInTheEnd(World world, Location location) {
		switch (getBiomeFromLocation(world, location)) {
			case END_BARRENS:
				return true;
			case END_HIGHLANDS:
				return true;
			case END_MIDLANDS:
				return true;
			case SMALL_END_ISLANDS:
				return true;
			case THE_END:
				return true;
			default:
				return false;
		}
	}

	public static boolean isLocationInTheNether(World world, Location location) {
		switch (getBiomeFromLocation(world, location)) {
			case BASALT_DELTAS:
				return true;
			case CRIMSON_FOREST:
				return true;
			case NETHER_WASTES:
				return true;
			case WARPED_FOREST:
				return true;
			case SOUL_SAND_VALLEY:
				return true;
			default:
				return false;
		}
	}
}
