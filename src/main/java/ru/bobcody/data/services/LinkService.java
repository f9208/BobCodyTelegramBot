package ru.bobcody.data.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.data.entities.Link;
import ru.bobcody.data.repository.ILinkRepository;

import javax.persistence.EntityNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static ru.bobcody.utilits.CommonTextConstant.NO_SUCH_FILE;

@Slf4j
@Service
public class LinkService {
    @Autowired
    private ILinkRepository linkRepository;

    @Transactional
    public int saveLink(Link link) {
        Objects.requireNonNull(link);
        log.info("save link to db, path: {}, guest_id: {}", link.getPath(), link.getGuest().getId());
        return prepareAndSave(link);
    }

    public Path getPathByFilName(String fileName) {
        Link link = linkRepository.findLinkByName(fileName);
        if (link == null) throw new EntityNotFoundException(NO_SUCH_FILE);
        return Paths.get(link.getPath());
    }

    public int prepareAndSave(Link link) {
        log.info("prepare and save URL");
        return linkRepository.saveOne(
                link.getDateCreated(),
                link.isEnabled(),
                link.getPath(),
                link.getSize(),
                link.getChat().getId(),
                link.getGuest().getId(),
                link.getName());
    }
}