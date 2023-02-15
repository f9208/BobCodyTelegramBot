package ru.bobcody.handler;

import org.springframework.stereotype.Component;
import ru.bobcody.domain.RecordType;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class AskCaps extends AbstractAskRecord {

    @PostConstruct
    private void init() {
        parameterMap.put("!caps", RecordType.CAPS);
        parameterMap.put("!капс", RecordType.CAPS);
        parameterMap.put("!к", RecordType.CAPS);
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getCaps();
    }
}
