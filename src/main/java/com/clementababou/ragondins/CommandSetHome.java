package com.clementababou.ragondins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetHome implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player pl = (Player) sender;
			
			TeleportStone.playerHomes.put(pl.getUniqueId(), pl.getLocation());
			TeleportStone.writeHomes(TeleportStone.filePath);
			
			pl.sendMessage(ChatContent.GRAY + ChatContent.ITALIC + "Votre nouveau home a été défini");
			return true;
		}
		
		return false;
	}
}
