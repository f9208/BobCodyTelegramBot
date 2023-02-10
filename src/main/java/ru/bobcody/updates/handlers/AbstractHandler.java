package ru.bobcody.updates.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.AbstractCommand;
import ru.bobcody.services.DirectiveService;

public abstract class AbstractHandler implements Handler {
    @Autowired
    protected DirectiveService directiveService;

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
}
