package me.duncanruns.duncanstweaks.mixin;

import me.duncanruns.duncanstweaks.mixinint.FloorSleeper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BedItem.class)
public abstract class BedItemMixin extends BlockItem {

    public BedItemMixin(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ActionResult result = super.useOnBlock(context);

        // If the bed placement succeeded, cancel
        if (result.isAccepted()) return result;

        PlayerEntity player = context.getPlayer();
        if (player == null) return result;

        // If client processing, cancel
        if (player.getEntityWorld().isClient()) return result;

        // Get some info
        BlockPos placeAttemptPos = new ItemPlacementContext(context).getBlockPos();

        // If the player collides with the theoretical bed that was trying to be placed
        if (player.collidesWithStateAtPos(placeAttemptPos, Blocks.WHITE_BED.getDefaultState())) {

            // Set floor sleeping to true
            ((FloorSleeper) player).duncansTweaks$setFloorSleeping(true);

            // Try sleep at player's feet
            player.trySleep(player.getBlockPos()).ifLeft(reason -> {
                // If failed, set floor sleeping back to false and give the reason message
                ((FloorSleeper) player).duncansTweaks$setFloorSleeping(false); // setFloorSleeping(false) must also be called on player wake up
                if (reason.getMessage() != null) {
                    player.sendMessage(reason.getMessage(), true);
                }
            });

            // SWING THE ARM
            return ActionResult.SUCCESS;
        }
        return result;
    }
}
