package ru.bobcody.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bobcody.thirdPartyAPI.hotPies.PiesProvider;
import ru.bobcody.thirdPartyAPI.hotPies.SinglePie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@ConfigurationProperties(prefix = "pie")
public class PieService {
    @Autowired
    PiesProvider piesProvider;
    private static List<SinglePie> listPies = new ArrayList<>();
    private static int SIZE;
    private static int accumulate;

    static {
        refreshAccumulate();
    }

    @Scheduled(fixedDelayString = "PT10M")
    private void initList() throws IOException {
        if (!listPies.isEmpty()) listPies.clear();
        listPies.addAll(populate(SIZE));
        refreshAccumulate();
        log.info("Pies storage has been refreshed, current size={} ", listPies.size());
    }

    private List<SinglePie> populate(int count) throws IOException {
        List<SinglePie> result = new ArrayList<>();
        while (count != 0) {
            log.info("loading pies... {}", count);
            result.add(PiesProvider.getOneRandom());
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

    private static void refreshAccumulate() {
        accumulate = SIZE - 1;
    }

    @Value("${pie.size}") //Spring doesn't support injection in static fields
    private void setSize(int size) {
        PieService.SIZE = size;
    }
}
