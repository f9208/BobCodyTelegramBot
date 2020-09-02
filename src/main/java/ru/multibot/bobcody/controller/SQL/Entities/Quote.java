package ru.multibot.bobcody.controller.SQL.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(schema = "public", name = "quote_for_approval")


//помойка из предложенных цитат, бездна, короче.
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    // цитаты больше 5000 символов будут вызывать ошибку
    @Column(columnDefinition = "varchar(5000)")
    String text;
    @Column
    Integer data;
    @Column(name = "author_id")
    Long author;

    public Quote() {
    }

    public Quote(Long author, Integer data, String text) {
        this.text = text;
        this.data = data;
        this.author = author;
    }


}

