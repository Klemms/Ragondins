package com.clementababou.ragondins.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.clementababou.ragondins.Ragondins;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class WorldEdit implements Listener {
	@EventHandler
	public void onRightClickBlock(PlayerInteractEvent event) {
		if (event.getPlayer().isOp() && event.getPlayer().getGameMode() == GameMode.CREATIVE && event.getHand() == EquipmentSlot.HAND && event.hasItem() && event.getItem().getType() == Material.WOODEN_AXE) {
			if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
				event.setCancelled(true);
				Ragondins.worldEdit_first.put(event.getPlayer(), event.getClickedBlock().getLocation().clone());
				event.getPlayer().sendMessage("First location set");
				if (Ragondins.worldEdit_second.containsKey(event.getPlayer()))
					showMenu(event.getPlayer());
			}
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(true);
				Ragondins.worldEdit_second.put(event.getPlayer(), event.getClickedBlock().getLocation().clone());
				event.getPlayer().sendMessage("Second location set");
				if (Ragondins.worldEdit_first.containsKey(event.getPlayer()))
					showMenu(event.getPlayer());
			}
		}
	}

	public static void showMenu(Player player) {
		int x1 = Ragondins.worldEdit_first.get(player).getBlockX();
		int y1 = Ragondins.worldEdit_first.get(player).getBlockY();
		int z1 = Ragondins.worldEdit_first.get(player).getBlockZ();
		int x2 = Ragondins.worldEdit_second.get(player).getBlockX();
		int y2 = Ragondins.worldEdit_second.get(player).getBlockY();
		int z2 = Ragondins.worldEdit_second.get(player).getBlockZ();

		player.sendMessage(" ");
		
		String command_fill = "/fill " + x1 + " " + y1 + " " + z1 + " " + x2 + " " + y2 + " " + z2 + " ";
		TextComponent commandFill = new TextComponent(command_fill);
		commandFill.setColor(ChatColor.AQUA);
		commandFill.setItalic(Boolean.valueOf(true));
		commandFill.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command_fill));
		player.spigot().sendMessage(commandFill);
		
		String command_clone = "/clone " + x1 + " " + y1 + " " + z1 + " " + x2 + " " + y2 + " " + z2 + " ";
		TextComponent commandClone = new TextComponent(command_clone);
		commandClone.setColor(ChatColor.AQUA);
		commandClone.setItalic(Boolean.valueOf(true));
		commandClone.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command_clone));
		player.spigot().sendMessage(commandClone);
	}
}
