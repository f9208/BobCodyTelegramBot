package ru.multibot.bobcody.controller.handlers.IRCChatHandlers.secondLayerHandler;

import com.sun.javafx.binding.StringFormatter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.multibot.bobcody.Services.HotPies.SinglePie;
import ru.multibot.bobcody.controller.handlers.IRCChatHandlers.SimpleHandlerInterface;

import java.io.*;
import java.text.ChoiceFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Component
@Getter
@Setter
public class PiesPoemHandler implements SimpleHandlerInterface {
    @Autowired
    List<SinglePie> piesList;

    public static void main(String[] args) {
//        String pattern = "\\b\\w+";
//        Pattern pat = Pattern.compile(pattern);
//        Matcher m;
//        int i = 0;
//        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Вася\\IdeaProjects\\bobcodyToIRC\\src\\main\\java\\ru\\multibot\\bobcody\\controller\\handlers\\IRCChatHandlers\\secondLayerHandler\\toex.txt"));) {
//            while (br.ready()) {
//                String sub = br.readLine();
//                m = pat.matcher(sub);
//                while (m.find()) {
//                    System.out.println(i++ + ": " + m.group(0));
//
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        MessageFormat mf = new MessageFormat("здесь будту даблы {0}, " +
                "а здесь стринги {1}");

        double[] a = {1, 2, 3, 4, 5};
        String[] s = {"один", "два", "three", "four", "five"};
        ChoiceFormat cf = new ChoiceFormat(a, s);

        int count = 2;
        String otherWord = "чота там бла бла бла ";
        Object[] o = {count, otherWord};

    }

    @Override
    public SendMessage handle(Message inputMessage) {
        SendMessage result = new SendMessage();
        String textMessage;
        Random r = new Random();
        int sizePiesOnPage = piesList.size();
        System.out.println(sizePiesOnPage);
        int rand = r.nextInt(sizePiesOnPage);
        System.out.println(rand);
        textMessage = piesList.get(rand).toString();
        result.setText(textMessage);
        return result;
    }

    @Override
    public List<String> getOrderList() {
        List<String> commands = new ArrayList<>();
        commands.add("!пирожок");
        commands.add("!pie");
        return commands;
    }
}
