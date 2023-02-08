package ru.bobcody.updates.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.services.DirectiveService;
import ru.bobcody.updates.handlers.AbstractHandler;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class SlapHandler extends AbstractHandler {
    private final Random random = new SecureRandom();

    @Autowired
    private DirectiveService directiveService;

    private List<String> phrases;

    @PostConstruct
    private void init() {
        phrases = directiveService.getSlapPhrases();
    }

    private String getRandomAnswer() {
        return phrases.get(random.nextInt(phrases.size()));
    }

    @Override
    protected String getResponseTextMessage(Message inputMessage) {
        if (inputMessage.getFrom().getUserName() == null && inputMessage.getFrom().getUserName().equals("null")) {

            return "@" + inputMessage.getFrom().getFirstName() + ", " + getRandomAnswer();

        } else {

            return "@" + inputMessage.getFrom().getUserName() + ", " + getRandomAnswer();
        }
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getSlap();
    }
}
