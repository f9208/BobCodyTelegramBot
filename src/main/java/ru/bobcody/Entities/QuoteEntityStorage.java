package ru.bobcody.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(schema = "public", name = "quotation_storage")

//хранилище добавленных (аппрувленных) цитат. собственно, сам цитатник
public class QuoteEntityStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    Long id;
    @Column(name = "date_added")
    Long dateAdded;
    @Column(name = "date_approved")
    Long dateApproved;
    @Column(name = "quote_text", columnDefinition = "varchar(5000)")
    String text;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", nullable = false)
    Guest author;

    public QuoteEntityStorage() {
    }

    public QuoteEntityStorage(Guest author, Long dateAdded, Long dataApproved, String text) {
        this.dateAdded = dateAdded;
        this.text = text;
        this.author = author;
        this.dateApproved = dataApproved;
    }

}
