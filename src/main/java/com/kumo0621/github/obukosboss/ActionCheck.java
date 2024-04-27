package com.kumo0621.github.obukosboss;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

public class ActionCheck {

    private final Plugin plugin;

    public ActionCheck(Plugin plugin) {
        this.plugin = plugin;
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
        // Define specific actions for Warden
    }

    private void handleEvokerAction(int action, Entity entity) {
        if (action == 0) {
            entity.setVelocity(entity.getVelocity().add(new org.bukkit.util.Vector(0, 1, 0))); // Jump
        } else if (action == 1) {
            new bombSummon(plugin, entity); // Summon bombs
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
