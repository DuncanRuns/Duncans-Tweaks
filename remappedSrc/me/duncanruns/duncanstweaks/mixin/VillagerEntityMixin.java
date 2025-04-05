package me.duncanruns.duncanstweaks.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {

    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract int getExperience();

    @Shadow
    public abstract VillagerData getVillagerData();

    @Inject(method = "beginTradeWith", at = @At("HEAD"))
    private void scrambleOnTalk(PlayerEntity customer, CallbackInfo info) {
        if (getExperience() == 0 && getVillagerData().getLevel() <= 1 && !customer.isSneaking()) {
            this.offers = new TradeOfferList();
            fillRecipes();
        }
    }
}
