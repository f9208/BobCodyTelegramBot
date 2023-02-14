package ru.bobcody.updates.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.CommonConstants;
import ru.bobcody.command.AbstractCommand;
import ru.bobcody.services.DirectiveService;
import ru.bobcody.services.SettingService;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class AbstractHandler implements Handler, CommonConstants {
    @Autowired
    protected DirectiveService directiveService;

    @Autowired
    protected SettingService settingService;

    @Autowired
    protected AutowireCapableBeanFactory beanFactory;

    protected <T> T executeCommand(AbstractCommand command) {
        beanFactory.autowireBean(command);
        return command.execute();
    }

    public SendMessage handle(Message message) {
        SendMessage result = new SendMessage();

        result.setText(getResponseTextMessage(message));
        return result;
    }

    protected abstract String getResponseTextMessage(Message message);

    protected String getParameterOverDirective(String text, int capacity) {
        String[] words = text
                .toLowerCase()
                .replaceAll("\\s+", " ")
                .split(" ");

        if (words.length > 1) {
            return Arrays.stream(words).skip(1).limit(capacity).collect(Collectors.joining(" "));
        } else {
            return "";
        }
    }

    protected String getGuestName(org.telegram.telegrambots.meta.api.objects.User user) {
        return user.getUserName() == null || "null".equals(user.getUserName()) ?
                user.getFirstName() :
                user.getUserName();
    }

    protected String getDirective(String text) {
        String[] words = text
                .toLowerCase()
                .replaceAll("\\s+", " ")
                .split(" ");

        if (words.length != 0) {
            return words[0];
        } else {
            return "";
        }
    }
}
