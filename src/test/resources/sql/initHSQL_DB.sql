drop table if exists public.links;
drop table if exists public.text_message;
drop table if exists public.QUOTES;
drop table if exists public.chats;
drop table if exists public.guests cascade;
drop sequence if exists public.link_id_seq;
drop sequence if exists public.text_message_id_seq;
drop sequence if exists public.quote_id_seq;
drop sequence if exists public.caps_id_seq;
drop sequence if exists public.regul_id_seq;
CREATE TABLE public.chats
(
    id         INTEGER PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    type       VARCHAR(255),
    user_name  VARCHAR(255)
);


CREATE TABLE public.guests
(
    id            INTEGER PRIMARY KEY,
    first_name    VARCHAR(255),
    language_code VARCHAR(255),
    last_name     VARCHAR(255),
    user_name     VARCHAR(255),
    city_name     varchar(40) default 'Izhevsk'
);

CREATE SEQUENCE text_message_id_seq AS BIGINT START WITH 1;
CREATE TABLE public.text_message
(
    id           BIGINT GENERATED BY DEFAULT AS SEQUENCE text_message_id_seq PRIMARY KEY,
    date_time    TIMESTAMP DEFAULT now() NOT NULL,
    telegram_id  BIGINT,
    text_message VARCHAR(50000),
    chat         INTEGER,
    guest_id     INTEGER,
    FOREIGN KEY (chat) REFERENCES CHATS (id) ON DELETE CASCADE,
    FOREIGN KEY (guest_id) REFERENCES GUESTS (id) ON DELETE CASCADE
);
CREATE INDEX data_chat_id
    ON public.text_message (date_time, chat);

CREATE SEQUENCE public.link_id_seq AS BIGINT start with 1;
CREATE TABLE public.links
(
    id       BIGINT GENERATED BY DEFAULT AS SEQUENCE link_id_seq PRIMARY KEY,
    date     TIMESTAMP default now() NOT NULL,
    path     VARCHAR(1000)           NOT NULL,
    name     VARCHAR(255)            NOT NULL,
    size     BIGINT,
    chat_id  INTEGER,
    guest_id INTEGER                 NOT NULL,
    enabled  BOOLEAN   DEFAULT FALSE,
    FOREIGN KEY (chat_id) REFERENCES chats (id) on DELETE CASCADE,
    FOREIGN KEY (guest_id) REFERENCES guests (id) on DELETE CASCADE
);
CREATE INDEX date_guest_idx ON public.links (date, guest_id);

CREATE SEQUENCE quote_id_seq AS BIGINT START WITH 1;
CREATE TABLE quotes
(
    id        BIGINT GENERATED BY DEFAULT AS SEQUENCE quote_id_seq PRIMARY KEY,
    text      VARCHAR(5000) NOT NULL,
    added     timestamp(3)  NOT NULL,
    approved  timestamp(3),
    type      varchar(10)   NOT NULL,
    author_id BIGINT        NOT NULL,
    endorsed  boolean default false,
    caps_id   bigint  default 0,
    regul_id  bigint  default 0,
    foreign key (author_id) references guests (id) ON DELETE CASCADE
);

CREATE SEQUENCE caps_id_seq AS BIGINT START WITH 1;
CREATE SEQUENCE regul_id_seq AS BIGINT START WITH 1;
