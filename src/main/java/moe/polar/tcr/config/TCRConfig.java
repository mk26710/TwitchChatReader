package moe.polar.tcr.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TCRConfig {
    @Expose
    @NotNull
    public ConfigPrefixes prefixes;

    private TCRConfig() {
        prefixes = new ConfigPrefixes();
    }

    private static TCRConfig INSTANCE;

    public synchronized static TCRConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TCRConfig();
        }
        return INSTANCE;
    }

    private final static String fileName = "twitchchatreader.json";

    public Path getConfigFilePath() {
        return FabricLoader.getInstance().getConfigDir().toAbsolutePath().resolve(fileName);
    }

    public void saveToDisk() {
        final var cfgFile = getConfigFilePath();

        var gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();

        var json = gson.toJson(INSTANCE);

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

        var gson = new Gson();

        try {
            final String json = Files.readString(cfgFile, StandardCharsets.UTF_8);
            final TCRConfig savedCfg = gson.fromJson(json, TCRConfig.class);

            INSTANCE = savedCfg;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
