package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.entities.CapsQuoteStorage;
import ru.bobcody.services.CapsQuoteStorageService;
import ru.bobcody.controller.handlers.chatHandlers.SimpleHandlerInterface;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class CapsQuoteStorageHandler implements SimpleHandlerInterface {
    @Autowired
    CapsQuoteStorageService capsQuoteStorageService;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText(getCapsQuote(inputMessage));
        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("!капс");
        commands.add("!к");
        commands.add("!caps");
        return commands;
    }

    private String getCapsQuote(Message inputMessage) {
        String shortCommand = inputMessage.getText().trim();
        if (shortCommand.equals("!к") ||
                shortCommand.equals("!капс") ||
                shortCommand.equals("!caps")) {

            return randomQuoteFromQuoteStorage();
        }
        String fullCommand = inputMessage.getText();
        Long id = Long.valueOf(fullCommand.split(" ")[1]);
        try {
            System.out.println("запрашиваемый айдишник: " + id);
            return capsQuotePrettyTextById(id);
        } catch (NumberFormatException e) {
            return "в качестве номера цитаты используйте только цифры (числа)";
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private String capsQuotePrettyTextById(Long id) {
        String result;
        StringBuilder master = new StringBuilder();
        DateTimeFormatter formatDateToPrint = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        try {
            CapsQuoteStorage current = capsQuoteStorageService.getById(Long.valueOf(id));
            if (current != null) {
                master.append("Капс №")
                        .append(current.getId())
                        .append(" (")
                        .append(capsQuoteStorageService.getMaxID())
                        .append(") added: ")
                        .append(LocalDateTime.ofInstant(Instant.ofEpochSecond(current.getDateAdded()),
                                ZoneId.of("GMT")).format(formatDateToPrint))
                        .append("\n")
                        .append(current.getCapsQuoteText());
                result = master.toString();
            } else result = "нету такой.";
        } catch (Exception e) {
            e.printStackTrace();
            result = ("что-то пошло не так");
        }
        return result;
    }

    private String randomQuoteFromQuoteStorage() {
        Random r = new Random();
        Long temp=Long.valueOf(r.nextInt(capsQuoteStorageService.getMaxID().intValue())) + 1;
        while (!capsQuoteStorageService.existById(temp)) {
            temp = Long.valueOf(r.nextInt(capsQuoteStorageService.getMaxID().intValue())) + 1;
        }
        return capsQuotePrettyTextById(temp);
    }
}
