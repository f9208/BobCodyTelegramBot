package ru.bobcody.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class SettingService {
    @Value("${botloading.bot-name}")
    private String botName;

    @Value("${botloading.bot-token}")
    private String botToken;

    @Value("${botloading.web-hook-path}")
    private String webHookPath;

    @Value("${botloading.web-hook.startup:false}")
    private boolean setStartUpWebHook;


}
