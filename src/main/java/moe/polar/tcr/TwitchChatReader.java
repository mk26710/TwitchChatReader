package moe.polar.tcr;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import moe.polar.tcr.config.TCRConfig;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class TwitchChatReader implements ClientModInitializer {
    public static final @NotNull Logger LOGGER = LoggerFactory.getLogger("twitchchatreader");
    public static final @NotNull Executor TCR_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public void onInitializeClient() {
        TCRConfig.getInstance().readFromDisk();

        final var handler = new TwitchCommandHandler();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                ClientCommandManager
                        .literal("twitch")
                        .then(connect(handler))
                        .then(disconnect(handler))
                        .then(literal("config")
                                .then(configReload(handler))
                        )
        ));
    }


    private LiteralArgumentBuilder<FabricClientCommandSource> connect(TwitchCommandHandler handler) {
        return ClientCommandManager
                .literal("connect")
                .then(argument("channel", word()).executes(handler::connect));
    }

    private LiteralArgumentBuilder<FabricClientCommandSource> disconnect(TwitchCommandHandler handler) {
        return ClientCommandManager
                .literal("disconnect")
                .executes(handler::disconnect);
    }

    private LiteralArgumentBuilder<FabricClientCommandSource> configReload(TwitchCommandHandler handler) {
        return ClientCommandManager
                .literal("reload")
                .executes(handler::configReload);
    }
}