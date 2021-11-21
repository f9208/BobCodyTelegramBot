package ru.bobcody.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bobcody.thirdPartyAPI.HotPies.PiesProvider;
import ru.bobcody.thirdPartyAPI.HotPies.SinglePie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    }

    private List<SinglePie> populate(int count) throws IOException {
        List<SinglePie> result = new ArrayList<>();
        while (count != 0) {
            System.out.println("loading pies... " + count);
            result.add(PiesProvider.getOneRandom());
            count--;
        }
        return result;
    }

    public SinglePie getOneRandom() throws IOException {
        accumulate--;
        if (accumulate < 0) {
            initList();
        }
        if (listPies.isEmpty()) {
            initList();
        }
        return listPies.get(accumulate);
    }

    private static void refreshAccumulate() {
        accumulate = SIZE - 1;
    }

    @Value("${pie.size}") //Spring doesn't support injection in static fields
    public void setSize(int size) {
        PieService.SIZE = size;
    }
}
