package ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.updates.handlers.chathandlers.IHandler;
import ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.utils.ForecastProducer;

import java.util.List;

@Slf4j
@Component
@PropertySource(value = "classpath:weatherProp.properties", encoding = "UTF-8")
public class WeatherForecastHandler implements IHandler {
    @Autowired
    ForecastProducer forecastProducer;
    @Value("${weather.command}")
    private List<String> commands;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        result.setText(forecastProducer.weatherForecastAnswer(inputMessage));
        result.setReplyToMessageId(inputMessage.getMessageId());
        return result;
    }

    @Override
    public List<String> getOrderList() {
        return commands;
    }
}