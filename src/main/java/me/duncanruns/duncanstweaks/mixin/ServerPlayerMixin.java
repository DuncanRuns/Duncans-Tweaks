package me.duncanruns.duncanstweaks.mixin;

import com.mojang.authlib.GameProfile;
import me.duncanruns.duncanstweaks.mixinint.FloorSleeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    public ServerPlayerMixin(Level world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "bedBlocked", at = @At("HEAD"), cancellable = true)
    private void preventBedObstruction(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> info) {
        if (((FloorSleeper) this).duncansTweaks$isFloorSleeping()) {
            info.setReturnValue(false);
        }
    }
}
