package ru.multibot.bobcody.SQL.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(schema = "public", name = "quotation_storage")

//хранилище добавленных (аппрувленных) цитат. собственно, сам цитатник
public class QuoteInsideStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    Long quoteId;
    @Column(name = "author_id")
    String author;
    @Column(name = "date_added")
    Long dateAdded;
    @Column(name = "date_approved")
    Long dateApproved;
    @Column(name = "quote_text", columnDefinition = "varchar(5000)")
    String text;

    public QuoteInsideStorage() {
    }

    public QuoteInsideStorage(String author, Long dateAdded, Long dateApproved, String text) {
        this.dateAdded = dateAdded;
        this.text = text;
        this.author = author;
        this.dateApproved = dateApproved;
    }

}
