package ru.bobcody.updates.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.updates.handlers.AbstractHandler;

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
        if (inputMessage.getFrom().getUserName() == null && inputMessage.getFrom().getUserName().equals("null")) {
            return "@" + inputMessage.getFrom().getFirstName() + ", " + getRandomAnswer();
        } else
            return "@" + inputMessage.getFrom().getUserName() + ", " + getRandomAnswer();
    }

    private String getRandomAnswer() {
        return answers.get(random.nextInt(answers.size()));
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getQu();
    }
}
