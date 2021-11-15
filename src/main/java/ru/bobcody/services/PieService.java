package ru.bobcody.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bobcody.thirdPartyAPI.HotPies.PiesProvider;
import ru.bobcody.thirdPartyAPI.HotPies.SinglePie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PieService {
    @Autowired
    PiesProvider piesProvider;
    private static List<SinglePie> listPies = new ArrayList<>();
    private static final int counter = 10;
    private static int accumulate;

    static {
        refreshAccumulate();
    }

    @Scheduled(fixedDelayString = "PT20M")
    private void initList() throws IOException {
        if (!listPies.isEmpty()) listPies.clear();
        listPies.addAll(populate(counter));
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
            refreshAccumulate();
        }
        int r = new Random().nextInt(listPies.size());
        if (listPies.isEmpty()) {
            initList();
        }
        return listPies.get(r);
    }

    private static void refreshAccumulate() {
        accumulate = counter / 2;
    }
}
