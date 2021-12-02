INSERT INTO public.guests (id, first_name, language_code, last_name, user_name, city_name)
VALUES (2, 'Dmitry', 'ru', 'Batikov', 'bad', default),
       (3, 'Sergy', 'ru', 'Morozov', 'moroz', 'Vorkuta'),
       (445682905, 'Jonny', 'ru', 'Oker', 'Admin', default);

INSERT INTO public.chats (id, first_name, last_name, type, user_name)
VALUES (1, 'cars', null, 'private', 'privat_with_someone'),
       (2, 'flowers and kitty', 'F and K', 'group', 'chat for housekeeper');

INSERT INTO public.text_message (id, date_time, telegram_id, text_message, chat, guest_id)
VALUES (1, '2021-11-19 23:32:44', 1, 'some text', 2, 2),
       (2, '2021-11-20 22:12:10', 2, 'another text message', 2, 3),
       (3, '2021-11-21 23:22:32', 3, 'private message', 1, 3),
       (4, '2021-11-22 12:12:10', 4, 'next text message', 2, 3);

INSERT INTO public.quotes(id, text, added, type, author_id)
VALUES (1, 'цитата 1 в бездну', '2021-11-27 15:01:47', 'ABYSS', 2),
       (2, 'обычная цитата 2', '2021-11-27 15:01:43', 'ABYSS', 2);

INSERT INTO public.quotes(id, text, added, approved, type, author_id, endorsed, caps_id, regul_id)
VALUES (3, 'утвержденная обычная цитата', '2021-11-27 15:01:17', '2020-11-27 15:01:37', 'REGULAR', 2, TRUE, 0, 1),
       (4, 'утвержденный капс', '2021-11-17 03:01:47', '2022-11-27 15:01:47', 'CAPS', 2, TRUE, 1, 0);

call next value for caps_id_seq;
call next value for regul_id_seq;
-- ручками добавляем 4 цитаты - скипнем счетчки на 4
call next value for quote_id_seq;
call next value for quote_id_seq;
call next value for quote_id_seq;
call next value for quote_id_seq;
call next value for text_message_id_seq;
call next value for text_message_id_seq;
call next value for text_message_id_seq;
call next value for text_message_id_seq;