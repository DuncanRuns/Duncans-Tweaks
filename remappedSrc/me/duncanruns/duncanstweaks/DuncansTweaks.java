package me.duncanruns.duncanstweaks;

import me.duncanruns.duncanstweaks.mixinint.FloorSleeper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuncansTweaks implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("duncans-tweaks");

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