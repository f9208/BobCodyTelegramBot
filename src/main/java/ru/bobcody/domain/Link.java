package ru.bobcody.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table
@SequenceGenerator(name = "link_id_seq",
        sequenceName = "link_id_seq", initialValue = 100, allocationSize = 1)
public class Link implements Serializable {

    private static final long serialVersionUID = 445134875123798234L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_id_seq")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(1000)")
    private String path;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Guest guest;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Chat chat;

    @Column
    private boolean enabled;

    public Link(@NotNull String path, @NotNull String name, Long size, Guest guest, Chat chat, LocalDateTime createDate) {
        this.path = path;
        this.name = name;
        this.size = size;
        this.guest = guest;
        this.chat = chat;
        this.enabled = true;
        this.createDate = createDate;
    }

    public Link(@NotNull String path, @NotNull String name) {
        this.path = path;
        this.name = name;
        this.enabled = true;
    }
}
