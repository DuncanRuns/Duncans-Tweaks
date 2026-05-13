package me.duncanruns.duncanstweaks;

import me.duncanruns.duncanstweaks.mixinint.FloorSleeper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.util.EventResult;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

public class DuncansTweaks implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("duncans-tweaks");

    public static Holder<Enchantment> getEnchantment(Level world, Identifier identifier) throws NoSuchElementException {
        return world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(identifier).orElseThrow();
    }

    @Override
    public void onInitialize() {
        registerSleepEvents();
        LOGGER.info("Duncan's Tweaks initialized");
    }

    private void registerSleepEvents() {
        EntitySleepEvents.ALLOW_SETTING_SPAWN.register((player, _) -> !(((FloorSleeper) player).duncansTweaks$isFloorSleeping()));
        EntitySleepEvents.MODIFY_SLEEPING_DIRECTION.register((entity, _, sleepingDirection) -> ((FloorSleeper) entity).duncansTweaks$isFloorSleeping() ? Direction.SOUTH : sleepingDirection);
        EntitySleepEvents.ALLOW_BED.register((entity, _, _, _) -> ((FloorSleeper) entity).duncansTweaks$isFloorSleeping() ? EventResult.ALLOW : EventResult.PASS);
    }
}