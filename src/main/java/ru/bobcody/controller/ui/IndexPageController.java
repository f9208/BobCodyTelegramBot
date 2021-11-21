package ru.bobcody.controller.ui;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.bobcody.services.LinkService;
import ru.bobcody.services.TextMessageService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Controller
@Getter
@Setter
@ConfigurationProperties(prefix = "chat")
public class IndexPageController {
    @Autowired
    TextMessageService textMessageService;
    @Autowired
    LinkService linkService;
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

    @GetMapping(value = "/savedImages/{filename:\\w+}.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImageAsByteArray(@PathVariable("filename") String fileName) throws IOException {
        Path pathByFileName = getPathInOSFromDB(fileName + ".jpg");
        File file = new File(pathByFileName.toUri());
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);
    }

    private Path getPathInOSFromDB(String filName) {
        Path result = linkService.getPathByFilName(filName);
        return result;
    }
}
