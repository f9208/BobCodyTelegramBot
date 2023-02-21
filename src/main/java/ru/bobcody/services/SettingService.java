package ru.bobcody.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class SettingService {
    @Value("${bot.bot-name}")
    private String botName;

    @Value("${bot.bot-token}")
    private String botToken;

    @Value("${bot.web-hook-path}")
    private String webHookPath;

    @Value("${bot.web-hook.reload-on-startup:false}")
    private boolean setStartUpWebHook;

    @Value("${admin-chat-id}")
    private String adminChatId;

    @Value("${moderator-chat-id}")
    private List<Long> moderatorChatIds;

    @Value("${fga.random-advice-url}")
    private String fgaUrl;

    @Value("${sber.rate.url}")
    private String sberRateUrl;

    @Value("${weather.api.url}")
    private String urlWeatherApi;

    @Value("${weather.api.key}")
    private String keyWeatherApi;

    @Value("${elk.path}")
    private String elkPath;

    @Value("${maintenance}")
    private boolean maintenanceMode;
}
