package dev.mk26710.tcr;

import com.mojang.brigadier.context.CommandContext;
import dev.mk26710.tcr.config.TCRConfig;
import dev.mk26710.tcr.enums.PrefixType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TwitchCommandHandler {
    private @Nullable BetterTwirk twirk = null;

    public int connect(final @NotNull CommandContext<FabricClientCommandSource> ctx) {
        if (twirk != null) {
            if (twirk.isConnected()) {
                twirk.disconnect();
            }
            twirk = null;
        }

        final var channel = ctx.getArgument("channel", String.class);

        twirk = new BetterTwirk(channel);

        final var listener = new TwirkToMinecraftChatListener(twirk);
        twirk.addListener(listener);

        final var msg = Text.translatable("commands.connect.process", twirk.channel).formatted(Formatting.GRAY);
        ctx.getSource().sendFeedback(msg);

        twirk.connect().exceptionally((e) -> {
            final var errorMessage = Text.translatable("commands.connect.error", e.getMessage())
                    .formatted(Formatting.RED);
            ctx.getSource().sendError(errorMessage);

            return false;
        });

        return 0;
    }

    public int disconnect(final @NotNull CommandContext<FabricClientCommandSource> ctx) {
        if (twirk != null && twirk.isConnected()) {
            twirk.disconnect();
            twirk = null;
        }

        return 0;
    }

    public int configReload(final @NotNull CommandContext<FabricClientCommandSource> ctx) {
        TCRConfig.getInstance().readFromDisk();
        ctx.getSource().sendFeedback(
                Text.literal("TwitchChatReader config reloaded!").styled((s) -> s.withColor(Formatting.GREEN)));

        return 0;
    }

    private MutableText getPrefixFeedback(final @NotNull PrefixType type) {
        String prefix = null;
        String message = "Current ";

        switch (type) {
            case GLOBAL -> {
                prefix = TCRConfig.getInstance().prefixes.global;
                message += "global";
            }
            case SUBSCRIBERS -> {
                prefix = TCRConfig.getInstance().prefixes.subscribers;
                message += "subscribers";
            }
            case MODERATORS -> {
                prefix = TCRConfig.getInstance().prefixes.moderators;
                message += "moderators";
            }
        }

        message += " prefix is ";

        final MutableText response = Text.literal(message).styled((s) -> s.withColor(Formatting.GRAY));
        if (prefix == null) {
            response.append(Text.literal("not set.").styled((s) -> s.withColor(Formatting.RED)));
        } else {
            response.append(Utils.coloredTextFromString(prefix));
        }

        return response;
    }

    public int configPrefixGlobalGet(final @NotNull CommandContext<FabricClientCommandSource> ctx) {
        final var response = getPrefixFeedback(PrefixType.GLOBAL);
        ctx.getSource().sendFeedback(response);

        return 0;
    }

    public int configPrefixSubscribersGet(final @NotNull CommandContext<FabricClientCommandSource> ctx) {
        final var response = getPrefixFeedback(PrefixType.SUBSCRIBERS);
        ctx.getSource().sendFeedback(response);

        return 0;
    }

    public int configPrefixModeratorsGet(final @NotNull CommandContext<FabricClientCommandSource> ctx) {
        final var response = getPrefixFeedback(PrefixType.MODERATORS);
        ctx.getSource().sendFeedback(response);

        return 0;
    }

    public int configPrefixGlobalSet(final @NotNull CommandContext<FabricClientCommandSource> ctx) {
        final var newPrefix = ctx.getArgument("prefix", String.class);
        if (newPrefix.isEmpty() || newPrefix.isBlank()) {
            ctx.getSource().sendError(Text.literal("Prefixes must have at least one character!")
                    .styled((s) -> s.withColor(Formatting.RED)));
            return 1;
        }

        TCRConfig.getInstance().prefixes.global = newPrefix;
        TCRConfig.getInstance().saveToDisk();

        MutableText response = Text.empty();
        response.append(Text.literal("New global prefix was set to ").styled((s) -> s.withColor(Formatting.GRAY)));
        response.append(Utils.coloredTextFromString(TCRConfig.getInstance().prefixes.global));

        ctx.getSource().sendFeedback(response);

        return 0;
    }

    public int configPrefixGlobalUnset(final @NotNull CommandContext<FabricClientCommandSource> ctx) {
        TCRConfig.getInstance().prefixes.global = null;
        TCRConfig.getInstance().saveToDisk();

        var response = Text.literal("Global prefix was unset.").styled((s) -> s.withColor(Formatting.GRAY));
        ctx.getSource().sendFeedback(response);

        return 0;
    }

    public int configPrefixSubSet(final @NotNull CommandContext<FabricClientCommandSource> ctx) {
        final String newPrefix = ctx.getArgument("prefix", String.class);
        if (newPrefix.isBlank() || newPrefix.isEmpty()) {
            final MutableText msg = Text.literal("New prefix cannot be empty!")
                    .styled((s) -> s.withColor(Formatting.RED));
            ctx.getSource().sendError(msg);

            return 1;
        }

        TCRConfig.getInstance().prefixes.subscribers = newPrefix;
        TCRConfig.getInstance().saveToDisk();

        final MutableText msg = Text.empty();
        msg.append(Text.literal("New subscribers prefix was set to ").styled((s) -> s.withColor(Formatting.GRAY)));
        msg.append(Utils.coloredTextFromString(newPrefix));

        ctx.getSource().sendFeedback(msg);

        return 0;
    }

    public int configPrefixSubUnset(final @NotNull CommandContext<FabricClientCommandSource> ctx) {
        TCRConfig.getInstance().prefixes.subscribers = null;
        TCRConfig.getInstance().saveToDisk();

        var response = Text.literal("Subscribers prefix was unset.").styled((s) -> s.withColor(Formatting.GRAY));
        ctx.getSource().sendFeedback(response);

        return 0;
    }

    public int configPrefixModSet(final @NotNull CommandContext<FabricClientCommandSource> ctx) {
        final String newPrefix = ctx.getArgument("prefix", String.class);
        if (newPrefix.isBlank() || newPrefix.isEmpty()) {
            final MutableText msg = Text.literal("New prefix cannot be empty!")
                    .styled((s) -> s.withColor(Formatting.RED));
            ctx.getSource().sendError(msg);

            return 1;
        }

        TCRConfig.getInstance().prefixes.moderators = newPrefix;
        TCRConfig.getInstance().saveToDisk();

        final MutableText msg = Text.empty();
        msg.append(Text.literal("New moderators prefix was set to ").styled((s) -> s.withColor(Formatting.GRAY)));
        msg.append(Utils.coloredTextFromString(newPrefix));

        ctx.getSource().sendFeedback(msg);

        return 0;
    }

    public int configPrefixModUnset(final @NotNull CommandContext<FabricClientCommandSource> ctx) {
        TCRConfig.getInstance().prefixes.moderators = null;
        TCRConfig.getInstance().saveToDisk();

        var response = Text.literal("Moderators prefix was cleared!").styled((s) -> s.withColor(Formatting.GRAY));
        ctx.getSource().sendFeedback(response);

        return 0;
    }
}
