package com.kumo0621.github.obukosboss;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;

public class RandomTeleport {
    private Plugin plugin;

    public RandomTeleport(Plugin plugin) {
        this.plugin = plugin;
    }

    public void teleportNearbyPlayers(Entity entity, int radius, int teleportRadius) {
        List<Player> nearbyPlayers = entity.getWorld().getEntitiesByClass(Player.class).stream()
                .filter(player -> player.getLocation().distance(entity.getLocation()) <= radius)
                .collect(Collectors.toList());

        Random random = new Random();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : nearbyPlayers) {
                    int dx = random.nextInt(teleportRadius * 2 + 1) - teleportRadius;
                    int dz = random.nextInt(teleportRadius * 2 + 1) - teleportRadius;

                    Location newLocation = player.getLocation().clone().add(dx, 0, dz);
                    newLocation.setY(entity.getWorld().getHighestBlockYAt(newLocation)); // Ensure the player doesn't spawn underground or in the air.

                    player.teleport(newLocation);
                }
            }
        }.runTask(plugin); // Execute the task asynchronously
    }
}
