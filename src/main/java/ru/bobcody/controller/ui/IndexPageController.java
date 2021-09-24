package ru.bobcody.controller.ui;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.bobcody.services.TextMessageService;

import java.time.LocalDate;
import java.util.List;

@Controller
@Getter
@Setter
@ConfigurationProperties(prefix = "chat", ignoreInvalidFields = true)
public class IndexPageController {
    @Autowired
    TextMessageService textMessageService;

    private long main;

    @GetMapping(value = "/")
    public String get(Model model) {
        List<LocalDate> dates = textMessageService.getListDatesForChat(main);
        System.out.println( model.getAttribute("chatId"));
        model.addAttribute("dates", dates);
        model.addAttribute("mainChatId", main);
        return "index";
    }

    @GetMapping(value = "/{chatId}/{dateAsString}")
    public String getForDate(@PathVariable String dateAsString, @PathVariable long chatId, Model model) {
        LocalDate date = LocalDate.parse(dateAsString); //todo переделать этот костыль
        model.addAttribute("messages", textMessageService.getOnDateBetweenForChat(date, date, chatId));
        model.addAttribute("currentChatId", chatId);
        model.addAttribute("mainChatId", main);
        return "day";
    }

    @GetMapping(value = "/{chatId}")
    public String getDatesForChat(@PathVariable long chatId, Model model) {
        List<LocalDate> dates = textMessageService.getListDatesForChat(chatId);
        System.out.println(dates.size());
        model.addAttribute("dates", dates);
        model.addAttribute("chatId", chatId);
        return "days";
    }
}
