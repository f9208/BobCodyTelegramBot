package ru.bobcody.thirdpartyapi.fuckinggreatadvice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class FuckingGreatAdviser {
    @Value("${fga.randomAdviceLink}")
    private String randomAdviceLink;
    @Autowired
    private ObjectMapper objectMapper;
    private static final int TIMEOUT = 2000;
    private static final String ADVICE_FIELD = "text";

    private String parser() throws IOException {
        StringBuilder result = new StringBuilder();
        URL randomAdviceUrl = new URL(randomAdviceLink);
        HttpURLConnection connection = (HttpURLConnection) randomAdviceUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(TIMEOUT);
        connection.connect();
        String temp;
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((temp = br.readLine()) != null) {
            result.append(temp);
        }
        br.close();
        return result.toString();
    }

    public String getAdvice() throws IOException {
        String init = parser();
        JsonNode jsonNode = objectMapper.readTree(init);
        JsonNode text = jsonNode.get(ADVICE_FIELD);
        return text.asText();
    }
}

