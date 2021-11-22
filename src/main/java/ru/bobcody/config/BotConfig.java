package ru.bobcody.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.bobcody.BobCodyBot;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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
public class BotConfig {
    String botToken;
    String botName;
    String webHookPath;

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

    @SneakyThrows
    @PostConstruct
    //todo переделать на профиль только dev
    public void setWebHook() {
        URL link;
        HttpURLConnection connection = null;
        try {
            link = new URL("https://api.telegram.org/bot" + botToken + "/setWebhook?url=" + webHookPath);
            connection = (HttpURLConnection) link.openConnection();
            connection.setRequestMethod("GET");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer resp = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            resp.append(inputLine);
        }
        System.out.println("webHook" + webHookPath + "->> " + resp);
    }
}

