package ru.bobcody.controller.updates.handlers.chathandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.AmdSucksHandler;
import ru.bobcody.controller.updates.handlers.chathandlers.secondlayerhandler.SlapHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.bobcody.utilits.CommonTextConstant.BABAY_LINKS;

@Component
public class FlyHandler {
    @Value("${slap.command}")
    private List<String> slapList;
    @Value("${amd.command}")
    private List<String> amdList;
    @Value("${oldMan.command}")
    private List<String> oldMan;
    @Autowired
    SlapHandler slapHandler;
    @Autowired
    AmdSucksHandler amdSucksHandler;

    public String findCommandInside(Message message) {
        String[] singleWordArray = message.getText().split("[{^?!*+ .,$:;#%/|()]");
        Set<String> setUniqWords = Arrays.stream(singleWordArray).collect(Collectors.toSet());
        boolean slapCall = setUniqWords.stream().anyMatch(slapList::contains);
        boolean amdCall = setUniqWords.stream().anyMatch(amdList::contains);
        boolean oldManCall = setUniqWords.stream().anyMatch(oldMan::contains);
        if (slapCall) return slapHandler.handle(message).getText();
        if (amdCall) return amdSucksHandler.handle(message).getText();
        if (oldManCall) return BABAY_LINKS;
        return "";
    }
}
