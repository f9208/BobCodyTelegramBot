package ru.bobcody.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "public", name = "links",
        indexes = {@Index(name = "date_guest_idx", columnList = "date, guest_id")})
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @NotNull
    String path;
    @NotNull
    String name;
    Long size;
    @Column(name = "date")
    @NotNull
    LocalDateTime dateCreated;
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    Guest guest;
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    Chat chat;
    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT TRUE")
    boolean enabled;

    public Link(@NotNull String path, @NotNull String name, Long size, Guest guest, Chat chat) {
        this.path = path;
        this.name = name;
        this.size = size;
        this.guest = guest;
        this.chat = chat;
    }

    public Link(@NotNull String path, @NotNull String name) {
        this.path = path;
        this.name = name;
    }
}
