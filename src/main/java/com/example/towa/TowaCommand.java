package com.example.towa;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Main command:  /effek <effect> [<pos> [<scale> [<sound>]]]
 * Shortcut commands (generated from the converter settings) play one effect with fixed settings.
 *
 * The particle is sent to clients with count 0, which makes the client pass the first number
 * (delta x) to the provider as the scale. The same trick works with vanilla /particle:
 *   /particle towa:<effect> ~ ~ ~ 2 0 0 1 0   (scale 2)
 */
@Mod.EventBusSubscriber(modid = TowaMod.MODID)
public class TowaCommand {
    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> d = event.getDispatcher();
        d.register(Commands.literal("effek")
                .requires(src -> src.hasPermission(2))
                .then(Commands.argument("effect", StringArgumentType.word())
                        .suggests((ctx, b) -> SharedSuggestionProvider.suggest(TowaMod.TYPES.keySet(), b))
                        .executes(ctx -> play(ctx.getSource(), ctx.getSource().getPosition(),
                                StringArgumentType.getString(ctx, "effect"), 1.0F, null, 1.0F, 1.0F))
                        .then(Commands.argument("pos", Vec3Argument.vec3())
                                .executes(ctx -> play(ctx.getSource(), Vec3Argument.getVec3(ctx, "pos"),
                                        StringArgumentType.getString(ctx, "effect"), 1.0F, null, 1.0F, 1.0F))
                                .then(Commands.argument("scale", FloatArgumentType.floatArg(0.01F, 100.0F))
                                        .executes(ctx -> play(ctx.getSource(), Vec3Argument.getVec3(ctx, "pos"),
                                                StringArgumentType.getString(ctx, "effect"),
                                                FloatArgumentType.getFloat(ctx, "scale"), null, 1.0F, 1.0F))
                                        .then(Commands.argument("sound", StringArgumentType.word())
                                                .suggests((ctx, b) -> SharedSuggestionProvider.suggest(TowaMod.SOUNDS.keySet(), b))
                                                .executes(ctx -> play(ctx.getSource(), Vec3Argument.getVec3(ctx, "pos"),
                                                        StringArgumentType.getString(ctx, "effect"),
                                                        FloatArgumentType.getFloat(ctx, "scale"),
                                                        StringArgumentType.getString(ctx, "sound"), 1.0F, 1.0F)))))));
        d.register(Commands.literal("towa")
                .requires(src -> src.hasPermission(2))
                .executes(ctx -> play(ctx.getSource(), ctx.getSource().getPosition(), "towa", 1.0F, "towasound", 1.0F, 1.0F))
                .then(Commands.argument("pos", Vec3Argument.vec3())
                        .executes(ctx -> play(ctx.getSource(), Vec3Argument.getVec3(ctx, "pos"), "towa", 1.0F, "towasound", 1.0F, 1.0F))));
    }

    /** Spawns the effect for nearby players and, if a sound name is given, plays that sound at the same spot. */
    private static int play(CommandSourceStack src, Vec3 pos, String effect, float scale, String sound, float volume, float pitch) {
        var type = TowaMod.TYPES.get(effect);
        if (type == null) {
            src.sendFailure(Component.literal("Unknown effect: " + effect));
            return 0;
        }
        var event = sound == null ? null : TowaMod.SOUNDS.get(sound);
        if (sound != null && event == null) {
            src.sendFailure(Component.literal("Unknown sound: " + sound));
            return 0;
        }
        ServerLevel level = src.getLevel();
        level.sendParticles(type.get(), pos.x, pos.y, pos.z, 0, scale, 0.0, 0.0, 1.0);
        if (event != null) {
            level.playSound(null, pos.x, pos.y, pos.z, event.get(), SoundSource.PLAYERS, volume, pitch);
        }
        src.sendSuccess(() -> Component.literal("Played " + effect + (sound == null ? "" : " + " + sound) + " at scale " + scale), true);
        return 1;
    }
}
