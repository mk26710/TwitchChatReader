package dev.mk26710.tcr;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static ChatHud getChat() {
        return MinecraftClient.getInstance().inGameHud.getChatHud();
    }

    public static Integer randomNumIn(Integer min, Integer max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    @Deprecated
    public static Text colorStringToText(String s) {
        return Text.of(s.replaceAll("&", "§"));
    }

    public static String colorRegex = "(&#[a-fA-F0-9]{6}|&[0-9abcdefgulomkr])";
    public static Pattern colorPattern = Pattern.compile(colorRegex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    // this is technically O(2*s), probably can reduce time complexity to O(s)?
    public static Text coloredTextFromString(String s) {
        Matcher matcher = colorPattern.matcher(s);
        ArrayList<String> result = new ArrayList<>();

        int lastEnd = 0;

        while (matcher.find()) {
            // Add the text before the match
            if (lastEnd < matcher.start()) {
                result.add(s.substring(lastEnd, matcher.start()));
            }
            // Add the match itself
            result.add(matcher.group());
            lastEnd = matcher.end();
        }

        // Add any remaining text after the last match
        if (lastEnd < s.length()) {
            result.add(s.substring(lastEnd));
        }

        MutableText currentText = Text.empty();
        Style currentStyle = Style.EMPTY;

        for (String part : result) {
            if (part.startsWith("&") && part.length() == 2) {
                currentStyle = currentStyle.withFormatting(Formatting.byCode(part.charAt(1)));
            } else if (part.startsWith("&#") && part.length() == 8) {
                Integer color = Integer.parseInt(part.substring(2), 16);
                currentStyle = currentStyle.withColor(color);
            } else {
                Text textPart = Text.literal(part).setStyle(currentStyle);
                currentText.append(textPart);
                currentStyle = Style.EMPTY;
            }
        }

        return currentText;
    }
}
