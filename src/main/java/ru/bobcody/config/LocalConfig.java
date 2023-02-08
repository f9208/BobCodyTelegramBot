package ru.bobcody.config;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.bobcody.services.SettingService;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Slf4j
@Getter
@Setter
@Configuration
public class LocalConfig {
    @Autowired
    private SettingService settingService;

    @SneakyThrows
    @PostConstruct
    public void setWebHook() {
        if (settingService.isSetStartUpWebHook()) {
//todo переделать по человечески
            URL link = new URL("https://api.telegram.org/bot"
                    + settingService.getBotToken() + "/setWebhook?url=" + settingService.getWebHookPath());
            HttpURLConnection connection;
            StringBuilder resp = new StringBuilder();
            try {
                connection = (HttpURLConnection) link.openConnection();
                connection.setRequestMethod("GET");
                try (InputStream connectionInputStream = connection.getInputStream()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connectionInputStream));
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        resp.append(inputLine);
                    }
                    log.info("webHook {} ->> {}", settingService.getWebHookPath(), resp);
                }
            } catch (MalformedURLException | ProtocolException e) {
                e.printStackTrace();
            }
        }
    }
}
