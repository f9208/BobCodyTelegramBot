package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.bobcody.config.BotConfigTest;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = {"classpath:sql/initHSQL_DB.sql", "classpath:sql/populateDB.sql"})
@SpringBootTest(classes = BotConfigTest.class)
public abstract class AbstractSpringBootStarterTest {
    protected String[] ignoreFields;

    protected void assertMatchIgnoreFields(Object actual, Object expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields(ignoreFields)
                .isEqualTo(expected);
    }

    protected void assertMatch(Object actual, Object expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}