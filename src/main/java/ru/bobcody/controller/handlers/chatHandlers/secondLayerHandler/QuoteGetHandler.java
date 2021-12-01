package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;
import ru.bobcody.entities.Quote;
import ru.bobcody.entities.Type;
import ru.bobcody.services.QuoteService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Slf4j
@Data
@Component
public class QuoteGetHandler implements SimpleHandlerInterface {
    @Value("${quote.get.command}")
    private List<String> commands;
    @Autowired
    QuoteService quoteService;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        Type type;
        if (inputMessage.getText().trim().startsWith("!ц") ||
                inputMessage.getText().trim().startsWith("!quote") ||
                inputMessage.getText().trim().startsWith("!цитата") ||
                inputMessage.getText().trim().startsWith("!q")) {
            type = Type.REGULAR;
            result.setText(getPreparedText(inputMessage, type));
        }
        if (inputMessage.getText().trim().startsWith("!caps") ||
                inputMessage.getText().trim().startsWith("!капс") ||
                inputMessage.getText().trim().startsWith("!к")) {
            type = Type.CAPS;
            result.setText(getPreparedText(inputMessage, type));
        }
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }

    private String getPreparedText(Message message, Type type) {
        String[] command = message.getText().toLowerCase().split(" ");
        Quote quote = null;
        if (command.length == 1) {
            log.info("get random quote");
            quote = getRandom(type);
        }
        if (command.length == 2) {
            try {
                long quoteId = Long.parseLong(command[1]);
                if (quoteId <= 0) throw new NumberFormatException();
                log.info("get quote with id {}", quoteId);
                quote = getById(quoteId, type);
            } catch (NumberFormatException e) {
                return "в качестве номера цитаты используйте только положительные числа";
            }
        }
        if (quote != null) {
            return facade(quote);
        } //todo никак не реагируем на отстутвующий номер цитаты. переделать
        return "для поиска цитат используйте синтаксис: !q + номер_цитаты_цифрами";
    }

    private Quote getRandom(Type type) {
        Random r = new Random();
        int id;
        if (type.equals(Type.CAPS)) {
            id = r.nextInt((int) quoteService.getLastCapsId()) + 1;
            return getById(id, type);
        }
        if (type.equals(Type.REGULAR)) {
            id = r.nextInt((int) quoteService.getLastRegularId()) + 1;
            return getById(id, type);
        }
        throw new RuntimeException("только caps или regular types");
    }

    private Quote getById(long id, Type type) {
        if (type == Type.CAPS) return quoteService.getByCapsId(id);
        if (type == Type.REGULAR) return quoteService.getByRegularId(id);
        return null;
    }

    private String facade(Quote quote) {
        String result;
        StringBuilder master = new StringBuilder();
        DateTimeFormatter formatDateToPrint = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        if (quote.getType().equals(Type.REGULAR)) {
            master.append("Цитата №").
                    append(quote.getRegularId())
                    .append(" (")
                    .append(quoteService.getLastRegularId());
        }
        if (quote.getType().equals(Type.CAPS)) {
            master.append("Капс №").
                    append(quote.getCapsId())
                    .append(" (")
                    .append(quoteService.getLastCapsId());
        }
        master.append(") added: ")
                .append(quote.getAdded().format(formatDateToPrint))
                .append("\n")
                .append(quote.getText());
        result = master.toString();
        return result;
    }
}
