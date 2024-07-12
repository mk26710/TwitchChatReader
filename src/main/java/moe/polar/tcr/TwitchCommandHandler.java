package moe.polar.tcr;

import com.mojang.brigadier.context.CommandContext;
import moe.polar.tcr.config.TCRConfig;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
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
            final var errorMessage = Text.translatable("commands.connect.error", e.getMessage()).formatted(Formatting.RED);
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
        Utils.getChat().addMessage(Text.of("TCR config reloaded!"));

        return 0;
    }
}
