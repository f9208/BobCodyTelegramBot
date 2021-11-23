package ru.bobcody.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
@Table(schema = "public", name = "quotation_abyss")
@SequenceGenerator(name = "id_seq",
        sequenceName = "quotation_abyss_id_seq",
        allocationSize = 1)
public class QuoteAbyss {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    Long id;
    @Column(columnDefinition = "varchar(5000)", name = "quote_text")
    String text;
    @Column(name = "date_added")
    Long date;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    Guest author;

    public QuoteAbyss(Guest author, Long date, String text) {
        this.author = author;
        this.date = date;
        this.text = text;
    }
}

