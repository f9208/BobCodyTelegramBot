package ru.bobcody.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Data
@Table(schema = "public", name = "chats")
public class Chat implements Serializable {
    private static final long serialVersionUID = 5465777336968949952L;
    @Id
    private Long id;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_name")
    private String userName;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TextMessage> textMessages;

    public Chat(Long id, String type, String firstName, String lastName, String userName) {
        this.id = id;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public Chat(org.telegram.telegrambots.meta.api.objects.Chat inputChat) {
        id = inputChat.getId();
        type = inputChat.getType();
        firstName = inputChat.getFirstName();
        lastName = inputChat.getLastName();
        userName = inputChat.getUserName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return id.equals(chat.id) && Objects.equals(type, chat.type) && firstName.equals(chat.firstName) && Objects.equals(lastName, chat.lastName) && Objects.equals(userName, chat.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, firstName, lastName, userName);
    }
}
