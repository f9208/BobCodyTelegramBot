package ru.multibot.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.ThirdPartyAPI.FuckingGreatAdvice.FuckingGreatAdvice;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class FuckingGreatAdviceHandler implements SimpleHandlerInterface {
    @Autowired
    FuckingGreatAdvice fuckingGreatAdvice;

    public String getAdvice() {
        String result;
        try {
            result = fuckingGreatAdvice.getAdvice();
        } catch (Exception e) {
            result = "че то сервис не алё. совет от бота - не еби, блять, другим (и себе) мозги!";
        }
        return result;
    }

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        if (inputMessage.getText().equals("!обс") || inputMessage.getText().equals("!fga")) {
            if (inputMessage.getFrom().getUserName() == null)
                result.setText(inputMessage.getFrom().getFirstName() + ", " + getAdvice());
            else result.setText("@" + inputMessage.getFrom().getUserName() + ", " + getAdvice());
        }
        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("!обс");
        commands.add("!fga");
        return commands;
    }
}
