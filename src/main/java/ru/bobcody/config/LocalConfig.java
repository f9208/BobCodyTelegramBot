package ru.bobcody.config;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "botloading")
@Profile("local")
public class LocalConfig {
    String botToken;
    String webHookPath;

    @SneakyThrows
    @PostConstruct
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
