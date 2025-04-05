package me.duncanruns.duncanstweaks.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowItem.class)
public abstract class BowItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void allowNoArrowMixin(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> info) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (EnchantmentHelper.getLevel(Enchantments.INFINITY, itemStack) > 0) {
            user.setCurrentHand(hand);
            info.setReturnValue(TypedActionResult.consume(itemStack));
        }
    }
}
