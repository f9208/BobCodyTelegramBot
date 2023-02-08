package ru.bobcody.command;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bobcody.CommonConstants;
import ru.bobcody.services.SettingService;

public abstract class AbstractCommand implements CommonConstants {

    @Autowired
    protected SettingService settingService;

    abstract public <R> R  execute();
}
