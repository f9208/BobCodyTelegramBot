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

abstract public class AbstractHandlerTest {
    protected static User user;
    protected static Message message;
    protected static Chat chat;
    protected static MainHandlerTextMessage mainHandlerTextMessage;

    static {
        user = new User();
        user.setFirstName("Petrov");
        user.setUserName("Viktor");
        user.setId(12345L);
        user.setIsBot(false);
        chat = new Chat();
        chat.setId(54321L);

        message = new Message();
        message.setFrom(user);
        message.setChat(chat);
    }

    protected List<String> getCommandsByKey(String propertyKey) {
        String props = getAnyProperties("commands.properties", propertyKey);
        String[] cut = props.replaceAll("\\s+", "").split("[,]");
        return Lists.list(cut);

    }

    protected List<String> getPropertiesByPath(String propertiesPath, String key) {
        String props = getAnyProperties(propertiesPath, key);
        String[] cut = props
                .replaceAll("\'", "")
                .replaceAll("\\{", "")
                .replaceAll("\\}", "")
                .split("[,]");
        return Lists.list(cut);
    }

    private String getAnyProperties(String propertiesPath, String key) {
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