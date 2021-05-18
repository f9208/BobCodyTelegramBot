package ru.bobcody.Entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(schema = "public", name = "logInputTextMessage")
public class InputMessage {
    @Id
    @Column(name = "messageId")
    long messageId;
    @Column(name = "dateTime")
    @CreationTimestamp
    LocalDateTime dateTime;
    @Column(name = "chatId")
    Long chatId;
    @Column(name = "textMessage", columnDefinition = "varchar(10000)")
    String textMessage;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    Guest guest;

    public InputMessage() {
    }

    public InputMessage(long messageId, LocalDateTime dateTime, Long chatId, String textMessage, Guest guest) {
        this.messageId = messageId;
        this.dateTime = dateTime;
        this.chatId = chatId;
        this.textMessage = textMessage;
        this.guest = guest;
    }
}
