package moe.polar.tcr.config;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TCRConfig {
    public @NotNull ConfigPrefixes prefixes;

    private TCRConfig() {
        prefixes = new ConfigPrefixes();
    }

    @Json(ignore = true)
    private static TCRConfig INSTANCE;

    public synchronized static TCRConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TCRConfig();
        }
        return INSTANCE;
    }

    @Json(ignore = true)
    private final static String fileName = "twitchchatreader.json";

    public Path getConfigFilePath() {
        return FabricLoader.getInstance().getConfigDir().toAbsolutePath().resolve(fileName);
    }

    public void saveToDisk() {
        final var cfgFile = getConfigFilePath();

        Moshi moshi = new Moshi.Builder().build();

        JsonAdapter<TCRConfig> jsonAdapter = moshi
                .adapter(TCRConfig.class)
                .serializeNulls();

        String json = jsonAdapter.indent("  ").toJson(getInstance());

        try {
            Files.writeString(cfgFile, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromDisk() {
        final var cfgFile = getConfigFilePath();
        if (Files.notExists(cfgFile)) {
            saveToDisk();
            return;
        }

        final Moshi moshi = new Moshi.Builder().build();
        final JsonAdapter<TCRConfig> jsonAdapter = moshi.adapter(TCRConfig.class);

        try {
            final String json = Files.readString(cfgFile, StandardCharsets.UTF_8);
            final TCRConfig savedCfg = jsonAdapter.fromJson(json);

            INSTANCE = savedCfg;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
