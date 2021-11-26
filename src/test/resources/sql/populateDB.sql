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