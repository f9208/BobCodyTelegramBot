package ru.bobcody.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bobcody.entities.Link;
import ru.bobcody.repository.LinkRepository;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LinkService {
    @Autowired
    LinkRepository linkRepository;

    public Link saveLink(Link link) {
        return linkRepository.save(link);
    }

    public Path getPathByFilName(String fileName) {
        Link link = linkRepository.findLinkByName(fileName);
        return Paths.get(link.getPath());
    }
}