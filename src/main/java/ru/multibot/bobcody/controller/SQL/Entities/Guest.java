package ru.multibot.bobcody.controller.SQL.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.persistence.*;

@Entity
@Table(schema = "public", name = "list_users")
public class Guest {
    @Getter
    @Setter
    @Id
    @Column(name = "user_id")
    private Long userID;
    @Setter
    @Getter
    @Column(name = "first_name")
    private String firstName;
    @Setter
    @Getter
    @Column(name = "last_name")
    private String lastName;
    @Setter
    @Getter
    @Column(name = "user_name")
    private String userName;
    @Getter
    @Setter
    @Column(name = "language_code")
    private String languageCode;

    public Guest(User user) {
        this.userID = Long.valueOf(user.getId());
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUserName();
        this.languageCode=user.getLanguageCode();
    }

    public Guest() {
    }

    ;

    @Override
    public String toString() {
        return new String(userName + " " + lastName + " " + firstName);
    }
}
