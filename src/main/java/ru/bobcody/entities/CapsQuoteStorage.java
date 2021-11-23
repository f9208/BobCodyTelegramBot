package ru.bobcody.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(schema = "public", name = "capsQuotationStorage")
@SequenceGenerator(name = "id_seq",
        sequenceName = "caps_quotation_storage_id_seq",
        allocationSize = 1)
@Data
public class CapsQuoteStorage {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
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
