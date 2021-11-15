package ru.bobcody.thirdPartyAPI.FuckingGreatAdvice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "fga")
public class FuckingGreatAdvice {
    String randomAdviceLink;
    @Autowired
    ObjectMapper objectMapper;

    private String parser() throws IOException, SocketTimeoutException, MalformedURLException {
        StringBuilder result = new StringBuilder();
        URL randomAdviceUrl = new URL(randomAdviceLink);
        HttpURLConnection connection = (HttpURLConnection) randomAdviceUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(2000);
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
        JsonNode text = jsonNode.get("text");
        return text.asText();
    }

}

