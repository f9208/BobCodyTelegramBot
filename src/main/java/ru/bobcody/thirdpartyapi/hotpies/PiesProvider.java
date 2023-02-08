package ru.bobcody.thirdpartyapi.hotpies;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
//@Component
public class PiesProvider {
    /* у пирожков в айдишниках иногда попадаются мертвые души. Часто они идут блоками друг за другом по несколько штук
     *  чтоб не перебирать друг за другом, в случае попадания на мертвый - пропускаем сразу десять (константа SKIP)*/
    private static final int SKIP = 10;
    private static final int COUNTER_TRY = 50;
    private static final int SEED = 111306;
    private static final String STOP_WORD = "Другие лучшие";
    private static final int TIMEOUT = 5000;

    private final ObjectMapper objectMapper;
    @Value("${pie.url}")
    private String rootUrl;
    private final Random rand = new SecureRandom();

    public PiesProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public SinglePie getOneRandomly() throws IOException {
        int quoteId = rand.nextInt(SEED);
        return getOne(quoteId);
    }

    private SinglePie getOne(int pieId) throws IOException {
        HttpURLConnection conn = getConnect(pieId);
        conn.connect();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            StringBuilder pageHtml = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String temp;
            while ((temp = br.readLine()) != null) {
                pageHtml.append(temp);
                if (temp.contains(STOP_WORD)) {
                    break;
                }
            }
            String cleanedHtml = pageHtml.toString().replace("&quot;", "\"");
            int start = cleanedHtml.indexOf("{\"content\":"); //начало цитаты в строке
            int end = cleanedHtml.indexOf("data-hydrate"); // конец цитаты в строке
            String cutHtml = cleanedHtml.substring(start, end);
            cutHtml = cutHtml.replaceFirst("\\\\n" + "\\\\n " + "#poetory", "");
            cutHtml = cutHtml.replace("https://poetory.ru/", "#poetory/");

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(cutHtml, SinglePie.class);

        } else return new SinglePie("ya.ru", "олол ололо, без Алеши все легло!");
    }

    private HttpURLConnection getConnect(int pieId) throws IOException {
        HttpURLConnection conn = createConnect(pieId);
        int counter = 0;
        while (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            counter = counter + SKIP;
            conn = createConnect(pieId + counter);
            log.info("Re-connect, try # {}", counter / SKIP);
            if (counter / SKIP >= COUNTER_TRY) {
                break;
            }
        }
        return conn;
    }

    private HttpURLConnection createConnect(int pieId) throws IOException {
        URL url = new URL(rootUrl + pieId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(TIMEOUT);
        log.info("Create Connect to {}: ResponseCode: {}, pieID = {}", rootUrl, conn.getResponseCode(), pieId);
        return conn;
    }
}




