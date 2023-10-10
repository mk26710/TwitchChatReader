package moe.polar.tcr;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.gikk.twirk.events.TwirkListener;
import lombok.NonNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static moe.polar.tcr.Utils.randomNumIn;
import static moe.polar.tcr.TwitchChatReader.TCR_EXECUTOR;

public class BetterTwirk {
    @NonNull
    public final String nick = getAnonymousTwitchUsername();

    @NonNull
    public final String password = "VeryCoolPassword";

    @NonNull
    private final Twirk underlyingTwirk;

    @NonNull
    public final String channel;

    public BetterTwirk(@NonNull String channel) {
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
