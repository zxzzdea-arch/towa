# Towa

Forge 1.20.1 mod generated from Effekseer effects. Needs AAA Particles at runtime.

Effects: towa

## Build
1. Set `aaaVersion` in gradle.properties to the AAA Particles Forge 1.20.1 version from Modrinth.
2. Run `gradle wrapper` once (or copy the wrapper from a Forge MDK), then `./gradlew build`.
3. Put the jar from build/libs and AAA Particles in your mods folder.

## Use in game
`/effek towa ~ ~ ~ 1.5 towasound` (effect, position, scale, sound; needs permission level 2)

Shortcut commands: `/towa`

Sounds: towasound (also usable with `/playsound towa:<name> master @a`)\n

Vanilla also works, scale is the first delta number: `/particle towa:towa ~ ~ ~ 1.5 0 0 1 0`

In code (client side): `AAALevel.addParticle(level, new ParticleEmitterInfo(new ResourceLocation("towa", "towa")).position(x, y, z));`
