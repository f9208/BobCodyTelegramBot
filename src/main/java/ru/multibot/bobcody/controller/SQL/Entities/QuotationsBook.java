package ru.multibot.bobcody.controller.SQL.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(schema = "public", name = "QUOTATION")
public class QuotationsBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long quotationId;
    @Column(name = "author")
    String author;
    @Column(name = "data")
    Integer data;
    @Column(name = "quotation_Text")
    String text;

    public QuotationsBook() {
    }

    public QuotationsBook(String author, Integer date, String text) {
        this.data=date;
        this.text=text;
        this.author=author;
    }

}
