package me.duncanruns.duncanstweaks.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.entity.npc.villager.VillagerData;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {

    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, ServerLevel world) {
        super(entityType, world);
    }

    @Shadow
    public abstract int getVillagerXp();

    @Shadow
    public abstract VillagerData getVillagerData();

    @Inject(method = "startTrading", at = @At("HEAD"))
    private void scrambleOnTalk(Player player, CallbackInfo info) {
        Level entityWorld = level();
        if (entityWorld.isClientSide()) return;
        if (getVillagerXp() == 0 && getVillagerData().level() <= 1 && !player.isShiftKeyDown()) {
            this.offers = new MerchantOffers();
            updateTrades((ServerLevel) entityWorld);
        }
    }
}
