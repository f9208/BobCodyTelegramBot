package ru.bobcody.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.domain.Link;
import ru.bobcody.repository.LinkRepository;

import javax.persistence.EntityNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static ru.bobcody.CommonTextConstant.NO_SUCH_FILE;

@Slf4j
//@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;

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
                link.getCreateDate(),
                link.isEnabled(),
                link.getPath(),
                link.getSize(),
                link.getChat().getId(),
                link.getGuest().getId(),
                link.getName());
    }
}