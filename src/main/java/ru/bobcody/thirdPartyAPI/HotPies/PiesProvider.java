package ru.bobcody.thirdPartyAPI.HotPies;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

@Setter
@Getter
public class PiesProvider {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String rootLink = "https://poetory.ru/";

    public SinglePie getOneRandom() throws IOException {
        int quoteId = new Random().nextInt(111306);
        return getOne(quoteId); //продумать вариант ответа если нет коннекта.
    }

    private SinglePie getOne(int pieId) throws IOException {
        URL url = new URL(rootLink + pieId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(3000);
        conn.connect();
        StringBuilder pageHtml = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String temp;
        while ((temp = br.readLine()) != null) {
            pageHtml.append(temp);
            if (temp.contains("Другие лучшие")) {
                break;
            }
        }

        String cleanedHtml = pageHtml.toString().replaceAll("&quot;", "\"");
        int start = cleanedHtml.indexOf("{\"content\":"); //начало цитаты в строке
        int end = cleanedHtml.indexOf("data-hydrate"); // конец цитаты в строке
        String cutHtml = cleanedHtml.substring(start, end);
        cutHtml = cutHtml.replaceFirst("\\\\n" + "\\\\n " + "#poetory", "");
        cutHtml = cutHtml.replaceAll("https://poetory.ru/", "#poetory/");

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SinglePie pie = objectMapper.readValue(cutHtml, SinglePie.class);
        return pie;
    }
}




