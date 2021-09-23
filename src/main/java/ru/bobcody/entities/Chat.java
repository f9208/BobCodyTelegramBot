package ru.bobcody.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(schema = "public", name = "chats")
public class Chat {
    @Id
    private Long id;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "username")
    private String userName;
    @Getter
    @Setter
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<TextMessage> textMessages;

    public Chat() {
    }

    public Chat(Long id, String type, String firstName, String lastName, String userNam) {
        this.id = id;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userNam;
    }

    public Chat(org.telegram.telegrambots.meta.api.objects.Chat inputChat) {
        id = inputChat.getId();
        type = "type";
        firstName = inputChat.getFirstName();
        lastName = inputChat.getLastName();
        userName = inputChat.getUserName();
    }
}
