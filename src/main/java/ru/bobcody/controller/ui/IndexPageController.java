package ru.bobcody.controller.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.bobcody.services.TextMessageService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexPageController {
    @Autowired
    TextMessageService textMessageService;

    @GetMapping(value = "/")
    public String get(Model model) {
        List<LocalDate> dates = textMessageService.getListDates();
        model.addAttribute("dates", dates);
        return "index";
    }

    @GetMapping(value = "/today")
    public String getToday(Model model) {
        model.addAttribute("message",
                textMessageService.getForDateBetween(LocalDate.now(), LocalDate.now()));
        return "today";
    }

    @GetMapping(value = "/{dateAsString}")
    public String getForDate(@PathVariable String dateAsString, Model model) {
        LocalDate date = LocalDate.parse(dateAsString); //todo переделать этот костыль
        model.addAttribute("message",
                textMessageService.getForDateBetween(date, date));
        return "today";
    }
}
