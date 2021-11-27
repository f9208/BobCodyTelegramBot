package ru.bobcody.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(schema = "public", name = "quotes")
@SequenceGenerator(name = "quote_id",
        sequenceName = "quote_id_seq",
        allocationSize = 1)
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_id")
    Long id;
    @NotNull
    @Column(name = "text", columnDefinition = "varchar(5000)", nullable = false)
    String text;
    @NotNull
    @Column(name = "added", nullable = false)
    LocalDateTime added;
    @Column(name = "approved")
    LocalDateTime approved;
    @NotNull
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    Type type;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    Guest author;
    @Column(name = "endorsed")
    boolean endorsed;
    @Column(name = "caps_id")
    long capsId;
    @Column(name = "regul_id")
    long regularId;

    public Quote(Long id, @NotNull String text, @NotNull LocalDateTime added, LocalDateTime approved, @NotNull Type type, @NotNull Guest author, boolean endorsed, long capsId, long regularId) {
        this(id, text, added, type, author);
        this.approved = approved;
        this.endorsed = endorsed;
        this.capsId = capsId;
        this.regularId = regularId;
    }

    public Quote(@NotNull String text, @NotNull LocalDateTime added, @NotNull Type type, @NotNull Guest author) {
        this.text = text;
        this.added = added;
        this.type = type;
        this.author = author;
        this.endorsed = false;
    }

    public Quote(Long id, @NotNull String text, @NotNull LocalDateTime added, @NotNull Type type, @NotNull Guest author) {
        this(text, added, type, author);
        this.id = id;
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
