package com.clementababou.ragondins;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;

public class ChatContent {

	public static final String BLACK = "\u00A70";
	public static final String DARK_BLUE = "\u00A71";
	public static final String DARK_GREEN = "\u00A72";
	public static final String DARK_AQUA = "\u00A73";
	public static final String DARK_RED = "\u00A74";
	public static final String DARK_PURPLE = "\u00A75";
	public static final String GOLD = "\u00A76";
	public static final String GRAY = "\u00A77";
	public static final String DARK_GRAY = "\u00A78";
	public static final String BLUE = "\u00A79";
	public static final String GREEN = "\u00A7a";
	public static final String AQUA = "\u00A7b";
	public static final String RED = "\u00A7c";
	public static final String PINK = "\u00A7d";
	public static final String YELLOW = "\u00A7e";
	public static final String WHITE = "\u00A7f";
	public static final String OBFUSCATED = "\u00A7k";
	public static final String BOLD = "\u00A7l";
	public static final String STRIKETHROUGH = "\u00A7m";
	public static final String UNDERLINE = "\u00A7n";
	public static final String ITALIC = "\u00A7o";
	public static final String RESET = "\u00A7r";
	
	public static String translateColorCodes(String string) {
		List<String> matches = new ArrayList<String>();
		Matcher m = Pattern.compile("(&#[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9])").matcher(string);
		
		while(m.find()) {
			ChatColor color = null;
			try {
				Method ofMethod = ChatColor.class.getMethod("of", String.class);
				color = (ChatColor) ofMethod.invoke(null, m.group().substring(1));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			string = string.replace(m.group(), color.toString());
		}
		
		return string
				.replace("&0", ChatContent.BLACK)
				.replace("&1", ChatContent.DARK_BLUE)
				.replace("&2", ChatContent.DARK_GREEN)
				.replace("&3", ChatContent.DARK_AQUA)
				.replace("&4", ChatContent.DARK_RED)
				.replace("&5", ChatContent.DARK_PURPLE)
				.replace("&6", ChatContent.GOLD)
				.replace("&7", ChatContent.GRAY)
				.replace("&8", ChatContent.DARK_GRAY)
				.replace("&9", ChatContent.BLUE)
				.replace("&a", ChatContent.GREEN)
				.replace("&b", ChatContent.AQUA)
				.replace("&c", ChatContent.RED)
				.replace("&d", ChatContent.PINK)
				.replace("&e", ChatContent.YELLOW)
				.replace("&f", ChatContent.WHITE)
				.replace("&k", ChatContent.OBFUSCATED)
				.replace("&l", ChatContent.BOLD)
				.replace("&m", ChatContent.STRIKETHROUGH)
				.replace("&n", ChatContent.UNDERLINE)
				.replace("&o", ChatContent.ITALIC)
				.replace("&r", ChatContent.RESET);
	}
}
