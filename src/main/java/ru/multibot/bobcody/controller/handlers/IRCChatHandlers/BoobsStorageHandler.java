//package ru.multibot.bobcody.controller.handlers.IRCChatHandlers;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import ru.multibot.bobcody.controller.SQL.Entities.BoobsStorage;
//import ru.multibot.bobcody.controller.SQL.Servies.BoobsStorageServiceImp;
//
//import java.util.*;
//
//@Component
//@Setter
//@Getter
//public class BoobsStorageHandler {
////    @Autowired
////    BoobsStorageServiceImp boobsStorageServiceImp;
////
////    private String getById(Long id) throws NoSuchElementException {
////        String result = boobsStorageServiceImp.getById(id);
////        return result;
////    }
//
//    public void addBoobsLink(String link) {
//        BoobsStorage bs = new BoobsStorage();
//        bs.setLink(link);
//        boobsStorageServiceImp.add(bs);
//    }
//
//    private String getRandom() {
//        Random r = new Random();
//        List<BoobsStorage> boobsList=getDbAsList();
//        int k = r.nextInt(boobsList.size());
//
//        return boobsList.get(k).getLink();
//    }
//
//    private List<BoobsStorage> getDbAsList() {
//        List<BoobsStorage> result = new ArrayList<>();
//        Iterable<BoobsStorage> iterablyDB = boobsStorageServiceImp.getAllAsIterator();
//        for (BoobsStorage one : iterablyDB) {
//            result.add(one);
//        }
//        return result;
//    }
//
//    public String getAnyBoobs(String inputTextMessage) {
//
//
//
//
//        return getRandom();
//    }
//}
