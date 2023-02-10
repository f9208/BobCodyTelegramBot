package ru.bobcody.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

public abstract class AbstractTimer {
    @Autowired
    protected AutowireCapableBeanFactory beanFactory;

    abstract protected boolean isEnabled();

    abstract protected void startInternal();

    protected void start() {
        if (isEnabled()) {
            startInternal();
        }

    }
}
