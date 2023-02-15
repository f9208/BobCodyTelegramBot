package ru.bobcody.command;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.thirdpartyapi.hotpies.UnitPie;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetHotPieCommand {

    @Autowired
    private ObjectMapper objectMapper;

    private String pieId;
    private final String rootUrl = "https://poetory.ru/";

    public GetHotPieCommand(String pieId) {
        this.pieId = pieId;
    }

    public UnitPie execute() throws IOException, InterruptedException {
        String requestUrl = rootUrl + pieId;

        HttpRequest request;
        HttpResponse<String> response;

        try {
            request = HttpRequest.newBuilder(new URI(requestUrl))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        HttpClient client = HttpClient.newHttpClient();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new HttpResponseException(response.statusCode(), "");
        }

        String cleanedHtml = response.body().replace("&quot;", "\"");

        int start = cleanedHtml.indexOf("{\"content\":"); //начало цитаты в строке
        int end = cleanedHtml.indexOf("data-hydrate"); // конец цитаты в строке
        String cutHtml = cleanedHtml.substring(start, end);
        cutHtml = cutHtml.replaceFirst("\\\\n" + "\\\\n " + "#poetory", "");

        String jsonHtml = cutHtml.replace("https://poetory.ru/", "#poetory/");
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(jsonHtml, UnitPie.class);
    }
}
