package me.duncanruns.duncanstweaks;

import me.duncanruns.duncanstweaks.mixinint.FloorSleeper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

public class DuncansTweaks implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("duncans-tweaks");

    public static RegistryEntry<Enchantment> getEnchantment(World world, Identifier identifier) throws NoSuchElementException {
        return world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getEntry(identifier).get();
    }

    @Override
    public void onInitialize() {
        registerSleepEvents();
    }

    private void registerSleepEvents() {
        EntitySleepEvents.ALLOW_SETTING_SPAWN.register((player, sleepingPos) -> !(((FloorSleeper) player).duncansTweaks$isFloorSleeping()));
        EntitySleepEvents.MODIFY_SLEEPING_DIRECTION.register((entity, sleepingPos, sleepingDirection) -> ((FloorSleeper) entity).duncansTweaks$isFloorSleeping() ? Direction.SOUTH : sleepingDirection);
        EntitySleepEvents.ALLOW_BED.register((entity, sleepingPos, state, vanillaResult) -> ((FloorSleeper) entity).duncansTweaks$isFloorSleeping() ? ActionResult.SUCCESS : ActionResult.PASS);
    }
}