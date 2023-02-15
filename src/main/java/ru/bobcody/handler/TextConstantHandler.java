package ru.bobcody.handler;

public class TextConstantHandler {


    /* Quote and Caps relative constants */
    public static final String CAPS_HAS_ADDED = "Капс добавлен за номером ";
    public static final String QUOTE_HAS_ADDED = "Цитата добавлена за номером ";
    public static final String WHAT_ADD = "чо добавить то?";
    public static final String ADD_TO_ABYSS = "Записал в бездну. Цитата будет добавлена в хранилище после проверки модератором.";
    public static final String TOO_LONG_TEXT = "Видимо, телеграмм отключил ограничение " +
            "в 4096 символов на сообщение и ты как то смог в так длинно." +
            " Но я не буду это сохранять, запиши себе в блокнот, потом поржешь";
    public static final String NO_SUCH_ID = "нет такого айди";
    public static final String HAS_APPROVED_AS_CAPS = "цитату за этим номером уже апрували как капс";
    public static final String HAS_APPROVED_AS_QUOTE = "цитату за этим номером уже апрували как обычную цитату";
    public static final String SAVE_FAILURE = "не удалось сохранить цитату";
    public static final String USE_NUMBERS = "цифры вводи.";
    public static final String WRITE_QUOTE = "ты цитату то введи";
    public static final String QUOTE_NOT_FOUND = "цитаты с таким id не найдено";
    public static final String ONLY_POSITIVE_NUMBERS = "в качестве номера цитаты используйте только натуральные числа";
    public static final String FOR_SEARCHING_TIP = "для поиска цитат используйте синтаксис: !q + номер_цитаты_цифрами";


    /* weather constants */
    public static final String CITY_NOT_FOUND = "? Где это? в Бельгии что-ли?";
    /* set city */
    public static final String YOUR_WEATHER_CITY = "%s, ваш дефолтный город - %s"; // имя пользователя, название города
    public static final String YOUR_WEATHER_CITY_UPDATED = "%s, теперь твой погодный город - %s";
    /* amd */
    public static final String AMD_SUCKS = "AMD сосет";
    public static final String AMD_FOREVER = "AMD форева!";
    /* day of week */
    public static final String MO_TU_WE_TODAY = "сегодня %s. работай давай!";
    public static final String THURSDAY_TODAY = "Сегодня - четверг. А четверг - маленькая пятница! а большая - завтра.";
    public static final String SUNDAY_TODAY = "че за вопросы? сегодня же %s! гуляй, рванина!";
    /* my chat id */
    public static final String CURRENT_CHAT_ID = "айдишник этого чата:  %d";
    /* pie */
    public static final String PIE_SERVICE_FAILURE = "че то с сервисом пирожков не то";


    private TextConstantHandler() {
    }
}
