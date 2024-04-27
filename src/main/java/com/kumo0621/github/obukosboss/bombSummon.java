package com.kumo0621.github.obukosboss;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class bombSummon {
    private Plugin plugin;  // プラグインインスタンスを保持するためのフィールド

    // コンストラクタで Plugin と Entity を受け取る
    public bombSummon(Plugin plugin, Entity entity) {
        this.plugin = plugin;
        summonCreepersAround((LivingEntity) entity);
    }

    public void summonCreepersAround(LivingEntity evoker) {
        Location evokerLocation = evoker.getLocation();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Location spawnLocation = evokerLocation.clone().add(random.nextInt(21) - 10, 0, random.nextInt(21) - 10);
            Creeper creeper = (Creeper) evoker.getWorld().spawnEntity(spawnLocation, EntityType.CREEPER);
            scheduleCreeperExplosion(creeper, random.nextInt(30) * 20); // 0-9 seconds delay
        }
    }

    public void scheduleCreeperExplosion(Creeper creeper, int delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                creeper.explode();
            }
        }.runTaskLater(plugin, delay);  // 正しい plugin インスタンスを使用
    }
}