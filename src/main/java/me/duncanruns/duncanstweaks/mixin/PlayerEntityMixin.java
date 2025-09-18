package me.duncanruns.duncanstweaks.mixin;

import me.duncanruns.duncanstweaks.DuncansTweaks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getProjectileType", at = @At("RETURN"), cancellable = true)
    private void allowNoArrowMixin(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (cir.getReturnValue().isEmpty() && stack.getItem() instanceof BowItem && EnchantmentHelper.getLevel(DuncansTweaks.getEnchantment(getEntityWorld(), Enchantments.INFINITY.getValue()), stack) > 0) {
            cir.setReturnValue(Items.ARROW.getDefaultStack());
        }
    }
}
