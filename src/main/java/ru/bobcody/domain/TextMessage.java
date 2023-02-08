package ru.bobcody.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Data
@Table
@SequenceGenerator(name = "tm_id_seq",
        sequenceName = "text_message_id_seq",
        allocationSize = 1)
public class TextMessage implements Serializable {
    private static final long serialVersionUID = 86956734497234925L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tm_id_seq")
    private Long id;

    @Column(name = "telegramId")
    private Long telegramId;

    @Column(name = "dateTime")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "textMessage", columnDefinition = "varchar(50000)")
    private String text;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;


    public TextMessage(SendMessage sendMessage, Guest guest, int messageId, Chat chat) {
        this.telegramId = (long) (messageId + 1);
        this.dateTime = LocalDateTime.now();
        this.text = sendMessage.getText();
        this.guest = guest;
        this.chat = chat;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "id=" + id +
                ", telegram=" + telegramId +
                ", dateTime=" + dateTime +
                ", textMessage='" + text +
                '}';
    }
}
