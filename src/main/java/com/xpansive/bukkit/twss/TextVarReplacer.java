package com.xpansive.bukkit.twss;

import org.bukkit.ChatColor;

public class TextVarReplacer {
    public static String replace(String text, Variable... args) {
        // Handle passed variables
        for (Variable v : args) {
            text = text.replace("$" + v.name, v.content);
        }

        // Handle colours
        for (ChatColor c : ChatColor.values()) {
            text = text.replace("$" + c.name().toLowerCase(), c.toString());
        }

        return text;
    }

    public static class Variable {
        protected String name, content;

        public Variable(String name, String content) {
            this.name = name;
            this.content = content;
        }

        public Variable(String name, int content) {
            this(name, Integer.toString(content));
        }

        public Variable(String name, double content) {
            this(name, Double.toString(content));
        }
    }
}
