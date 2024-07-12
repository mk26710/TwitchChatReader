package moe.polar.tcr;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;

import java.util.Random;

public class Utils {
    public static ChatHud getChat() {
        return MinecraftClient.getInstance().inGameHud.getChatHud();
    }

    public static Integer randomNumIn(Integer min, Integer max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static Text colorStringToText(String s) {
        return Text.of(s.replaceAll("&", "§"));
    }
}
