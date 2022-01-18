package ru.bobcody.controller.handlers.chathandlers.secondlayerhandler;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.bobcody.config.BotTestConfig;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = {"classpath:sql/initHSQL_DB.sql", "classpath:sql/populateDB.sql"})
@SpringBootTest(classes = BotTestConfig.class)
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
