package ru.bobcody.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bobcody.entities.Link;
import ru.bobcody.repository.LinkRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Slf4j
@Service
public class LinkService {
    @Autowired
    LinkRepository linkRepository;

    public Link saveLink(Link link) {
        Objects.requireNonNull(link);
        log.info("save link to db, path: {}, guest_id: {}", link.getPath(), link.getGuest().getId());
        return linkRepository.save(link);
    }

    public Path getPathByFilName(String fileName) {
        Link link = linkRepository.findLinkByName(fileName);
        return Paths.get(link.getPath());
    }
}