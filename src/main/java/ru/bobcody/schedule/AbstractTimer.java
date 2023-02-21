package ru.bobcody.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import ru.bobcody.services.SettingService;

public abstract class AbstractTimer {
    @Autowired
    protected AutowireCapableBeanFactory beanFactory;

    @Autowired
    private SettingService settingService;

    abstract protected boolean isEnabled();

    abstract protected void startInternal();

    protected void start() {
        if (!settingService.isMaintenanceMode()
                && isEnabled()) {
            startInternal();
        }
    }
}
