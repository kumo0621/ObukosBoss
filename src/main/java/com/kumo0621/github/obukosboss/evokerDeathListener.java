package com.kumo0621.github.obukosboss;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

class EvokerDeathListener implements Listener {

    private ObukosBoss plugin;
    private Set<EntityType> targetEntityTypes = EnumSet.of(
            EntityType.WARDEN,
            EntityType.EVOKER,
            EntityType.PIGLIN_BRUTE,
            EntityType.SHULKER,
            EntityType.ELDER_GUARDIAN
    );

    public EvokerDeathListener(ObukosBoss plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (targetEntityTypes.contains(event.getEntity().getType())) {
            UUID entityId = event.getEntity().getUniqueId();
            BossBar bossBar = plugin.bossBars.get(entityId);
            if (bossBar != null) {
                bossBar.removeAll();  // Remove all players from the boss bar
                plugin.bossBars.remove(entityId);  // Remove the boss bar from the map
            }
            plugin.evokerHealthMap.remove(entityId);  // Remove the entity's health record
        }
    }

    // 対象となるエンティティタイプのセットを定義
    private static final Set<EntityType> TARGET_ENTITY_TYPES = EnumSet.of(
            EntityType.WARDEN,        // ヴォーデン
            EntityType.EVOKER,        // エヴォーカー
            EntityType.PIGLIN_BRUTE,  // ピグリンブルート
            EntityType.SHULKER,       // シェルカー
            EntityType.ELDER_GUARDIAN // エルダーガーディアン
    );

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        boolean effectApplied = false;

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (TARGET_ENTITY_TYPES.contains(entity.getType())) {
                    double distance = entity.getLocation().distance(player.getLocation());
                    if (distance <= 20) {
                        if (!effectApplied) { // 効果がまだ適用されていない場合のみ適用
                            PotionEffect effect = new PotionEffect(PotionEffectType.SLOW_DIGGING, 400, 2); // 400ティック = 20秒, レベル3
                            player.addPotionEffect(effect);
                            effectApplied = true; // 効果を適用したことをマーク
                        }
                    }
                }
            }
        }
    }
}

