package com.gam0zing.stay_on_my_target;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(StayOnMyTarget.MOD_ID)
public class StayOnMyTarget
{
    public static final String MOD_ID = "stay_on_my_target";
    private static final Logger LOGGER = LogUtils.getLogger();

    public StayOnMyTarget(FMLJavaModLoadingContext context) {
        IEventBus eventBus = context.getModEventBus();

        context.registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ModConfig.SPEC_COMMON);

        eventBus.register(ModConfig.class);
    }

}
