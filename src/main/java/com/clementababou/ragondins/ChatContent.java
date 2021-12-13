package com.clementababou.ragondins;

public class ChatContent {

	public static final String BLACK = "§0";
	public static final String DARK_BLUE = "§1";
	public static final String DARK_GREEN = "§2";
	public static final String DARK_AQUA = "§3";
	public static final String DARK_RED = "§4";
	public static final String DARK_PURPLE = "§5";
	public static final String GOLD = "§6";
	public static final String GRAY = "§7";
	public static final String DARK_GRAY = "§8";
	public static final String BLUE = "§9";
	public static final String GREEN = "§a";
	public static final String AQUA = "§b";
	public static final String RED = "§c";
	public static final String PINK = "§d";
	public static final String YELLOW = "§e";
	public static final String WHITE = "§f";
	public static final String OBFUSCATED = "§k";
	public static final String BOLD = "§l";
	public static final String STRIKETHROUGH = "§m";
	public static final String UNDERLINE = "§n";
	public static final String ITALIC = "§o";
	public static final String RESET = "§r";

	public static String translateColorCodes(String string) {
		return string.replace("&0", BLACK).replace("&1", DARK_BLUE).replace("&2", DARK_GREEN).replace("&3", DARK_AQUA)
				.replace("&4", DARK_RED).replace("&5", DARK_PURPLE).replace("&6", GOLD).replace("&7", GRAY)
				.replace("&8", DARK_GRAY).replace("&9", BLUE).replace("&a", GREEN).replace("&b", AQUA)
				.replace("&c", RED).replace("&d", PINK).replace("&e", YELLOW).replace("&f", WHITE)
				.replace("&k", OBFUSCATED).replace("&l", BOLD).replace("&m", STRIKETHROUGH).replace("&n", UNDERLINE)
				.replace("&o", ITALIC).replace("&r", RESET);
	}
}
