package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;
import ru.bobcody.thirdPartyAPI.fuckingGreatAdvice.FuckingGreatAdvice;

import java.util.List;

@Slf4j
@Component
public class FuckingGreatAdviceHandler implements SimpleHandlerInterface {
    @Autowired
    @Setter
    private FuckingGreatAdvice fuckingGreatAdvice;
    @Value("${fga.command}")
    private List<String> commands;

    public String getAdvice() {
        log.info("make advice");
        String result;
        try {
            result = fuckingGreatAdvice.getAdvice();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("service is not available");
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
        return commands;
    }
}
