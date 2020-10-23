package ru.multibot.bobcody.controller.SQL.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(schema = "public", name = "guests")
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
    @Getter
    @Setter
    @OneToMany(mappedBy = "author",
            cascade = CascadeType.ALL)
    List<Quote> quotes;

    public Guest(User user) {
        this.userID = Long.valueOf(user.getId());
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUserName();
        this.languageCode = user.getLanguageCode();

    }

    public Guest() {
    }

    @Override
    public String toString() {
        return new String(userName + " "
                + lastName + " "
                + firstName + " "
                + userID + " "
                + languageCode
        );
    }
    //  имя пользоваетял, фамилию ник можно поменять и тогда юзеры станут типа другими.
    // но айдишник - не поменяешь, поэтому он и будет вводной уникальности

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest)) return false;
        Guest guest = (Guest) o;
        return Objects.equals(getUserID(), guest.getUserID());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUserID());
    }
}
