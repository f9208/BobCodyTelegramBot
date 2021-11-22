package ru.bobcody.controller.handlers.chatHandlers.secondLayerHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.services.CapsQuoteStorageService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * данный класс - просто артефакт ручного добавления (восстановления) цитат из файла баз Вербы.
 * оставил его на память и на всякий случай
 */
@Deprecated
public class HandAddingHandler {
    @Autowired
    CapsQuoteStorageService capsQuoteStorageServiceImp;

    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                Map<Long, String> mapForHandAdd = getCAPSasMap();
                //здесь надо смержить мапу цитаток и мапу капсов
                for (Object entryObj : mapForHandAdd.entrySet()) {
                    Map.Entry<Long, String> singleEntry = (Map.Entry<Long, String>) entryObj;
                    capsQuoteStorageServiceImp.manualAdd(singleEntry.getKey(), singleEntry.getValue());
                    System.out.println(i++);
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
        result.setText("напечатал");
        return result;
    }

    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        return commands;
    }


    private String getFileAsString(InputStream inputStream, Charset charset) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
        if (inputStreamReader == null) return null;
        BufferedReader br = new BufferedReader(inputStreamReader);
        if (br == null) return null;
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    private InputStream getFileAsInputStream(String in) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(in);
        return inputStream;
    }

    private Map<Long, String> getVerbsTextAsMap() {
        String oneBigString = new String();
        Map<Long, String> listWIthTimeStampAsKey = new TreeMap<>();

        try {// файлик из ресурсов
            oneBigString = getFileAsString(getFileAsInputStream("verba_cut"), Charset.forName("CP1251"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        String regexWithDataTime = "\\(\\d*\\,\\'([\\wА-я,.\\s\\\\\")!&^~><??@*+$…{}—_&\u200E«#»:(ёЁ `\\-\\[\\]%\\/|?=;]*)\\'\\,\\'([.,\\s\\d*]*|NICO|я)\\',\\d*,\\d*,'([\\d-\\s:]*)";
        Pattern pattern = Pattern.compile(regexWithDataTime);
        Matcher matcher = pattern.matcher(oneBigString);
        while (matcher.find()) {
            String forAdd = matcher.group(1).replace("\\n", "\n").replace("\\r", "\r");
            listWIthTimeStampAsKey.put((Long.valueOf(stringToData(matcher.group(3)).toEpochSecond(ZoneOffset.UTC))), forAdd);

        }
        return listWIthTimeStampAsKey;
    }

    private LocalDateTime stringToData(String inputString) {
        LocalDateTime result = LocalDateTime.parse(inputString, DateTimeFormatter.ofPattern("y-M-d H:m:s"));
        return result;

    }

    public Map<Long, String> getCAPSasMap() {
        String oneBigString = new String();
        Map<Long, String> listWIthTimeStampAsKey = new TreeMap<>();

        try {// файлик из ресурсов
            oneBigString = getFileAsString(getFileAsInputStream("caps_cut"), Charset.forName("UTF-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(oneBigString);
        String lookCapsRegEx = "'','([<>А-я\\s\\wЁё\\,.\\-+]*)\\'\\,\\'([<>А-я\\s:\\wЁё\\,.\\-|\\\\&\"\\/?~!]*)','([\\d-\\s:]*)";
        Pattern pattern = Pattern.compile(lookCapsRegEx);
        Matcher matcher = pattern.matcher(oneBigString);
        StringBuilder temporaly;
        while (matcher.find()) {
            temporaly = new StringBuilder();
            temporaly.append(matcher.group(1)).append("\n\n")
                    .append(matcher.group(2));
            listWIthTimeStampAsKey.put((Long.valueOf(stringToData(matcher.group(3)).toEpochSecond(ZoneOffset.UTC))), temporaly.toString());

        }

        Map<Long, String> mapForHandAdd = listWIthTimeStampAsKey;
        for (Object entryObj : mapForHandAdd.entrySet()) {
            Map.Entry<Long, String> singleEntry = (Map.Entry<Long, String>) entryObj;
            System.out.println(singleEntry.getKey());
            System.out.println(singleEntry.getValue());
        }
        System.out.println("добавили капсы");

        return listWIthTimeStampAsKey;
    }
}
