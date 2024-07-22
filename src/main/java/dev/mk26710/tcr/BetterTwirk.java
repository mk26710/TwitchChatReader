package dev.mk26710.tcr;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.gikk.twirk.events.TwirkListener;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class BetterTwirk {
    private final @NotNull Twirk underlyingTwirk;

    public final @NotNull String nick = getAnonymousTwitchUsername();
    public final @NotNull String password = "VeryCoolPassword";

    public final @NotNull String channel;

    public BetterTwirk(@NotNull String channel) {
        this.channel = channel;
        underlyingTwirk = new TwirkBuilder(channel, nick, password).build();
    }

    private String getAnonymousTwitchUsername() {
        final Integer num = Utils.randomNumIn(1000, 9999);
        return "justinfan" + num;
    }

    public Twirk getInstance() {
        return underlyingTwirk;
    }

    public Boolean isConnected() {
        return underlyingTwirk.isConnected();
    }

    public Boolean isDisposed() {
        return underlyingTwirk.isDisposed();
    }

    public CompletableFuture<Boolean> connect() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return underlyingTwirk.connect();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TwitchChatReader.TCR_EXECUTOR);
    }

    public void disconnect() {
        underlyingTwirk.disconnect();
    }

    public void addListener(TwirkListener listener) {
        underlyingTwirk.addIrcListener(listener);
    }
}
