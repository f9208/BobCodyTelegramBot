package ru.bobcody.config;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.bobcody.services.PieService;
import ru.bobcody.thirdpartyapi.fuckinggreatadvice.FuckingGreatAdviser;
import ru.bobcody.thirdpartyapi.hotpies.SinglePie;

import javax.sql.DataSource;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.bobcody.updates.handlers.chathandlers.secondlayerhandler.utils.TextConstantHandler.DEFAULT_ADVICE;

@TestConfiguration
public class BotTestConfig {
    /* предпочел захардкодить креденшелы для hsqldb чтобы случайно не дропнуть postgresql базу во время тестов*/
    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username("aaa")
                .password("12345")
                .driverClassName("org.hsqldb.jdbc.JDBCDriver")
                .url("jdbc:hsqldb:file:D:/temp/jenny/temp")
                .build();
    }

    @Bean
    @Qualifier("mock")
    public PieService pieService() {
        PieService pieService = Mockito.mock(PieService.class);
        SinglePie pie = new SinglePie();
        pie.setShareURL("url://somwere.com");
        pie.setShareText("пирожок пирожок ура");
        try {
            when(pieService.getOne()).thenReturn(pie);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pieService;
    }

//    @Bean
    public FuckingGreatAdviser fuckingGreatAdviser() {
        //эдвайсер работает с внешним апи которое может в любой момент отвалиться поэтому мок
        FuckingGreatAdviser fuckingGreatAdviser = mock(FuckingGreatAdviser.class);
        try {
            when(fuckingGreatAdviser.getAdvice()).thenReturn(DEFAULT_ADVICE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fuckingGreatAdviser;
    }
}