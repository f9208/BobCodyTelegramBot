package ru.bobcody.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table
public class Chat implements Serializable {
    private static final long serialVersionUID = 5465777336968949952L;
    @Id
    private Long id;

    @Column(  nullable = false)
    private String type;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String userName;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    private Set<TextMessage> textMessages = new HashSet<>();


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
