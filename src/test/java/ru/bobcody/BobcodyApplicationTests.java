package ru.bobcody;

import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
//import ru.bobcody.config.BotConfigTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.bobcody.config.BotConfig;
import ru.bobcody.config.BotConfigTest;
import ru.bobcody.services.GuestService;

import javax.sql.DataSource;
import java.sql.SQLException;

//@Import(BotConfigTest.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BotConfigTest.class)
class BobcodyApplicationTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    ApplicationContext applicationContext;
    @Test
    public void t() {
        System.out.println(dataSource);    }

}
