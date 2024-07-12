package moe.polar.tcr;

import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;
import moe.polar.tcr.config.TCRConfig;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import static moe.polar.tcr.Utils.colorStringToText;
import static moe.polar.tcr.Utils.getChat;

public class TwirkToMinecraftChatListener implements TwirkListener {
    private final @NotNull BetterTwirk twirk;

    public TwirkToMinecraftChatListener(@NotNull BetterTwirk instance) {
        twirk = instance;
    }

    @Override
    public void onPrivMsg(TwitchUser sender, TwitchMessage message) {
        final var modCfg = TCRConfig.getInstance();

        final var content = Text.empty();
        var hasPrefixes = false;

        if (modCfg.prefixes.global != null) {
            content.append(colorStringToText(modCfg.prefixes.global));
            hasPrefixes = true;
        }

        if (sender.isMod() && modCfg.prefixes.moderators != null) {
            if (hasPrefixes) {
                content.append(" ");
            }

            content.append(colorStringToText(modCfg.prefixes.moderators));
            hasPrefixes = true;
        }

        if (sender.isSub() && modCfg.prefixes.subscribers != null) {
            if (hasPrefixes) {
                content.append(" ");
            }

            content.append(colorStringToText(modCfg.prefixes.subscribers));
            hasPrefixes = true;
        }

        if (hasPrefixes) {
            content.append(" ");
        }

        content.append(Text.literal(sender.getUserName()).styled(s -> s.withColor(sender.getColor())));
        content.append(": ");
        content.append(message.getContent());

        getChat().addMessage(content);
    }

    @Override
    public void onConnect() {
        final var msg = Text
                .translatable("commands.connect.success", twirk.channel)
                .formatted(Formatting.DARK_GREEN);

        getChat().addMessage(msg);
    }

    @Override
    public void onDisconnect() {
        final var msg = Text
                .translatable("commands.disconnect.success", twirk.channel)
                .formatted(Formatting.GRAY);

        getChat().addMessage(msg);
    }

    @Override
    public void onReconnect() {
        final var msg = Text
                .translatable("events.reconnect", twirk.channel)
                .formatted(Formatting.GRAY);

        getChat().addMessage(msg);
    }
}
