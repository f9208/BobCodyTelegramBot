package ru.bobcody.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table
@SequenceGenerator(name = "quote_id",
        sequenceName = "quote_id_seq",
        allocationSize = 1)
public class Quote implements Serializable {

    private static final long serialVersionUID = 690854609845783434L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_id")
    private Long id;

    @Column(columnDefinition = "varchar(5000)", nullable = false)
    private String text;

    @Column( nullable = false)
    private LocalDateTime createDate;

    @Column
    private LocalDateTime approvedDate;

    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Guest author;

    @Column
    private boolean endorsed;

    @Column
    private long capsId;

    @Column
    private long regularId;

    public Quote(Long id, @NotNull String text, @NotNull LocalDateTime createDate, LocalDateTime approvedDate, @NotNull Type type, @NotNull Guest author, boolean endorsed, long capsId, long regularId) {
        this(id, text, createDate, type, author);
        this.approvedDate = approvedDate;
        this.endorsed = endorsed;
        this.capsId = capsId;
        this.regularId = regularId;
    }

    public Quote(@NotNull String text, @NotNull LocalDateTime createDate, @NotNull Type type, @NotNull Guest author) {
        this(text, type, author);
        this.createDate = createDate;
    }

    public Quote(Long id, @NotNull String text, @NotNull LocalDateTime createDate, @NotNull Type type, @NotNull Guest author) {
        this(text, createDate, type, author);
        this.id = id;
    }

    public Quote(@NotNull String text, @NotNull Type type, @NotNull Guest author) {
        this.text = text;
        this.type = type;
        this.author = author;
        this.createDate = LocalDateTime.now();
    }

    public Quote(Quote createCopy) {
        this(createCopy.getId(),
                createCopy.getText(),
                createCopy.getCreateDate(),
                createCopy.getApprovedDate(),
                createCopy.getType(),
                createCopy.getAuthor(),
                createCopy.isEndorsed(),
                createCopy.getCapsId(),
                createCopy.getRegularId());
    }
}
