package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Getter
@Setter
public class AmdSucksHandler implements SimpleHandlerInterface {
    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        int temp = Double.valueOf(Math.random() * 4).intValue();
        if (temp >> 1 == 1)
            result.setText("@" + inputMessage.getFrom().getUserName() + ", AMD сосет");
        else result.setText("AMD форева!");
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return Stream.of("amd", "амд", "амд.", "amd.", "амд,", "amd,").collect(Collectors.toList());
    }
}


