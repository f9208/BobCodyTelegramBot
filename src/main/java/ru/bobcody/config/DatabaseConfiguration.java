package ru.bobcody.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {DatabaseConfiguration.BASE_PACKAGE__DOMAIN,
        DatabaseConfiguration.BASE_PACKAGE__REPOSITORY})
public class DatabaseConfiguration {
    public static final String BASE_PACKAGE__DOMAIN = "ru.bobcody.domain";
    public static final String BASE_PACKAGE__REPOSITORY = "ru.bobcody.repository";

    @Bean
    @ConfigurationProperties("db")
    public HikariDataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }


}
