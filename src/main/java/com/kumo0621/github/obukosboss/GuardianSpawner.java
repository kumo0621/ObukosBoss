package com.kumo0621.github.obukosboss;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class GuardianSpawner {
    private Plugin plugin;

    public GuardianSpawner(Plugin plugin) {
        this.plugin = plugin;
    }

    public void spawnGuardians(Entity target, int number) {
        World world = target.getWorld();
        Random random = new Random();
        new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < number; i++) {
                    int dx = random.nextInt(10) - 5;  // 10マスの範囲内でランダムに位置をずらす
                    int dz = random.nextInt(10) - 5;
                    Location spawnLocation = target.getLocation().add(dx, 0, dz);
                    spawnLocation.setY(world.getHighestBlockYAt(spawnLocation));  // 地面の高さに設定

                    Guardian guardian = (Guardian) world.spawnEntity(spawnLocation, EntityType.GUARDIAN);
                    setCustomStats(guardian);
                }
            }
        }.runTask(plugin);  // 非同期タスクとして実行
    }

    private void setCustomStats(LivingEntity entity) {
        entity.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
        entity.setHealth(100);
        entity.getAttribute(org.bukkit.attribute.Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(200);
    }
}
