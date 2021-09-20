package ru.bobcody.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(schema = "public", name = "quotation_abyss")
public class QuoteAbyss {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quote_id")
    Long id;
    @Column(columnDefinition = "varchar(5000)", name = "quote_text")
    String text;
    @Column(name = "date_added")
    Long date;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "guest_id", nullable = false)
    Guest author;

    public QuoteAbyss() {
    }

    public QuoteAbyss(Guest author, Long date, String text) {
        this.author = author;
        this.date = date;
        this.text = text;
    }
}

