package ru.multibot.bobcody.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.multibot.bobcody.controller.BobCodyBot;
import ru.multibot.bobcody.ThirdPartyAPI.HotPies.SinglePie;
import ru.multibot.bobcody.SQL.Entities.Guest;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * конфиг Spring boot'a
 * <p>
 * botToken, botName выдаются при регистрации бота,
 * webHookPath - адрес, куда телеграм прислыает вебхуки. по идее, это должен быть
 * непосредственно какой то серер, но щас он настроен на сервер на heroku
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "botloading")
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableScheduling

public class BotConfig {
    String botToken;
    String botName;
    String webHookPath;

    /**
     * guestsList - список, в который записываются гости конфы
     * под гостями подразумеваются те, от кого бот фиксировал какие либо входящие сообщения
     * почему List - незнаю, по идее, можно переделать на какой-нибудь HashSet
     * но тогда надо реализовывать equals() and hash() но пока нет необходимости
     */
    @Bean
    public List<Guest> guestsList() {
        return new ArrayList<>();
    }

    @Bean
    public BobCodyBot bobCodyBot() {
        return new BobCodyBot(botName, botToken, webHookPath);
    }

    @Bean
    public MessageSource messageSource() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("someNameMessageSource");
        return messageSource;
    }

    /**
     * "объект" доступа к базе данных. к нему самому непосредственно
     * мы никак не обращаемся, с ним взаимодействует только спринг (jdbcTemplate)
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    /**
     * объект для обработки JSONчиков. Создал синглтон, т.к. врядли будет паралельность в запросах
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * список пирожков. пирожок - это литературный жанр, кто не знает - google.com
     * сама функция была добавлена по приколу.
     * список из 60ти пирожков (с двух рендомных веб-страниц) при каждом запуске бота
     * загружается новый. Да да, там тупой парсинг веб-страницы.
     * В список закидываю для ускорения ответа на команду !пирожок
     * Но есть нюанс - когда бот переедет на какую то стабильную
     * площадку, он не будет постоянно "включаться-выключаться", т.е. обновляться этот
     * список не будет. Позже это надо как то обходить, как - я пока не придумал.
     */
    @Bean
    public List<SinglePie> piesList() {
        return new SinglePie().getPiesList();
    }
}

