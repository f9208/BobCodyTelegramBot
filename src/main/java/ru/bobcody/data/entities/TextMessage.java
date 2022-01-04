package ru.bobcody.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Entity
@Data
@Table(schema = "public", name = "textMessage",
        indexes = {@Index(name = "date_chat_id", columnList = "dateTime, chat")})
@SequenceGenerator(name = "tm_id_seq",
        sequenceName = "text_message_id_seq",
        allocationSize = 1)
public class TextMessage implements Serializable {
    private static final long serialVersionUID = 86956734497234925L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tm_id_seq")
    private Long id;
    @Column(name = "telegramId")
    private Long telegram;
    @Column(name = "dateTime")
    @NotNull
    private LocalDateTime dateTime;
    @Column(name = "textMessage", columnDefinition = "varchar(50000)")
    private String text;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "guest", nullable = false, referencedColumnName = "id")
    private Guest guest;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "chat", nullable = false, referencedColumnName = "id")
    private Chat chat;

    public TextMessage(Message message) {
        this.telegram = Long.valueOf(message.getMessageId());
        this.dateTime = LocalDateTime.parse(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(message.getDate().longValue() * 1000)));
        this.text = message.getText();
        this.guest = new Guest(message.getFrom());
        this.chat = new Chat(message.getChat());
    }

    public TextMessage(SendMessage sendMessage, Guest guest, int messageId, Chat chat) {
        this.telegram = (long) (messageId + 1);
        this.dateTime = LocalDateTime.now();
        this.text = sendMessage.getText();
        this.guest = guest;
        this.chat = chat;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "id=" + id +
                ", telegram=" + telegram +
                ", dateTime=" + dateTime +
                ", textMessage='" + text +
                '}';
    }
}
