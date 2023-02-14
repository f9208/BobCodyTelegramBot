package ru.bobcody.command;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.domain.Record;
import ru.bobcody.domain.RecordType;
import ru.bobcody.repository.RecordRepository;

import java.time.LocalDateTime;
import java.util.Optional;


public class ModifyAbyssCommand extends AbstractCommand {

    @Autowired
    private RecordRepository recordRepository;

    private String recordId;
    private RecordType type;

    public ModifyAbyssCommand(String recordId, RecordType type) {
        this.type = type;
        this.recordId = recordId;
    }

    @Override
    public String execute() {
        Long id;

        try {
            id = Long.valueOf(recordId);
        } catch (NumberFormatException e) {
            return "Неверный формат номера. Используйте натуральные цифры";
        }

        Optional<Record> abyssOpt = recordRepository.findById(id);

        if (abyssOpt.isEmpty()) {
            return "В бездне нет записи с идентификатором " + id;
        }

        Record abyss = abyssOpt.get();

        if (abyss.getParticularId()!=null) {
            return "Запись ранее уже была подтверждена";
        }

        if (type == RecordType.CAPS) {
            return processCaps(abyss);
        }

        if (type == RecordType.QUOTE) {
            return processQuote(abyss);
        }

        return "Желаемый тип записи не определен";
    }

    private String processCaps(Record record) {

        if (record.getApprovedDate() != null) {
            return "Запись ранее уже была подтверждена";
        }

        record.setType(RecordType.CAPS);
        record.setApprovedDate(LocalDateTime.now());
        record.setParticularId(recordRepository.getCapsNextId());

        recordRepository.save(record);

        return String.format("Капс сохранен под номером %d", record.getParticularId());
    }

    private String processQuote(Record record) {

        if (record.getApprovedDate() != null) {
            return "Запись ранее уже была подтверждена";
        }

        record.setType(RecordType.QUOTE);
        record.setApprovedDate(LocalDateTime.now());
        record.setParticularId(recordRepository.getQuoteNextId());

        recordRepository.save(record);

        return String.format("Цитата сохранена под номером %d", record.getParticularId());
    }
}
