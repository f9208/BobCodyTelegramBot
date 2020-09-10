package ru.multibot.bobcody.controller.SQL.Entities;

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
