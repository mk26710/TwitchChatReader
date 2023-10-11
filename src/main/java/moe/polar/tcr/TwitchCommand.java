package moe.polar.tcr;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static moe.polar.tcr.Utils.getChat;
import static moe.polar.tcr.TwitchChatReader.TWIRK;

public class TwitchCommand {
    public static int connect(CommandContext<FabricClientCommandSource> ctx) {
        if (TWIRK != null) {
            if (TWIRK.isConnected()) {
                TWIRK.disconnect();
            }
            TWIRK = null;
        }

        final var channel = ctx.getArgument("channel", String.class);

        TWIRK = new BetterTwirk(channel);
        TWIRK.addListener(new TwirkToMinecraftChatListener(TWIRK));

        final var msg = Text
                .literal("Connecting to https://twitch.tv/" + TWIRK.channel + "...")
                .styled(s -> s.withColor(Formatting.GRAY));

        getChat().addMessage(msg);

        TWIRK.connect().exceptionally((e) -> {
            final var errorMessage = Text
                    .literal("ERROR: ")
                    .append(e.getMessage())
                    .styled(s -> s.withColor(0xFF0000));

            getChat().addMessage(errorMessage);

            return false;
        });


        return 0;
    }

    public static int disconnect(CommandContext<FabricClientCommandSource> ctx) {
        if (TWIRK != null && TWIRK.isConnected())
            TWIRK.disconnect();

        return 0;
    }
}
