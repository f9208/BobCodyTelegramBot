package ru.bobcody.command;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.domain.Guest;
import ru.bobcody.domain.Record;
import ru.bobcody.domain.RecordType;
import ru.bobcody.repository.GuestRepository;
import ru.bobcody.repository.RecordRepository;

import java.time.LocalDateTime;

public class CreateAbyssCommand extends AbstractCommand {
    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private GuestRepository guestRepository;

    private Long guestId;
    private String text;

    public CreateAbyssCommand(Long guestId, String text) {
        this.guestId = guestId;
        this.text = text;
    }

    @Override
    public Long execute() {
        Record recordAbyss = new Record();

        setNotNullAttributes(recordAbyss);

        recordRepository.save(recordAbyss);

        return recordAbyss.getId();
    }

    private void setNotNullAttributes(Record record) {
        final Guest guest = guestRepository.findById(guestId)
                .orElseThrow();

        record.setAuthor(guest);

        record.setCreateDate(LocalDateTime.now());

        record.setText(text);

        record.setType(RecordType.ABYSS);
    }
}
