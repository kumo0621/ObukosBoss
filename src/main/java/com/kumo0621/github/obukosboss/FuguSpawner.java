package com.kumo0621.github.obukosboss;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PufferFish;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class FuguSpawner {

    private final Plugin plugin;

    public FuguSpawner(Plugin plugin) {
        this.plugin = plugin;
    }

    public void spawnSpeedyPufferfishPeriodically(Entity targetEntity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (targetEntity.isValid()) {
                    for (int i = 0; i < 3; i++) {
                        // `EntityType.PUFFERFISH`を指定してスポーン
                        PufferFish pufferfish = (PufferFish) targetEntity.getWorld().spawnEntity(targetEntity.getLocation(), EntityType.PUFFERFISH);
                        // `PufferFish`エンティティに移動速度のポーション効果を付与
                        PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false);
                        pufferfish.addPotionEffect(speedEffect);
                    }
                } else {
                    this.cancel();  // ターゲットエンティティが無効の場合、タスクをキャンセル
                }
            }
        }.runTaskTimer(plugin, 0L, 60L);  // タスクを即時開始し、その後3秒ごと（60ティック）に実行
    }
}
