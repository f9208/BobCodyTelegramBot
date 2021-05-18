package ru.bobcody.Servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.Entities.InputMessage;
import ru.bobcody.repository.InputMessageRepository;

@Service
public class InputMessageServiceImp implements InputMessageService {
    @Autowired
    InputMessageRepository inputMessageRepository;

    @Override
    @Transactional
    public InputMessage add(InputMessage inputMessage) {
        return inputMessageRepository.save(inputMessage);
    }
}
