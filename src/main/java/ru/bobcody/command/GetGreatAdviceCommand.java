package ru.bobcody.command;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ru.bobcody.services.SettingService;

public class GetGreatAdviceCommand extends AbstractCommand {

    @Autowired
    private SettingService settingService;

    @Override
    public String execute() {

        RestTemplate restTemplate = new RestTemplate();
        String fgaApiUrl = settingService.getFgaUrl();

        Advice advice = restTemplate.getForObject(fgaApiUrl, Advice.class);

        return advice.getText();
    }

    @Getter
    @Setter
    private static class Advice {
        String id;
        String text;
        String sound;
    }
}
