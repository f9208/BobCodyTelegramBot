package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.thirdPartyAPI.fuckingGreatAdvice.FuckingGreatAdvice;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@Getter
@Setter
public class FuckingGreatAdviceHandler implements SimpleHandlerInterface {
    @Autowired
    FuckingGreatAdvice fuckingGreatAdvice;

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
        return Stream.of("!fga", "!обс").collect(Collectors.toList());
    }
}
