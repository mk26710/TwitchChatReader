package moe.polar.tcr;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import static moe.polar.tcr.Utils.getChat;

public class TwitchCommand {


    @Nullable
    public static BetterTwirk twirk = null;

    public static int connect(CommandContext<FabricClientCommandSource> ctx) {
        if (twirk != null) {
            if (twirk.isConnected()) {
                twirk.disconnect();
            }
            twirk = null;
        }

        final var channel = ctx.getArgument("channel", String.class);

        twirk = new BetterTwirk(channel);
        twirk.addListener(new TwirkToMinecraftChatListener(twirk));

        final var msg = Text
                .literal("Connecting to https://twitch.tv/" + twirk.channel + "...")
                .styled(s -> s.withColor(Formatting.GRAY));

        getChat().addMessage(msg);

        twirk.connect().exceptionally((e) -> {
            final var errorMessage = Text
                    .literal("ERROR: ")
                    .append(e.getMessage())
                    .styled(s -> s.withColor(Formatting.RED));

            getChat().addMessage(errorMessage);

            return false;
        });


        return 0;
    }

    public static int disconnect(CommandContext<FabricClientCommandSource> ctx) {
        if (twirk != null && twirk.isConnected())
            twirk.disconnect();

        return 0;
    }
}
