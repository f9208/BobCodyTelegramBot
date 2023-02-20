package ru.bobcody.config;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.bobcody.services.SettingService;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static ru.bobcody.CommonUtils.checkEmpty;

@Slf4j
@Getter
@Setter
@Configuration
@EnableConfigurationProperties
public class BotConfig {
    @Autowired
    private SettingService settingService;

    @SneakyThrows
    @PostConstruct
    public void setWebHook() {
        if (settingService.isSetStartUpWebHook()) {

            String webHook = settingService.getWebHookPath();

            checkEmpty(webHook, "Параметр WebHookPath пуст!");

            String telegramSetWebHookUrl = String.format("https://api.telegram.org/bot%s/setWebhook?url=%s",
                    settingService.getBotToken(),
                    webHook);

            HttpRequest request =
                    HttpRequest.newBuilder(new URI(telegramSetWebHookUrl))
                            .GET()
                            .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("webHook {} ->> {}", settingService.getWebHookPath(), response.body());
        }
    }
}
