## Telegram-бот на java

Бот изначально разрабатывался для одного конкретного чата под конкретных людей, а именно бывших обитателей одного
мертвого ныне IRC канала Ижевской сети. И в какой то мере повторят возможности и функции работавших на том канале ботов.

@BobCodyBot - имя бота в Telegram.
https://glacial-hollows-98092.herokuapp.com/ - веб морда с логами

#### Доступные команды:

- **!help, !хелп** - выводит список команд с описанием их работы.
- **!погода [!weather]** + называние города - показывает погоду в указанном городе, по умолчанию - Ижевск.
- **!п [!w, !g]** + название города - тоже показывает погоду в указанном городе но в более коротком варианте. По
  умолчанию - Ижевск.
- **!курс** - показывает текущий курс некоторых валют (по данным ЦБ РФ)
- **!обс, !fga** - дает очень хорошие и рандомные советы.
- **!ц [!q, !цитата, !quote]** - кидает рандомную цитату из ранее добавленных модератором. Большая часть цитат/капсов
  копилась годами и просто переехала из старой базы в новую. У каждой цитаты указывается Id и время добавления. По Id
  нему можно еще раз вызвать эту цитату:

> пример: !ц 75  
> Цитата №75 (662) added: 2009.09.14  
(16:54:37) brdk:а что там сейчас модно? я вот думаю питон чтоли поковырять  
(16:56:45) Stervec:руби, питон.  
(17:09:38) brdk:руби @ питонь

- **!дц, !aq** - предложить свою цитату. Ее проверит модератор.
- **!капс, !к, !caps** - кидает рандомную капс-цитату. Иногда попадаются смешные.
- **!дн, !dow, !пятница, !friday** - говорит какой сегодня день недели. По пятнциам кидает забавную гифку с лосем. Также
  закидывает эту же гифку в пятницу в 11:00МСК как напоминалку.
- **!время, !time, !ща, !now** - показывает сколько сейчас времени в некоторых определенных городах, в которых также
  есть пара пользователей чата. Команда была нужа прежде всего чтобы понимать сколько времени у них там, в загнивающем
  западе.
- **123**  - кидает рандомную фразочку.
- **!айди, !id** - кидает айдишник этого чата. Нужен чтобы можно было посмотреть логи в web.
- **!qu, !ку, qu, ку, ку!** - бот приветствует в ответ.
- бот отвечает на разные слапы:  bot, бот, бобби, bobcodybot, b0t

##### Стек технологий:

- Telegram-bot API
- Spring Boot, Spring Data
- Hibernate
- PostgreSQL
- JSP/JSTL
- Maven
- Ehcache
- JSON (jackson)
- сторонние API (через json)
- lombok
- Heroku
##### Внутренние описание работы в двух словах

Обновления из Telegram можно получить двумя путями - либо постоянный опрос, либо через вебхуки. Я решил сделать через
вебхуки: для этого необходимо указать url ресурса, куда будут отправляться обновления.
> из описания к API:  
> Каждый раз при получении обновления на этот адрес будет отправлен HTTPS POST с сериализованным в JSON объектом Update

В ответ он будет отправлять json с объектом PartialBotApiMethod (SendMessage, например) и кодом ответа 200 в случае
отсутствия каких то ошибок.

RestController мапится на "/", туда и прилетают наши Updates. Дальше летит в слой фасада, логируется и проверятся на
наличие текста, фоток, анимации и т.д.  
Если в сообщении был текст, то оно передается дальше в Resolver, где сохранятся в базу текст сообщеняи и данные
пользователя, update передается дальше в MainHandlerTextMessage. Он содержит мапу multiHandler со всеми реализациями
интерфейса обработчика команд SimpleHandlerInterface. Если находит подходящую - обрабатывает должным образом. Если
ничего не находит - то в ответ будет отправлено пустое сообщение.   


 