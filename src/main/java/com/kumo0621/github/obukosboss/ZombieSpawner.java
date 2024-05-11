package com.kumo0621.github.obukosboss;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ZombieSpawner {

    private final Plugin plugin;

    public ZombieSpawner(Plugin plugin) {
        this.plugin = plugin;
    }

    public void spawnSpeedyBabyZombiesPeriodically(Entity targetEntity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (targetEntity.isValid()) {  // エンティティがまだ有効か確認
                    Zombie zombie = (Zombie) targetEntity.getWorld().spawnEntity(targetEntity.getLocation(), EntityType.ZOMBIE);
                    zombie.setBaby(true);  // ゾンビをチビゾンビ（ベビーゾンビ）に設定

                    // 移動速度6の効果を持たせる
                    PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 9, false, false);
                    zombie.addPotionEffect(speedEffect);
                } else {
                    this.cancel();  // ターゲットエンティティが無効の場合、タスクをキャンセル
                }
            }
        }.runTaskTimer(plugin, 0L, 60L);  // タスクを即時開始し、その後3秒ごと（60ティック）に実行
    }
}


