package com.clementababou.ragondins;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

public class Utils {
	
	public static String getCurrentTime() {
		TimeZone tz = TimeZone.getTimeZone("Europe/Paris");
		DateFormat date = new SimpleDateFormat("HH:mm:ss");
		date.setTimeZone(tz);
		
		return date.format(new Date());
	}
	
	public static String getColoredPlayerName(Player pl, String defaultColor, String prefix) {
		return getPlayerTeamColor(pl, defaultColor) + prefix + pl.getDisplayName();
	}
	
	/**
	 * 
	 * @param pl
	 * @param defaultColor
	 * @return Player name colored with its team color (or defaultColor if none)
	 */
	public static String getColoredPlayerName(Player pl, String defaultColor) {
		return getPlayerTeamColor(pl, defaultColor) + pl.getDisplayName();
	}
	
	/**
	 * 
	 * @param pl
	 * @return Player name colored with its team color (or Gray if none)
	 */
	public static String getColoredPlayerName(Player pl) {
		return getColoredPlayerName(pl, ChatContent.GRAY);
	}
	
	public static Team getPlayerTeam(Player pl) {
		return pl.getScoreboard().getPlayerTeam(pl);
	}
	
	public static String getPlayerTeamColor(Player pl, String defaultColor) {
		Team team = getPlayerTeam(pl);
		
		return team == null ? defaultColor : team.getColor().toString();
	}
	
	public static boolean isPlayerName(String playerName) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (pl.getName().equalsIgnoreCase(playerName) || pl.getDisplayName().equalsIgnoreCase(playerName))
				return true;
		}
		
		return false;
	}
	
	public static Biome getBiomeFromLocation(World world, Location location) {
		world.loadChunk(location.getChunk());
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
	
	public static List<Block> getBlocksInRadius(World world, Location location, int radius) {
		List<Block> blocks = new ArrayList<Block>();
		
		for (double x = location.getX() - radius; x <= location.getX() + radius; x++) {
			for (double y = location.getY() - radius; y <= location.getY() + radius; y++) {
				for (double z = location.getZ() - radius; z <= location.getZ() + radius; z++) {
					blocks.add(world.getBlockAt(new Location(world, x, y, z)));
				}
			}
		}
		
		return blocks;
	}
	
	public static boolean isItemInInventory(Inventory inv, Material mat) {
		for (ItemStack is : inv.getContents()) {
			if (is != null && is.getType() == mat)
				return true;
		}
		
		return false;
	}
	
	public static Color getRandomBukkitColor() {
		int rand = ThreadLocalRandom.current().nextInt(16);
		switch (rand) {
			case 0:
				return Color.AQUA;
			case 1:
				return Color.BLACK;
			case 2:
				return Color.BLUE;
			case 3:
				return Color.FUCHSIA;
			case 4:
				return Color.GRAY;
			case 5:
				return Color.GREEN;
			case 6:
				return Color.LIME;
			case 7:
				return Color.MAROON;
			case 8:
				return Color.NAVY;
			case 9:
				return Color.OLIVE;
			case 10:
				return Color.ORANGE;
			case 11:
				return Color.PURPLE;
			case 12:
				return Color.RED;
			case 13:
				return Color.SILVER;
			case 14:
				return Color.TEAL;
			case 15:
				return Color.WHITE;
			case 17:
				return Color.YELLOW;
			default:
				return Color.WHITE;
					
		}
	}
}
