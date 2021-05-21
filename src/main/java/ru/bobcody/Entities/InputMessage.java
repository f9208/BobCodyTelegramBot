package ru.bobcody.Entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(schema = "public", name = "inputTextMessage")
public class InputMessage {
    @Id
    long id;
    @Column(name = "dateTime")
    @CreationTimestamp
    @NotNull
    LocalDateTime dateTime;
    @Column(name = "chatId")
    Long chatId;
    @Column(name = "textMessage", columnDefinition = "varchar(10000)")
    String textMessage;
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "guest_id", nullable = false, referencedColumnName = "id")
    Guest guest;

    public InputMessage() {
    }

    public InputMessage(Message message) {
        this.id = message.getMessageId();
        this.dateTime = LocalDateTime.parse(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(message.getDate().longValue() * 1000)));
        this.chatId = message.getChatId();
        this.textMessage = message.getText();
        this.guest = new Guest(message.getFrom());
    }
}
