package ru.bobcody.controller.updates.handlers.chatHandlers.secondLayerHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.updates.handlers.chatHandlers.IHandler;
import ru.bobcody.controller.updates.handlers.chatHandlers.secondLayerHandler.utils.QuoteProducer;
import ru.bobcody.data.entities.Type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QuoteGetHandler implements IHandler {
    @Value("${quote.get.command}")
    private List<String> commands;

    @Autowired
    QuoteProducer quoteProducer;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        Type type;
        if (askQuote(inputMessage.getText())) {
            type = Type.REGULAR;
            result.setText(quoteProducer.getText(inputMessage, type));
        }
        if (askCaps(inputMessage.getText())) {
            type = Type.CAPS;
            result.setText(quoteProducer.getText(inputMessage, type));
        }
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }

    private boolean askQuote(String text) {
        List<String> quoteCommand = commands.stream()
                .filter((p) -> p.contains("q") || p.contains("ц"))
                .collect(Collectors.toList());
        String[] textMessage = text.split(" ");
        return Arrays.stream(textMessage).filter(quoteCommand::contains).count() > 0;
    }

    private boolean askCaps(String text) {
        List<String> capsCommand = commands.stream()
                .filter((p) -> p.contains("к") || p.contains("caps"))
                .collect(Collectors.toList());
        String[] textMessage = text.split(" ");
        return Arrays.stream(textMessage).filter(capsCommand::contains).count() > 0;
    }
}
