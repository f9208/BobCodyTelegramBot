package ru.bobcody.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.command.GetHotPieCommand;
import ru.bobcody.handler.AbstractHandler;
import ru.bobcody.thirdpartyapi.hotpies.PieBuffer;
import ru.bobcody.thirdpartyapi.hotpies.UnitPie;

import java.util.List;

@Slf4j
@Component
public class PiesHandler extends AbstractHandler {
    @Autowired
    private PieBuffer pieBuffer;

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();

        result.setText(getResponseTextMessage(inputMessage));
        result.setReplyToMessageId(inputMessage.getMessageId());
        return result;
    }

    @Override
    protected String getResponseTextMessage(Message message) {

        String pieId = getParameterOverDirective(message.getText(), 1);

        if (StringUtils.isEmpty(pieId)) {
            UnitPie randomPie = pieBuffer.getRandomBufferedPie();

            return randomPie.toString();
        } else {

            GetHotPieCommand command = new GetHotPieCommand(pieId);

            beanFactory.autowireBean(command);

            try {
                UnitPie pie = command.execute();
                return pie.toString();

            } catch (HttpResponseException e) {
                if (e.getStatusCode() == HttpStatus.NOT_FOUND.value()) {
                    return String.format("пирожок с номером %s не найден", pieId);
                }

            } catch (Exception e) {
                return "внутрення ошибка стороннего сервиса";
            }
        }
        return "";
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getPies();
    }
}
