package ru.bobcody.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "public", name = "capsQuotationStorage")
@Getter
@Setter
public class CapsQuoteStorage {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    @Id
    Long capsQuoteID;
    @Column(name = "caps_text", columnDefinition = "varchar(5000)")
    String capsQuoteText;
    @Column(name = "author")
    String author;
    @Column(name = "date_added")
    Long dateAdded;
    @Column(name = "date_approved")
    Long dateApproved;

    public CapsQuoteStorage() {
    }

    public CapsQuoteStorage(String capsQuoteText, String author, Long dateAdded, Long dateApproved) {
        this.capsQuoteText = capsQuoteText;
        this.author = author;
        this.dateAdded = dateAdded;
        this.dateApproved = dateApproved;
    }
}
