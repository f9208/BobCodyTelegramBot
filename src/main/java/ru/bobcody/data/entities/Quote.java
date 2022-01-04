package ru.bobcody.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(schema = "public", name = "quotes")
@SequenceGenerator(name = "quote_id",
        sequenceName = "quote_id_seq",
        allocationSize = 1)
public class Quote implements Serializable {
    private static final long serialVersionUID = 690854609845783434L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_id")
    private Long id;
    @NotNull
    @Column(name = "text", columnDefinition = "varchar(5000)", nullable = false)
    private String text;
    @NotNull
    @Column(name = "added", nullable = false)
    private LocalDateTime added;
    @Column(name = "approved")
    private LocalDateTime approved;
    @NotNull
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Guest author;
    @Column(name = "endorsed")
    private boolean endorsed;
    @Column(name = "caps_id")
    private long capsId;
    @Column(name = "regul_id")
    private long regularId;

    public Quote(Long id, @NotNull String text, @NotNull LocalDateTime added, LocalDateTime approved, @NotNull Type type, @NotNull Guest author, boolean endorsed, long capsId, long regularId) {
        this(id, text, added, type, author);
        this.approved = approved;
        this.endorsed = endorsed;
        this.capsId = capsId;
        this.regularId = regularId;
    }

    public Quote(@NotNull String text, @NotNull LocalDateTime added, @NotNull Type type, @NotNull Guest author) {
        this(text, type, author);
        this.added = added;
    }

    public Quote(Long id, @NotNull String text, @NotNull LocalDateTime added, @NotNull Type type, @NotNull Guest author) {
        this(text, added, type, author);
        this.id = id;
    }

    public Quote(@NotNull String text, @NotNull Type type, @NotNull Guest author) {
        this.text = text;
        this.type = type;
        this.author = author;
        this.added = LocalDateTime.now();
    }

    public Quote(Quote createCopy) {
        this(createCopy.getId(),
                createCopy.getText(),
                createCopy.getAdded(),
                createCopy.getApproved(),
                createCopy.getType(),
                createCopy.getAuthor(),
                createCopy.isEndorsed(),
                createCopy.getCapsId(),
                createCopy.getRegularId());
    }
}
