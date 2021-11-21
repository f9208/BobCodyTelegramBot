package ru.bobcody.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(schema = "public", name = "links")
//todo index by dateCreated
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
    LocalDateTime dateCreated;
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    Guest guest;
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    Chat chat;
    @Column(columnDefinition = "boolean default true")
    boolean enabled;

    public Link() {
    }

    public Link(@NotNull String path, @NotNull String name, Long size, Guest guest, Chat chat) {
        this.path = path;
        this.name = name;
        this.size = size;
        this.guest = guest;
        this.chat = chat;
        this.enabled = true;
    }
}
