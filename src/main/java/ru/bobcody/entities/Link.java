package ru.bobcody.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(schema = "public", name = "links",
        indexes = {@Index(name = "date_guest_idx", columnList = "date, guest_id")})
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    String path;
    @NotNull
    String name;
    Long size;
    @Column(name = "date")
    @NotNull
    LocalDateTime dateCreated;
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Guest guest;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Chat chat;
    @Column
    boolean enabled;

    public Link(@NotNull String path, @NotNull String name, Long size, Guest guest, Chat chat) {
        this.path = path;
        this.name = name;
        this.size = size;
        this.guest = guest;
        this.chat = chat;
        this.enabled = true;
    }

    public Link(@NotNull String path, @NotNull String name) {
        this.path = path;
        this.name = name;
        this.enabled = true;
    }
}
