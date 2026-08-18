package me.duncanruns.duncanstweaks.mixin;

import me.duncanruns.duncanstweaks.mixinint.FloorSleeper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BedItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BedItem.class)
public abstract class BedItemMixin extends BlockItem {

    @SuppressWarnings("ConstantConditions")
    public BedItemMixin() {
        super(null, null);
    }

    @Override
    public @NonNull InteractionResult useOn(@NonNull UseOnContext context) {
        InteractionResult result = super.useOn(context);

        // If the bed placement succeeded, cancel
        if (result.consumesAction()) return result;

        Player player = context.getPlayer();
        if (player == null) return result;

        // If client processing, cancel
        if (player.level().isClientSide()) return result;

        // Get some info
        BlockPos placeAttemptPos = new BlockPlaceContext(context).getClickedPos();

        // If the player collides with the theoretical bed that was trying to be placed
        if (player.isColliding(placeAttemptPos, Blocks.BED.white().defaultBlockState())) {

            // Set floor sleeping to true
            ((FloorSleeper) player).duncansTweaks$setFloorSleeping(true);

            // Try sleep at player's feet
            player.startSleepInBed(player.blockPosition()).ifLeft(reason -> {
                // If failed, set floor sleeping back to false and give the reason message
                ((FloorSleeper) player).duncansTweaks$setFloorSleeping(false); // setFloorSleeping(false) must also be called on player wake up
                if (reason.message() != null) {
                    player.sendOverlayMessage(reason.message());
                }
            });

            // SWING THE ARM
            return InteractionResult.SUCCESS;
        }
        return result;
    }
}
