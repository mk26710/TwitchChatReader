package moe.polar.tcr;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.gikk.twirk.events.TwirkListener;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static moe.polar.tcr.Utils.randomNumIn;
import static moe.polar.tcr.TwitchChatReader.TCR_EXECUTOR;

public class BetterTwirk {
    @NotNull
    public final String nick = getAnonymousTwitchUsername();

    @NotNull
    public final String password = "VeryCoolPassword";

    @NotNull
    private final Twirk underlyingTwirk;

    @NotNull
    public final String channel;

    public BetterTwirk(@NotNull String channel) {
        this.channel = channel;
        underlyingTwirk = new TwirkBuilder(channel, nick, password).build();
    }

    private String getAnonymousTwitchUsername() {
        final Integer num = randomNumIn(1000, 9999);
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
        }, TCR_EXECUTOR);
    }

    public void disconnect() {
        underlyingTwirk.disconnect();
    }

    public void addListener(TwirkListener listener) {
        underlyingTwirk.addIrcListener(listener);
    }
}
