package me.duncanruns.duncanstweaks.mixin;

import com.mojang.authlib.GameProfile;
import me.duncanruns.duncanstweaks.mixinint.FloorSleeper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "isBedObstructed", at = @At("HEAD"), cancellable = true)
    private void preventBedObstruction(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> info) {
        if (((FloorSleeper) this).duncansTweaks$isFloorSleeping()) {
            info.setReturnValue(false);
        }
    }
}
