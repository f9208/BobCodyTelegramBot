package ru.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.Entities.QuoteEntityStorage;
import ru.bobcody.Servies.QuoteStorageService;
import ru.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Setter
@Getter
public class QuoteStorageHandler implements SimpleHandlerInterface {
    @Autowired // заменить на интерфейс?
            QuoteStorageService quoteStorageServiceImp;

    private String getTextQuoteStorage(Message message) {
        String shortCommand = message.getText().trim();
        if (shortCommand.equals("!ц") ||
                shortCommand.equals("!цитата") ||
                shortCommand.equals("!quote") ||
                shortCommand.equals("!q")) {
            return randomQuoteFromQuoteStorage();
        }

        String fullCommand = message.getText();
        try {
            return textQuotePrettyById(Long.valueOf(fullCommand.split(" ")[1]));
        } catch (NumberFormatException e) {
            return "в качестве номера цитаты используйте только цифры (числа)";
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private String randomQuoteFromQuoteStorage() {
        Random r = new Random();
        Long temp;
        temp = Long.valueOf(r.nextInt(quoteStorageServiceImp.getMaxID().intValue())) + 1;
        /* в случае если какую то из цитат "грохнули" ее quoteId-шник уже не будет показываться
         * и его уже никто не займет - особенность sql. поэтому предварительно проверяем
         * закреплена ли какая то цитата за генерируемым рендомным числом
         * - если нет, то генерируем новое число. инкремент - чтобы крайняя цитата тоже попадала в выборку
         * */
        while (!existsById(temp)) {
            temp = Long.valueOf(r.nextInt(quoteStorageServiceImp.getMaxID().intValue())) + 1;
        }
        return textQuotePrettyById(temp);
    }

    private String textQuotePrettyById(Long id) {
        String result;
        Long number = id;
        StringBuilder master = new StringBuilder();
        DateTimeFormatter formatDateToPrint = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        try {
            QuoteEntityStorage current = quoteStorageServiceImp.getSingleQuoteFromStorageById(Long.valueOf(number));
            if (current != null) {
                master.append("Цитата №")
                        .append(current.getId())
                        .append(" (")
                        .append(quoteStorageServiceImp.getMaxID())
                        .append(") added: ")
                        .append(LocalDateTime.ofInstant(Instant.ofEpochSecond(current.getDateAdded()), ZoneId.of("GMT")).format(formatDateToPrint))
                        .append("\n")
                        .append(current.getText());
                result = master.toString();
            } else result = "нету такой.";
        } catch (Exception e) {
            e.printStackTrace();
            result = ("что-то пошло не так");
        }
        return result;
    }

    private boolean existsById(Long id) {
        return quoteStorageServiceImp.existById(id);
    }

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText(getTextQuoteStorage(inputMessage));
        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("!ц");
        commands.add("!quote");
        commands.add("!q");
        commands.add("!цитата");
        return commands;
    }
}