package ru.multibot.bobcody.Service.FuckingGreatAdvice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "fga")
public class FuckingGreatAdvice {
    String randomAdviceLink;
    //надо инициализировать как то иначе, пусть бин создается, чтоли.
    ObjectMapper objectMapper = new ObjectMapper();


    private String parser() throws IOException {
        StringBuilder result = new StringBuilder();
        String temp;
        URL randomAdvice = null;
        try {
            randomAdvice = new URL(randomAdviceLink);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(randomAdvice.openStream()));
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

