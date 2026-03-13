package com.gam0zing.stay_on_my_target;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class ModConfig {
    // #region common
    // SPEC定义部分
    public static final ForgeConfigSpec SPEC_COMMON;
    public static final ForgeConfigSpec.DoubleValue CUSTOM_PRECISION;
    // 运行时部分
    public static double customPrecision = 8000D;
    // #endregion

    static {
        final ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();

        commonBuilder.comment("common");
        CUSTOM_PRECISION = commonBuilder.comment("Used to control the precision and upper limit of the velocity component in network packets. \nThe precision is 1 divided by this number, and the upper limit is 32767 divided by this number. \nFor example, the vanilla value is 8000, so the precision of the velocity component is 0.000125, and the upper limit is 4.095875 (block/tick) (vanilla was artificially set to 3.9).").defineInRange("CUSTOM_PRECISION",8000D, 100D, 32767D);
        SPEC_COMMON = commonBuilder.build();
    }

    @SubscribeEvent
    public static void load(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == SPEC_COMMON) customPrecision = CUSTOM_PRECISION.get();
    }

    @SubscribeEvent
    public static void load(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == SPEC_COMMON) customPrecision = CUSTOM_PRECISION.get();
    }
}
