package ru.bobcody.controller.updates.resolvers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class AnimationMessageResolver implements IMessageResolver {
    @Override
    public SendMessage process(Message message) {
        SendMessage result = new SendMessage();
        result.setText("сохранялка анимешек в разработке");
        result.setChatId(message.getChatId().toString());
        return result;
    }
}
