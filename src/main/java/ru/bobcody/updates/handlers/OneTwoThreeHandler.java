package ru.bobcody.updates.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.updates.handlers.AbstractHandler;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Component
public class OneTwoThreeHandler extends AbstractHandler {

    private final Random random = new SecureRandom();
    private List<String> phrases;

    @PostConstruct
    private void init() {
        phrases = directiveService.getAnswerPhrases();
    }

    @Override
    protected String getResponseTextMessage(Message message) {
        return getRandomPhrase();
    }

    private String getRandomPhrase() {
        return phrases.get(random.nextInt(phrases.size()));
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getOneTwoThree();
    }
}
