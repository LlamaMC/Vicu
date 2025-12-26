package com.llamamc.spit.networking.util;

import java.util.ArrayList;
import java.util.List;

public class MotdUtils {

    public static String toJsonMotd(String motd) {
        List<String> components = new ArrayList<>();
        StringBuilder currentText = new StringBuilder();
        String currentColor = "#FFFFFF";
        Gradient gradient = null;
        for (int i = 0; i < motd.length(); i++) {
            char c = motd.charAt(i);
            if (c == '§' && i + 1 < motd.length()) {
                String legacyHex = legacyColorToHex(motd.charAt(i + 1));
                if (legacyHex != null) {
                    if (currentText.length() > 0) {
                        if (gradient != null) {
                            components.addAll(buildGradientComponents(currentText.toString(), gradient));
                            gradient = null;
                        } else {
                            components.add(buildComponent(currentText.toString(), currentColor));
                        }
                        currentText.setLength(0);
                    }
                    currentColor = legacyHex;
                    i++;
                    continue;
                }
            }
            if (c == '<') {
                int end = motd.indexOf('>', i);
                if (end > i) {
                    String tag = motd.substring(i + 1, end);
                    if (tag.contains("-")) {
                        String[] parts = tag.split("-");
                        if (parts.length == 2) {
                            gradient = new Gradient(parts[0], parts[1]);
                            if (currentText.length() > 0) {
                                components.add(buildComponent(currentText.toString(), currentColor));
                                currentText.setLength(0);
                            }
                        }
                    } else {
                        currentColor = tag;
                        if (currentText.length() > 0) {
                            if (gradient != null) {
                                components.addAll(buildGradientComponents(currentText.toString(), gradient));
                                gradient = null;
                            } else {
                                components.add(buildComponent(currentText.toString(), currentColor));
                            }
                            currentText.setLength(0);
                        }
                    }
                    i = end;
                    continue;
                }
            }
            currentText.append(c);
        }
        if (currentText.length() > 0) {
            if (gradient != null) {
                components.addAll(buildGradientComponents(currentText.toString(), gradient));
            } else {
                components.add(buildComponent(currentText.toString(), currentColor));
            }
        }
        return "{\"text\":\"\",\"extra\":[" + String.join(",", components) + "]}";
    }

    private static List<String> buildGradientComponents(String text, Gradient gradient) {
        List<String> components = new ArrayList<>();
        int len = text.length();
        for (int i = 0; i < len; i++) {
            String color = gradient.getColorAt(i, len);
            components.add(buildComponent(Character.toString(text.charAt(i)), color));
        }
        return components;
    }

    private static String buildComponent(String text, String color) {
        return "{\"text\":\"" + escape(text) + "\",\"color\":\"" + color + "\"}";
    }

    private static String escape(String str) {
        return str.replace("\"", "\\\"");
    }

    private static String legacyColorToHex(char code) {
        return switch (Character.toLowerCase(code)) {
            case '0' -> "#000000";
            case '1' -> "#0000AA";
            case '2' -> "#00AA00";
            case '3' -> "#00AAAA";
            case '4' -> "#AA0000";
            case '5' -> "#AA00AA";
            case '6' -> "#FFAA00";
            case '7' -> "#AAAAAA";
            case '8' -> "#555555";
            case '9' -> "#5555FF";
            case 'a' -> "#55FF55";
            case 'b' -> "#55FFFF";
            case 'c' -> "#FF5555";
            case 'd' -> "#FF55FF";
            case 'e' -> "#FFFF55";
            case 'f' -> "#FFFFFF";
            default -> null;
        };
    }

    private static class Gradient {
        private final int startR, startG, startB;
        private final int endR, endG, endB;

        public Gradient(String startHex, String endHex) {
            startR = Integer.parseInt(startHex.substring(1, 3), 16);
            startG = Integer.parseInt(startHex.substring(3, 5), 16);
            startB = Integer.parseInt(startHex.substring(5, 7), 16);

            endR = Integer.parseInt(endHex.substring(1, 3), 16);
            endG = Integer.parseInt(endHex.substring(3, 5), 16);
            endB = Integer.parseInt(endHex.substring(5, 7), 16);
        }

        public String getColorAt(int index, int total) {
            if (total <= 1) total = 2;
            int r = startR + (endR - startR) * index / (total - 1);
            int g = startG + (endG - startG) * index / (total - 1);
            int b = startB + (endB - startB) * index / (total - 1);
            return String.format("#%02X%02X%02X", r, g, b);
        }
    }
}
