package moe.polar.tcr.config;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.Nullable;

public class ConfigPrefixes {
    @Expose
    @Nullable
    public String global;

    @Expose
    @Nullable
    public String followers;

    @Expose
    @Nullable
    public String subscribers;

    @Expose
    @Nullable
    public String moderators;

    @Expose
    @Nullable
    public String vips;

    public ConfigPrefixes() {
        global = null;
        followers = null;
        subscribers = "&5[SUB]";
        moderators = "&2[MOD]";
        vips = "&5[VIP]";
    }
}
