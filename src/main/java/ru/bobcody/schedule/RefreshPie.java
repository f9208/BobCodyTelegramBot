package ru.bobcody.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.bobcody.thirdpartyapi.hotpies.PieBuffer;

@Component
public class RefreshPie extends AbstractTimer {
    @Autowired
    private PieBuffer pieBuffer;

    @Value("${timer.pie.enabled}")
    private boolean enabled;

    @Override
    protected boolean isEnabled() {
        return enabled;
    }

    @Override
    @Scheduled(cron = "${timer.pie.cron}")
    protected void start() {
        super.start();
    }

    @Override
    protected void startInternal() {
        process();
    }

    private void process() {
        pieBuffer.refreshBuffer();
    }
}
