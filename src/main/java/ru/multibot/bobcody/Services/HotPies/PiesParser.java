package ru.multibot.bobcody.Services.HotPies;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class PiesParser {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNodeFullLine;

    private List<String> getAllPiesAsListJson() throws IOException {
        List<String> result = new ArrayList<>();
        Random r = new Random();
        int numberOfPage = r.nextInt(3200);
        if (numberOfPage == 0) numberOfPage = 1; // на нулевую страницу не кинет
        System.out.println(numberOfPage);
        String urlLink = "https://poetory.ru/all/" + numberOfPage;
        Document doc = Jsoup.connect(urlLink).get();
        Elements allHeadersPies = doc.getElementsByAttribute("data-react-props");
        for (Element ae : allHeadersPies) {
            String forAdd = ae.attr("data-react-props");
            if (forAdd.length() != 2) result.add(forAdd);
        }
        return result;
    }

    public List<SinglePie> handleList() throws IOException {
        JsonNode head;
        JsonNode mainContent;
        List<SinglePie> allPiesFromOneSite = new ArrayList<>();
        SinglePie currentPie = new SinglePie();
        for (String temp : getAllPiesAsListJson()) {
            head = objectMapper.readTree(temp);
            mainContent = head.get("content");
            currentPie.setTextPieItself(mainContent.get("clean_content").asText().replace("\n", "\n"));
            currentPie.setLinkToPie(head.get("shareURL").asText());
            allPiesFromOneSite.add(currentPie);
        }
        return allPiesFromOneSite;
    }
}

@Getter
@Setter
class SinglePie {
    String textPieItself;
    String linkToPie;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(textPieItself).append("\n").append(linkToPie);
        return result.toString();
    }
}

//package controller;
//
//        import org.jsoup.Jsoup;
//        import org.jsoup.nodes.Document;
//        import org.jsoup.nodes.Element;
//        import org.jsoup.select.Elements;
//
//        import java.io.IOException;
//        import java.net.MalformedURLException;
//        import java.net.URL;
//        import java.util.ArrayList;
//
////Elements content = doc.select("div[id~=(shout_)[0-9]+]");
//// Elements divs = parse.select("div[id^=shout");
//
//
//    public ArrayList<String> getTemperatureList() throws IOException {
//        ArrayList<String> result = new ArrayList<>();
//        Element table = page.select("table[id=forecastTable]").first();
//        Elements temperature = table.select("div[class=t_0]");
//
//        for (int i = 0; i < temperature.size(); i++) {
//            result.add(temperature.get(i).select("div[class=t_0]").text());
//        }
//
//        return result;
//    }
//
//    public ArrayList<String> getPrecipitation() throws IOException {
//        ArrayList<String> resultX2 = new ArrayList<>();
//        Element table = page.select("table[id=forecastTable]").first();
//        Elements prec = table.select("div[class=pr_0]");
//        for (Element temp : prec) {
//            String a = temp.attr("onmouseover");
//            if (a.contains("\'")) {
//                String[] b = a.split("\'");
//                resultX2.add(b[1]);
//            }
//        }
//
//        for (int i = 0; i < resultX2.size(); i++) {
//            String test = resultX2.get(i);
//            StringBuilder changedString = new StringBuilder();
//            if (test.contains("Явления ") || test.contains("Без осадков"))
//                changedString.append("Без осадков");
//            else {
//                int indexBracket = test.indexOf("(");
//                if (test.contains("снега")) {
//                    int indexEnd = test.lastIndexOf("снега");
//                    changedString.append(test.substring(indexBracket + 1, indexEnd + 5));
//                } else if (test.contains("воды")) {
//                    int indexEnd = test.lastIndexOf("воды");
//                    changedString.append(test.substring(indexBracket + 1, indexEnd + 4));
//                }
//            }
//            resultX2.set(i, changedString.toString());
//            changedString = null;
//        }
//        ArrayList<String> result = new ArrayList<>();
//        for (int i = 0; i < resultX2.size(); i = i + 2) {
//            result.add(resultX2.get(i));
//        }
//
//        return result;
//    }
//
//    public ArrayList<String> getClouds() throws IOException {
//        ArrayList<String> result = new ArrayList<>();
//        Element table = page.select("table[id=forecastTable]").first();
//        Elements prec = table.select("div[class=cc_0]");
//        Elements prec2 = prec.select("div[class^=c]");
//        for (Element one : prec2) {
//            String innerString = one.attr("onmouseover");
//            if (innerString.contains("b>")) {
//                String[] cutedString = innerString.split("b>");
//                cutedString[1] = cutedString[1].substring(0, cutedString[1].length() - 2);
//                result.add(cutedString[1]);
//            }
//        }
//        return result;
//    }
//
//    public ArrayList<String> getWindDirection() throws IOException {
//        ArrayList<String> result = new ArrayList<>();
//        Element table = page.select("table[id=forecastTable]").first();
//        Elements line = table.select("td[class^=grayLittle]");
//        for (int i = 0; i < line.size(); i++) {
//            result.add(line.get(i).text());
//        }
//        return result;
//    }
//
//    public ArrayList<String> getWindPower() throws IOException {
//        ArrayList<String> result = new ArrayList<>();
//        Element table = page.select("table[id=forecastTable]").first();
//        Elements line = table.select("div[class^=wv_0]");
//        for (Element one : line) {
//            String innerString = one.attr("onmouseover");
//            if (innerString.contains("this")) {
//                String[] cut = innerString.split("\'");
//                result.add(cut[1]);
//            }
//        }
//        return result;
//    }
//}



