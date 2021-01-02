package ru.multibot.bobcody.ThirdPartyAPI.HotPies;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PiesParser {
    private ObjectMapper objectMapper = new ObjectMapper();
    private JsonNode jsonNodeFullLine;

    private List<String> allPiesAsListJsonParser(int numberOfPage) throws IOException {
        List<String> result = new ArrayList<>();
        if (numberOfPage == 0) numberOfPage = 1; // на нулевую страницу не кинет
        String urlLink = "https://poetory.ru/all/" + numberOfPage;
        Document doc = Jsoup.connect(urlLink).get();
        Elements allHeadersPies = doc.getElementsByAttribute("date-react-props");
        for (Element ae : allHeadersPies) {
            String forAdd = ae.attr("date-react-props");
            if (forAdd.length() != 2) result.add(forAdd);
        }
        return result;
    }

    public List<SinglePie> gelListPies(int numberOfPage) throws IOException {
        JsonNode head;
        JsonNode mainContent;
        List<SinglePie> allPiesFromOneSite = new ArrayList<>();
        SinglePie currentPie;

        for (String temp : allPiesAsListJsonParser(numberOfPage)) {
            currentPie = new SinglePie();
            head = objectMapper.readTree(temp);
            mainContent = head.get("content");
            currentPie.setTextPieItself(mainContent.get("clean_content").asText().replace("\n", "\n"));
            currentPie.setLinkToPie(head.get("shareURL").asText());
            allPiesFromOneSite.add(currentPie);
        }

        return allPiesFromOneSite;

    }
}



