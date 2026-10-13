package com.example.towa.client;

import com.example.towa.TowaMod;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TowaMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        TowaMod.TYPES.forEach((name, type) -> {
            ResourceLocation effect = new ResourceLocation(TowaMod.MODID, name);
            ParticleProvider<SimpleParticleType> provider =
                    (t, level, x, y, z, vx, vy, vz) -> new EffekParticle(level, x, y, z, effect, (float) (vx > 0 ? vx : 1.0));
            event.registerSpecial(type.get(), provider);
        });
    }
}
