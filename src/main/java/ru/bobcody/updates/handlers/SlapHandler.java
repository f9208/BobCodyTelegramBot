package ru.bobcody.updates.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.services.DirectiveService;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

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

        String guestName = getGuestName(inputMessage.getFrom());

        return String.format("@%s, %s", guestName, getRandomAnswer());
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getSlap();
    }
}
