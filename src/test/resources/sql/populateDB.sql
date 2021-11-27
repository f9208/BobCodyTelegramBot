INSERT INTO public.guests (id, first_name, language_code, last_name, user_name)
VALUES (22222, 'Dmitry', 'ru', 'Batikov', 'bad'),
       (22223, 'Sergy', 'ru', 'Morozov', 'moroz');

INSERT INTO public.chats (id, first_name, last_name, type, user_name)
VALUES (11111, 'cars', null, 'private', 'privat_with_someone'),
       (11112, 'flowers and kitty', 'F and K', 'group', 'chat for housekeeper');

INSERT INTO public.text_message (id, date_time, telegram_id, text_message, chat, guest_id)
VALUES (444441, '2021-11-19 23:32:44', 33331, 'some text', 11112, 22222),
       (444442, '2021-11-20 22:12:10', 33332, 'another text message', 11112, 22223),
       (444443, '2021-11-21 23:22:32', 33333, 'private message', 11111, 22223),
       (444444, '2021-11-22 12:12:10', 33334, 'next text message', 11112, 22223);

INSERT INTO public.caps_quotation_storage(id, date_added, date_approved, caps_text, author_id)
VALUES (51111, 123123, 41242135, 'В ЧЕМ СИЛА? В НЬЮТОНАХ!', 22222),
       (51112, 1231233, 412442135, 'ЫЫЫ', 22223),
       (51113, 1232123, 41432135, 'WWW', 22222);

INSERT INTO public.quotes(id, text, added, type, author_id)
VALUES (1, 'цитата 1 в бездну', '2021-11-27 15:01:47', 'ABYSS', 22222),
       (2, 'обычная цитата 2', '2021-11-27 15:01:43', 'ABYSS', 22222);

INSERT INTO public.quotes(id, text, added, approved, type, author_id, endorsed, caps_id, regul_id)
VALUES (3, 'утвержденная обычная цитата', '2021-11-27 15:01:17', '2020-11-27 15:01:37', 'REGULAR', 22222, TRUE, 0, 1),
       (4, 'утвержденный капс', '2021-11-17 03:01:47', '2022-11-27 15:01:47', 'CAPS', 22222, TRUE, 1, 0);

call next value for caps_id_seq;
call next value for regul_id_seq;
-- ручками добавляем 4 цитаты - скипнем счетчки на 4
call next value for quote_id_seq;
call next value for quote_id_seq;
call next value for quote_id_seq;
call next value for quote_id_seq;
