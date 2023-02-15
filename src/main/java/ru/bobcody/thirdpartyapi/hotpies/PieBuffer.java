package ru.bobcody.thirdpartyapi.hotpies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.bobcody.command.GetHotPieCommand;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class PieBuffer {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private final int BUFFER_SIZE = 10;
    private final List<UnitPie> BUFFER_PIES = new ArrayList<>(BUFFER_SIZE);
    private static final int MAX_COUNT_EXECUTE = 20;

    private int executeGetRandomCounter = 0;
    private boolean maintenanceRefresh = false;

    private final int SEED = 118104; // максимальный идентификатор записи стороннего сервиса на данный момент

    private final UnitPie dummy = new UnitPie("ололо ололо, что то в бОте полегло", "");

    @EventListener(ApplicationReadyEvent.class)
    public void refreshBuffer() {
        if (maintenanceRefresh) {
            return;
        }

        log.info("старт обновления буфера пирожков");
        maintenanceRefresh = true;
        LocalTime startTime = LocalTime.now();

        Random random = new Random();
        GetHotPieCommand command;
        int countExecute = 0;

        for (int i = 0; i < BUFFER_SIZE; ) {
            int randomId = random.nextInt(SEED);

            command = new GetHotPieCommand(String.valueOf(randomId));
            beanFactory.autowireBean(command);

            try {
                UnitPie unitPie = command.execute();

                if (BUFFER_PIES.size() <= i) {
                    BUFFER_PIES.add(unitPie);
                } else {
                    BUFFER_PIES.set(i, unitPie);
                }
                i++;
            } catch (Exception e) {
                log.info("ошибка. попытка номер {}", ++countExecute);
            }

            if (countExecute > MAX_COUNT_EXECUTE) {
                break;
            }
        }
        executeGetRandomCounter = 0;
        LocalTime finishTime = LocalTime.now();
        log.info("буфер пирожков обновился за {} секунды", Duration.between(startTime, finishTime).getSeconds());

        maintenanceRefresh = false;
    }

    public UnitPie getRandomBufferedPie() {
        if (BUFFER_PIES.isEmpty()) {
            return dummy;
        }

        Random random = new Random();

        int randomIndex = random.nextInt(BUFFER_PIES.size());

        executeGetRandomCounter++;
        if (executeGetRandomCounter > 5) {
            new Thread(this::refreshBuffer).start();
            executeGetRandomCounter = 0;
        }

        return BUFFER_PIES.get(randomIndex);
    }
}
