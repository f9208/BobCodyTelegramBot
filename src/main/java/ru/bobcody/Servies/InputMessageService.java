package ru.bobcody.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.Entities.InputMessage;
import ru.bobcody.repository.InputMessageRepository;

@Service
public class InputMessageService {
    @Autowired
    InputMessageRepository inputMessageRepository;

    @Transactional
    public InputMessage save(InputMessage inputMessage) {
        return inputMessageRepository.save(inputMessage);
    }
}
