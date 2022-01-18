package ru.bobcody.data.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.bobcody.controller.handlers.chathandlers.secondlayerhandler.AbstractSpringBootStarterTest;

import javax.persistence.EntityNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.bobcody.data.services.manual.LinkData.LINK_1;

class LinkServiceTest extends AbstractSpringBootStarterTest {
    @Autowired
    LinkService linkService;

    @Test
    @Transactional
    void saveLink() {
        assertThatThrownBy(() ->
                linkService.getPathByFilName(LINK_1.getName())).isInstanceOf(EntityNotFoundException.class);
        int count = linkService.saveLink(LINK_1);
        assertThat(count).isEqualTo(1);
        Path saved = linkService.getPathByFilName(LINK_1.getName());
        assertThat(saved).isEqualTo(Paths.get(LINK_1.getPath()));
    }
}