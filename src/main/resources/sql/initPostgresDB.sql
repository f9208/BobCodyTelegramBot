-- CREATE TABLE quotes
-- (
--     id        BIGINT  default nextval('quote_id_seq') PRIMARY KEY,
--     text      VARCHAR(5000) NOT NULL,
--     added     timestamp(3)  NOT NULL,
--     approved  timestamp(3),
--     type      varchar(10)   NOT NULL,
--     author_id BIGINT        NOT NULL,
--     endorsed  boolean default false,
--     caps_id   bigint  default 0,
--     regul_id  bigint  default 0,
--     foreign key (author_id) references guests (id) ON DELETE CASCADE
-- );
--
-- CREATE TABLE public.text_message
-- (
--     id           BIGINT default nextval(text_message_id_seq) PRIMARY KEY,
--     date_time    TIMESTAMP DEFAULT now() NOT NULL,
--     telegram_id  BIGINT,
--     text_message VARCHAR(50000),
--     chat         INTEGER,
--     guest_id     INTEGER,
--     FOREIGN KEY (chat) REFERENCES CHATS (id) ON DELETE CASCADE,
--     FOREIGN KEY (guest_id) REFERENCES GUESTS (id) ON DELETE CASCADE
-- );


alter table text_message
    alter id set default nextval('text_message_id_seq');
alter table quotes
    alter id set default nextval('quote_id_seq');
alter table links
    alter id set default nextval('link_id_seq');
insert into guests (id, first_name, language_code, last_name, user_name)
values (0, 'Bob', 'binary', 'Cody', 'BobCody');
drop sequence if exists caps_id_seq cascade;
drop sequence if exists regul_id_seq cascade;
CREATE SEQUENCE public.caps_id_seq AS BIGINT START WITH 1;
CREATE SEQUENCE public.regul_id_seq AS BIGINT START WITH 1;