package ru.bobcody.controller.resolvers;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Data
@NoArgsConstructor
public class AnimationMessageResolver extends AbstractMessageResolver {
    @Override
    public SendMessage process(Message message) {
        SendMessage result = new SendMessage();
        result.setText("сохранялка анимешек в разработке");
        result.setChatId(message.getChatId().toString());
        return result;
    }
}
