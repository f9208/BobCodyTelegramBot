package ru.bobcody.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.List.of;

@Service
@Getter
@PropertySource(value = {"classpath:touchBot.properties",
        "classpath:onetwothree.properties"},
        encoding = "UTF-8")
public class DirectiveService {
    private List<String> slap = of("бот", "bot", "bobcodybot", "бобби", "@bobcodybot",
            "bob", "боб", "бобу", "боту", "бота");

    private List<String> amd = of("amd", "амд");

    private List<String> oldMan = of("!ссылки");

    private List<String> course = of("!курс");

    private List<String> friday = of("пятница", "!пятница", "friday",
            "!friday", "!today", "!дн");

    private List<String> fga = of("!обс", "!fga");

    private List<String> help = of("!help", "!хелп", "!помощь", "/help", "!команды",
            "!старт", "/start");

    private List<String> oneTwoThree = of("123", "!123");

    private List<String> pies = List.of("!pie", "!пирожок", "pie");

    private List<String> qu = of("ку", "!ку", " ку!", "qu", "!qu");

    private List<String> today = of("!сегодня", "!дата", "!время", "!time", "!now", "!time");

    private List<String> forecast = of("!w", "!weather", "!g", "!п", "!погода");

    private List<String> city = of("!город");

    private List<String> top = of("!top", "!топ");

    private List<String> recordApprove = of("!approvecaps", "!approvequote");

    private List<String> recordAdd = of("!aq", "!дц");

    private List<String> quote = of("!q", "!quote", "!ц", "!цитата");

    private List<String> caps = of("!caps", "!капс", "!к");

    //фразочки-ответы
    @Value("#{${qu.answer}}")
    private List<String> quAnswer;

    @Value("#{${onetwothree.phrases}}")
    private List<String> oneTwoThreePhrases;

    @Value("#{${slap.phrases}}")
    private List<String> slapPhrases;
}
