package ru.bobcody.updates.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Component
public class QuAnswerHandler extends AbstractHandler {
    private final Random random = new SecureRandom();

    private List<String> answers;

    @PostConstruct
    private void init() {
        answers = directiveService.getQuAnswer();
    }

    @Override
    protected String getResponseTextMessage(Message inputMessage) {
        String guestName = getGuestName(inputMessage.getFrom());

        return String.format("@%s, %s", guestName, getRandomAnswer());
    }

    private String getRandomAnswer() {
        return answers.get(random.nextInt(answers.size()));
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getQu();
    }
}
