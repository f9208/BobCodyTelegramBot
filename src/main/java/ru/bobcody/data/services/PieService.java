package ru.bobcody.data.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bobcody.thirdPartyAPI.hotPies.PiesProvider;
import ru.bobcody.thirdPartyAPI.hotPies.SinglePie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PieService {
    @Autowired
    private PiesProvider piesProvider;
    private List<SinglePie> listPies = new ArrayList<>();
    @Value("${pie.size}")
    private int SIZE;
    private int accumulate;

    {
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
        accumulate = SIZE - 1;
    }
}
