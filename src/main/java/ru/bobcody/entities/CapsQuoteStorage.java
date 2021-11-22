package ru.bobcody.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@NoArgsConstructor
@Entity
@Table(schema = "public", name = "capsQuotationStorage")
@Getter
@Setter
public class CapsQuoteStorage {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial", name="id")
    @Id
    Long id;
    @Column(name = "caps_text", columnDefinition = "varchar(5000)")
    String capsQuoteText;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", nullable = false)
    Guest author;
    @Column(name = "date_added")
    Long dateAdded;
    @Column(name = "date_approved")
    Long dateApproved;

    public CapsQuoteStorage(String capsQuoteText, Guest author, Long dateAdded, Long dateApproved) {
        this.capsQuoteText = capsQuoteText;
        this.author = author;
        this.dateAdded = dateAdded;
        this.dateApproved = dateApproved;
    }
}
