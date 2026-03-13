package com.gam0zing.stay_on_my_target.mixin;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientboundSetEntityMotionPacket.class)
public class ClientboundSetEntityMotionPacketMixin {

    @Unique
    private static final double CUSTOM_PRECISION = 8000.0;

    @Shadow
    @Final
    @Mutable
    private int xa;

    @Shadow
    @Final
    @Mutable
    private int ya;

    @Shadow
    @Final
    @Mutable
    private int za;

    @Inject(method = "<init>(ILnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
    private void onConstruct(int pId, Vec3 pDeltaMovement, CallbackInfo ci) {
        double customLimit = Short.MAX_VALUE / CUSTOM_PRECISION;

        double highest = Math.max(Math.max(pDeltaMovement.x, pDeltaMovement.z), pDeltaMovement.y);
        double scale = 1;
        boolean dirty = false;
        if (highest > customLimit) {
            scale *= customLimit / highest;
            dirty = true;
        }

        double d1 = pDeltaMovement.x;
        double d2 = pDeltaMovement.y;
        double d3 = pDeltaMovement.z;

        if (dirty) {
            d1 *= scale;
            d2 *= scale;
            d3 *= scale;
        }

        this.xa = (int)(d1 * CUSTOM_PRECISION);
        this.ya = (int)(d2 * CUSTOM_PRECISION);
        this.za = (int)(d3 * CUSTOM_PRECISION);
    }
}