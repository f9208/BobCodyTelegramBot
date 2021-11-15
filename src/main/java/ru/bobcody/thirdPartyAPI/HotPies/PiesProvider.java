package ru.bobcody.thirdPartyAPI.HotPies;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

@Setter
@Getter
@Component
public class PiesProvider {
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static String rootUrl = "https://poetory.ru/";

    private PiesProvider() {
    }

    public static SinglePie getOneRandom() throws IOException {
        int quoteId = new Random().nextInt(111306);
        return getOne(quoteId);
    }

    public static SinglePie getOne(int pieId) throws IOException {
        HttpURLConnection conn = getConnect(pieId);
        conn.connect();
        if (conn.getResponseCode() == 200) {
            StringBuilder pageHtml = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String temp;
            while ((temp = br.readLine()) != null) {
                pageHtml.append(temp);
                if (temp.contains("Другие лучшие")) { //другие лучшие - брек поинт, все что ниже нам не нужно загружать
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
        } else return new SinglePie("ya.ru", "олол ололо, без Алеши все легло!");
    }

    private static HttpURLConnection getConnect(int pieId) throws IOException {
        URL url = new URL(rootUrl + pieId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        int counter = 1;
        while (conn.getResponseCode() != 200) {
            System.out.println("ResponseCode: " + conn.getResponseCode() + ", pieID=" + pieId+counter);
            conn = getConnect(pieId+counter);
            conn.connect();
            counter++;
            if (counter >= 100) {
                break;
            }
        }
        return conn;
    }
}




