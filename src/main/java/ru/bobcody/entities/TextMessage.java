package ru.bobcody.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(schema = "public", name = "textMessage")
public class TextMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "telegramId")
    Long telegram;
    @Column(name = "dateTime")
    @NotNull
    private LocalDateTime dateTime;
    @Column(name = "textMessage", columnDefinition = "varchar(50000)")
    String textMessage;
    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "guest_id", nullable = false, referencedColumnName = "id")
    Guest guest;
    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "chat", nullable = false, referencedColumnName = "id")
    Chat chat;

    public TextMessage() {
    }

    public TextMessage(Message message) {
        this.id = 0L;
        this.telegram = Long.valueOf(message.getMessageId());
        this.dateTime = LocalDateTime.parse(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(message.getDate().longValue() * 1000)));
        this.textMessage = message.getText();
        this.guest = new Guest(message.getFrom());
        this.chat = new Chat(message.getChat());
    }

    public TextMessage(SendMessage sendMessage, Guest guest, int messageId, Chat chat) {
        this.id = 0L;
        this.telegram = Long.valueOf(messageId + 1);
        this.dateTime = LocalDateTime.now();
        this.textMessage = sendMessage.getText();
        this.guest = guest;
        this.chat = chat;
    }
}
