package com.kumo0621.github.obukosboss;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class AnvilRain {
    private Plugin plugin; // 修正：staticを削除

    public AnvilRain(Plugin plugin) {
        this.plugin = plugin; // 修正：staticを削除
    }

    // 修正：メソッドをインスタンスメソッドに変更
    public void dropAnvils(Entity entity, int radius, int quantity) {
        World world = entity.getWorld();
        Location center = entity.getLocation();
        Random random = new Random();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < quantity; i++) {
                    int dx = random.nextInt(radius * 2 + 1) - radius;
                    int dz = random.nextInt(radius * 2 + 1) - radius;
                    int dy = 20; // Height from which anvils will drop

                    Location spawnLocation = center.clone().add(dx, dy, dz);
                    spawnLocation.setY(world.getHighestBlockYAt(spawnLocation) + 20); // Drop from above the highest block

                    world.spawnFallingBlock(spawnLocation, Material.LAVA.createBlockData());
                }
            }
        }.runTaskLater(plugin, 20L); // Delay to start dropping anvils
    }
}
