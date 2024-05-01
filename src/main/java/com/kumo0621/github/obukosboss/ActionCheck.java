package com.kumo0621.github.obukosboss;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ActionCheck {

    private final Plugin plugin;
    private AnvilRain anvilRain;
    public ActionCheck(Plugin plugin) {
        this.plugin = plugin;
        this.anvilRain = new AnvilRain(plugin); // AnvilRain インスタンスを作成
    }

    public void run(int action, Entity entity) {
        EntityType type = entity.getType();
        switch (type) {
            case WARDEN:
                handleWardenAction(action, entity);
                break;
            case EVOKER:
                handleEvokerAction(action, entity);
                break;
            case PIGLIN_BRUTE:
                handlePiglinBruteAction(action, entity);
                break;
            case SHULKER:
                handleShulkerAction(action, entity);
                break;
            case ELDER_GUARDIAN:
                handleElderGuardianAction(action, entity);
                break;
            default:
                // Default action for other entities
                break;
        }
    }

    private void handleWardenAction(int action, Entity entity) {
        if (action == 0) {
            entity.setVelocity(entity.getVelocity().add(new org.bukkit.util.Vector(0, 1, 0))); // Jump
        } else if (action == 1) {
            new bombSummon(plugin, entity); // Summon bombs
        }
    }

    private void handleEvokerAction(int action, Entity entity) {
        System.out.println(action);
        if (action == 0 || action == 1 || action == 2 || action == 3 ) {
            entity.setVelocity(entity.getVelocity().add(new org.bukkit.util.Vector(0, 1, 0))); // Jump
        } else if (action == 5) {
            anvilRain.dropAnvils(entity, 10, 20); // インスタンスメソッドを呼び出す
        } else if (action == 6 || action == 7 || action == 8 || action == 9){
            if (entity instanceof LivingEntity) { // entity が LivingEntity のインスタンスであることを確認
                LivingEntity livingEntity = (LivingEntity) entity;
                int duration = 600; // 30秒間（600ティック）
                int amplifier = 5; // 効果のレベル1 (通常のレベル表記で0から始まるため)

                PotionEffect fireResistance = new PotionEffect(PotionEffectType.SPEED, duration, amplifier);
                livingEntity.addPotionEffect(fireResistance);
            }
        }
    }

    private void handlePiglinBruteAction(int action, Entity entity) {
        // Define specific actions for Piglin Brute
    }

    private void handleShulkerAction(int action, Entity entity) {
        // Define specific actions for Shulker
    }

    private void handleElderGuardianAction(int action, Entity entity) {
        // Define specific actions for Elder Guardian
    }
}
