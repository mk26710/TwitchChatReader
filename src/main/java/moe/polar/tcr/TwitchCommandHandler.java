package moe.polar.tcr;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import static moe.polar.tcr.Utils.getChat;

public class TwitchCommandHandler {
    private @Nullable BetterTwirk twirk = null;

    public int connect(CommandContext<FabricClientCommandSource> ctx) {
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

        final var msg = Text
                .literal("Connecting to https://twitch.tv/" + twirk.channel + "...")
                .styled(s -> s.withColor(Formatting.GRAY));

        getChat().addMessage(msg);

        twirk.connect().exceptionally((e) -> {
            final var errorMessage = Text
                    .literal("ERROR: ")
                    .append(e.getMessage())
                    .styled(s -> s.withColor(0xFF0000));

            getChat().addMessage(errorMessage);

            return false;
        });


        return 0;
    }

    public int disconnect(CommandContext<FabricClientCommandSource> ctx) {
        if (twirk != null && twirk.isConnected()) {
            twirk.disconnect();
            twirk = null;
        }

        return 0;
    }
}
