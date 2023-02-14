package ru.bobcody.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
@SequenceGenerator(name = "quote_id",
        sequenceName = "quote_id_seq",
        allocationSize = 1)
public class Record implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_id")
    private Long id;

    @Column(columnDefinition = "varchar(5000)", nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column
    private LocalDateTime approvedDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RecordType type;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Guest author;

    @Column
    private Long particularId;
}
