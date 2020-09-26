package ru.multibot.bobcody.controller.SQL.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(schema = "public", name = "quotationBook")

//хранилище добавленных (аппрувленных) цитат. собственно, сам цитатник
public class QuoteStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    Long quotationId;
    @Column(name = "author")
    String author;
    @Column(name = "data")
    Integer data;
    @Column(name = "quote_text", columnDefinition = "varchar(5000)")
    String text;

    public QuoteStorage() {
    }

    public QuoteStorage(String author, Integer date, String text) {
        this.data = date;
        this.text = text;
        this.author = author;
    }

}
