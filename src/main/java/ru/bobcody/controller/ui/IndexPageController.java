package ru.bobcody.controller.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.bobcody.services.TextMessageService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class IndexPageController {
    @Autowired
    TextMessageService textMessageService;

//    private static final long CURRENT_CHAT_ID = -458401902;
    private static final long DEFAULT_CHAT_ID = 445682905;

    @GetMapping(value = "/")
    public String get(Model model) {
        List<LocalDate> dates = textMessageService.getListDatesForChat(DEFAULT_CHAT_ID);
        model.addAttribute("dates", dates);
        return "index";
    }

    @GetMapping(value = "/{dateAsString}")
    public String getForDate(@PathVariable String dateAsString, Model model) {
        LocalDate date = LocalDate.parse(dateAsString); //todo переделать этот костыль
        model.addAttribute("messages", textMessageService.getOnDateBetweenForChat(date, date, DEFAULT_CHAT_ID));
        return "today";
    }
}
