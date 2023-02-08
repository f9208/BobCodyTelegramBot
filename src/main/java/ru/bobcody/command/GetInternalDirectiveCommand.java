package ru.bobcody.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bobcody.services.DirectiveService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetInternalDirectiveCommand extends AbstractCommand {
    @Autowired
    private DirectiveService directiveService;

    private final Message message;

    public GetInternalDirectiveCommand(Message message) {
        this.message = message;
    }

    @Override
    public String execute() {

        String[] singleWordArray = message.getText().split("[{^?!*+ .,$:;#%/|()]");
        Set<String> setUniqueWords = Arrays.stream(singleWordArray).collect(Collectors.toSet());

        List<String> commonList = Stream.of(directiveService.getAmd(),
                directiveService.getOldMan(),
                directiveService.getAmd()).flatMap(Collection::stream).collect(Collectors.toList());

        String foundInternalDirective = setUniqueWords
                .stream()
                .filter(commonList::contains)
                .findFirst()
                .orElseGet(() -> "");

        return foundInternalDirective;
    }
}
