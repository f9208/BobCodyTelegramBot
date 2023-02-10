package ru.bobcody.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.bobcody.command.SendElkCommand;

@Component
public class ElkTimer extends AbstractTimer {

    @Value("${timer.elk.chat}")
    private String[] chatsDestination; // -1001207502467

    @Value("${timer.elk.enabled}")
    private boolean enabled;

    @Override
    protected boolean isEnabled() {
        return enabled;
    }

    @Scheduled(cron = "${timer.elk.cron}")
    @Override
    protected void start() {
        super.start();
    }

    @Override
    protected void startInternal() {
        process();
    }

    private void process() {
        for (String chatId : chatsDestination) {

            SendElkCommand command = new SendElkCommand(chatId);
            beanFactory.autowireBean(command);
            command.execute();
        }
    }
}
