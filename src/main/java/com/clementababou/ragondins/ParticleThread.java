package com.clementababou.ragondins;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleThread implements Runnable {
	
	private int timesToRun;
	private Location loc;
	private Player player;
	
	public ParticleThread(int timesToRun, Location loc, Player player) {
		this.timesToRun = timesToRun;
		this.loc = loc;
		this.player = player;
	}

	@Override
	public void run() {
		for (int timer = 0; timer < this.timesToRun; timer++) {
			this.player.spawnParticle(Particle.REDSTONE, loc.getX() + 0.5D, loc.getY() + 0.5D, loc.getZ() + 0.5D, 20, 0.25D, 0.25D, 0.25D, new Particle.DustOptions(Utils.getRandomBukkitColor(), 1f));
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
