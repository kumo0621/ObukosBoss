package com.kumo0621.github.obukosboss;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LightningStrike {
    private Plugin plugin;

    public LightningStrike(Plugin plugin) {
        this.plugin = plugin;
    }

    public void strikeLightningNearbyPlayers(Entity entity, int radius) {
        List<Player> nearbyPlayers = entity.getWorld().getEntitiesByClass(Player.class).stream()
                .filter(player -> player.getLocation().distance(entity.getLocation()) <= radius)
                .collect(Collectors.toList());

        Random random = new Random();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : nearbyPlayers) {
                    int strikes = 1 + random.nextInt(5); // Randomly decide between 1 and 5 lightning strikes
                    for (int i = 0; i < strikes; i++) {
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }
            }
        }.runTask(plugin); // Execute the task asynchronously
    }
}

