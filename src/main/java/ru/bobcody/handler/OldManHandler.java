package ru.bobcody.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.CommonConstants;

import java.util.List;

@Component
public class OldManHandler extends AbstractHandler {
    private final long izhMainCHatId = -1001207502467L;

    @Override
    protected String getResponseTextMessage(Message message) {
        if (message.getChatId() != izhMainCHatId) {
            return "";
        } else {
            return CommonConstants.BABAY_LINKS;
        }
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getOldMan();
    }
}
