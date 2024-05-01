package com.kumo0621.github.obukosboss;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
public final class ObukosBoss extends JavaPlugin {
    HashMap<UUID, Double> evokerHealthMap = new HashMap<>();
    HashMap<UUID, BossBar> bossBars = new HashMap<>();
    private Set<EntityType> targetEntityTypes = EnumSet.of(
            EntityType.WARDEN,
            EntityType.EVOKER,
            EntityType.PIGLIN_BRUTE,
            EntityType.SHULKER,
            EntityType.ELDER_GUARDIAN
    );
    @Override
    public void onEnable() {
        // Register any listeners
        getServer().getPluginManager().registerEvents(new EvokerDeathListener(this), this);

        // Health and BossBar updates
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity entity : Bukkit.getWorlds().get(0).getEntities()) {
                    if (targetEntityTypes.contains(entity.getType())) {
                        LivingEntity target = (LivingEntity) entity;
                        UUID entityId = target.getUniqueId();

                        // Set up health and potion effects
                        if (!evokerHealthMap.containsKey(entityId)) {
                            target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2000);
                            target.setHealth(2000);
                            target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 4));
                            target.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 4));
                            evokerHealthMap.put(entityId, 2000.0);
                        }

                        // Set up and update boss bars
                        BossBar bossBar = bossBars.computeIfAbsent(entityId, k -> Bukkit.createBossBar("Health: " + target.getType(), BarColor.PURPLE, BarStyle.SOLID));
                        bossBar.setProgress(target.getHealth() / target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                        updateBossBarVisibility(target, bossBar);

                        // Check if the entity is dead
                        if (target.isDead() || !target.isValid()) {
                            bossBar.removeAll();
                            bossBars.remove(entityId);
                            evokerHealthMap.remove(entityId);
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0L, 20L);  // Update every second

        // Perform actions every 20 seconds
        new BukkitRunnable() {
            @Override
            public void run() {
                ActionCheck actionCheck = new ActionCheck(ObukosBoss.this);
                for (Entity entity : Bukkit.getWorlds().get(0).getEntities()) {
                    if (targetEntityTypes.contains(entity.getType())) {
                        LivingEntity target = (LivingEntity) entity;
                        UUID entityId = target.getUniqueId();

                        if (evokerHealthMap.containsKey(entityId)) {
                            Random random = new Random();
                            int action = random.nextInt(10);
                            actionCheck.run(action, target);
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0L, 400L);  // Perform every 20 seconds
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Clean up any resources
        bossBars.values().forEach(BossBar::removeAll);
        bossBars.clear();
        evokerHealthMap.clear();
    }

    private void updateBossBarVisibility(LivingEntity evoker, BossBar bossBar) {
        Location evokerLocation = evoker.getLocation();
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getWorld().equals(evoker.getWorld()) && player.getLocation().distance(evokerLocation) <= 100) {
                bossBar.addPlayer(player);
            } else {
                bossBar.removePlayer(player);
            }
        });
    }
}
