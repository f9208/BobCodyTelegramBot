package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.bobcody.config.BotConfigTest;

@Sql(scripts = {"classpath:sql/initDB.sql", "classpath:sql/populateDB.sql"})
@SpringBootTest(classes = BotConfigTest.class)
public abstract class AbstractSpringBootStarterTest {
    protected String[] ignoreFields;

    protected void assertMatchIgnoreFilds(Object actual, Object expected) {
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields(ignoreFields)
                .isEqualTo(expected);
    }
    protected void assertMatch(Object actual, Object expected) {
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
