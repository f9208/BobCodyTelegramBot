package ru.bobcody.updates.handlers.chathandlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.updates.handlers.Handler;
import ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.utils.QuoteConsumer;

import java.util.List;

@Slf4j
//@Component
@RequiredArgsConstructor
public class QuoteSetHandler implements Handler {
    @Value("${quote.set.command}")
    private List<String> commands;
    @Value("${chatid.admin}")
    private Long moderatorChatId;
    private final QuoteConsumer quoteConsumer;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        if (inputMessage.getFrom().getId().equals(moderatorChatId)
                && inputMessage.getText().trim().startsWith("!approvecaps")) {
            result.setText(quoteConsumer.approveCaps(inputMessage));
            return result;
        }
        if (inputMessage.getFrom().getId().equals(moderatorChatId)
                && inputMessage.getText().trim().startsWith("!approvequote")) {
            result.setText(quoteConsumer.approveQuote(inputMessage));
            return result;
        }
        if (inputMessage.getText().trim().startsWith("!aq") ||
                inputMessage.getText().trim().startsWith("!дц")) {
            String replay;
            replay = quoteConsumer.addQuoteForApprove(inputMessage);
            result.setChatId(inputMessage.getChatId().toString());
            result.setText(replay);
            return result;
        }
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}