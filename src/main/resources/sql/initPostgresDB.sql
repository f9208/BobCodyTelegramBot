CREATE SEQUENCE quote_id_seq AS BIGINT START WITH 1;
CREATE SEQUENCE caps_id_seq AS BIGINT START WITH 1;
CREATE SEQUENCE regul_id_seq AS BIGINT START WITH 1;
CREATE TABLE quotes
(
    id        BIGINT  default nextval('quote_id_seq') PRIMARY KEY,
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

-- поменять тип epoch на таймштапм. не факт что понадобится
-- ALTER TABLE quotation_abyss
--     ALTER COLUMN date_added SET DATA TYPE timestamp(2)
--         USING
--         timestamp without time zone 'epoch' + date_added * interval '1 second';
--
--
-- insert into quotes(text, added, approved, type, author_id, endorsed, caps_id, regul_id)
-- values (public.quotation_storage.quote_text, public.quotation_storage.added, public.quotation_storage.approved
--     'REGULAT', public.quotation_storage.author_id, true, 0, nextval('regul_id_seq'));