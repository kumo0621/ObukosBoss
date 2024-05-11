package com.kumo0621.github.obukosboss;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;  // Zombieクラスを使用
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PigrinSpawner {

    private final Plugin plugin;

    public PigrinSpawner(Plugin plugin) {
        this.plugin = plugin;
    }

    public void spawnSpeedyZombifiedPiglinsPeriodically(Entity targetEntity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (targetEntity.isValid()) {
                    for (int i = 0; i < 5; i++) {
                        // ゾンビピグリンをZombieとしてスポーン
                        Zombie zombifiedPiglin = (Zombie) targetEntity.getWorld().spawnEntity(targetEntity.getLocation(), EntityType.ZOMBIFIED_PIGLIN);

                        // 移動速度のポーション効果を設定
                        PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 9, false, false);
                        zombifiedPiglin.addPotionEffect(speedEffect);
                    }
                } else {
                    this.cancel();  // ターゲットエンティティが無効の場合、タスクをキャンセル
                }
            }
        }.runTaskTimer(plugin, 0L, 60L);  // タスクを即時開始し、その後3秒ごと（60ティック）に実行
    }
}
