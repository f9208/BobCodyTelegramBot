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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.multibot.bobcody.BobCodyBot;
import ru.multibot.bobcody.Services.HotPies.PiesParser;
import ru.multibot.bobcody.Services.HotPies.SinglePie;
import ru.multibot.bobcody.controller.SQL.Entities.Guest;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@Configuration // говорит спрингу, типа, тут есть бины. несколько!
@ConfigurationProperties(prefix = "botloading")
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableScheduling

public class BotConfig {
    String botToken;
    String botName;
    String webHookPath;


    @Bean
    public List<Guest> guestsList() {
        return new ArrayList<>();
    }

    @Bean
    public BobCodyBot bobCodyBot() {
        BobCodyBot bot = new BobCodyBot();
        bot.setBotName(botName);
        bot.setBotToken(botToken);
        bot.setWebHookPath(webHookPath);
        return bot;
    }

    @Bean
    public MessageSource messageSource() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("someNameMessangeSource");
        return messageSource;
    }

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

    @Bean
    public List<SinglePie> piesList() {
        Random r = new Random();
        int numberOfPage = r.nextInt(3100);
        List<SinglePie> result = new ArrayList<>();
        SinglePie failed = new SinglePie();
        failed.setTextPieItself("сегодня пирожков нет");
        try {
            result = new PiesParser().gelListPies(numberOfPage);
            result.addAll(new PiesParser().gelListPies(numberOfPage + 1));
        } catch (Exception e) {
            result.add(failed);
        }
        return result;
    }


}

