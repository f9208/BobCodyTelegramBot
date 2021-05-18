package ru.bobcody.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(schema = "public", name = "quotation_abyss")

//помойка из предложенных цитат, бездна, короче.
public class QuoteEntityAbyss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long quoteId;
    // цитаты больше 5000 символов будут вызывать ошибку
    @Column(columnDefinition = "varchar(5000)", name = "quote_text")
    String text;
    @Column(name = "date_added")
    Long date;
    @Column(name = "author_id")
    Long author;

    public QuoteEntityAbyss() {
    }

    public QuoteEntityAbyss(Long author, Long date, String text) {
        this.text = text;
        this.date = date;
        this.author = author;
    }



}

