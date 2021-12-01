package ru.bobcody.controller.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Slf4j
@Controller
public class IndexPageController {
    @Autowired
    private TextMessageService textMessageService;
    @Autowired
    private LinkService linkService;
    // Id чата, который будет показываться на главной по умолчанию. для dev и prod это разные айдишники
    private final long defaultChatId;

    public IndexPageController(@Value("${chat.defaultChatId}") long defaultChatId) {
        this.defaultChatId = defaultChatId;
    }

    @GetMapping(value = "/")
    public String get(Model model, HttpServletRequest httpServletRequest) {
        log.info("get index page");
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
        log.info("get page with logs, link:  /{}/{}", chatId, dateAsString);
        LocalDate date = LocalDate.parse(dateAsString);
        model.addAttribute("messages", textMessageService.getOnDateBetweenForChat(date, date, chatId));
        model.addAttribute("currentChatId", chatId);
        boolean label = chatId == defaultChatId;
        model.addAttribute("label", label);
        return "day";
    }

    @GetMapping(value = "/savedImages/{filename:\\w+}.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImageAsByteArray(@PathVariable("filename") String fileName) throws IOException {
        log.info("get image for fileName {}.jpg", fileName);
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
