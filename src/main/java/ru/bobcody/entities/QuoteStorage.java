package ru.bobcody.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(schema = "public", name = "quotation_storage")
@SequenceGenerator(name = "id_seq",
        sequenceName = "quotation_storage_id_seq",
        allocationSize = 1)
//хранилище добавленных (аппрувленных) цитат. собственно, сам цитатник
public class QuoteStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    Long id;
    @Column(name = "date_added")
    Long dateAdded;
    @Column(name = "date_approved")
    Long dateApproved;
    @Column(name = "quote_text", columnDefinition = "varchar(5000)")
    String text;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    Guest author;

    public QuoteStorage(Guest author, Long dateAdded, Long dataApproved, String text) {
        this.dateAdded = dateAdded;
        this.text = text;
        this.author = author;
        this.dateApproved = dataApproved;
    }
}
