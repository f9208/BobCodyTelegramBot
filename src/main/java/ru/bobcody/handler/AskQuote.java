package ru.bobcody.handler;

import org.springframework.stereotype.Component;
import ru.bobcody.domain.RecordType;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class AskQuote extends AbstractAskRecord {

    @PostConstruct
    private void init() {
        parameterMap.put("!ц", RecordType.QUOTE);
        parameterMap.put("!цитата", RecordType.QUOTE);
        parameterMap.put("!q", RecordType.QUOTE);
        parameterMap.put("!quote", RecordType.QUOTE);
    }

    @Override
    public List<String> getOrderList() {
        return directiveService.getQuote();
    }
}
