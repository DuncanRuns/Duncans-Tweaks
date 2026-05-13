package me.duncanruns.duncanstweaks.mixin;

import me.duncanruns.duncanstweaks.DuncansTweaks;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "getProjectile", at = @At("RETURN"), cancellable = true)
    private void allowNoArrowMixin(ItemStack heldWeapon, CallbackInfoReturnable<ItemStack> cir) {
        if (cir.getReturnValue().isEmpty() && heldWeapon.getItem() instanceof BowItem && EnchantmentHelper.getItemEnchantmentLevel(DuncansTweaks.getEnchantment(level(), Enchantments.INFINITY.identifier()), heldWeapon) > 0) {
            cir.setReturnValue(Items.ARROW.getDefaultInstance());
        }
    }
}
