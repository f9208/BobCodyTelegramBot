package ru.multibot.bobcody.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.multibot.bobcody.BobCodyBot;


import javax.sql.DataSource;

@Getter
@Setter
@Configuration // говорит спрингу, типа, тут есть бины. несколько!
@ConfigurationProperties(prefix = "botloading")
//@EnableJpaRepositories
@EnableTransactionManagement
public class BotConfig {
    String botToken;
    String botName;
    String webHookPath;


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


}

