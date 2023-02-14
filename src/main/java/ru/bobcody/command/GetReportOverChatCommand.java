package ru.bobcody.command;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.repository.TextMessageRepository;

import java.time.LocalDateTime;
import java.util.List;

public class GetReportOverChatCommand extends AbstractCommand {

    @Autowired
    private TextMessageRepository textMessageRepository;

    private Long chatId;
    private LocalDateTime after;
    private LocalDateTime before;

    public GetReportOverChatCommand(Long chatId, LocalDateTime after, LocalDateTime before) {
        this.chatId = chatId;
        this.after = after;
        this.before = before;
    }

    @Override
    public List<String> execute() {
        return textMessageRepository.getTop(chatId, after, before);
    }
}
