package moe.polar.tcr;

import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import static moe.polar.tcr.Utils.getChat;

public class TwirkToMinecraftChatListener implements TwirkListener {
    @NotNull
    private final BetterTwirk twirk;

    public TwirkToMinecraftChatListener(@NotNull BetterTwirk instance) {
        twirk = instance;
    }

    @Override
    public void onPrivMsg(TwitchUser sender, TwitchMessage message) {
        final var content = Text.empty();

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
