package ru.multibot.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.ThirdPartyAPI.HotPies.SinglePie;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Getter
@Setter
public class PiesHandler implements SimpleHandlerInterface {
    @Autowired
    List<SinglePie> piesList;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        String textMessageAnswer;
        Random r = new Random();
        int sizePiesOnPage = piesList.size();
        System.out.println("количество пирожков в листе:"+sizePiesOnPage);
        int rand = r.nextInt(sizePiesOnPage);
        System.out.println("рендомный индекс:"+rand);
        textMessageAnswer = piesList.get(rand).toString();
        result.setText(textMessageAnswer);
        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("!пирожок");
        commands.add("!pie");
        return commands;
    }
}