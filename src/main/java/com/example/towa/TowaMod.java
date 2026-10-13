package com.example.towa;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.Map;

@Mod(TowaMod.MODID)
public class TowaMod {
    public static final String MODID = "towa";
    /** One particle type per .efkefc file; the name equals the file name in assets/towa/effeks/. */
    public static final String[] EFFECTS = { "towa" };

    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);
    public static final Map<String, RegistryObject<SimpleParticleType>> TYPES = new LinkedHashMap<>();

    static {
        for (String name : EFFECTS) {
            TYPES.put(name, PARTICLES.register(name, () -> new SimpleParticleType(true)));
        }
    }

    /** One sound event per .ogg file; the name equals the file name in assets/towa/sounds/ (see sounds.json). */
    public static final String[] SOUND_NAMES = { "towasound" };

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final Map<String, RegistryObject<SoundEvent>> SOUNDS = new LinkedHashMap<>();

    static {
        for (String name : SOUND_NAMES) {
            SOUNDS.put(name, SOUND_EVENTS.register(name,
                    () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, name))));
        }
    }

    public TowaMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        PARTICLES.register(bus);
        SOUND_EVENTS.register(bus);
    }
}
