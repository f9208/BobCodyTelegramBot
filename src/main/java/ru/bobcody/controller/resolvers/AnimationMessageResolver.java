package ru.bobcody.controller.resolvers;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@NoArgsConstructor
public class AnimationMessageResolver implements IMessageResolver {
    @Override
    public SendMessage process(Message message) {
        SendMessage result = new SendMessage();
        result.setText("сохранялка анимешек в разработке");
        result.setChatId(message.getChatId().toString());
        return result;
    }
}
