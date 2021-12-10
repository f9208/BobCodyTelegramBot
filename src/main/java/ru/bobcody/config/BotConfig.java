package ru.bobcody.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.bobcody.BobCodyBot;

import javax.sql.DataSource;

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
@EnableCaching
@PropertySource(value = "classpath:commands.properties", encoding = "UTF-8")
public class BotConfig {
    private String botToken;
    private String botName;
    private String webHookPath;

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

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

