package ru.bobcody.config;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
@ConfigurationProperties(prefix = "botloading")
@Profile(value = {"local"})
public class LocalConfig {
    String botToken;
    String webHookPath;

    @SneakyThrows
    @PostConstruct
    public void setWebHook() {
        URL link = new URL("https://api.telegram.org/bot" + botToken + "/setWebhook?url=" + webHookPath);
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
                log.info("webHook {} ->> {}", webHookPath, resp);
            }
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        }
    }
}
