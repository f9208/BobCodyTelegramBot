package ru.bobcody.controller.handlers.chatHandlers;

import org.assertj.core.util.Lists;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

public class PropertiesUtils {
    static public List<String> getCommandsByKey(String propertyKey) {
        String props = getAnyProperties("commands.properties", propertyKey);
        String[] cut = props.replaceAll("\\s+", "").split("[,]");
        return Lists.list(cut);
    }

    public static List<String> getPropertiesByPath(String propertiesPath, String key) {
        String props = getAnyProperties(propertiesPath, key);
        String regex = "\',\'";
        String[] cut = props
                .replaceAll("\\{\'", "")
                .replaceAll("\'}", "")
                .split(regex);
        return Lists.list(cut);
    }

    static public String getAnyProperties(String propertiesPath, String key) {
        Properties props = new Properties();
        InputStream is = ClassLoader.getSystemResourceAsStream(propertiesPath);
        Reader r = new InputStreamReader(is);
        try {
            props.load(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String commands = props.getProperty(key);
        if (commands == null) {
            System.out.println("no such command has found");
            return "";
        }
        return commands;
    }
}