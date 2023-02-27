package ru.bobcody.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class HelpReplyHandler extends AbstractHandler {

    private final String helpAnswer = "доступные команды:\n" +
            "  !help, !хелп, !помощь, /help, !команды, !старт, /start  - выводит это сообщение \n" +
            "  !п, !w, !g,  - погода на ближайшие сутки. По умолчанию (без указания города)\n" +
            "  выводится погода для Ижевска. короткий (4 записи с интервалом в 6 часов) вариант.\n" +
            "  !погода, !weather + название города  - все то же самое но более длинный вариант\n" +
            "  Сам прогноз тырится с OpenWeatherMap - делайте выводы.\n" +
            "  !город + название_города - сменить дефолтный город\n" +
            "  123, !123, !одиндватри - кидает рандомные фразы. \n" +
            "  !дц, !aq  - предварительное добавление цитат. Не знаю зачем - все равно уже давно никто аццки не жгет. капс-цитатки тоже добавляются в общую кучу через эту команду, их при модерации уже положат в нужную кучу\n" +
            "  !ц, !q, !цитата, !quote - кидает рандомную цитату из добавленных модератором.\n" +
            "  !капс, !caps, !к - капс-цитатки, нумерация отлична от обычных цитат\n" +
            "  !курс - курс валют\n" +
            "  !обс, !fga - советчик.\n" +
            "  пятница, !пятница, friday, !friday, !today, !дн, !dow - говорит, какой сегодня день недели. По пятницам кидает гифку с лосем.\n" +
            "  !сегодня, !дата, !время, !ща, !time, time,  !now - кидает текущую дату и время. да, это может показаться тупо кидать дату на запрос времени. но решено было сделать так. тебе ведь лень посмотреть в правый нижний угол экрана.\n" +
            "  !pie, pie, !пирожок - кидает рандомный пирожок\n" +
            "  ку, !ку, ку!, qu, !qu - бот поприветствует вас в ответ";

    @Override
    public List<String> getOrderList() {
        return directiveService.getHelp();
    }

    @Override
    protected String getResponseTextMessage(Message message) {
        return helpAnswer;
    }
}