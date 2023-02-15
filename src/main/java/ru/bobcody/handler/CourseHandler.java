package ru.bobcody.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.GetCourseCommand;

import java.util.List;

@Component
public class CourseHandler extends AbstractHandler {

    @Override
    protected String getResponseTextMessage(Message inputMessage) {
        return executeCommand(new GetCourseCommand());
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getCourse();
    }
}
