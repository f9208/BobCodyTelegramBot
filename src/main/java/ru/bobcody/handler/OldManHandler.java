package ru.bobcody.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.CommonTextConstant;

import java.util.List;

@Component
public class OldManHandler extends AbstractHandler {
    private final long izhMainCHatId = -1001207502467L;

    @Override
    protected String getResponseTextMessage(Message message) {
        if (message.getChatId() != izhMainCHatId){
            return "";
        } else {
            return CommonTextConstant.BABAY_LINKS;
        }
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getOldMan();
    }
}
