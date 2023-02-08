package ru.bobcody.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bobcody.thirdpartyapi.hotpies.PiesProvider;
import ru.bobcody.thirdpartyapi.hotpies.SinglePie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//@Service("original")
@RequiredArgsConstructor
public class PieService implements InitializingBean {
    private final PiesProvider piesProvider;
    private final List<SinglePie> listPies = new ArrayList<>();
    @Value("${pie.size}")
    private int size;
    private int accumulate;

    @Override
    public void afterPropertiesSet() throws Exception {
        refreshAccumulate();
    }

    @Scheduled(fixedDelayString = "PT10M")
    private void initList() throws IOException {
        if (!listPies.isEmpty()) listPies.clear();
        listPies.addAll(populate(size));
        refreshAccumulate();
        log.info("Pies storage has been refreshed, current size={} ", listPies.size());
    }

    private List<SinglePie> populate(int count) throws IOException {
        List<SinglePie> result = new ArrayList<>();
        while (count != 0) {
            log.info("loading pies... {}", count);
            result.add(piesProvider.getOneRandomly());
            count--;
        }
        return result;
    }

    public SinglePie getOne() throws IOException {
        accumulate--;
        if (accumulate < 0) {
            initList();
        }
        if (listPies.isEmpty()) {
            initList();
        }
        log.info("send pie, index={}", accumulate);
        return listPies.get(accumulate);
    }

    private void refreshAccumulate() {
        accumulate = size - 1;
    }
}
