package ru.bobcody.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(schema = "public", name = "links",
        indexes = {@Index(name = "date_guest_idx", columnList = "date, guest_id")})
@SequenceGenerator(name = "link_id_seq",
        sequenceName = "link_id_seq", initialValue = 100, allocationSize = 1)
public class Link implements Serializable {
    private final static long serialVersionUID = 445134875123798234L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_id_seq")
    private Long id;
    @NotNull
    @Column(name = "path", columnDefinition = "varchar(1000)")
    private String path;
    @NotNull
    private String name;
    private Long size;
    @Column(name = "date")
    @NotNull
    private LocalDateTime dateCreated;
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Guest guest;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Chat chat;
    @Column
    private boolean enabled;

    public Link(@NotNull String path, @NotNull String name, Long size, Guest guest, Chat chat, LocalDateTime dateCreated) {
        this.path = path;
        this.name = name;
        this.size = size;
        this.guest = guest;
        this.chat = chat;
        this.enabled = true;
        this.dateCreated = dateCreated;
    }

    public Link(@NotNull String path, @NotNull String name) {
        this.path = path;
        this.name = name;
        this.enabled = true;
    }
}
