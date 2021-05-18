package ru.bobcody.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BoobsStorage")
@Setter
@Getter
public class BoobsStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    Long id;

    @Column (name="link", columnDefinition = "varchar(5000)")
    String link;


}
//-- INSERT into quotation_book (author, dateAdded, quote_text)
//        --   Select
//        --     quote_for_approval.author_id,
//        --     quote_for_approval."dateAdded",
//        --      quote_for_approval."text"
//        --    from quote_for_approval
//        --    where (quote_for_approval.quoteId = 4);


//
//-- Замена идентификаторов ручками:
//        -- UPDATE quotation_book set quotation_id=3 WHERE quotation_id=4;
