package me.duncanruns.duncanstweaks.mixin;

import me.duncanruns.duncanstweaks.mixinint.FloorSleeper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements FloorSleeper {
    @Unique
    private boolean floorSleeping = false;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public boolean duncansTweaks$isFloorSleeping() {
        return floorSleeping;
    }

    @Override
    public void duncansTweaks$setFloorSleeping(boolean floorSleeping) {
        this.floorSleeping = floorSleeping;
    }

    @Inject(method = "wakeUp", at = @At("HEAD"))
    private void stopFloorSleeping(CallbackInfo info) {
        duncansTweaks$setFloorSleeping(false);
    }
}
