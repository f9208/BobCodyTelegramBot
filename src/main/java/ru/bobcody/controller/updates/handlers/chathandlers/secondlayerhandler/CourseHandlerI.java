package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.updates.handlers.chathandlers.IHandler;
import ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils.CourseWrapper;

import java.util.List;

@Component
public class CourseHandlerI implements IHandler {
    private final CourseWrapper courseWrapper;
    @Value("${course.command}")
    private List<String> commands;

    public CourseHandlerI(CourseWrapper courseWrapper) {
        this.courseWrapper = courseWrapper;
    }

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText(courseWrapper.getCourse());
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}
