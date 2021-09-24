package ru.bobcody.controller.ui;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.bobcody.services.TextMessageService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
@Getter
@Setter
@ConfigurationProperties(prefix = "chat")
public class IndexPageController {
    @Autowired
    TextMessageService textMessageService;

    private final long defaultChatId;

    public IndexPageController(@Value("${chat.defaultChatId}") long defaultChatId) {
        this.defaultChatId = defaultChatId;
    }

    @GetMapping(value = "/")
    public String get(Model model, HttpServletRequest httpServletRequest) {
        long currentChatId = defaultChatId;
        String id = httpServletRequest.getParameter("chatId");
        if (id != null) {
            currentChatId = Long.parseLong(id);
        }
        List<LocalDate> dates = textMessageService.getListDatesForChat(currentChatId);
        model.addAttribute("dates", dates);
        model.addAttribute("currentChatId", currentChatId);
        boolean label = currentChatId == defaultChatId;
        model.addAttribute("label", label);
        return "index";
    }

    @GetMapping(value = "/{chatId}/{dateAsString}")
    public String getForDate(@PathVariable String dateAsString, @PathVariable long chatId, Model model) {
        LocalDate date = LocalDate.parse(dateAsString); //todo переделать этот костыль
        model.addAttribute("messages", textMessageService.getOnDateBetweenForChat(date, date, chatId));
        model.addAttribute("currentChatId", chatId);
        boolean label = chatId == defaultChatId;
        model.addAttribute("label", label);
        return "day";
    }
}
