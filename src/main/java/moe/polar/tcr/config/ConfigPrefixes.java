package moe.polar.tcr.config;

import org.jetbrains.annotations.Nullable;

public class ConfigPrefixes {
    public @Nullable String global;
    public @Nullable String followers;
    public @Nullable String subscribers;
    public @Nullable String moderators;
    public @Nullable String vips;

    public ConfigPrefixes() {
        global = null;
        followers = null;
        subscribers = "[SUB]";
        moderators = "[MOD]";
        vips = "[VIP]";
    }
}
