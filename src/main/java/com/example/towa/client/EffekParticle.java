package com.example.towa.client;

import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.resources.ResourceLocation;

/** Invisible vanilla particle that hands the real work to AAA Particles once. */
public class EffekParticle extends NoRenderParticle {
    private final ResourceLocation effect;
    private final float scale;
    private boolean fired;

    public EffekParticle(ClientLevel level, double x, double y, double z, ResourceLocation effect, float scale) {
        super(level, x, y, z);
        this.effect = effect;
        this.scale = scale;
        this.lifetime = 2;
    }

    @Override
    public void tick() {
        if (!fired) {
            fired = true;
            AAALevel.addParticle(this.level, new ParticleEmitterInfo(effect).position(this.x, this.y, this.z).scale(scale));
        }
        super.tick();
    }
}
