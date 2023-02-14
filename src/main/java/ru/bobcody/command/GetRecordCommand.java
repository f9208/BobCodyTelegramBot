package ru.bobcody.command;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.domain.Record;
import ru.bobcody.domain.RecordType;
import ru.bobcody.repository.RecordRepository;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

public class GetRecordCommand extends AbstractCommand {
    @Autowired
    private RecordRepository recordRepository;

    private String id;
    private RecordType type;

    public GetRecordCommand(String id, RecordType type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String execute() {

        Optional<Record> optionalRecord;

        long maxParticularId = recordRepository.getMaxIdByType(type);

        if (StringUtils.isEmpty(id)) {

            optionalRecord = getRandomOptionalRecord(maxParticularId, type);

        } else {
            try {
                Long particularId = Long.valueOf(id);

                optionalRecord = recordRepository.findByParticularIdAndType(particularId, type);

                if (optionalRecord.isEmpty()) {
                    return String.format("Запись с id %d не найдено", particularId);
                }

            } catch (NumberFormatException e) {
                return "Неверный формат номера. Используйте натуральные числа";
            }
        }

        if (optionalRecord.isEmpty()) {
            return String.format("не удалось найти записи типа %s", type.getName());
        } else {
            return wrap(optionalRecord.get(), maxParticularId);
        }
    }

    private Optional<Record> getRandomOptionalRecord(long seed, RecordType type) {

        if (!recordRepository.existsByType(type)) {
            return Optional.empty();
        }

        Random ran = new Random();
        int id = ran.nextInt((int) seed + 1);

        Optional<Record> expect =
                recordRepository.findByParticularIdAndType((long) id, type);

        while (expect.isEmpty()) {
            expect = getRandomOptionalRecord(seed, type);
        }

        return expect;
    }

    private String wrap(Record record, long maxParticularId) {
        DateTimeFormatter formatDateToPrint = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        return String.format("%s №%d (%d), added: %s\n%s",
                record.getType().getName(),
                record.getParticularId(),
                maxParticularId,
                record.getApprovedDate().format(formatDateToPrint),
                record.getText());
    }
}
