package cz.mcdandy.winksmod.command;

import com.mojang.brigadier.CommandDispatcher;
import cz.mcdandy.winksmod.attachment.ModAttachments;
import cz.mcdandy.winksmod.attachment.Species;
import cz.mcdandy.winksmod.attachment.Transformation;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.PermissionCheck;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentType;

/**
 * The mod's commands. {@code /winksmod setSpecies <human|fairy>} selects the species, while
 * {@code /winksmod transform} toggles the fairy transformation. The transformation itself never comes from an item,
 * so no armour or inventory slot is ever touched by it.
 */
public final class WinksCommands {
    /** Same permission level vanilla's own {@code /gamemode} uses. */
    private static final PermissionCheck PERMISSION = Commands.LEVEL_GAMEMASTERS;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("winksmod")
                .then(Commands.literal("setSpecies")
                        .requires(Commands.hasPermission(PERMISSION))
                        .then(Commands.literal("human").executes(event -> setSpecies(event.getSource(), Species.HUMAN)))
                        .then(Commands.literal("fairy").executes(event -> setSpecies(event.getSource(), Species.FAIRY))))
                .then(Commands.literal("transform")
                        .requires(Commands.hasPermission(PERMISSION))
                        .executes(event -> toggleTransformation(event.getSource()))));
    }

    private static int setSpecies(CommandSourceStack source, Species species) {
        ServerPlayer player = source.getPlayer();

        if (player == null) {
            source.sendFailure(Component.translatable("command.winksmod.set_species.no_player"));
            return 0;
        }

        AttachmentType<Transformation> attachment = ModAttachments.TRANSFORMATION.get();
        Transformation next = player.getData(attachment).withSpecies(species);
        player.setData(attachment, next);

        if (!next.isFairy()) {
            // Ground them properly instead of leaving a stale glide velocity lying around.
            player.getData(ModAttachments.FLIGHT_MOTION.get()).reset();
            var energy = player.getData(ModAttachments.FAIRY_ENERGY.get());
            energy.setAmount(0.0D);
            player.setData(ModAttachments.FAIRY_ENERGY.get(), energy);
        }

        Component speciesName = Component.translatable("form.winksmod." + (species == Species.FAIRY ? "fairy" : "human"));
        source.sendSuccess(() -> Component.translatable("command.winksmod.set_species.success", speciesName), true);
        return 1;
    }

    private static int toggleTransformation(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();

        if (player == null) {
            source.sendFailure(Component.translatable("command.winksmod.transform.no_player"));
            return 0;
        }

        AttachmentType<Transformation> attachment = ModAttachments.TRANSFORMATION.get();
        Transformation current = player.getData(attachment);
        if (!current.isFairy()) {
            source.sendFailure(Component.translatable("command.winksmod.transform.not_fairy"));
            return 0;
        }

        Transformation next = current.withTransformed(!current.isTransformed());
        player.setData(attachment, next);
        if (!next.isTransformed()) {
            player.getData(ModAttachments.FLIGHT_MOTION.get()).reset();
        }

        source.sendSuccess(() -> Component.translatable(
                next.isTransformed() ? "command.winksmod.transform.enabled" : "command.winksmod.transform.disabled"
        ), true);
        return 1;
    }

    private WinksCommands() {
    }
}
