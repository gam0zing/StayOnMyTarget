package com.gam0zing.stay_on_my_target.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Unique
    private static final double CUSTOM_PRECISION = 1000.0;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private ClientLevel level;

    @Inject(method = "handleSetEntityMotion", at = @At("HEAD"), cancellable = true)
    public void handleSetEntityMotion(ClientboundSetEntityMotionPacket pPacket, CallbackInfo ci) {
        PacketUtils.ensureRunningOnSameThread(pPacket, (ClientPacketListener) (Object) this, this.minecraft);

        Entity entity = this.level.getEntity(pPacket.getId());
        if (entity == null) return;

        double motX = pPacket.getXa() / CUSTOM_PRECISION;
        double motY = pPacket.getYa() / CUSTOM_PRECISION;
        double motZ = pPacket.getZa() / CUSTOM_PRECISION;

        entity.lerpMotion(motX, motY, motZ);
        ci.cancel();
    }
}